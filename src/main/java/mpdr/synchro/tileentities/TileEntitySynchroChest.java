package mpdr.synchro.tileentities;

import mpdr.synchro.manager.SynchroChestManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.*;
import javax.annotation.Nullable;



public class TileEntitySynchroChest extends TileEntity implements IInventory{

    private String frequency;
    private ItemStack[] chestContents;
    private String customName;
    
    public TileEntitySynchroChest() {
    	this.frequency = "1";
    	this.chestContents = this.getStorage();
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
        this.chestContents = this.getStorage();
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public boolean hasCustomName() {
        return this.getCustomName() != null && !this.getCustomName().equals("");
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setString("frequency", frequency);

        if (this.hasCustomName()) {
            compound.setString("CustomName", this.getCustomName());
        }

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.frequency = compound.getString("frequency");

        if (compound.hasKey("CustomName", 8)) {
            this.setCustomName(compound.getString("CustomName"));
        }
    }
    
    public ItemStack[] getStorage() {
    	return SynchroChestManager.manager.getStorage(this.frequency);
    }

    //IInventory methods/overrides
    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.synchro_chest";
    }

    @Override
    public int getSizeInventory() {
        return 54;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
    	synchronized (chestContents) {
	        if (index < 0 || index >= this.getSizeInventory())
	            return null;
	        return this.chestContents[index];
    	}
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
    	synchronized (chestContents) {
    		if (this.getStackInSlot(index) != null) {
	            ItemStack itemstack;
	
	            if (this.getStackInSlot(index).stackSize <= count) {
	                itemstack = this.getStackInSlot(index);
	                this.setInventorySlotContents(index, null);
	                this.markDirty();
	                return itemstack;
	            } else {
	                itemstack = this.getStackInSlot(index).splitStack(count);
	
	                if (this.getStackInSlot(index).stackSize <= 0) {
	                    this.setInventorySlotContents(index, null);
	                } else {
	                    //Just to show that changes happened
	                    this.setInventorySlotContents(index, this.getStackInSlot(index));
	                }
	
	                this.markDirty();
	                return itemstack;
	            }
	        } else {
	            return null;
	        }
    	}
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        synchronized (chestContents) {
        	ItemStack stack = this.getStackInSlot(index);
            this.setInventorySlotContents(index, null);
            return stack;
		}
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
    	synchronized (chestContents) {
	        if (index < 0 || index >= this.getSizeInventory())
	            return;
	
	        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
	            stack.stackSize = this.getInventoryStackLimit();
	
	        if (stack != null && stack.stackSize == 0)
	            stack = null;
	
	        this.chestContents[index] = stack;
	        this.markDirty();
    	}
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.getSizeInventory(); i++){
            this.setInventorySlotContents(i, null);
        }
    }

    @Override
    public void markDirty() {
        this.setDirty();
    }

    private void setDirty(){
        if (!this.checkDirty()){
            SynchroChestManager.manager.requestSave(frequency, chestContents);
        }
    }
    
    private boolean checkDirty() {
    	return SynchroChestManager.manager.checkDirty(this.frequency);
    }
}
