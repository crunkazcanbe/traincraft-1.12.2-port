package net.minecraftforge.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * A minimal Wavefront .obj model loader + immediate-mode renderer.
 *
 * This restores the behaviour of the old 1.7.10 Forge {@code AdvancedModelLoader}/{@code WavefrontObject}
 * API that Traincraft's TileEntitySpecialRenderers rely on. The old Forge classes were removed in 1.8+,
 * and the previous port shipped a no-op stub for {@link IModelCustom} (renderAll/renderPart did nothing),
 * which is why all TC track TESRs drew zero geometry (tracks were invisible).
 *
 * The .obj files used by Traincraft are simple: vertices (v), texture coords (vt), grouped faces (g / f),
 * quads in {@code v/vt} form, no normals. We still parse the general case (v//vn, v/vt/vn, triangles,
 * n-gons, negative indices) defensively, and compute a per-face normal so OpenGL lighting in the world
 * looks correct.
 */
@SideOnly(Side.CLIENT)
public class WavefrontObject implements IModelCustom {

    private final List<float[]> vertices = new ArrayList<float[]>();
    private final List<float[]> texCoords = new ArrayList<float[]>();
    private final List<float[]> normals = new ArrayList<float[]>();
    private final List<Group> groups = new ArrayList<Group>();

    private static class Face {
        int[] v;   // 0-based vertex indices
        int[] vt;  // 0-based texcoord indices, or null
        int[] vn;  // 0-based normal indices, or null
    }

    private static class Group {
        final String name;
        final List<Face> faces = new ArrayList<Face>();
        Group(String name) { this.name = name; }
    }

    private final ResourceLocation location;
    private boolean loaded = false;

    public WavefrontObject(ResourceLocation location) {
        // Store only; the actual .obj is parsed lazily on first render (see ensureLoaded()). Parsing
        // is deferred because these objects are created in static initializers during client setup,
        // which can run before the mod's resource pack is fully available. Deferring to first render
        // guarantees the resource manager is ready.
        this.location = location;
    }

    private synchronized void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        Group current = new Group("default");
        groups.add(current);
        InputStream stream = null;
        try {
            stream = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.charAt(0) == '#') {
                    continue;
                }
                if (line.startsWith("v ")) {
                    vertices.add(parseFloats(line.substring(2), 3));
                } else if (line.startsWith("vt ")) {
                    texCoords.add(parseFloats(line.substring(3), 2));
                } else if (line.startsWith("vn ")) {
                    normals.add(parseFloats(line.substring(3), 3));
                } else if (line.startsWith("g ") || line.startsWith("o ")) {
                    String name = line.substring(2).trim();
                    // Start a fresh group only if the current one already has faces, so a leading
                    // "g name" before any faces just renames the (empty) default group.
                    if (current.faces.isEmpty() && groups.size() == 1) {
                        groups.remove(current);
                    }
                    current = new Group(name);
                    groups.add(current);
                } else if (line.startsWith("f ")) {
                    current.faces.add(parseFace(line.substring(2)));
                }
            }
        } catch (Exception e) {
            // Leave whatever was parsed; a failed load just renders nothing rather than crashing.
            System.err.println("[Traincraft] Failed to load OBJ model " + location + ": " + e);
        } finally {
            if (stream != null) {
                try { stream.close(); } catch (IOException ignored) {}
            }
        }
    }

    private static float[] parseFloats(String s, int count) {
        String[] tok = s.trim().split("\\s+");
        float[] out = new float[count];
        for (int i = 0; i < count && i < tok.length; i++) {
            out[i] = Float.parseFloat(tok[i]);
        }
        return out;
    }

    private Face parseFace(String s) {
        String[] tok = s.trim().split("\\s+");
        Face f = new Face();
        f.v = new int[tok.length];
        int[] vt = new int[tok.length];
        int[] vn = new int[tok.length];
        boolean hasVt = false;
        boolean hasVn = false;
        for (int i = 0; i < tok.length; i++) {
            String[] parts = tok[i].split("/");
            f.v[i] = resolveIndex(parts[0], vertices.size());
            if (parts.length >= 2 && !parts[1].isEmpty()) {
                vt[i] = resolveIndex(parts[1], texCoords.size());
                hasVt = true;
            }
            if (parts.length >= 3 && !parts[2].isEmpty()) {
                vn[i] = resolveIndex(parts[2], normals.size());
                hasVn = true;
            }
        }
        f.vt = hasVt ? vt : null;
        f.vn = hasVn ? vn : null;
        return f;
    }

    /** OBJ indices are 1-based; negative values count back from the end. */
    private static int resolveIndex(String s, int size) {
        int idx = Integer.parseInt(s.trim());
        return idx < 0 ? size + idx : idx - 1;
    }

    @Override
    public void renderAll() {
        ensureLoaded();
        for (Group g : groups) {
            renderGroup(g);
        }
    }

    @Override
    public void renderPart(String partName) {
        ensureLoaded();
        for (Group g : groups) {
            if (g.name.equalsIgnoreCase(partName)) {
                renderGroup(g);
            }
        }
    }

    private void renderGroup(Group g) {
        for (Face face : g.faces) {
            int n = face.v.length;
            GL11.glBegin(n == 3 ? GL11.GL_TRIANGLES : (n == 4 ? GL11.GL_QUADS : GL11.GL_POLYGON));
            if (face.vn == null) {
                float[] normal = computeNormal(face);
                GL11.glNormal3f(normal[0], normal[1], normal[2]);
            }
            for (int i = 0; i < n; i++) {
                if (face.vn != null) {
                    float[] nrm = normals.get(face.vn[i]);
                    GL11.glNormal3f(nrm[0], nrm[1], nrm[2]);
                }
                if (face.vt != null) {
                    float[] uv = texCoords.get(face.vt[i]);
                    // OBJ V axis is bottom-up; OpenGL texture V is top-down.
                    GL11.glTexCoord2f(uv[0], 1.0f - uv[1]);
                }
                float[] vert = vertices.get(face.v[i]);
                GL11.glVertex3f(vert[0], vert[1], vert[2]);
            }
            GL11.glEnd();
        }
    }

    private float[] computeNormal(Face face) {
        if (face.v.length < 3) {
            return new float[] { 0f, 1f, 0f };
        }
        float[] a = vertices.get(face.v[0]);
        float[] b = vertices.get(face.v[1]);
        float[] c = vertices.get(face.v[2]);
        float ux = b[0] - a[0], uy = b[1] - a[1], uz = b[2] - a[2];
        float vx = c[0] - a[0], vy = c[1] - a[1], vz = c[2] - a[2];
        float nx = uy * vz - uz * vy;
        float ny = uz * vx - ux * vz;
        float nz = ux * vy - uy * vx;
        float len = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        if (len < 1.0e-6f) {
            return new float[] { 0f, 1f, 0f };
        }
        return new float[] { nx / len, ny / len, nz / len };
    }
}
