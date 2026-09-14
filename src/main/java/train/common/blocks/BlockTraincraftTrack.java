package train.common.blocks;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.tile.TileEntityTraincraftTrack;
import train.common.tracks.ITrackInstance;
import train.common.tracks.ITrackLockdown;
import train.common.tracks.ITrackPowered;
import train.common.tracks.ITrackReversable;

import javax.annotation.Nullable;

/**
 * Generic host block for all of Traincraft's "outfitted track" types (see
 * train.common.library.Tracks / train.common.tracks package-info for the full story).
 *
 * Which specific track behavior a given placed block has is NOT encoded in metadata
 * (metadata is reserved for the vanilla rail SHAPE/POWERED bits, exactly like a vanilla
 * powered/detector rail) - it's encoded as a string tag in the TileEntity, read from the
 * placed ItemStack's NBT (see TrackSpec#getItem).
 */
public class BlockTraincraftTrack extends BlockRailBase {

	public static final PropertyEnum<EnumRailDirection> SHAPE = PropertyEnum.create("shape", EnumRailDirection.class, new Predicate<EnumRailDirection>() {
		@Override
		public boolean apply(@Nullable EnumRailDirection input) {
			return input != EnumRailDirection.NORTH_EAST && input != EnumRailDirection.NORTH_WEST
					&& input != EnumRailDirection.SOUTH_EAST && input != EnumRailDirection.SOUTH_WEST;
		}
	});
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockTraincraftTrack() {
		super(true);
		// ponytail: hidden from creative tab — this is an unfinished internal track with no
		// model (rendered pink/black). Real track is tcRail. Re-add tab if it ever gets modeled.
		setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, EnumRailDirection.NORTH_SOUTH).withProperty(POWERED, Boolean.FALSE));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE, POWERED);
	}

	@Override
	public IProperty<EnumRailDirection> getShapeProperty() {
		return SHAPE;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(SHAPE, EnumRailDirection.byMetadata(meta & 7)).withProperty(POWERED, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(SHAPE).getMetadata();
		if (state.getValue(POWERED)) i |= 8;
		return i;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTraincraftTrack();
	}

	@Nullable
	private TileEntityTraincraftTrack getTrack(IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		return te instanceof TileEntityTraincraftTrack ? (TileEntityTraincraftTrack) te : null;
	}

	@Nullable
	private ITrackInstance getInstance(IBlockAccess world, BlockPos pos) {
		TileEntityTraincraftTrack te = getTrack(world, pos);
		return te != null ? te.getTrackInstance() : null;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		TileEntityTraincraftTrack te = getTrack(world, pos);
		if (te != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("track")) {
			te.initFromTag(stack.getTagCompound().getString("track"));
			ITrackInstance instance = te.getTrackInstance();
			if (instance != null) {
				instance.onBlockPlacedBy(placer);
			}
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		ITrackInstance instance = getInstance(world, pos);
		if (instance != null) {
			if (!world.isRemote) {
				for (ItemStack drop : instance.getDrops(0)) {
					spawnAsEntity(world, pos, drop);
				}
			}
			instance.onBlockRemoved();
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public int quantityDropped(java.util.Random random) {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ITrackInstance instance = getInstance(world, pos);
		return instance != null && instance.blockActivated(player);
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos) {
		ITrackInstance instance = getInstance(world, pos);
		if (instance != null) {
			instance.onMinecartPass(cart);
		}
	}

	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos) {
		ITrackInstance instance = getInstance(world, pos);
		return instance != null ? instance.getRailMaxSpeed(cart) : 0.4f;
	}

	@Override
	public boolean isFlexibleRail(IBlockAccess world, BlockPos pos) {
		ITrackInstance instance = getInstance(world, pos);
		return instance != null ? instance.isFlexibleRail() : !this.isPowered;
	}

	@Override
	public boolean canMakeSlopes(IBlockAccess world, BlockPos pos) {
		ITrackInstance instance = getInstance(world, pos);
		return instance != null ? instance.canMakeSlopes() : true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos) {
		super.neighborChanged(state, world, pos, neighborBlock, fromPos);
		if (world.isRemote) return;
		ITrackInstance instance = getInstance(world, pos);
		if (instance != null) {
			instance.onNeighborBlockChange(neighborBlock);
			updatePoweredState(world, pos, state, instance);
		}
	}

	@Override
	protected void updateState(IBlockState state, World world, BlockPos pos, Block blockIn) {
		ITrackInstance instance = getInstance(world, pos);
		if (instance != null) {
			updatePoweredState(world, pos, state, instance);
		}
	}

	private void updatePoweredState(World world, BlockPos pos, IBlockState state, ITrackInstance instance) {
		if (!(instance instanceof ITrackPowered)) return;
		ITrackPowered powered = (ITrackPowered) instance;
		boolean isPowered = world.isBlockPowered(pos);
		if (isPowered != powered.isPowered()) {
			powered.setPowered(isPowered);
			world.setBlockState(pos, state.withProperty(POWERED, isPowered), 3);
			world.notifyNeighborsOfStateChange(pos, this, false);
			world.notifyNeighborsOfStateChange(pos.down(), this, false);
			if (state.getValue(SHAPE).isAscending()) {
				world.notifyNeighborsOfStateChange(pos.up(), this, false);
			}
			TileEntityTraincraftTrack te = getTrack(world, pos);
			if (te != null) te.sendUpdateToClient();
		}
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		ITrackInstance instance = getInstance(world, pos);
		if (instance != null && instance instanceof ITrackLockdown && ((ITrackLockdown) instance).isCartLockedDown(null)) {
			return false;
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
		ITrackInstance instance = getInstance(world, pos);
		if (instance instanceof ITrackReversable) {
			((ITrackReversable) instance).setReversed(!((ITrackReversable) instance).isReversed());
			TileEntityTraincraftTrack te = getTrack(world, pos);
			if (te != null) te.sendUpdateToClient();
			return true;
		}
		return super.rotateBlock(world, pos, axis);
	}
}
