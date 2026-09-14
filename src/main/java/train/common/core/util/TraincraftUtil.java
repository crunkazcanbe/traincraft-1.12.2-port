package train.common.core.util;

import net.minecraft.block.BlockRailBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import train.common.api.EntityRollingStock;
import train.common.api.Locomotive;


public class TraincraftUtil{

    public static Item getItemFromName(String name){
        net.minecraft.util.ResourceLocation rl = new net.minecraft.util.ResourceLocation(name);
        if (Item.REGISTRY.containsKey(rl)){
            return Item.REGISTRY.getObject(rl);
        } else {
            return null;
        }
    }

    public static ItemStack getItemFromUnlocalizedName(String itemName, int meta){
        Item item = getItemFromName(itemName);
        if(item != null){
            return new ItemStack(item, 1, meta);
        }
        return null;
    }

    public static byte getByteFromColor(String c){
        if(c.equals("Black")){
            return 0;
        } else if (c.equals("Red")){
            return 1;
        } else if(c.equals("Green")){
            return 2;
        } else if(c.equals("Brown")){
            return 3;
        } else if(c.equals("Blue")){
            return 4;
        } else if(c.equals("Purple")){
            return 5;
        } else if(c.equals("Cyan")){
            return 6;
        } else if(c.equals("LightGrey")){
            return 7;
        } else if(c.equals("Grey")){
            return 8;
        } else if(c.equals("Magenta")){
            return 13;
        } else if(c.equals("Lime")){
            return 10;
        } else if(c.equals("Yellow")){
            return 11;
        } else if(c.equals("LightBlue")){
            return 12;
        } else if(c.equals("Pink")){
            return 9;
        } else if(c.equals("Orange")){
            return 14;
        } else if(c.equals("White")){
            return 15;
        } else if(c.equals("Full")){
            return 101;
        } else if (c.equals("Empty")){
            return 100;
        }
        return 0;
    }

    public static byte[] getBytesFromColors(String[] c){
        byte[] ret = new byte[c.length];
        for(int i=0; i<c.length;i++){
            ret[i]=getByteFromColor(c[i]);
        }
        return ret;
    }

    public static boolean itemStackMatches(ItemStack item1, ItemStack item2){
    	return (item1.getItem() == item2.getItem()) && 
    			(item1.getItemDamage() == item2.getItemDamage() 
    				|| item1.getItemDamage() == OreDictionary.WILDCARD_VALUE
    				|| item2.getItemDamage() == OreDictionary.WILDCARD_VALUE);
    }

    public static boolean isRailBlockAt(World world, int x, int y, int z){
        return world.getBlockState(new net.minecraft.util.math.BlockPos(x,y,z)).getBlock() instanceof BlockRailBase;
    }

    public static final double degrees = (180d / Math.PI);
    public static final float radian = (float)(Math.PI / 180.0D);
    public static void updateRider(EntityRollingStock transport, double distance, double yOffset) {
        if(transport.getPassengers().isEmpty()){return;}
        net.minecraft.entity.Entity rider = transport.getPassengers().get(0);
        String cn = transport.getClass().getSimpleName();
        // Per-model, LIVE-tunable via ~/.config/dogpound/train-render.conf. Falls back to the
        // caller's hardcoded numbers so an untuned loco keeps its existing seat distance.
        //   seatFwd_<Class>  = blocks forward toward the cab (defaults to `distance`)
        //   seatSide_<Class> = sideways nudge (default 0)
        //   seatUp_<Class>   = raise/lower in the cab (defaults to `yOffset`)
        double dist = EntityRollingStock.seatConf("seatFwd_"  + cn, (float) distance);
        double side = EntityRollingStock.seatConf("seatSide_" + cn, 0.0F);
        double up   = EntityRollingStock.seatConf("seatUp_"   + cn, (float) yOffset);

        // THE FIX: seat rotation must use the SAME basis the renderer draws the model with, or the
        // seat and the visible train diverge differently on each track direction. The renderer
        // draws at glRotatef(90 - basis). Loco basis = serverRealRotation (the value that drives
        // the render and is synced to BOTH sides); wagon basis = rotationYaw. Using one synced
        // basis also removes the old client(renderYaw) vs server(serverRealRotation) split and the
        // wagon +180/+90 mismatch that made every direction land somewhere different.
        float basis = (transport instanceof Locomotive) ? transport.serverRealRotation : transport.rotationYaw;
        double th = Math.toRadians(90.0 - basis);
        double c = Math.cos(th), s = Math.sin(th);
        // World offset = GL Y-rotation of local (dist forward, side sideways) by (90 - basis).
        double dx =  dist * c + side * s;
        double dz = -dist * s + side * c;

        // Vertical: mount offset + tunable up, plus rise along inclines (pitch).
        float pitchRads = (transport.side.isServer() ? transport.serverRealPitch * 60F : transport.anglePitchClient) * radian;
        double y = transport.posY + transport.getMountedYOffset() + rider.getYOffset() + up
                 + (Math.tan(pitchRads) * dist);
        rider.setPosition(transport.posX + dx, y, transport.posZ + dz);
        // [TC-RIDE-DEBUG] where does the rider actually end up vs the train?
        if (transport.ticksExisted % 20 == 0) {
            System.out.println("[TC-RIDE] side=" + (transport.world.isRemote ? "CLIENT" : "SERVER")
                + " trainY=" + String.format("%.3f", transport.posY)
                + " trainZ=" + String.format("%.3f", transport.posZ)
                + " riderY=" + String.format("%.3f", rider.posY)
                + " riderZ=" + String.format("%.3f", rider.posZ)
                + " mountY=" + String.format("%.3f", transport.getMountedYOffset())
                + " riderYOff=" + String.format("%.3f", rider.getYOffset())
                + " dist=" + String.format("%.3f", dist)
                + " basis=" + String.format("%.1f", basis)
                + " pitchTerm=" + String.format("%.3f", (Math.tan(pitchRads) * dist)));
        }
    }

    public static float atan2f(double x, double z) {
        float pi =-3.141592653f;
        float multiplier = 1.0f;

        if (z < 0.0d) {
            if (x < 0.0d) {
                z = -z;
                x = -x;
            } else {
                z = -z;
                multiplier = -1.0f;
            }

        } else {
            if (x < 0.0d) {
                x = -x;
                multiplier = -1.0f;
            }

            pi = 0.0f;
        }

        double invDiv = 1.0D / (((z < x) ? x : z) * (1.0D / (ATAN2_SQRT - 1)));
        return (atan2[(int)(x * invDiv) * ATAN2_SQRT + (int)(z * invDiv)] + pi) * multiplier;
    }

    public static float atan2degreesf(double x, double y){
        return atan2f(x,y)*degreesF;
    }

    private static final int ATAN2_SQRT = (int) Math.sqrt(1024);
    private static final float[] atan2 = new float[1024];
    static {
        for (int i = 0; i < ATAN2_SQRT; i++) {
            for (int j = 0; j < ATAN2_SQRT; j++) {
                atan2[j * ATAN2_SQRT + i] = (float) Math.atan2((float) j / ATAN2_SQRT, (float) i / ATAN2_SQRT);
            }
        }
    }

    public static final float degreesF = (float) (180.0d / Math.PI);


}
