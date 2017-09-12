package io.github.Theray070696.rayadd.client.render;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassic;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class RenderBullet extends Render
{
    public RenderBullet(RenderManager renderManager)
    {
        super(renderManager);
    }

    public void renderArrow(EntityBullet entityBullet, double d, double d1, double d2, float f, float f1)
    {
        if(!entityBullet.canRender)
        {
            return;
        }

        GL11.glPushMatrix();
        bindTexture(getEntityTexture(entityBullet));
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef((entityBullet.prevRotationYaw + (entityBullet.rotationYaw - entityBullet.prevRotationYaw) * f1) - 90F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entityBullet.prevRotationPitch + (entityBullet.rotationPitch - entityBullet.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        int i = 0;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (float) (0 + i * 10) / 32F;
        float f5 = (float) (5 + i * 10) / 32F;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (float) (5 + i * 10) / 32F;
        float f9 = (float) (10 + i * 10) / 32F;
        float f10 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glRotatef(45F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(-7D, -2D, -2D).tex(f6, f8).endVertex();
        vertexbuffer.pos(-7D, -2D, 2D).tex(f7, f8).endVertex();
        vertexbuffer.pos(-7D, 2D, 2D).tex(f7, f9).endVertex();
        vertexbuffer.pos(-7D, 2D, -2D).tex(f6, f9).endVertex();
        /*tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7D, -2D, -2D, f6, f8);
        tessellator.addVertexWithUV(-7D, -2D, 2D, f7, f8);
        tessellator.addVertexWithUV(-7D, 2D, 2D, f7, f9);
        tessellator.addVertexWithUV(-7D, 2D, -2D, f6, f9);*/
        tessellator.draw();
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(-7D, 2D, -2D).tex(f6, f8).endVertex();
        vertexbuffer.pos(-7D, 2D, 2D).tex(f7, f8).endVertex();
        vertexbuffer.pos(-7D, -2D, 2D).tex(f7, f9).endVertex();
        vertexbuffer.pos(-7D, -2D, -2D).tex(f6, f9).endVertex();
        /*tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7D, 2D, -2D, f6, f8);
        tessellator.addVertexWithUV(-7D, 2D, 2D, f7, f8);
        tessellator.addVertexWithUV(-7D, -2D, 2D, f7, f9);
        tessellator.addVertexWithUV(-7D, -2D, -2D, f6, f9);*/
        tessellator.draw();

        for(int j = 0; j < 4; j++)
        {
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-8D, -2D, 0.0D).tex(f2, f4).endVertex();
            vertexbuffer.pos(8D, -2D, 0.0D).tex(f3, f4).endVertex();
            vertexbuffer.pos(8D, 2D, 0.0D).tex(f3, f5).endVertex();
            vertexbuffer.pos(-8D, 2D, 0.0D).tex(f2, f5).endVertex();
            /*tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-8D, -2D, 0.0D, f2, f4);
            tessellator.addVertexWithUV(8D, -2D, 0.0D, f3, f4);
            tessellator.addVertexWithUV(8D, 2D, 0.0D, f3, f5);
            tessellator.addVertexWithUV(-8D, 2D, 0.0D, f2, f5);*/
            tessellator.draw();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        renderArrow((EntityBullet) entity, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("rayadd:render/Bullets.png");
    }
}
