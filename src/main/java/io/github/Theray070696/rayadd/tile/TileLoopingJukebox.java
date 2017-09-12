package io.github.Theray070696.rayadd.tile;

import io.github.Theray070696.rayadd.lib.RecordLengthDB;
import io.github.Theray070696.raycore.item.ItemRayRecord;
import io.github.Theray070696.raycore.tile.TileInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class TileLoopingJukebox extends TileInventory implements ITickable
{
    private int playTime;
    private int recordLength = -1;
    private boolean isPlayingRecord = false;
    private ItemStack recordItemStack = null;
    
    @Override
    public void update()
    {
        if(this.isPlayingRecord)
        {
            if(this.getStackInSlot(0) == null || this.recordItemStack == null || !this.getStackInSlot(0).equals(this.recordItemStack))
            {
                this.stopRecord();
            }
            
            if(this.recordLength != -1)
            {
                this.playTime++;
    
                if(this.playTime >= this.recordLength)
                {
                    this.stopRecord();
                    this.playRecord();
                }
            }
        }
    }
    
    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return stack.getItem() instanceof ItemRecord;
    }

    public void playRecord()
    {
        if(this.getStackInSlot(0) != null && this.getStackInSlot(0).getItem() instanceof ItemRecord)
        {
            this.recordItemStack = this.getStackInSlot(0);
            Item item = this.recordItemStack.getItem();

            this.worldObj.playEvent(1010, pos, Item.getIdFromItem(item));
            this.isPlayingRecord = true;
            this.playTime = 0;
            
            if(item instanceof ItemRayRecord)
            {
                this.recordLength = ((ItemRayRecord) item).recordLength;
            } else if(RecordLengthDB.recordLengthMap.containsKey(item))
            {
                this.recordLength = RecordLengthDB.recordLengthMap.get(item);
            } else
            {
                this.recordLength = -1; // Record will only play once if there's no entry for the record.
            }
        }
    }
    
    public void stopRecord()
    {
        this.worldObj.playEvent(1010, this.pos, 0);
        this.worldObj.playRecord(this.pos, null);
        this.isPlayingRecord = false;
        this.playTime = 0;
        this.recordLength = -1;
        this.recordItemStack = null;
    }

    @Override
    public String getName()
    {
        return "Looping Jukebox";
    }

    @Override
    public boolean hasCustomName()
    {
        return true;
    }
}
