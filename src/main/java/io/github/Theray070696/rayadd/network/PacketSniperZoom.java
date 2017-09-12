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
public class PacketSniperZoom implements IMessage
{
    private boolean isZoomed;

    public PacketSniperZoom() {}

    public PacketSniperZoom(boolean isZoomed)
    {
        this.isZoomed = isZoomed;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.isZoomed = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(this.isZoomed);
    }

    public static class Handler implements IMessageHandler<PacketSniperZoom, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSniperZoom message, MessageContext ctx)
        {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    GunHandler.isSniperZoomedIn.put(ctx.getServerHandler().playerEntity, message.isZoomed);
                }
            });

            return null;
        }
    }
}
