package io.github.Theray070696.rayadd.configuration;

import io.github.Theray070696.rayadd.RaysAdditions;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class ConfigHandler
{
    private static Configuration config;

    public static boolean bulletsDestroyGlass;
    public static boolean ammoRestrictions;
    public static boolean ammoCasings;
    public static boolean explosionsDestroyBlocks;
    public static boolean showAmmoBar;
    public static boolean spawnWithDeagle;
    public static boolean classicMode;

    public static final boolean bulletsDestroyGlassDefault = true;
    public static final boolean ammoRestrictionsDefault = true;
    public static final boolean ammoCasingsDefault = true;
    public static final boolean explosionsDestroyBlocksDefault = true;
    public static final boolean showAmmoBarDefault = true;
    public static final boolean spawnWithDeagleDefault = true;
    public static final boolean classicModeDefault = true;

    public static void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        bulletsDestroyGlass = config.getBoolean("Bullets Destroy Glass", "General", bulletsDestroyGlassDefault, "Whether or not bullets should destroy glass. Currently only effects vanilla glass.");
        ammoRestrictions = config.getBoolean("Ammo Restrictions", "General", ammoRestrictionsDefault, "If true, empty casings can be picked up to reclaim into iron.");
        ammoCasings = config.getBoolean("Ammo Casings", "General", ammoCasingsDefault, "Whether or not ammo casings should drop.");
        explosionsDestroyBlocks = config.getBoolean("Explosions Destroy Blocks", "General", explosionsDestroyBlocksDefault, "Whether or not Rocket and Grenade explosions destroy blocks.");
        spawnWithDeagle = config.getBoolean("Spawn with Deagle", "General", spawnWithDeagleDefault, "Whether or not the player should spawn with a Desert Eagle and ammo.");
        classicMode = config.getBoolean("Classic Gun Mode", "General", classicModeDefault, "If true, the mod load items and stats from the original SDKs Guns mod. If false, the mod will break the everything. Don't try it. Changing this will erase many items and blocks from existing worlds!");

        if(RaysAdditions.proxy.getSide().equals(Side.CLIENT))
        {
            showAmmoBar = config.getBoolean("Show Ammo Bar", "General", showAmmoBarDefault, "Whether or not to show the ammo bar in the UI.");
        }

        saveConfig();
    }

    public static void saveConfig()
    {
        if(config != null)
        {
            config.save();
        }
    }
}
