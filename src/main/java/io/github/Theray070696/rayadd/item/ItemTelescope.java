package io.github.Theray070696.rayadd.item;

import io.github.Theray070696.rayadd.RaysAdditions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class ItemTelescope extends ItemRayAdd
{
    public ItemTelescope()
    {
        super();
        setUnlocalizedName("itemTelescope");
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nullable ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if(world.isRemote)
        {
            RaysAdditions.proxy.useZoom();
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
