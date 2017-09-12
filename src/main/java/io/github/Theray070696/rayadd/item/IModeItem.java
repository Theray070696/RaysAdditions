package io.github.Theray070696.rayadd.item;

import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 2/3/2017.
 */
public interface IModeItem
{
    public long getHighlightStart();
    
    public void setHighlightStart(long time);
    
    public String getModeName(ItemStack itemstack);
    
    public String getModeDisplayName(ItemStack itemstack);
    
    public String getModeInfomation(ItemStack itemstack);
}
