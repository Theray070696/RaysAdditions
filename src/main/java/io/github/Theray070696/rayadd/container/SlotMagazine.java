package io.github.Theray070696.rayadd.container;

import io.github.Theray070696.rayadd.item.gun.ItemBullet;
import io.github.Theray070696.rayadd.item.gun.ItemMag;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 6/8/2017.
 */
public class SlotMagazine extends Slot
{
    private Item itemMag;

    public SlotMagazine(IInventory inv, int index, int xPos, int yPos, Item itemMag)
    {
        super(inv, index, xPos, yPos);
        this.itemMag = itemMag;
    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        // Everything returns true except an instance of our Item
        return itemstack.getItem() instanceof ItemBullet && itemMag instanceof ItemMag && ((ItemBullet) itemstack.getItem()).bulletCaliber.equals(((ItemMag) itemMag).bulletCaliber);
    }
}
