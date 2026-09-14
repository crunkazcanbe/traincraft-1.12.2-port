/*******************************************************************************
 * Copyright (c) 2012 Mrbrutal. All rights reserved.
 *
 * @name TrainCraft
 * @author Mrbrutal
 ******************************************************************************/

package train.common.items;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import train.common.Traincraft;
import train.common.library.ItemIDs;

public class ItemTCCompositeSuit extends ItemTCArmor {
	public ItemTCCompositeSuit(String iconName, ArmorMaterial material, int par3, int par4, int color) {
		super(iconName, material, par3, par4, color);
		setCreativeTab(Traincraft.tcTab);
		this.color = color;
		Traincraft.proxy.registerEvent(this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (stack.getItem() == ItemIDs.helmet_suit_paintable.item || stack.getItem() == ItemIDs.jacket_suit_paintable.item || stack.getItem() == ItemIDs.boots_suit_paintable.item) {
			if (type != null) return train.common.library.Info.resourceLocation + ":" + train.common.library.Info.armorPrefix + "composite_suit_2.png";
			return train.common.library.Info.resourceLocation + ":" + train.common.library.Info.armorPrefix + "composite_suit_1.png";
		} else if (stack.getItem() == ItemIDs.pants_suit_paintable.item) {
			if (type != null) return train.common.library.Info.resourceLocation + ":" + train.common.library.Info.armorPrefix + "composite_suit_pants_2.png";
			return train.common.library.Info.resourceLocation + ":" + train.common.library.Info.armorPrefix + "composite_suit_pants_1.png";
		} else {
			return super.getArmorTexture(stack, entity, slot, type);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.EPIC;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		super.onArmorTick(world, player, stack);
		updateTicks++;
		ItemStack armorHelmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		if (!armorHelmet.isEmpty() && armorHelmet.getItem() instanceof ItemTCCompositeSuit) {
			ItemTCCompositeSuit itemarmor = (ItemTCCompositeSuit) armorHelmet.getItem();
			if (itemarmor.getArmorMaterial() == Traincraft.instance.armorCompositeSuit) {
				if (player.getActivePotionEffect(MobEffects.POISON) != null) {
					if (armorHelmet.getMaxDamage() - armorHelmet.getItemDamage() > 5) {
						player.removePotionEffect(MobEffects.POISON);
						armorHelmet.damageItem(5, player);
					} else {
						armorHelmet.damageItem(armorHelmet.getMaxDamage() - armorHelmet.getItemDamage(), player);
					}
				}
				if (player.getActivePotionEffect(MobEffects.WITHER) != null) {
					if (armorHelmet.getMaxDamage() - armorHelmet.getItemDamage() > 5) {
						player.removePotionEffect(MobEffects.WITHER);
						armorHelmet.damageItem(5, player);
					} else {
						armorHelmet.damageItem(armorHelmet.getMaxDamage() - armorHelmet.getItemDamage(), player);
					}
				}
				if (player.getActivePotionEffect(MobEffects.BLINDNESS) != null) {
					if (armorHelmet.getMaxDamage() - armorHelmet.getItemDamage() > 5) {
						player.removePotionEffect(MobEffects.BLINDNESS);
						armorHelmet.damageItem(5, player);
					} else {
						armorHelmet.damageItem(armorHelmet.getMaxDamage() - armorHelmet.getItemDamage(), player);
					}
				}
				if (player.getActivePotionEffect(MobEffects.NAUSEA) != null) {
					if (armorHelmet.getMaxDamage() - armorHelmet.getItemDamage() > 5) {
						player.removePotionEffect(MobEffects.NAUSEA);
						armorHelmet.damageItem(5, player);
					} else {
						armorHelmet.damageItem(armorHelmet.getMaxDamage() - armorHelmet.getItemDamage(), player);
					}
				}
				if (player.isInWater() && player.getActivePotionEffect(MobEffects.WATER_BREATHING) == null) {
					if (armorHelmet.getMaxDamage() - armorHelmet.getItemDamage() > 1) {
						player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 200, 0));
						armorHelmet.damageItem(1, player);
					} else {
						armorHelmet.damageItem(armorHelmet.getMaxDamage() - armorHelmet.getItemDamage(), player);
					}
				}
				int eyeX = (int) player.posX, eyeY = (int) (player.posY + player.getEyeHeight()), eyeZ = (int) player.posZ;
				BlockPos eyePos = new BlockPos(eyeX, eyeY, eyeZ);
				if (!world.isRemote && world.getLight(eyePos) <= 4 && (world.isAirBlock(eyePos) || world.containsAnyLiquid(player.getEntityBoundingBox()))) {
					if (armorHelmet.getMaxDamage() - armorHelmet.getItemDamage() > 1) {
						if (player.getActivePotionEffect(MobEffects.NIGHT_VISION) == null || player.getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration() < 220) {
							player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 820, 0, true, true));
							armorHelmet.damageItem(1, player);
						}
					} else {
						armorHelmet.damageItem(armorHelmet.getMaxDamage() - armorHelmet.getItemDamage(), player);
					}
				}
			}
		}
		ItemStack armorChest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		if (!armorChest.isEmpty() && armorChest.getItem() instanceof ItemTCCompositeSuit) {
			ItemTCCompositeSuit itemarmor = (ItemTCCompositeSuit) armorChest.getItem();
			if (itemarmor.getArmorMaterial() == Traincraft.instance.armorCompositeSuit) {
				if (player.getHealth() < player.getMaxHealth() && updateTicks % 100 == 0) {
					if (armorChest.getMaxDamage() - armorChest.getItemDamage() > 1) {
						player.heal(1);
						armorChest.damageItem(1, player);
					} else {
						armorChest.damageItem(armorChest.getMaxDamage() - armorChest.getItemDamage(), player);
					}
				}
			}
		}
		ItemStack armorPants = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		if (!armorPants.isEmpty() && armorPants.getItem() instanceof ItemTCCompositeSuit) {
			if (player.isBurning()) {
				if (armorPants.getMaxDamage() - armorPants.getItemDamage() > 1) {
					player.extinguish();
					armorPants.damageItem(1, player);
				} else {
					armorPants.damageItem(armorPants.getMaxDamage() - armorPants.getItemDamage(), player);
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			ItemStack armor = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
			if (!armor.isEmpty() && armor.getItem() instanceof ItemTCArmor && !player.isInWater()) {
				ItemTCArmor itemarmor = (ItemTCArmor) armor.getItem();
				if (itemarmor.getArmorMaterial() == Traincraft.instance.armorCompositeSuit) {
					if (armor.getMaxDamage() - armor.getItemDamage() > 5) {
						event.getEntityLiving().motionY += 0.05;
						armor.damageItem(5, player);
					} else {
						armor.damageItem(armor.getMaxDamage() - armor.getItemDamage(), player);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityLivingFallEvent(LivingFallEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			ItemStack armor = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
			if (!armor.isEmpty() && armor.getItem() instanceof ItemTCCompositeSuit && !player.isInWater()) {
				ItemTCCompositeSuit itemarmor = (ItemTCCompositeSuit) armor.getItem();
				if (itemarmor.getArmorMaterial() == Traincraft.instance.armorCompositeSuit) {
					if (event.getDistance() - 3 > 0) {
						if (armor.getMaxDamage() - armor.getItemDamage() > 5) {
							armor.damageItem(5, player);
							event.setCanceled(true);
						} else {
							armor.damageItem(armor.getMaxDamage() - armor.getItemDamage(), player);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.getItem() == Items.DIAMOND;
	}
}
