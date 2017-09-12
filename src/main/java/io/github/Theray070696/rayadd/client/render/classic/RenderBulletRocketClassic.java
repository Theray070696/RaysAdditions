package io.github.Theray070696.rayadd.client.render.classic;

import io.github.Theray070696.rayadd.entity.EntityBullet;
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
 * Created by Theray070696 on 5/19/2017.
 */
public class RenderBulletRocketClassic extends Render
{
    public RenderBulletRocketClassic(RenderManager renderManager)
    {
        super(renderManager);
    }

    public void renderArrow(EntityBullet entityRocket, double d, double d1, double d2, float f, float f1)
    {
        if(!entityRocket.canRender)
        {
            return;
        }

        GL11.glPushMatrix();
        bindTexture(getEntityTexture(entityRocket));
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef((entityRocket.prevRotationYaw + (entityRocket.rotationYaw - entityRocket.prevRotationYaw) * f1) - 90F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entityRocket.prevRotationPitch + (entityRocket.rotationPitch - entityRocket.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexBuffer = tessellator.getBuffer();
        int i = 0;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (float) (i * 10) / 32F;
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
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-7D, -2D, -2D).tex(f6, f8).endVertex();
        vertexBuffer.pos(-7D, -2D, 2D).tex(f7, f8).endVertex();
        vertexBuffer.pos(-7D, 2D, 2D).tex(f7, f9).endVertex();
        vertexBuffer.pos(-7D, 2D, -2D).tex(f6, f9).endVertex();
        tessellator.draw();
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-7D, 2D, -2D).tex(f6, f8).endVertex();
        vertexBuffer.pos(-7D, 2D, 2D).tex(f7, f8).endVertex();
        vertexBuffer.pos(-7D, -2D, 2D).tex(f7, f9).endVertex();
        vertexBuffer.pos(-7D, -2D, -2D).tex(f6, f9).endVertex();
        tessellator.draw();

        for (int j = 0; j < 4; j++)
        {
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexBuffer.pos(-8D, -2D, 0.0D).tex(f2, f4).endVertex();
            vertexBuffer.pos(8D, -2D, 0.0D).tex(f3, f4).endVertex();
            vertexBuffer.pos(8D, 2D, 0.0D).tex(f3, f5).endVertex();
            vertexBuffer.pos(-8D, 2D, 0.0D).tex(f2, f5).endVertex();
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
        return new ResourceLocation("rayadd:render/Rockets.png");
    }
}
