package train.common.items;

import com.mojang.authlib.GameProfile;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.items.IMinecartItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.api.*;
import train.common.core.handlers.ConfigHandler;
import train.common.core.util.TraincraftUtil;
import train.common.entity.rollingStock.EntityTracksBuilder;
import train.common.items.ItemTCRail.TrackTypes;
import train.common.library.BlockIDs;
import train.common.library.EnumTrains;
import train.common.tile.TileTCRail;
import train.common.tile.TileTCRailGag;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRollingStock extends ItemMinecart {

	private String iconName = "";
	private String trainName;

	public ItemRollingStock(String iconName) {
		super(EntityMinecart.Type.RIDEABLE);
		this.iconName = iconName;
		maxStackSize = 1;
		trainName = this.getTranslationKey();
		setCreativeTab(Traincraft.tcTab);
	}

	public static ItemStack setPersistentData(@Nullable ItemStack oldStack, @Nullable AbstractTrains train, @Nullable Integer trainID, @Nullable EntityPlayer player) {
		ItemStack stack = oldStack;
		if (train != null) {
			for (EnumTrains trains : EnumTrains.values()) {
				if (trains.getEntityClass().equals(train.getClass())) {
					stack = (new ItemStack(trains.getItem()));
					break;
				}
			}
		}
		if (stack != null) {
			NBTTagCompound tag = stack.getTagCompound();
			if (tag == null) {
				tag = new NBTTagCompound();
			}
			if (train != null) {
				tag.setString("puuid", train.getPersistentUUID());
				tag.setString("trainCreator", player == null ? train.getEntityData().getString("theCreator") : player.getDisplayName().getUnformattedText());
				if (train.getEntityData().hasKey("theOwner")) {
					tag.setString("theOwner", train.getEntityData().getString("theOwner"));
				}
				if (train.getEntityData().hasKey("color")) {
					tag.setInteger("trainColor", train.getEntityData().getInteger("color"));
				}
			} else {
				tag.setString("trainCreator", player != null ? player.getDisplayName().getUnformattedText() : "Creative");
			}
			tag.setInteger("uniqueID", trainID == null ? AbstractTrains.uniqueIDs++ : trainID);
			stack.setTagCompound(tag);
		} else {
			return null;
		}
		return stack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, @Nullable World worldIn, List<String> par3List, ITooltipFlag flagIn) {
		if (par1ItemStack.hasTagCompound()) {
			NBTTagCompound var5 = par1ItemStack.getTagCompound();
			if (var5.hasKey("trainCreator")) {
				par3List.add("§7" + "Creator: " + var5.getString("trainCreator"));
			}
			if (var5.hasKey("trainOwner")) {
				par3List.add("§7" + "Owner: " + var5.getString("trainOwner"));
			}
			if (var5.hasKey("treinColor")) {
				par3List.add("§7" + "Color: " + AbstractTrains.getColorAsString(var5.getInteger("trainColor")));
			}
		}
		double mass = getMass();
		int power = getMHP();
		int maxSpeed = getMaxSpeed();
		if (getTrainType().length() > 0) {
			par3List.add("§7" + "Type: " + getTrainType());
		}
		if (power > 0) {
			par3List.add("§7" + "Power: " + power + " Mhp");
		}
		if (mass != 0) {
			par3List.add("§7" + "Mass: " + (mass * 10));
		}
		if (maxSpeed > 0) {
			par3List.add("§7" + "Max Speed: " + maxSpeed);
		}
		if (getCargoCapacity() > 0) {
			par3List.add("§7" + "Slots: " + getCargoCapacity());
		}
		if (getAdditionnalInfo() != null) {
			par3List.add("§7" + getAdditionnalInfo());
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.RARE;
	}

	public String getTrainType() {
		for (EnumTrains trains : EnumTrains.values()) {
			if (trains.getItem() == this) return trains.getTrainType();
		}
		return "";
	}

	public double getMass() {
		for (EnumTrains trains : EnumTrains.values()) {
			if (trains.getItem() == this) return trains.getMass();
		}
		return 0;
	}

	public int getMaxSpeed() {
		for (EnumTrains trains : EnumTrains.values()) {
			if (trains.getItem() == this) return trains.getMaxSpeed();
		}
		return 0;
	}

	public int getMHP() {
		for (EnumTrains trains : EnumTrains.values()) {
			if (trains.getItem() == this) return trains.getMHP();
		}
		return 0;
	}

	public String getAdditionnalInfo() {
		for (EnumTrains trains : EnumTrains.values()) {
			if (trains.getItem() == this) return trains.getAdditionnalTooltip();
		}
		return null;
	}

	public int getCargoCapacity() {
		for (EnumTrains trains : EnumTrains.values()) {
			if (trains.getItem() == this) return trains.getCargoCapacity();
		}
		return 0;
	}

	public String getTrainName() {
		return trainName;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer par2EntityPlayer, World par3World, BlockPos pos, EnumHand hand, EnumFacing facing, float par8, float par9, float par10) {
		ItemStack par1ItemStack = par2EntityPlayer.getHeldItem(hand);
		int par4 = pos.getX(), par5 = pos.getY(), par6 = pos.getZ();
		IBlockState state = par3World.getBlockState(pos);
		int meta = state.getBlock().getMetaFromState(state);
		TileEntity tileentity = par3World.getTileEntity(pos);
		if (par3World.isRemote) {
			return EnumActionResult.PASS;
		}
		if (tileentity instanceof TileTCRail) {
			TileTCRail tile = (TileTCRail) tileentity;
			if (ItemTCRail.isTCStraightTrack(tile)
					|| tile.getType().equals(TrackTypes.SMALL_ROAD_CROSSING.getLabel())
					|| tile.getType().equals(TrackTypes.SMALL_ROAD_CROSSING_1.getLabel())
					|| tile.getType().equals(TrackTypes.SMALL_ROAD_CROSSING_2.getLabel())) {
				this.placeCart(par2EntityPlayer, par1ItemStack, par3World, par4, par5, par6);
				return EnumActionResult.SUCCESS;
			}
			par2EntityPlayer.sendMessage(new TextComponentString("Place me on a straight piece of track !"));
			return EnumActionResult.PASS;
		} else if (tileentity instanceof TileTCRailGag) {
			TileTCRailGag tileGag = (TileTCRailGag) tileentity;
			TileTCRail tile = (TileTCRail) par3World.getTileEntity(new BlockPos(tileGag.originX, tileGag.originY, tileGag.originZ));
			if (tile != null && ItemTCRail.isTCStraightTrack(tile)) {
				this.placeCart(par2EntityPlayer, par1ItemStack, par3World, par4, par5, par6);
				return EnumActionResult.SUCCESS;
			}
			par2EntityPlayer.sendMessage(new TextComponentString("Place me on a straight piece of track !"));
			return EnumActionResult.PASS;
		} else if (TraincraftUtil.isRailBlockAt(par3World, par4, par5, par6) && (meta < 2 || meta > 5)) {
			this.placeCart(par2EntityPlayer, par1ItemStack, par3World, par4, par5, par6);
			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.PASS;
		}
	}

	private Block getBlock(World world, int x, int y, int z) {
		return world.getBlockState(new BlockPos(x, y, z)).getBlock();
	}

	private boolean isRailAt(World world, int x, int y, int z) {
		return BlockRailBase.isRailBlock(world.getBlockState(new BlockPos(x, y, z)));
	}

	public EntityMinecart placeCart(EntityPlayer player, ItemStack itemstack, World world, int i, int j, int k) {
		EntityRollingStock rollingStock = null;
		for (EnumTrains train : EnumTrains.values()) {
			if (train.getItem() == itemstack.getItem()) {
				rollingStock = (EntityRollingStock) train.getEntity(world, i + 0.5F, j + 0.2F, k + 0.5F);
				if (train.getColors() != null && rollingStock != null) {
					rollingStock.setColor(train.getColors()[0]);
				}
				break;
			}
		}
		if (rollingStock != null) {
			if (!world.isRemote) {
				if ((rollingStock instanceof SteamTrain && !ConfigHandler.ENABLE_STEAM)
						|| (rollingStock instanceof ElectricTrain && !ConfigHandler.ENABLE_ELECTRIC)
						|| (rollingStock instanceof DieselTrain && !ConfigHandler.ENABLE_DIESEL)
						|| (rollingStock instanceof EntityTracksBuilder && !ConfigHandler.ENABLE_BUILDER)
						|| (rollingStock instanceof Tender && !ConfigHandler.ENABLE_TENDER)) {
					if (player != null) player.sendMessage(new TextComponentString("This type of train has been deactivated by the OP"));
					rollingStock.setDead();
					return rollingStock;
				}

				int dir = 0;
				IBlockState blockState = world.getBlockState(new BlockPos(i, j, k));
				int meta = blockState.getBlock().getMetaFromState(blockState);
				if (player != null)
					dir = MathHelper.floor((player.rotationYaw * 4F) / 360F + 0.5D) & 3;

				if (dir == 2) {
					rollingStock.rotationYaw = 0;
					rollingStock.serverRealRotation = (meta == 0) ? -90 : 180;
					if (getBlock(world, i, j, k) == BlockIDs.tcRail.block || getBlock(world, i, j, k) == BlockIDs.tcRailGag.block) {
						rollingStock.rotationYaw = (meta == 0 || meta == 2) ? 90 : 0;
					}
					if (rollingStock instanceof Locomotive) {
						if ((getBlock(world, i, j, k - 1) == BlockIDs.tcRail.block || getBlock(world, i, j, k - 1) == BlockIDs.tcRailGag.block || isRailAt(world, i, j, k - 1))
								&& (getBlock(world, i, j, k - 2) == BlockIDs.tcRail.block || getBlock(world, i, j, k - 2) == BlockIDs.tcRailGag.block || isRailAt(world, i, j, k - 2))) {
							if (meta == 0 || meta == 2) rollingStock.serverRealRotation = -90;
						} else {
							player.sendMessage(new TextComponentString("Place me on a straight piece of track!"));
							rollingStock.setDead();
							return rollingStock;
						}
					}
				}
				if (dir == 1) {
					rollingStock.rotationYaw = 90;
					rollingStock.serverRealRotation = (meta == 1) ? 180 : -90;
					if (getBlock(world, i, j, k) == BlockIDs.tcRail.block || getBlock(world, i, j, k) == BlockIDs.tcRailGag.block) {
						rollingStock.rotationYaw = (meta == 1 || meta == 3) ? 0 : 90;
					}
					if (rollingStock instanceof Locomotive) {
						if ((getBlock(world, i - 1, j, k) == BlockIDs.tcRail.block || getBlock(world, i - 1, j, k) == BlockIDs.tcRailGag.block || isRailAt(world, i - 1, j, k))
								&& (getBlock(world, i - 2, j, k) == BlockIDs.tcRail.block || getBlock(world, i - 2, j, k) == BlockIDs.tcRailGag.block || isRailAt(world, i - 2, j, k))) {
							if (meta == 1 || meta == 3) rollingStock.serverRealRotation = 180;
						} else {
							player.sendMessage(new TextComponentString("Place me on a straight piece of track!"));
							rollingStock.setDead();
							return rollingStock;
						}
					}
				}
				if (dir == 0) {
					rollingStock.rotationYaw = -178.5F;
					rollingStock.serverRealRotation = (meta == 0) ? 90 : 0;
					if (getBlock(world, i, j, k) == BlockIDs.tcRail.block || getBlock(world, i, j, k) == BlockIDs.tcRailGag.block) {
						rollingStock.rotationYaw = (meta == 0 || meta == 2) ? -90 : 178.5F;
					}
					if (rollingStock instanceof Locomotive) {
						if ((getBlock(world, i, j, k + 1) == BlockIDs.tcRail.block || getBlock(world, i, j, k + 1) == BlockIDs.tcRailGag.block || isRailAt(world, i, j, k + 1))
								&& (getBlock(world, i, j, k + 2) == BlockIDs.tcRail.block || getBlock(world, i, j, k + 2) == BlockIDs.tcRailGag.block || isRailAt(world, i, j, k + 2))) {
							if (meta == 0 || meta == 2) rollingStock.serverRealRotation = 90;
						} else {
							if (player != null) {
								player.sendMessage(new TextComponentString("Place me on a straight piece of track!"));
								rollingStock.setDead();
								return rollingStock;
							} else {
								if (meta == 0 || meta == 2) rollingStock.serverRealRotation = 90;
							}
						}
					}
				}
				if (dir == 3) {
					rollingStock.rotationYaw = 178.5F;
					rollingStock.serverRealRotation = (meta == 1) ? 0 : 90;
					if (getBlock(world, i, j, k) == BlockIDs.tcRail.block || getBlock(world, i, j, k) == BlockIDs.tcRailGag.block) {
						rollingStock.rotationYaw = (meta == 1 || meta == 3) ? 178.5F : 90;
					}
					if (rollingStock instanceof Locomotive) {
						if ((getBlock(world, i + 1, j, k) == BlockIDs.tcRail.block || getBlock(world, i + 1, j, k) == BlockIDs.tcRailGag.block || isRailAt(world, i + 1, j, k))
								&& (getBlock(world, i + 2, j, k) == BlockIDs.tcRail.block || getBlock(world, i + 2, j, k) == BlockIDs.tcRailGag.block || isRailAt(world, i + 2, j, k))) {
							if (meta == 1 || meta == 3) rollingStock.serverRealRotation = 0;
						} else {
							player.sendMessage(new TextComponentString("Place me on a straight piece of track!"));
							rollingStock.setDead();
							return rollingStock;
						}
					}
				}

				rollingStock.trainType = ((ItemRollingStock) itemstack.getItem()).getTrainType();
				rollingStock.trainName = itemstack.getItem().getItemStackDisplayName(itemstack);
				if (player != null) {
					rollingStock.trainOwner = player.getDisplayName().getUnformattedText();
				}
				rollingStock.mass = getMass();

				int uniID = -1;
				if (itemstack.hasTagCompound()) {
					NBTTagCompound var5 = itemstack.getTagCompound();
					uniID = var5.getInteger("uniqueID");
					if (uniID != -1) rollingStock.uniqueID = uniID;
					if (uniID != -1) rollingStock.getEntityData().setInteger("uniqueID", uniID);
					if (var5.hasKey("trainColor")) rollingStock.setColor(var5.getInteger("trainColor"));
					rollingStock.trainCreator = var5.getString("trainCreator");
				}
				if (itemstack.hasTagCompound()) {
					String owner;
					if (itemstack.getTagCompound().hasKey("theOwner")) {
						owner = itemstack.getTagCompound().getString("theOwner");
					} else if (player != null) {
						owner = player.getDisplayName().getUnformattedText();
					} else {
						owner = "";
					}
					rollingStock.setInformation(
							((ItemRollingStock) itemstack.getItem()).getTrainType(),
							owner,
							itemstack.getTagCompound().getString("theCreator"),
							itemstack.getItem().getItemStackDisplayName(itemstack),
							uniID);
					if (itemstack.getTagCompound().hasKey("color")) rollingStock.setColor(itemstack.getTagCompound().getInteger("color"));
					if (itemstack.getTagCompound().hasKey("puuid")) rollingStock.getEntityData().setString("puuid", itemstack.getTagCompound().getString("puuid"));
				} else if (player != null) {
					rollingStock.setInformation(((ItemRollingStock) itemstack.getItem()).getTrainType(), player.getDisplayName().getUnformattedText(), "Creative", itemstack.getItem().getItemStackDisplayName(itemstack), uniID);
				} else {
					rollingStock.setInformation(((ItemRollingStock) itemstack.getItem()).getTrainType(), "", "Creative", itemstack.getItem().getItemStackDisplayName(itemstack), uniID);
				}

				if (ConfigHandler.SHOW_POSSIBLE_COLORS && rollingStock.acceptedColors != null && rollingStock.acceptedColors.size() > 0) {
					String concatColors = ": ";
					for (int t = 0; t < rollingStock.acceptedColors.size(); t++) {
						if (!AbstractTrains.getColorAsString(rollingStock.acceptedColors.get(t)).equals("Empty")
								&& !AbstractTrains.getColorAsString(rollingStock.acceptedColors.get(t)).equals("Full"))
							concatColors = concatColors.concat(AbstractTrains.getColorAsString(rollingStock.acceptedColors.get(t)) + ", ");
					}
					if (concatColors.length() > 4 && player != null) {
						player.sendMessage(new TextComponentString("Possible colors" + concatColors));
						player.sendMessage(new TextComponentString("To paint, click me with the right (vanilla) dye"));
					}
				}
				world.spawnEntity(rollingStock);
			}
			itemstack.shrink(1);
		}
		return rollingStock;
	}

	public boolean canBePlacedByNonPlayer(ItemStack cart) {
		return true;
	}

	public EntityMinecart placeCart(GameProfile owner, ItemStack cart, World world, BlockPos pos) {
		return placeCart((EntityPlayer) null, cart, world, pos.getX(), pos.getY(), pos.getZ());
	}

	public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
		return false;
	}
}
