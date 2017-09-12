package io.github.Theray070696.rayadd.tile;

import io.github.Theray070696.rayadd.lib.RecordLengthDB;
import io.github.Theray070696.raycore.item.ItemRayRecord;
import io.github.Theray070696.raycore.tile.TileInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundEvent;

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
        if(isPlayingRecord)
        {
            if(getStackInSlot(0) == null || recordItemStack == null || !getStackInSlot(0).equals(recordItemStack))
            {
                stopRecord();
            }
            
            if(recordLength != -1)
            {
                playTime++;
    
                if(playTime >= recordLength)
                {
                    stopRecord();
                    playRecord();
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
        if(getStackInSlot(0) != null && getStackInSlot(0).getItem() instanceof ItemRecord)
        {
            recordItemStack = getStackInSlot(0);
            Item item = recordItemStack.getItem();

            worldObj.playEvent(1010, pos, Item.getIdFromItem(item));
            isPlayingRecord = true;
            playTime = 0;
            
            if(item instanceof ItemRayRecord)
            {
                recordLength = ((ItemRayRecord) item).recordLength;
            } else if(RecordLengthDB.recordLengthMap.containsKey(item))
            {
                recordLength = RecordLengthDB.recordLengthMap.get(item);
            } else
            {
                recordLength = -1; // Record will only play once if there's no entry for the record.
            }
        }
    }
    
    public void stopRecord()
    {
        worldObj.playEvent(1010, pos, 0);
        worldObj.playRecord(pos, (SoundEvent)null);
        isPlayingRecord = false;
        playTime = 0;
        recordLength = -1;
        recordItemStack = null;
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
