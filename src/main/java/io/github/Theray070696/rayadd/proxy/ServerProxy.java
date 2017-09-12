package io.github.Theray070696.rayadd.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class ServerProxy extends CommonProxy
{
    @Override
    public Side getSide()
    {
        return Side.SERVER;
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return null;
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }
}
