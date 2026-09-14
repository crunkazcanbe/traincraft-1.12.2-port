package train.common.tracks;

/**
 * Small local energy accumulator used by BlockEnergyTrack.
 *
 * This is a Traincraft-owned replacement for what used to be a (never actually
 * implemented in this codebase - "ChargeHandler" didn't exist anywhere in the source
 * tree, it was only ever referenced) integration with Railcraft's old
 * mods.railcraft.api.electricity.IElectricGrid. Current Railcraft (1.12.2,
 * 12.1.0-beta-8) replaced that whole electricity system with its own internal
 * "Charge" network (mods.railcraft.api.charge.*), which - like TrackKit - is deeply
 * tied to Railcraft's own block/tile hosting and isn't meant to be implemented by
 * third-party blocks directly.
 *
 * BlockEnergyTrack already implements CoFH's IEnergyHandler (a simple, standard RF
 * interface) which is what actually gets used for transferring power to Traincraft's
 * electric locomotives, so this class only needs to be a plain double-based energy
 * store - it does not need to participate in any wider grid/network protocol.
 */
public class ChargeHandler {

	public enum ConnectType {
		TRACK
	}

	private final Object owner;
	private final ConnectType connectType;
	private double charge;

	public ChargeHandler(Object owner, ConnectType connectType) {
		this.owner = owner;
		this.connectType = connectType;
	}

	public double getCharge() {
		return charge;
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}

	public void addCharge(double amount) {
		this.charge += amount;
	}

	public void removeCharge(double amount) {
		this.charge = Math.max(0, this.charge - amount);
	}
}
