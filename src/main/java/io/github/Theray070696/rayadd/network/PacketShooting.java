package io.github.Theray070696.rayadd.network;

import io.github.Theray070696.rayadd.gun.GunHandler;
import io.github.Theray070696.rayadd.item.gun.ItemGun;
import io.github.Theray070696.rayadd.item.gun.classic.ItemGunClassic;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class PacketShooting implements IMessage
{
    private boolean shooting;

    public PacketShooting() {}

    public PacketShooting(boolean shooting)
    {
        this.shooting = shooting;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.shooting = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(this.shooting);
    }

    public static class Handler implements IMessageHandler<PacketShooting, IMessage>
    {
        @Override
        public IMessage onMessage(PacketShooting message, MessageContext ctx)
        {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    if(message.shooting && ctx.getServerHandler().playerEntity.getHeldItemMainhand() != null && (ctx.getServerHandler().playerEntity.getHeldItemMainhand().getItem() instanceof ItemGunClassic || ctx.getServerHandler().playerEntity.getHeldItemMainhand().getItem() instanceof ItemGun))
                    {
                        GunHandler.shooting.add(ctx.getServerHandler().playerEntity);
                    } else
                    {
                        GunHandler.shooting.remove(ctx.getServerHandler().playerEntity);
                    }
                }
            });

            return null;
        }
    }
}
