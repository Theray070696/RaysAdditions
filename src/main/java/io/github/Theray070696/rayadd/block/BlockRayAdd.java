package io.github.Theray070696.rayadd.block;

import io.github.Theray070696.rayadd.lib.ModInfo;
import io.github.Theray070696.raycore.block.BlockRay;
import net.minecraft.block.material.Material;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class BlockRayAdd extends BlockRay
{
    public BlockRayAdd()
    {
        this(true);
    }

    public BlockRayAdd(boolean addToCreativeTab)
    {
        this(Material.ROCK, addToCreativeTab);
    }

    public BlockRayAdd(Material material)
    {
        this(material, true);
    }

    public BlockRayAdd(Material material, boolean addToCreativeTab)
    {
        super(material, addToCreativeTab, ModInfo.MOD_ID);
    }
}
