package io.github.Theray070696.rayadd.item;

import io.github.Theray070696.rayadd.lib.ModInfo;
import io.github.Theray070696.raycore.item.ItemRay;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class ItemRayAdd extends ItemRay
{
    public ItemRayAdd()
    {
        this(true);
    }
    
    public ItemRayAdd(boolean addToCreativeTab)
    {
        super(addToCreativeTab, ModInfo.MOD_ID);
    }
}
