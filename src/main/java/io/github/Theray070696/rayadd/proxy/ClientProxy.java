package io.github.Theray070696.rayadd.proxy;

import io.github.Theray070696.rayadd.client.core.ClientEventHandler;
import io.github.Theray070696.rayadd.client.core.KeybindHandler;
import io.github.Theray070696.rayadd.client.gun.GunHandlerClient;
import io.github.Theray070696.rayadd.client.render.*;
import io.github.Theray070696.rayadd.client.render.classic.RenderBulletClassic;
import io.github.Theray070696.rayadd.client.render.classic.RenderBulletRocketClassic;
import io.github.Theray070696.rayadd.client.render.classic.RenderBulletRocketLaserClassic;
import io.github.Theray070696.rayadd.client.render.classic.RenderBulletShotgunClassic;
import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletCasingShell;
import io.github.Theray070696.rayadd.entity.classic.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Random;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class ClientProxy extends CommonProxy
{
    public static Minecraft mc = Minecraft.getMinecraft();

    @Override
    public Side getSide()
    {
        return Side.CLIENT;
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return mc.thePlayer;
    }

    @SuppressWarnings("all")
    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);

        if(ConfigHandler.classicMode)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityBulletClassic.class, new IRenderFactory<EntityBulletClassic>()
            {
                @Override
                public Render<? super EntityBulletClassic> createRenderFor(RenderManager manager)
                {
                    return new RenderBulletClassic(manager);
                }
            });

            RenderingRegistry.registerEntityRenderingHandler(EntityBulletClassicShotgun.class, new IRenderFactory<EntityBulletClassicShotgun>()
            {
                @Override
                public Render<? super EntityBulletClassicShotgun> createRenderFor(RenderManager manager)
                {
                    return new RenderBulletShotgunClassic(manager);
                }
            });
            RenderingRegistry.registerEntityRenderingHandler(EntityBulletClassicRocket.class, new IRenderFactory<EntityBulletClassicRocket>()
            {
                @Override
                public Render<? super EntityBulletClassicRocket> createRenderFor(RenderManager manager)
                {
                    return new RenderBulletRocketClassic(manager);
                }
            });
            RenderingRegistry.registerEntityRenderingHandler(EntityBulletClassicRocketLaser.class, new IRenderFactory<EntityBulletClassicRocketLaser>()
            {
                @Override
                public Render<? super EntityBulletClassicRocketLaser> createRenderFor(RenderManager manager)
                {
                    return new RenderBulletRocketLaserClassic(manager);
                }
            });
        } else
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new IRenderFactory<EntityBullet>()
            {
                @Override
                public Render<? super EntityBullet> createRenderFor(RenderManager manager)
                {
                    return new RenderBullet(manager);
                }
            });
        }

        RenderingRegistry.registerEntityRenderingHandler(EntityBulletCasing.class, new IRenderFactory<EntityBulletCasing>()
        {
            @Override
            public Render<? super EntityBulletCasing> createRenderFor(RenderManager manager)
            {
                return new RenderBulletCasing(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBulletCasingShell.class, new IRenderFactory<EntityBulletCasingShell>()
        {
            @Override
            public Render<? super EntityBulletCasingShell> createRenderFor(RenderManager manager)
            {
                return new RenderBulletCasingShell(manager);
            }
        });

        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

        KeybindHandler.init();
    }

    @Override
    public void useZoom()
    {
        GunHandlerClient.useZoom();
    }

    @Override
    public void resetData()
    {
        //GunHandlerClient.jetPackReady = false;
        //GunHandlerClient.jetPackOn = false;
        GunHandlerClient.currentUtilityZoom = 1.0F;
        GunHandlerClient.lastUtilityZoom = GunHandlerClient.currentUtilityZoom;
        GunHandlerClient.currentGunZoom = 1.0F;
        GunHandlerClient.lastGunZoom = GunHandlerClient.currentGunZoom;
    }

    @Override
    public void doRecoil(double v, double h)
    {
        GunHandlerClient.currentRecoilV += v;
        GunHandlerClient.currentRecoilH += h;
    }

    @Override
    public void doExplosionFX(double x, double y, double z)
    {
        Random rand = new Random();

        for(int i = 0; i < 32; i++)
        {
            mc.theWorld.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, x, y, z, rand.nextDouble() - 0.5D, rand.nextDouble() - 0.5D, rand.nextDouble() - 0.5D);
            mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, rand.nextDouble() - 0.5D, rand.nextDouble() - 0.5D, rand.nextDouble() - 0.5D);
        }
    }
}
