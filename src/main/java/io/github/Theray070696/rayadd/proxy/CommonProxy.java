package io.github.Theray070696.rayadd.proxy;

import io.github.Theray070696.rayadd.RaysAdditions;
import io.github.Theray070696.rayadd.core.EventHandler;
import io.github.Theray070696.rayadd.entity.*;
import io.github.Theray070696.rayadd.lib.ModInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public abstract class CommonProxy implements IProxy
{
    @Override
    public void init(FMLInitializationEvent event)
    {
        EntityRegistry.registerModEntity(EntityBulletAk47.class, ModInfo.MOD_ID + ":bulletAk47", 0, RaysAdditions.INSTANCE, 40, 100, true);
        EntityRegistry.registerModEntity(EntityBulletDeagle.class, ModInfo.MOD_ID + ":bulletDeagle", 3, RaysAdditions.INSTANCE, 40, 100, true);
        EntityRegistry.registerModEntity(EntityBulletM4.class, ModInfo.MOD_ID + ":bulletM4", 6, RaysAdditions.INSTANCE, 40, 100, true);
        EntityRegistry.registerModEntity(EntityBulletMinigun.class, ModInfo.MOD_ID + ":bulletMinigun", 7, RaysAdditions.INSTANCE, 40, 100, true);
        EntityRegistry.registerModEntity(EntityBulletMp5.class, ModInfo.MOD_ID + ":bulletMp5", 8, RaysAdditions.INSTANCE, 40, 100, true);
        EntityRegistry.registerModEntity(EntityBulletRocket.class, ModInfo.MOD_ID + ":bulletRocket", 9, RaysAdditions.INSTANCE, 80, 100, true);
        EntityRegistry.registerModEntity(EntityBulletRocketLaser.class, ModInfo.MOD_ID + ":bulletRocketLaser", 10, RaysAdditions.INSTANCE, 80, 100,
                true);
        EntityRegistry.registerModEntity(EntityBulletSg552.class, ModInfo.MOD_ID + ":bulletSg552", 11, RaysAdditions.INSTANCE, 40, 100, true);
        EntityRegistry.registerModEntity(EntityBulletShotgun.class, ModInfo.MOD_ID + ":bulletShotgun", 12, RaysAdditions.INSTANCE, 40, 100, true);
        EntityRegistry.registerModEntity(EntityBulletSniper.class, ModInfo.MOD_ID + ":bulletSniper", 13, RaysAdditions.INSTANCE, 40, 100, true);

        EntityRegistry.registerModEntity(EntityBulletCasing.class, ModInfo.MOD_ID + ":bulletCasing", 1, RaysAdditions.INSTANCE, 40, 100, true);
        EntityRegistry.registerModEntity(EntityBulletCasingShell.class, ModInfo.MOD_ID + ":bulletCasingShell", 2, RaysAdditions.INSTANCE, 40, 100,
                true);

        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}
