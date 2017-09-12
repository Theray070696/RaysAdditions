package io.github.Theray070696.rayadd.item.gun;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 6/8/2017.
 */
public class ItemMagInventory implements IInventory
{
    protected ItemStack[] inventory;
    private int magSize;
    private final ItemStack invItem;

    // TODO: Instead of storing each individual item, store NUMBER of rounds, and TYPE of bullet, be it Standard, Tracer, etc.
    // TODO: This will reduce memory usage, cpu usage, and be FAR easier to keep track of internally, as all I need to do to USE a bullet is subtract 1 from the current bullet counter for the magazine and offset the bullet type list accordingly.

    public ItemMagInventory(ItemStack stack, int magSize)
    {
        this.invItem = stack;

        if(!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        this.magSize = magSize;
        this.inventory = new ItemStack[this.magSize];

        readFromNBT(stack.getTagCompound());
    }

    public void readFromNBT(NBTTagCompound tagCompound)
    {
        NBTTagList list = tagCompound.getTagList("Items", 10);
        for(int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        NBTTagList list = new NBTTagList();
        for(int i = 0; i < this.getSizeInventory(); ++i)
        {
            if(this.getStackInSlot(i) != null)
            {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }
        tagCompound.setTag("Items", list);

        return tagCompound;
    }

    @Override
    public int getSizeInventory()
    {
        return this.magSize;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if(slot < 0 || slot >= this.getSizeInventory())
        {
            return null;
        }

        return this.inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int count)
    {
        if(this.getStackInSlot(slot) != null)
        {
            ItemStack itemstack;

            if(this.getStackInSlot(slot).stackSize <= count)
            {
                itemstack = this.getStackInSlot(slot);
                this.setInventorySlotContents(slot, null);
                this.markDirty();
                return itemstack;
            } else
            {
                itemstack = this.getStackInSlot(slot).splitStack(count);

                if(this.getStackInSlot(slot).stackSize <= 0)
                {
                    this.setInventorySlotContents(slot, null);
                } else
                {
                    this.setInventorySlotContents(slot, this.getStackInSlot(slot));
                }

                this.markDirty();
                return itemstack;
            }
        } else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        if(slot < 0 || slot >= this.getSizeInventory())
        {
            return;
        }

        if(stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if(stack != null && stack.stackSize == 0)
        {
            stack = null;
        }

        this.inventory[slot] = stack;
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public void markDirty()
    {
        for(int i = 0; i < getSizeInventory(); ++i)
        {
            if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0)
            {
                inventory[i] = null;
            }
        }

        writeToNBT(invItem.getTagCompound());
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void clear()
    {
        for(int i = 0; i < this.inventory.length; i++)
        {
            this.inventory[i] = null;
        }
    }

    @Override
    public int getField(int id) { return 0; }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() { return 0; }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return stack.getItem() instanceof ItemBullet && invItem.getItem() instanceof ItemMag && ((ItemBullet) stack.getItem()).bulletCaliber.equals(((ItemMag) invItem.getItem()).bulletCaliber);
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

    @Override
    public String getName()
    {
        return "Magazine";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return null;
    }

    public ItemStack getInvItem()
    {
        return this.invItem;
    }
}
