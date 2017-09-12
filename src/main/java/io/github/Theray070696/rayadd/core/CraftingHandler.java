package io.github.Theray070696.rayadd.core;

import io.github.Theray070696.rayadd.block.ModBlocks;
import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Created by Theray070696 on 2/3/2017.
 */
public class CraftingHandler
{
    public static void initCraftingRecipes()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockLoopingJukebox), "iii", "iji", "iii", 'i', "ingotIron", 'j', Blocks.JUKEBOX));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.IRON_INGOT), "XX", "XX", 'X', ModItems.itemBulletCasing));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.IRON_INGOT), "XX", "XX", 'X', ModItems.itemBulletCasingShell));

        if(ConfigHandler.classicMode)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemBulletLight, ConfigHandler.ammoRestrictions ? 4 : ModItems.itemBulletLight.getItemStackLimit()), "X", "#", 'X', "ingotIron", '#', "gunpowder"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemBulletMedium, ConfigHandler.ammoRestrictions ? 4 : ModItems.itemBulletMedium.getItemStackLimit()), "X ", "##", 'X', "ingotIron", '#', "gunpowder"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemBulletMedium, ConfigHandler.ammoRestrictions ? 4 : ModItems.itemBulletMedium.getItemStackLimit()), " X", "##", 'X', "ingotIron", '#', "gunpowder"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemBulletShell, ConfigHandler.ammoRestrictions ? 4 : ModItems.itemBulletShell.getItemStackLimit()), "X ", "#Y", 'X', "ingotIron", '#', "gunpowder", 'Y', "paper"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemBulletShell, ConfigHandler.ammoRestrictions ? 4 : ModItems.itemBulletShell.getItemStackLimit()), " X", "#Y", 'X', "ingotIron", '#', "gunpowder", 'Y', "paper"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemBulletHeavy, ModItems.itemBulletHeavy.getItemStackLimit()), "XX", "##", 'X', "ingotIron", '#', "gunpowder"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemBulletRocket, ConfigHandler.ammoRestrictions ? 1 : ModItems.itemBulletRocket.getItemStackLimit()), "###", "#X#", "XXX", 'X', "gunpowder", '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemBulletRocketLaser, ConfigHandler.ammoRestrictions ? 1 : ModItems.itemBulletRocket.getItemStackLimit()), "#Y#", "#X#", "XXX", 'X', "gunpowder", '#', "ingotIron", 'Y', "dustRedstone"));
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemTelescope), "#", "X", "X", '#', "blockGlass", 'X', "ingotIron"));

        if(ConfigHandler.classicMode)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemGunAk47), "BRS", "MG ", 'B', ModItems.itemLongBarrel, 'R', ModItems.itemMetalReceiver, 'S', ModItems.itemStockWood, 'M', ModItems.itemMagazine, 'G', ModItems.itemWoodGrip));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemGunDeagle), "SR", " G", 'S', ModItems.itemShortBarrel, 'R', ModItems.itemMetalReceiver, 'G', ModItems.itemMetalGrip));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemGunM4), "BRS", "MG ", 'B', ModItems.itemLongBarrel, 'R', ModItems.itemMetalReceiver, 'S', ModItems.itemStockMetal, 'M', ModItems.itemMagazine, 'G', ModItems.itemMetalGrip));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemGunMinigun), " H ", "BRS", 'H', ModItems.itemHandleMinigun, 'B', ModItems.itemMinigunBarrel, 'R', ModItems.itemMetalReceiver, 'S', ModItems.itemStockMetal));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemGunMp5), "BRS", "MG ", 'B', ModItems.itemShortBarrel, 'R', ModItems.itemMetalReceiver, 'S', ModItems.itemStockMetal, 'M', ModItems.itemMagazine, 'G', ModItems.itemMetalGrip));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemGunRocketLauncher), "BBB", "GG ", 'B', ModItems.itemFatBarrel, 'G', ModItems.itemMetalGrip));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemGunRocketLauncherLaser), "DMR", 'D', "gemDiamond", 'M', ModItems.itemMetalParts, 'R', ModItems.itemGunRocketLauncher));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemGunSg552), " T ", "BRS", "MG ", 'T', ModItems.itemScope, 'B', ModItems.itemLongBarrel, 'R', ModItems.itemMetalReceiver, 'S', ModItems.itemStockMetal, 'M', ModItems.itemMagazine, 'G', ModItems.itemMetalGrip));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemGunShotgun), "BRS", "MG ", 'B', ModItems.itemShotgunBarrel, 'R', ModItems.itemMetalReceiver, 'S', ModItems.itemStockWood, 'G', ModItems.itemWoodGrip));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemGunSniper), " T ", "BRS", " G ", 'T', ModItems.itemScope, 'B', ModItems.itemLongBarrel, 'R', ModItems.itemMetalReceiver, 'S', ModItems.itemStockMetal, 'G', ModItems.itemMetalGrip));
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemScope), "X#X", 'X', "gemDiamond", '#', "ingotIron"));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemWoodGrip), "LL", " L", " L", 'L', "logWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemStockWood), "LLL", " LL", 'L', "logWood"));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemHandleMinigun), " II", "  I", " II", 'I', "ingotIron"));

        if(ConfigHandler.classicMode)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMagazine), "I", "I", "I", 'I', "ingotIron"));
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMetalGrip), "II", " I", " I", 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemStockMetal), "III", " II", 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemMetalParts), "ingotIron", "ingotIron", "ingotIron", "ingotIron"));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemMetalReceiver), "ingotIron", ModItems.itemMetalParts, "ingotIron"));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemShortBarrel), "III", 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemLongBarrel), "SI", 'S', ModItems.itemShortBarrel, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemFatBarrel), "III", "   ", "III", 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemShotgunBarrel), "BB", 'B', ModItems.itemLongBarrel));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMinigunBarrel), " BB", "BBB", " B ", 'B', ModItems.itemLongBarrel));
    }
}
