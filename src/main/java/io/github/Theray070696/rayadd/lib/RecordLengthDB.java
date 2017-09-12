package io.github.Theray070696.rayadd.lib;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Theray070696 on 2/3/2017.
 */
public class RecordLengthDB
{
    // This will hold the length of all vanilla records and some mod records in ticks.
    // To calculate, take length of sound file in seconds, then multiply by 20 (number of ticks per second)
    
    public static Map<Item, Integer> recordLengthMap = new HashMap<Item, Integer>();
    
    public static void initRecordLengths()
    {
        initVanillaRecordLengths();
        
        initModRecordLengths();
    }
    
    private static void initVanillaRecordLengths()
    {
        recordLengthMap.put(Items.RECORD_11, 1386);
        recordLengthMap.put(Items.RECORD_13, 3561);
        recordLengthMap.put(Items.RECORD_BLOCKS, 6918);
        recordLengthMap.put(Items.RECORD_CAT, 3706);
        recordLengthMap.put(Items.RECORD_CHIRP, 3711);
        recordLengthMap.put(Items.RECORD_FAR, 3489);
        recordLengthMap.put(Items.RECORD_MALL, 3944);
        recordLengthMap.put(Items.RECORD_MELLOHI, 1923);
        recordLengthMap.put(Items.RECORD_STAL, 3017);
        recordLengthMap.put(Items.RECORD_STRAD, 3763);
        recordLengthMap.put(Items.RECORD_WAIT, 4757);
        recordLengthMap.put(Items.RECORD_WARD, 5027);
    }
    
    private static void initModRecordLengths()
    {

    }
}
