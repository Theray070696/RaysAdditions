package io.github.Theray070696.rayadd.block;

import io.github.Theray070696.rayadd.lib.ModInfo;
import io.github.Theray070696.raycore.block.BlockRayContainer;
import net.minecraft.block.material.Material;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public abstract class BlockRayAddContainer extends BlockRayContainer
{
    public BlockRayAddContainer()
    {
        this(true);
    }

    public BlockRayAddContainer(boolean addToCreativeTab)
    {
        this(Material.ROCK, addToCreativeTab);
    }

    public BlockRayAddContainer(Material material)
    {
        this(material, true);
    }

    public BlockRayAddContainer(Material material, boolean addToCreativeTab)
    {
        super(material, addToCreativeTab, ModInfo.MOD_ID);
    }
}
