package io.github.Theray070696.rayadd.block;

import io.github.Theray070696.rayadd.tile.TileLoopingJukebox;
import io.github.Theray070696.rayadd.util.LogHelper;
import io.github.Theray070696.raycore.api.block.RayBlockRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class ModBlocks
{
    public static BlockRayAddContainer blockLoopingJukebox;
    
    public static void initBlocks()
    {
        LogHelper.info("Loading Blocks");
        
        //GameRegistry.registerBlock(blockLoopingJukebox, "blockLoopingJukebox");

        blockLoopingJukebox = RayBlockRegistry.register(new BlockLoopingJukebox());
        
        GameRegistry.registerTileEntity(TileLoopingJukebox.class, "tileLoopingJukebox");
        
        LogHelper.info("Block Loading Complete");
    }
}
