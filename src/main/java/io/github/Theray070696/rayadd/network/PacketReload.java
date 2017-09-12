package io.github.Theray070696.rayadd.network;

import io.github.Theray070696.rayadd.gun.GunHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class PacketReload implements IMessage
{
    public PacketReload() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketReload, IMessage>
    {
        @Override
        public IMessage onMessage(PacketReload message, MessageContext ctx)
        {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    GunHandler.handleReloadClassic(ctx.getServerHandler().playerEntity.worldObj, ctx.getServerHandler().playerEntity, false);
                }
            });

            return null;
        }
    }
}
