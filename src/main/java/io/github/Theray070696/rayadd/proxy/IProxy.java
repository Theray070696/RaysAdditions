package io.github.Theray070696.rayadd.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public interface IProxy
{
    public Side getSide();

    public void init(FMLInitializationEvent event);

    public EntityPlayer getClientPlayer();
}
