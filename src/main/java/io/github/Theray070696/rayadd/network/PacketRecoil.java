package io.github.Theray070696.rayadd.network;

import io.github.Theray070696.rayadd.RaysAdditions;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class PacketRecoil implements IMessage
{
    double vRecoil, hRecoil;

    public PacketRecoil() {}

    public PacketRecoil(double vRecoil, double hRecoil)
    {
        this.vRecoil = vRecoil;
        this.hRecoil = hRecoil;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.vRecoil = buf.readDouble();
        this.hRecoil = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(this.vRecoil);
        buf.writeDouble(this.hRecoil);
    }

    public static class Handler implements IMessageHandler<PacketRecoil, IMessage>
    {
        @Override
        public IMessage onMessage(PacketRecoil message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    RaysAdditions.proxy.doRecoil(message.vRecoil, message.hRecoil);
                }
            });

            return null;
        }
    }
}
