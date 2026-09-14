package train.common.tracks;

import net.minecraft.entity.item.EntityMinecart;

/**
 * Any rail tile entity that can completely halt all cart movement should implement
 * this interface (used in collision handling).
 */
public interface ITrackLockdown extends ITrackInstance {

	boolean isCartLockedDown(EntityMinecart cart);

	void releaseCart();
}
