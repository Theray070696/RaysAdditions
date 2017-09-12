package io.github.Theray070696.rayadd.block;

import io.github.Theray070696.rayadd.RaysAdditions;
import io.github.Theray070696.rayadd.lib.GuiIds;
import io.github.Theray070696.rayadd.tile.TileLoopingJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class BlockLoopingJukebox extends BlockRayAddContainer
{
    public BlockLoopingJukebox()
    {
        super();
        
        this.setUnlocalizedName("blockLoopingJukebox");
    }
    
    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileLoopingJukebox();
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            if(world.getTileEntity(blockPos) != null && world.getTileEntity(blockPos) instanceof TileLoopingJukebox)
            {
                player.openGui(RaysAdditions.INSTANCE, GuiIds.LOOPING_JUKEBOX_GUI_ID, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
                return true;
            }
            return false;
        }
        return false;
    }
    
    @Override
    public void breakBlock(World world, BlockPos blockPos, IBlockState blockState)
    {
        world.playEvent(1010, blockPos, 0);
        world.playRecord(blockPos, (SoundEvent)null);
        
        super.breakBlock(world, blockPos, blockState);
    }
}
