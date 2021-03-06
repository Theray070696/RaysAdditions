package io.github.Theray070696.rayadd.core;

import io.github.Theray070696.rayadd.block.BlockLoopingJukebox;
import io.github.Theray070696.rayadd.client.gui.container.GuiLoopingJukebox;
import io.github.Theray070696.rayadd.container.ContainerLoopingJukebox;
import io.github.Theray070696.rayadd.lib.GuiIds;
import io.github.Theray070696.rayadd.tile.TileLoopingJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by Theray070696 on 3/31/2016.
 */
public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        IBlockState blockState = world.getBlockState(new BlockPos(x, y, z));

        if(ID == GuiIds.LOOPING_JUKEBOX_GUI_ID && blockState.getBlock() instanceof BlockLoopingJukebox)
        {
            return new ContainerLoopingJukebox(player.inventory, ((TileLoopingJukebox) world.getTileEntity(new BlockPos(x, y, z))));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        IBlockState blockState = world.getBlockState(new BlockPos(x, y, z));

        if(ID == GuiIds.LOOPING_JUKEBOX_GUI_ID && blockState.getBlock() instanceof BlockLoopingJukebox)
        {
            return new GuiLoopingJukebox(new ContainerLoopingJukebox(player.inventory, ((TileLoopingJukebox) world.getTileEntity(new BlockPos(x, y, z)))));
        }

        return null;
    }
}
