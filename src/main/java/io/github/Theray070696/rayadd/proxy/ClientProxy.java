package io.github.Theray070696.rayadd.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

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
}
