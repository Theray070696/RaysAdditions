package io.github.Theray070696.rayadd.client.core;

import io.github.Theray070696.rayadd.RaysAdditions;
import io.github.Theray070696.rayadd.client.gun.GunHandlerClient;
import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.gun.GunTools;
import io.github.Theray070696.rayadd.item.ModItems;
import io.github.Theray070696.rayadd.item.gun.ItemGun;
import io.github.Theray070696.rayadd.item.gun.classic.ItemGunClassic;
import io.github.Theray070696.rayadd.network.PacketReload;
import io.github.Theray070696.rayadd.network.PacketShooting;
import io.github.Theray070696.rayadd.network.PacketSniperZoom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class ClientEventHandler
{
    public Minecraft mc = Minecraft.getMinecraft();

    public boolean prevShooting;
    public boolean didMenuReset = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(mc.theWorld != null)
        {
            boolean shooting = Mouse.isButtonDown(1) && mc.thePlayer.getHeldItemMainhand() != null && (mc.thePlayer.getHeldItemMainhand().getItem() instanceof ItemGunClassic || mc.thePlayer.getHeldItemMainhand().getItem() instanceof ItemGun) && mc.currentScreen == null;

            if(prevShooting != shooting)
            {
                RaysAdditions.network.sendToServer(new PacketShooting(shooting));
            }

            prevShooting = shooting;

            if(!mc.thePlayer.isEntityAlive())
            {
                GunHandlerClient.currentGunZoom = 1.0F;

                if(GunHandlerClient.reloadTimes.containsKey(mc.thePlayer))
                {
                    GunHandlerClient.reloadTimes.remove(mc.thePlayer);
                }

                GunHandlerClient.currentRecoilV = 0.0D;
                GunHandlerClient.currentRecoilH = 0.0D;
            }

            GunHandlerClient.handleRecoil(mc);

            /*GunHandlerClient.setJetPack(GunHandlerClient.handleJetPack(mc));

            ItemStack itemstack1 = mc.thePlayer.inventory.armorItemInSlot(3);

            if(itemstack1 == null || !itemstack1.equals(ModItems.itemNightvisionGoggles))
            {
                GunHandlerClient.nightvisionEnabled = false;
            }*/
        }

        if(mc.currentScreen == null)
        {
            if(KeybindHandler.reloadKey.isPressed())
            {
                RaysAdditions.network.sendToServer(new PacketReload());
            }

            if(KeybindHandler.zoomKey.isPressed())
            {
                GunHandlerClient.toggleZoomEnabled();
            }
        }
    }

    @SubscribeEvent
    public void updateFOV(FOVUpdateEvent event)
    {
        if(mc.theWorld != null)
        {
            boolean flag = false;
            float f = 1.0F;
            ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();

            if(mc.gameSettings != null && mc.thePlayer != null && mc.thePlayer.inventory != null)
            {
                if(itemstack == null || !itemstack.getItem().equals(ModItems.itemGunSniper) && !itemstack.getItem().equals(ModItems.itemGunSg552) || mc.thePlayer.inventory.currentItem != GunHandlerClient.lastGunZoomSlot || mc.gameSettings.thirdPersonView > 0 || mc.currentScreen != null)
                {
                    GunHandlerClient.zoomEnabled = false;
                }

                GunHandlerClient.lastGunZoomSlot = mc.thePlayer.inventory.currentItem;

                if(GunHandlerClient.zoomEnabled && mc.gameSettings.thirdPersonView == 0)
                {
                    if(itemstack.getItem().equals(ModItems.itemGunSniper))
                    {
                        f = 0.125F;
                    } else
                    {
                        f = 0.25F;
                    }

                    if(GunHandlerClient.currentGunZoom > f)
                    {
                        flag = true;
                    }
                }
            }

            if(GunHandlerClient.zoomEnabled && flag)
            {
                GunHandlerClient.currentGunZoom = Math.max(f, GunHandlerClient.currentGunZoom - 0.075F);
            } else if(!GunHandlerClient.zoomEnabled && GunHandlerClient.currentGunZoom < 1.0F)
            {
                GunHandlerClient.currentGunZoom = Math.min(1.0F, GunHandlerClient.currentGunZoom + 0.075F);
            }

            if(GunHandlerClient.currentGunZoom == 0.125F && !GunHandlerClient.sniperZoomedIn && itemstack != null && itemstack.getItem().equals(ModItems.itemGunSniper))
            {
                GunHandlerClient.sniperZoomedIn = true;
                RaysAdditions.network.sendToServer(new PacketSniperZoom(GunHandlerClient.sniperZoomedIn));
            } else if(GunHandlerClient.currentGunZoom != 0.125F && GunHandlerClient.sniperZoomedIn)
            {
                GunHandlerClient.sniperZoomedIn = false;
                RaysAdditions.network.sendToServer(new PacketSniperZoom(GunHandlerClient.sniperZoomedIn));
            }

            event.setNewfov(1.0F * GunHandlerClient.currentGunZoom);

            GunHandlerClient.lastGunZoom = GunHandlerClient.currentGunZoom;

            ItemStack itemstack2 = mc.thePlayer.inventory.getCurrentItem();

            if(itemstack2 == null || !itemstack2.getItem().equals(ModItems.itemTelescope) || mc.thePlayer.inventory.currentItem != GunHandlerClient.lastUtilityZoomSlot || mc.gameSettings.thirdPersonView > 0 || mc.currentScreen != null)
            {
                GunHandlerClient.currentUtilityZoomIndex = 0;
            }

            GunHandlerClient.lastUtilityZoomSlot = mc.thePlayer.inventory.currentItem;
            float f2 = GunHandlerClient.MAX_ZOOMS[GunHandlerClient.currentUtilityZoomIndex];

            if(GunHandlerClient.currentUtilityZoom > f2)
            {
                GunHandlerClient.currentUtilityZoom = Math.max(f2, GunHandlerClient.currentUtilityZoom - 0.0625F);
            } else if(GunHandlerClient.currentUtilityZoom < f2)
            {
                GunHandlerClient.currentUtilityZoom = Math.min(f2, GunHandlerClient.currentUtilityZoom + 0.0625F);
            }

            if(!GunHandlerClient.zoomEnabled)
            {
                event.setNewfov(1.0F * GunHandlerClient.currentUtilityZoom);
            }

            GunHandlerClient.lastUtilityZoom = GunHandlerClient.currentUtilityZoom;
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event)
    {
        if(!didMenuReset && mc.currentScreen instanceof GuiMainMenu)
        {
            RaysAdditions.proxy.resetData();
            didMenuReset = true;
        } else if(!(mc.currentScreen instanceof GuiMainMenu))
        {
            didMenuReset = false;
        }

        if(mc.theWorld != null)
        {
            renderUtilityScopeOverlay(mc);
            renderGunScopeOverlay(mc);
            renderAmmo(mc);
        }
    }

    private void renderAmmo(Minecraft minecraft)
    {
        if(ConfigHandler.showAmmoBar && minecraft.currentScreen == null && (minecraft.gameSettings.thirdPersonView > 0 || GunHandlerClient.currentGunZoom == 1.0F))
        {
            ItemStack itemstack = minecraft.thePlayer.getHeldItemMainhand();

            if(itemstack != null)
            {
                Item item = itemstack.getItem();

                if(item instanceof ItemGunClassic)
                {
                    int i = GunTools.getNumberInInventory(minecraft.thePlayer.inventory, ((ItemGunClassic) item).requiredBullet);
                    ScaledResolution scaledresolution = new ScaledResolution(minecraft);
                    int j = scaledresolution.getScaledWidth();
                    int k = scaledresolution.getScaledHeight();
                    int l = k - 32 - 8 - 18;
                    String s = Integer.valueOf(i > 0 ? i - 1 : 0).toString();
                    int i1 = minecraft.fontRendererObj.getStringWidth(s);
                    minecraft.fontRendererObj.drawString(s, (j / 2 + 91) - i1, l, 0xffffff);
                    int j1 = GunTools.getNumberInFirstStackInInventory(minecraft.thePlayer.inventory, ((ItemGunClassic) item).requiredBullet);
                    Tessellator tessellator = Tessellator.getInstance();
                    VertexBuffer vertexbuffer = tessellator.getBuffer();
                    vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                    //tessellator.startDrawingQuads();

                    /*if(item instanceof ItemGunFlamethrower)
                    {
                        minecraft.renderEngine.bindTexture(new ResourceLocation("modernwarfare:gui/AmmoOil.png"));

                        for(int k1 = j1 - 1; k1 >= 0; k1--)
                        {
                            int j2 = (j / 2 + 91) - k1 - 1 - 14;
                            int i3 = l - 1;
                            vertexbuffer.pos(j2, i3 + 9, -90D).tex(0.0D, 1.0D).endVertex();
                            vertexbuffer.pos(j2 + 1, i3 + 9, -90D).tex(1.0D, 1.0D).endVertex();
                            vertexbuffer.pos(j2 + 1, i3, -90D).tex(1.0D, 0.0D).endVertex();
                            vertexbuffer.pos(j2, i3, -90D).tex(0.0D, 0.0D).endVertex();
                            tessellator.addVertexWithUV(j2, i3 + 9, -90D, 0.0D, 1.0D);
                            tessellator.addVertexWithUV(j2 + 1, i3 + 9, -90D, 1.0D, 1.0D);
                            tessellator.addVertexWithUV(j2 + 1, i3, -90D, 1.0D, 0.0D);
                            tessellator.addVertexWithUV(j2, i3, -90D, 0.0D, 0.0D);
                        }
                    } else if(item instanceof ItemGunLaser)
                    {
                        minecraft.renderEngine.bindTexture(new ResourceLocation("modernwarfare:gui/AmmoRedstone.png"));

                        for (int l1 = j1 - 1; l1 >= 0; l1--)
                        {
                            int k2 = (j / 2 + 91) - l1 - 1 - 14;
                            int j3 = l - 1;
                            vertexbuffer.pos(k2, j3 + 9, -90D).tex(0.0D, 1.0D).endVertex();
                            vertexbuffer.pos(k2 + 1, j3 + 9, -90D).tex(1.0D, 1.0D).endVertex();
                            vertexbuffer.pos(k2 + 1, j3, -90D).tex(1.0D, 0.0D).endVertex();
                            vertexbuffer.pos(k2, j3, -90D).tex(0.0D, 0.0D).endVertex();
                            tessellator.addVertexWithUV(k2, j3 + 9, -90D, 0.0D, 1.0D);
                            tessellator.addVertexWithUV(k2 + 1, j3 + 9, -90D, 1.0D, 1.0D);
                            tessellator.addVertexWithUV(k2 + 1, j3, -90D, 1.0D, 0.0D);
                            tessellator.addVertexWithUV(k2, j3, -90D, 0.0D, 0.0D);
                        }
                    } else
                    {*/
                        minecraft.renderEngine.bindTexture(new ResourceLocation("rayadd:gui/AmmoBullet.png"));

                        for(int i2 = j1 - 1; i2 >= 0; i2--)
                        {
                            int l2 = (j / 2 + 91) - i2 * 2 - 3 - 14;
                            int k3 = l - 1;
                            vertexbuffer.pos(l2, k3 + 9, -90D).tex(0.0D, 1.0D).endVertex();
                            vertexbuffer.pos(l2 + 3, k3 + 9, -90D).tex(1.0D, 1.0D).endVertex();
                            vertexbuffer.pos(l2 + 3, k3, -90D).tex(1.0D, 0.0D).endVertex();
                            vertexbuffer.pos(l2, k3, -90D).tex(0.0D, 0.0D).endVertex();
                            /*tessellator.addVertexWithUV(l2, k3 + 9, -90D, 0.0D, 1.0D);
                            tessellator.addVertexWithUV(l2 + 3, k3 + 9, -90D, 1.0D, 1.0D);
                            tessellator.addVertexWithUV(l2 + 3, k3, -90D, 1.0D, 0.0D);
                            tessellator.addVertexWithUV(l2, k3, -90D, 0.0D, 0.0D);*/
                        }
                    //}

                    tessellator.draw();
                }
            }
        }
    }

    private void renderGunScopeOverlay(Minecraft minecraft)
    {
        if(minecraft.gameSettings.thirdPersonView == 0 && GunHandlerClient.currentGunZoom != 1.0F && minecraft.currentScreen == null)
        {
            GunTools.renderTextureOverlay("gui/Scope.png", 1.0F, mc);
        }
    }

    private void renderUtilityScopeOverlay(Minecraft minecraft)
    {
        if(minecraft.gameSettings.thirdPersonView == 0 && GunHandlerClient.currentUtilityZoom != 1.0F && minecraft.currentScreen == null)
        {
            GunTools.renderTextureOverlay("gui/Telescope.png", 1.0F, mc);
        }
    }
}
