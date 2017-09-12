package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.RaysAdditions;
import io.github.Theray070696.rayadd.gun.EnumBulletCaliber;
import io.github.Theray070696.rayadd.item.ItemRayAdd;
import io.github.Theray070696.rayadd.lib.GuiIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 6/8/2017.
 */
public class ItemMag extends ItemRayAdd
{
    public EnumBulletCaliber bulletCaliber;
    private ItemMagInventory inventory;
    private int magSize;

    public ItemMag(EnumBulletCaliber bulletType, int magSize)
    {
        super();
        this.bulletCaliber = bulletType;
        this.magSize = magSize;
        this.setMaxStackSize(1);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 1;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nullable ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if(!world.isRemote)
        {
            if(!player.isSneaking())
            {
                this.inventory = new ItemMagInventory(player.getHeldItemMainhand(), this.magSize);
                player.openGui(RaysAdditions.INSTANCE, GuiIds.MAGAZINE_GUI_ID, world, 0, 0, 0);
            }
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    public ItemMagInventory getInventory()
    {
        return this.inventory;
    }

    public int getMagSize()
    {
        return this.magSize;
    }
}
