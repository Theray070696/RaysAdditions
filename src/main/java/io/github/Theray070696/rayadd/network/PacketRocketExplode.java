package io.github.Theray070696.rayadd.network;

import io.github.Theray070696.rayadd.RaysAdditions;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 5/19/2017.
 */
public class PacketRocketExplode implements IMessage
{
    private double posX, posY, posZ;

    public PacketRocketExplode() {}

    public PacketRocketExplode(double posX, double posY, double posZ)
    {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.posX = buf.readDouble();
        this.posY = buf.readDouble();
        this.posZ = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(this.posX);
        buf.writeDouble(this.posY);
        buf.writeDouble(this.posZ);
    }

    public static class Handler implements IMessageHandler<PacketRocketExplode, IMessage>
    {
        @Override
        public IMessage onMessage(PacketRocketExplode message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    RaysAdditions.proxy.doExplosionFX(message.posX, message.posY, message.posZ);
                }
            });

            return null;
        }
    }
}
