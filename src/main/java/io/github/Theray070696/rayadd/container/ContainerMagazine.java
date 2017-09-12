package io.github.Theray070696.rayadd.container;

import io.github.Theray070696.rayadd.item.gun.ItemBullet;
import io.github.Theray070696.rayadd.item.gun.ItemMag;
import io.github.Theray070696.rayadd.item.gun.ItemMagInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 6/8/2017.
 */
public class ContainerMagazine extends Container
{
    public final ItemMagInventory inventory;

    private static int INV_START;
    private static int INV_END;
    private static int HOTBAR_START;
    private static int HOTBAR_END;

    public ContainerMagazine(InventoryPlayer inventoryPlayer, ItemMagInventory inventory)
    {
        this.inventory = inventory;
        INV_START = inventory.getSizeInventory();
        INV_END = INV_START + 26;
        HOTBAR_START = INV_END + 1;
        HOTBAR_END = HOTBAR_START + 8;

        int i;

        for(i = 0; i < inventory.getSizeInventory(); i++)
        {
            this.addSlotToContainer(new SlotMagazine(this.inventory, i, 80 + (18 * (int) (i / 4)), 8 + (18 * (i % 4)), inventory.getInvItem().getItem()));
        }

        for(i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return inventory.isUseableByPlayer(entityplayer);
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index)
    {
        ItemStack itemStack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if(index < INV_START)
            {
                if(!this.mergeItemStack(itemStack1, INV_START, HOTBAR_END+1, true))
                {
                    return null;
                }

                slot.onSlotChange(itemStack1, itemStack);
            } else
            {
				if(itemStack1.getItem() instanceof ItemBullet && inventory.getInvItem().getItem() instanceof ItemMag && ((ItemBullet) itemStack1.getItem()).bulletCaliber.equals(((ItemMag) inventory.getInvItem().getItem()).bulletCaliber))
				{
					if(!this.mergeItemStack(itemStack1, 0, inventory.getSizeInventory(), false))
					{
						return null;
					}
				}
            }

            if(itemStack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            } else
            {
                slot.onSlotChanged();
            }

            if(itemStack1.stackSize == itemStack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemStack1);
        }

        return itemStack;
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean backwards)
    {
        boolean flag1 = false;
        int k = (backwards ? end - 1 : start);
        Slot slot;
        ItemStack itemStack1;

        if(stack.isStackable())
        {
            while(stack.stackSize > 0 && (!backwards && k < end || backwards && k >= start))
            {
                slot = (Slot) inventorySlots.get(k);
                itemStack1 = slot.getStack();

                if(!slot.isItemValid(stack))
                {
                    k += (backwards ? -1 : 1);
                    continue;
                }

                if(itemStack1 != null && itemStack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == itemStack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemStack1))
                {
                    int l = itemStack1.stackSize + stack.stackSize;

                    if(l <= stack.getMaxStackSize() && l <= slot.getSlotStackLimit())
                    {
                        stack.stackSize = 0;
                        itemStack1.stackSize = l;
                        inventory.markDirty();
                        flag1 = true;
                    } else if(itemStack1.stackSize < stack.getMaxStackSize() && l < slot.getSlotStackLimit())
                    {
                        stack.stackSize -= stack.getMaxStackSize() - itemStack1.stackSize;
                        itemStack1.stackSize = stack.getMaxStackSize();
                        inventory.markDirty();
                        flag1 = true;
                    }
                }

                k += (backwards ? -1 : 1);
            }
        }
        if(stack.stackSize > 0)
        {
            k = (backwards ? end - 1 : start);
            while (!backwards && k < end || backwards && k >= start)
            {
                slot = (Slot) inventorySlots.get(k);
                itemStack1 = slot.getStack();

                if(!slot.isItemValid(stack))
                {
                    k += (backwards ? -1 : 1);
                    continue;
                }

                if(itemStack1 == null)
                {
                    int l = stack.stackSize;
                    if(l <= slot.getSlotStackLimit())
                    {
                        slot.putStack(stack.copy());
                        stack.stackSize = 0;
                        inventory.markDirty();
                        flag1 = true;
                        break;
                    } else
                    {
                        putStackInSlot(k, new ItemStack(stack.getItem(), slot.getSlotStackLimit(), stack.getItemDamage()));
                        stack.stackSize -= slot.getSlotStackLimit();
                        inventory.markDirty();
                        flag1 = true;
                    }
                }

                k += (backwards ? -1 : 1);
            }
        }

        return flag1;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
        if(slotId >= 0 && getSlot(slotId) != null && getSlot(slotId).getStack() == player.getHeldItemMainhand())
        {
            return null;
        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }
}
