package io.github.Theray070696.rayadd.item;

import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.gun.EnumBulletCaliber;
import io.github.Theray070696.rayadd.item.gun.*;
import io.github.Theray070696.rayadd.item.gun.classic.*;
import io.github.Theray070696.rayadd.util.LogHelper;
import io.github.Theray070696.raycore.api.item.RayItemRegistry;
import net.minecraft.item.Item;

/**
 * Created by Theray on 1/22/2017.
 */
public class ModItems
{
    public static Item itemGunAk47;
    public static Item itemGunDeagle;
    public static Item itemGunM4;
    public static Item itemGunMinigun;
    public static Item itemGunMp5;
    public static Item itemGunSg552;
    public static Item itemGunShotgun;
    public static Item itemGunSniper;
    public static Item itemGunRocketLauncher;
    public static Item itemGunRocketLauncherLaser;

    public static Item itemBulletLight;
    public static Item itemBulletMedium;
    public static Item itemBulletHeavy;
    public static Item itemBulletShell;
    public static Item itemBulletRocket;
    public static Item itemBulletRocketLaser;

    public static Item itemBulletCasing;
    public static Item itemBulletCasingShell;

    public static Item itemShortBarrel;
    public static Item itemLongBarrel;
    public static Item itemFatBarrel;
    public static Item itemShotgunBarrel;
    public static Item itemMinigunBarrel;

    public static Item itemWoodGrip;
    public static Item itemStockWood;

    public static Item itemHandleMinigun;

    public static Item itemMagazine;

    public static Item itemStockMetal;
    public static Item itemMetalGrip;
    public static Item itemMetalParts;
    public static Item itemMetalReceiver;

    public static Item itemScope;

    public static Item itemTelescope;

    public static ItemMag itemMagDeagle50;
    public static Item itemBullet50AE;

    public static void initItems()
    {
        LogHelper.info("Loading Items");

        if(ConfigHandler.classicMode)
        {
            itemBulletLight = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemBulletLight").setMaxStackSize(32));
            itemBulletMedium = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemBulletMedium").setMaxStackSize(ConfigHandler.ammoRestrictions ? 8 : 32));
            itemBulletHeavy = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemBulletHeavy").setMaxStackSize(ConfigHandler.ammoRestrictions ? 4 : 32));
            itemBulletShell = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemBulletShell").setMaxStackSize(ConfigHandler.ammoRestrictions ? 8 : 32));
            itemBulletRocket = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemBulletRocket").setMaxStackSize(ConfigHandler.ammoRestrictions ? 4 : 32));
            itemBulletRocketLaser = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemBulletRocketLaser").setMaxStackSize(ConfigHandler.ammoRestrictions ? 4 : 32));
        } else
        {
            itemBullet50AE = RayItemRegistry.registerItem(new ItemBullet(EnumBulletCaliber.PISTOL_50_AE, 1, 9, 4F).setUnlocalizedName("itemBullet50AE"));
            itemMagDeagle50 = RayItemRegistry.registerItem(new ItemMagDeagle50());
        }

        itemBulletCasing = RayItemRegistry.registerItem(new ItemRayAdd().setUnlocalizedName("itemBulletCasing"));
        itemBulletCasingShell = RayItemRegistry.registerItem(new ItemRayAdd().setUnlocalizedName("itemBulletCasingShell"));

        if(ConfigHandler.classicMode)
        {
            itemGunAk47 = RayItemRegistry.registerItem(new ItemGunClassicAk47());
            itemGunDeagle = RayItemRegistry.registerItem(new ItemGunClassicDeagle());
            itemGunM4 = RayItemRegistry.registerItem(new ItemGunClassicM4());
            itemGunMinigun = RayItemRegistry.registerItem(new ItemGunClassicMinigun());
            itemGunMp5 = RayItemRegistry.registerItem(new ItemGunClassicMp5());
            itemGunSg552 = RayItemRegistry.registerItem(new ItemGunClassicSg552());
            itemGunShotgun = RayItemRegistry.registerItem(new ItemGunClassicShotgun());
            itemGunSniper = RayItemRegistry.registerItem(new ItemGunClassicSniper());
            itemGunRocketLauncher = RayItemRegistry.registerItem(new ItemGunClassicRocketLauncher());
            itemGunRocketLauncherLaser = RayItemRegistry.registerItem(new ItemGunClassicRocketLauncherLaser());
        } else
        {
            itemGunDeagle = RayItemRegistry.registerItem(new ItemGunDeagle());
        }

        itemShortBarrel = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemShortBarrel"));
        itemLongBarrel = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemLongBarrel"));
        itemFatBarrel = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemFatBarrel"));
        itemShotgunBarrel = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemShotgunBarrel"));
        itemMinigunBarrel = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemMinigunBarrel"));

        itemWoodGrip = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemWoodGrip"));
        itemStockWood = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemStockWood"));

        itemHandleMinigun = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemHandleMinigun"));

        if(ConfigHandler.classicMode)
        {
            itemMagazine = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemMagazine"));
        }

        itemMetalGrip = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemMetalGrip"));
        itemStockMetal = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemStockMetal"));
        itemMetalParts = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemMetalParts"));
        itemMetalReceiver = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemMetalReceiver"));

        itemScope = RayItemRegistry.registerItem(new ItemBulletClassic().setUnlocalizedName("itemScope"));

        itemTelescope = RayItemRegistry.registerItem(new ItemTelescope());
        
        LogHelper.info("Item Loading Complete");
    }
}
