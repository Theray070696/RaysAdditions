package io.github.Theray070696.rayadd.container;

import io.github.Theray070696.rayadd.tile.TileLoopingJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 2/3/2017.
 */
public class ContainerLoopingJukebox extends Container
{
    public TileLoopingJukebox loopingJukebox;
    
    public ContainerLoopingJukebox(IInventory playerInventory, TileLoopingJukebox loopingJukebox)
    {
        this.loopingJukebox = loopingJukebox;
        
        this.addSlotToContainer(new Slot(this.loopingJukebox, 0, 10, 31));
        
        this.addPlayerInventory(playerInventory);
    }
    
    private void addPlayerInventory(IInventory playerInventory)
    {
        for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        
        for(int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.loopingJukebox.isUseableByPlayer(player);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
    {
        ItemStack itemStack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotIndex);
        
        if(slot != null && slot.getHasStack())
        {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            
            if(slotIndex < this.loopingJukebox.getSizeInventory())
            {
                if(!this.mergeItemStack(itemStack1, this.loopingJukebox.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            } else if(!this.mergeItemStack(itemStack1, 0, this.loopingJukebox.getSizeInventory(), false))
            {
                return null;
            }
            
            if(itemStack1.stackSize == 0)
            {
                slot.putStack(null);
            } else
            {
                slot.onSlotChanged();
            }
            
            slot.onPickupFromSlot(player, itemStack1);
        }
        
        return itemStack;
    }
}
