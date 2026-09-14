/*******************************************************************************
 * Copyright (c) 2012 Mrbrutal. All rights reserved.
 *
 * @name TrainCraft
 * @author Mrbrutal
 ******************************************************************************/

package train.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;

//TODO Does this class even have ANY purpose? If it's supposed to identify TileEntities of this certain mod, use an interface. PLEASE. NO!
public class TileTraincraft extends TileEntity implements ISidedInventory{

    public ItemStack[] slots;
    public String invName;

    public TileTraincraft(){}

    public TileTraincraft(int slotAmount, String inventoryname){
        this.slots = new ItemStack[slotAmount];
        this.invName = inventoryname;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side){
        if(this.slots.length > 0){
            int[] theInt = new int[slots.length];
            for(int i = 0; i < theInt.length; i++){
                theInt[i] = i;
            }
            return theInt;
        } else {
            return new int[0];
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return false;
    }

    @Override
    public int getSizeInventory(){
        return this.slots==null?0:this.slots.length;
    }

    @Override
    public boolean isEmpty(){
        if(this.slots == null) return true;
        for(ItemStack s : this.slots){
            if(s != null && !s.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int slot){
        // 1.12.2: never hand vanilla a null stack (Slot.getStack().isEmpty() would NPE)
        if (slot < 0 || slot >= this.slots.length || this.slots[slot] == null) return ItemStack.EMPTY;
        return this.slots[slot];
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        if (i >= 0 && i < this.slots.length && this.slots[i] != null) {
            if (this.slots[i].getCount() <= j) {
                ItemStack itemstack = this.slots[i];
                this.slots[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = this.slots[i].splitStack(j);
            if (this.slots[i].isEmpty()) {
                this.slots[i] = null;
            }
            return itemstack1;
        }
        else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slot){
        if (slot >= 0 && slot < this.slots.length && this.slots[slot] != null) {
            ItemStack var2 = this.slots[slot];
            this.slots[slot] = null;
            return var2;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack){
        // store EMPTY as null internally so the mod's own `!= null` checks keep working
        this.slots[slot] = (stack == null || stack.isEmpty()) ? null : stack;
        if (this.slots[slot] != null && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
    }

    @Override
    public String getName(){
        return this.invName;
    }

    @Override
    public boolean hasCustomName(){
        return false;
    }

    @Override
    public int getInventoryStackLimit(){
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player){
        return player.getDistanceSq(getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player){

    }

    @Override
    public void closeInventory(EntityPlayer player){

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack){
        return true;
    }

    @Override
    public int getField(int id){ return 0; }

    @Override
    public void setField(int id, int value){}

    @Override
    public int getFieldCount(){ return 0; }

    @Override
    public void clear(){
        if(this.slots != null){
            for(int i = 0; i < this.slots.length; i++) this.slots[i] = null;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt){
        super.writeToNBT(nbt);
        this.writeToNBT(nbt, false);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt){
        super.readFromNBT(nbt);
        this.readFromNBT(nbt, false);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt, boolean forSyncing){
        if(!forSyncing){
            if(slots==null){
                return nbt;
            }
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.slots.length; i++) {
                if (this.slots[i] != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.slots[i].writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbt.setTag("Items", nbttaglist);
        }
        return nbt;
    }

    public void readFromNBT(NBTTagCompound nbt, boolean forSyncing){
        if(!forSyncing){
            NBTTagList nbttaglist = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
            this.slots = new ItemStack[getSizeInventory()];
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                byte byte0 = nbttagcompound1.getByte("Slot");
                if (byte0 >= 0 && byte0 < this.slots.length) {
                    this.slots[byte0] = new ItemStack(nbttagcompound1);
                }
            }
        }
    }

    public void syncTileEntity(){
        for(Object o : this.world.playerEntities){
            if(o instanceof EntityPlayerMP){
                EntityPlayerMP player = (EntityPlayerMP) o;
                if(player.getDistance(getPos().getX(), getPos().getY(), getPos().getZ()) <= 64) {
                    player.connection.sendPacket(this.getUpdatePacket());
                }
            }
        }
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt, true);
        return new SPacketUpdateTileEntity(this.pos, 1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
        if(pkt != null){
            this.readFromNBT(pkt.getNbtCompound(), true);
        }
    }

}
