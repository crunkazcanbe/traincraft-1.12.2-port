package train.common.tracks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Locale;

/**
 * Each type of track has a single instance of TrackSpec that corresponds with it.
 * Each track block in the world has an ITrackInstance that corresponds with it.
 *
 * @see TrackRegistry
 * @see ITrackInstance
 */
public final class TrackSpec {

	/** Set by train.common.blocks.TCBlocks once the host block is registered. */
	public static Block blockTrack;

	private final String tag;
	private final short trackId;
	private final List<String> tooltip;
	private final ITrackItemIconProvider iconProvider;
	private final Class<? extends ITrackInstance> instanceClass;

	public TrackSpec(short trackId, String tag, ITrackItemIconProvider iconProvider, Class<? extends ITrackInstance> instanceClass) {
		this(trackId, tag, iconProvider, instanceClass, null);
	}

	public TrackSpec(short trackId, String tag, ITrackItemIconProvider iconProvider, Class<? extends ITrackInstance> instanceClass, List<String> tooltip) {
		this.trackId = trackId;
		this.tag = tag.toLowerCase(Locale.ENGLISH);
		this.iconProvider = iconProvider;
		this.instanceClass = instanceClass;
		this.tooltip = tooltip;
	}

	public String getTrackTag() {
		return tag;
	}

	public short getTrackId() {
		return trackId;
	}

	/**
	 * This function will only work after the Init Phase.
	 *
	 * @return an ItemStack that can be used to place the track.
	 */
	public ItemStack getItem() {
		return getItem(1);
	}

	public ItemStack getItem(int qty) {
		if (blockTrack != null) {
			ItemStack stack = new ItemStack(blockTrack, qty, 0);
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("track", tag);
			stack.setTagCompound(nbt);
			return stack;
		}
		return ItemStack.EMPTY;
	}

	public ITrackInstance createInstanceFromSpec() {
		try {
			return instanceClass.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException("Improper Track Instance Constructor for " + tag, ex);
		}
	}

	public TextureAtlasSprite getItemIcon() {
		if (iconProvider == null)
			return null;
		return iconProvider.getTrackItemIcon(this);
	}

	public List<String> getItemToolTip() {
		return tooltip;
	}

	@Override
	public String toString() {
		return "Track -> " + getTrackTag();
	}
}
