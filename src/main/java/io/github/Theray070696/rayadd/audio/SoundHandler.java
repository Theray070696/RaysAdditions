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
    // Guns

    public static SoundEvent empty;
    public static SoundEvent reload;
    public static SoundEvent impact;

    public static SoundEvent ak47;
    public static SoundEvent deagle;
    public static SoundEvent m4;
    public static SoundEvent minigun;
    public static SoundEvent mp5;
    public static SoundEvent sg552;
    public static SoundEvent shotgun;
    public static SoundEvent sniper;
    public static SoundEvent rocket;

    public static void init()
    {
        // Guns

        empty = register("gun.empty");
        reload = register("gun.reload");
        impact = register("gun.impact");

        ak47 = register("gun.ak47");
        deagle = register("gun.deagle");
        m4 = register("gun.m4");
        minigun = register("gun.minigun");
        mp5 = register("gun.mp5");
        sg552 = register("gun.sg552");
        shotgun = register("gun.shotgun");
        sniper = register("gun.sniper");
        rocket = register("gun.rocket");
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
