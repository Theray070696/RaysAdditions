package io.github.Theray070696.rayadd.audio;

import io.github.Theray070696.rayadd.lib.ModInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Theray070696 on 3/23/2017.
 */
public class SoundHandler
{
    public static void init()
    {

    }

    public static SoundEvent register(String name)
    {
        ResourceLocation loc = new ResourceLocation(ModInfo.MOD_ID, name);
        SoundEvent e = new SoundEvent(loc);
        GameRegistry.register(e, loc);

        return e;
    }

    public static void playSoundName(String soundName, World world, SoundCategory category, BlockPos pos)
    {
        playSoundName(soundName, world, category, pos, 1.0F, 1.0F);
    }

    public static void playSoundName(String soundName, World world, SoundCategory category, BlockPos pos, float volume, float pitch)
    {
        SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(soundName));

        if(sound != null)
        {
            world.playSound(null, pos, sound, category, volume, pitch);
        }
    }
}
