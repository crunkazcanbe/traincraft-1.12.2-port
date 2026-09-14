package train.common.tracks;

import net.minecraftforge.fml.common.FMLLog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TrackRegistry {
	private static final Map<String, TrackSpec> registryByTag = new HashMap<>();
	private static final Map<Integer, TrackSpec> registryById = new HashMap<>();

	public static void registerTrackSpec(TrackSpec trackSpec) {
		registryByTag.put(trackSpec.getTrackTag(), trackSpec);
		registryById.put((int) trackSpec.getTrackId(), trackSpec);
		FMLLog.info("[Traincraft] Registered track spec: %s", trackSpec.getTrackTag());
	}

	public static TrackSpec getTrackSpec(String tag) {
		return registryByTag.get(tag);
	}

	public static TrackSpec getTrackSpec(int trackId) {
		return registryById.get(trackId);
	}

	public static Collection<TrackSpec> getTrackSpecs() {
		return registryByTag.values();
	}
}
