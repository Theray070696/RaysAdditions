package io.github.Theray070696.rayadd.client.gui.container;

import io.github.Theray070696.rayadd.RaysAdditions;
import io.github.Theray070696.rayadd.container.ContainerLoopingJukebox;
import io.github.Theray070696.rayadd.lib.ModInfo;
import io.github.Theray070696.rayadd.network.PacketButtonPressed;
import io.github.Theray070696.rayadd.tile.TileLoopingJukebox;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Theray070696 on 2/3/2017.
 */
public class GuiLoopingJukebox extends GuiContainer
{
    private TileLoopingJukebox loopingJukebox;
    private ResourceLocation guiTexture = new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), "textures/gui/guiLoopingJukebox.png");
    
    public GuiLoopingJukebox(ContainerLoopingJukebox container)
    {
        super(container);
        this.loopingJukebox = container.loopingJukebox;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.loopingJukebox.hasCustomName() ? this.loopingJukebox.getName() : I18n.format(this.loopingJukebox.getName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void actionPerformed(GuiButton button)
    {
        RaysAdditions.network.sendToServer(new PacketButtonPressed(loopingJukebox.getWorld().provider.getDimension(), loopingJukebox.getPos().getX(), loopingJukebox.getPos().getY(), loopingJukebox.getPos().getZ(), button.id));
    }
    
    @Override
    public void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        
        this.buttonList.add(new GuiButton(0, this.guiLeft + 123, this.guiTop + 57, 20, 20, ">"));
        this.buttonList.add(new GuiButton(1, this.guiLeft + 145, this.guiTop + 57, 20, 20, "||"));
    }
}
