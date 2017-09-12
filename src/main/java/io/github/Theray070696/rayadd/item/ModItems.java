package io.github.Theray070696.rayadd.item;

import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.item.gun.*;
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

    public static void initItems()
    {
        LogHelper.info("Loading Items");

        itemBulletLight = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemBulletLight").setMaxStackSize(32));
        itemBulletMedium = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemBulletMedium").setMaxStackSize
                (ConfigHandler.ammoRestrictions ? 8 : 32));
        itemBulletHeavy = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemBulletHeavy").setMaxStackSize(ConfigHandler
                .ammoRestrictions ? 4 : 32));
        itemBulletShell = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemBulletShell").setMaxStackSize(ConfigHandler
                .ammoRestrictions ? 8 : 32));
        itemBulletRocket = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemBulletRocket").setMaxStackSize
                (ConfigHandler.ammoRestrictions ? 4 : 32));
        itemBulletRocketLaser = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemBulletRocketLaser").setMaxStackSize
                (ConfigHandler.ammoRestrictions ? 4 : 32));

        itemBulletCasing = RayItemRegistry.registerItem(new ItemRayAdd().setUnlocalizedName("itemBulletCasing"));
        itemBulletCasingShell = RayItemRegistry.registerItem(new ItemRayAdd().setUnlocalizedName("itemBulletCasingShell"));

        itemGunAk47 = RayItemRegistry.registerItem(new ItemGunAk47());
        itemGunDeagle = RayItemRegistry.registerItem(new ItemGunDeagle());
        itemGunM4 = RayItemRegistry.registerItem(new ItemGunM4());
        itemGunMinigun = RayItemRegistry.registerItem(new ItemGunMinigun());
        itemGunMp5 = RayItemRegistry.registerItem(new ItemGunMp5());
        itemGunSg552 = RayItemRegistry.registerItem(new ItemGunSg552());
        itemGunShotgun = RayItemRegistry.registerItem(new ItemGunShotgun());
        itemGunSniper = RayItemRegistry.registerItem(new ItemGunSniper());
        itemGunRocketLauncher = RayItemRegistry.registerItem(new ItemGunRocketLauncher());
        itemGunRocketLauncherLaser = RayItemRegistry.registerItem(new ItemGunRocketLauncherLaser());

        itemShortBarrel = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemShortBarrel"));
        itemLongBarrel = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemLongBarrel"));
        itemFatBarrel = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemFatBarrel"));
        itemShotgunBarrel = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemShotgunBarrel"));
        itemMinigunBarrel = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemMinigunBarrel"));

        itemWoodGrip = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemWoodGrip"));
        itemStockWood = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemStockWood"));

        itemHandleMinigun = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemHandleMinigun"));

        itemMagazine = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemMagazine"));

        itemMetalGrip = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemMetalGrip"));
        itemStockMetal = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemStockMetal"));
        itemMetalParts = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemMetalParts"));
        itemMetalReceiver = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemMetalReceiver"));

        itemScope = RayItemRegistry.registerItem(new ItemBullet().setUnlocalizedName("itemScope"));

        itemTelescope = RayItemRegistry.registerItem(new ItemTelescope());

        LogHelper.info("Item Loading Complete");
    }
}
