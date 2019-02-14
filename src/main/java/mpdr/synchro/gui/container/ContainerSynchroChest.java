package mpdr.synchro.gui.container;


import mpdr.synchro.tileentities.TileEntitySynchroChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ContainerSynchroChest extends Container{

    private TileEntitySynchroChest storage;

    public ContainerSynchroChest(IInventory playerInv, TileEntitySynchroChest te) {
        this.storage = te;

        //Tile Inventory, Slots 0-53, Slot IDs 0-53
        for (int y = 0; y < 6; ++y){
            for (int x = 0; x < 9; ++x){
                this.addSlotToContainer(new Slot(this.storage, x + y * 9, 8 + x * 18, 18 + y * 18));
            }
        }

        //Player Inventory, Slots 9-35, Slot IDs 9-35
        for (int y = 0; y < 3; ++y){
            for (int x = 0; x < 9; ++x){
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 140 + y * 18));
            }
        }

        //player Inventory, Slots 0-8, Slot IDs 0-8
        for (int x = 0; x < 9; ++x){
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 198));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.storage.isUseableByPlayer(playerIn);
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
    	ItemStack previous = null;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (fromSlot < 54) {
                // From TE Inventory to Player Inventory
                if (!this.mergeItemStack(current, 54, 90, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(current, 0, 54, false)) {
                // From Player Inventory to TE Inventory
                    return null;
            }

            if (current.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
            
            if (current.stackSize == previous.stackSize)
                return null;
            slot.onPickupFromSlot(playerIn, current);
        }
        return previous;		
    }
    
    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
    	// TODO Auto-generated method stub
    	super.onContainerClosed(playerIn);
    }
}
