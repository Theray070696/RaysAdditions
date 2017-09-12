package io.github.Theray070696.rayadd.gun;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class GunTools
{
    public static int getNumberInFirstStackInInventory(InventoryPlayer inventoryplayer, Item item)
    {
        for(int j = 0; j < inventoryplayer.mainInventory.length; j++)
        {
            if(inventoryplayer.mainInventory[j] != null && inventoryplayer.mainInventory[j].getItem().equals(item))
            {
                if(inventoryplayer.mainInventory[j].getItem().getMaxDamage() > 0)
                {
                    return (inventoryplayer.mainInventory[j].getItem().getMaxDamage() - inventoryplayer.mainInventory[j].getItemDamage()) + 1;
                } else
                {
                    return inventoryplayer.mainInventory[j].stackSize;
                }
            }
        }

        return -1;
    }

    public static int getNumberInInventory(InventoryPlayer inventoryplayer, Item item)
    {
        int j = 0;

        for(int k = 0; k < inventoryplayer.mainInventory.length; k++)
        {
            if(inventoryplayer.mainInventory[k] != null && inventoryplayer.mainInventory[k].getItem().equals(item))
            {
                j++;
            }
        }

        return j;
    }

    @SideOnly(Side.CLIENT)
    public static void renderTextureOverlay(String s, float f, Minecraft minecraft)
    {
        ScaledResolution scaledresolution = new ScaledResolution(minecraft);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        minecraft.renderEngine.bindTexture(new ResourceLocation("rayadd:" + s));
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(0.0D, j, -90D).tex(0.0D, 1.0D).endVertex();
        vertexbuffer.pos(i, j, -90D).tex(1.0D, 1.0D).endVertex();
        vertexbuffer.pos(i, 0.0D, -90D).tex(1.0D, 0.0D).endVertex();
        vertexbuffer.pos(0.0D, 0.0D, -90D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
    }

    public static void attackEntityIgnoreDelay(EntityLiving entityliving, DamageSource damagesource, int i)
    {
        entityliving.attackEntityFrom(damagesource, i);
    }

    /**
     * Returns 2 if uses item or uses final item damage and another still exists, returns 1 if item or final item damage is used and another
     * doesn't exist, returns 0 if nothing is or can be done.
     *
     * @param entityplayer
     * @param item
     * @param doRemove
     * @return
     */
    public static int useItemInInventory(EntityPlayer entityplayer, Item item, boolean doRemove)
    {
        int slotID = getInventorySlotContainItem(entityplayer.inventory, item, -1);

        if(slotID < 0)
        {
            return 0;
        }

        if(item.getMaxDamage() > 0)
        {
            if(entityplayer.inventory.mainInventory[slotID].getItemDamage() + 1 > entityplayer.inventory.mainInventory[slotID].getMaxDamage())
            {
                if(doRemove)
                {
                    entityplayer.inventory.mainInventory[slotID] = new ItemStack(Items.BUCKET);
                }

                if(getInventorySlotContainItem(entityplayer.inventory, item, slotID) >= 0)
                {
                    return 2;
                }
            } else
            {
                if(doRemove)
                {
                    entityplayer.inventory.mainInventory[slotID].damageItem(1, entityplayer);
                }
            }
        } else
        {
            if(doRemove)
            {
                entityplayer.inventory.mainInventory[slotID].stackSize--;
            }

            if(entityplayer.inventory.mainInventory[slotID].stackSize <= 0)
            {
                if(doRemove)
                {
                    entityplayer.inventory.mainInventory[slotID] = null;
                }

                if(getInventorySlotContainItem(entityplayer.inventory, item, slotID) >= 0)
                {
                    return 2;
                }
            }
        }

        return 1;
    }

    /**
     * Returns -1 if doesn't contain item, otherwise slot ID it is in
     *
     * @param inventoryplayer
     * @param item
     * @return
     */
    private static int getInventorySlotContainItem(InventoryPlayer inventoryplayer, Item item, int ignoreSlot)
    {
        for(int slotID = 0; slotID < inventoryplayer.mainInventory.length; slotID++)
        {
            if(slotID == ignoreSlot)
            {
                continue;
            }

            if(inventoryplayer.mainInventory[slotID] != null && inventoryplayer.mainInventory[slotID].getItem() == item)
            {
                return slotID;
            }
        }

        return -1;
    }
}
