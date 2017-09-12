package io.github.Theray070696.rayadd.network;

import io.github.Theray070696.rayadd.tile.TileLoopingJukebox;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 2/3/2017.
 */
public class PacketButtonPressed implements IMessage
{
    private int dimID;
    private int x;
    private int y;
    private int z;
    private int buttonID;
    
    public PacketButtonPressed() {}
    
    public PacketButtonPressed(int dimID, int x, int y, int z, int buttonID)
    {
        this.dimID = dimID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.buttonID = buttonID;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.dimID = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.buttonID = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.dimID);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.buttonID);
    }
    
    public static class Handler implements IMessageHandler<PacketButtonPressed, IMessage>
    {
        @Override
        public IMessage onMessage(PacketButtonPressed message, MessageContext ctx)
        {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    World world = DimensionManager.getWorld(message.dimID);

                    if(world.isRemote)
                    {
                        return;
                    }

                    TileEntity tile = world.getTileEntity(new BlockPos(message.x, message.y, message.z));

                    if(tile != null)
                    {
                        if(tile instanceof TileLoopingJukebox)
                        {
                            if(message.buttonID == 0) // Play record.
                            {
                                ((TileLoopingJukebox) tile).playRecord();
                            } else if(message.buttonID == 1) // Stop record.
                            {
                                ((TileLoopingJukebox) tile).stopRecord();
                            }
                        }
                    }
                }
            });
            
            return null;
        }
    }
}
