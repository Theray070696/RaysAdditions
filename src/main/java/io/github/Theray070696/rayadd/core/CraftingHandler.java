package io.github.Theray070696.rayadd.core;

import io.github.Theray070696.rayadd.block.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by Theray070696 on 2/3/2017.
 */
public class CraftingHandler
{
    public static void initCraftingRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockLoopingJukebox), "iii", "iji", "iii", 'i', "ingotIron", 'j', Blocks
                .JUKEBOX));
    }
}
