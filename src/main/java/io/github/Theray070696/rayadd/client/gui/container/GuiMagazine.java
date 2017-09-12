package io.github.Theray070696.rayadd.client.gui.container;

import io.github.Theray070696.rayadd.container.ContainerMagazine;
import io.github.Theray070696.rayadd.item.gun.ItemMagInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Theray070696 on 6/13/2017.
 */
public class GuiMagazine extends GuiContainer
{
    private static final ResourceLocation iconLocation = new ResourceLocation("rayadd", "textures/gui/guiLoopingJukebox.png");
    private final ItemMagInventory inventory;

    public GuiMagazine(ContainerMagazine containerMagazine)
    {
        super(containerMagazine);
        this.inventory = containerMagazine.inventory;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.inventory.getName();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 0, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 26, this.ySize - 96 + 4, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(iconLocation);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
