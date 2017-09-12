package io.github.Theray070696.rayadd;

import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.block.ModBlocks;
import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.core.CraftingHandler;
import io.github.Theray070696.rayadd.core.GuiHandler;
import io.github.Theray070696.rayadd.item.ModItems;
import io.github.Theray070696.rayadd.lib.ModInfo;
import io.github.Theray070696.rayadd.lib.RecordLengthDB;
import io.github.Theray070696.rayadd.network.PacketButtonPressed;
import io.github.Theray070696.rayadd.plugins.PluginHandler;
import io.github.Theray070696.rayadd.proxy.IProxy;
import io.github.Theray070696.rayadd.util.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Theray070696 on 1/22/2017.
 */
@SuppressWarnings("unused")
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, dependencies = ModInfo.DEPENDENCIES)
public class RaysAdditions
{
    @Mod.Instance(ModInfo.MOD_ID)
    public static RaysAdditions INSTANCE;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.SERVER_PROXY)
    public static IProxy proxy;
    
    public static SimpleNetworkWrapper network;

    public RaysAdditions()
    {
        PluginHandler.getInstance().registerBuiltInPlugins();
    }
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Pre-Init");

        ConfigHandler.loadConfig(event);

        SoundHandler.init();
    
        ModBlocks.initBlocks();
        ModItems.initItems();

        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.CHANNEL);
        network.registerMessage(PacketButtonPressed.Handler.class, PacketButtonPressed.class, 0, Side.SERVER);

        PluginHandler.getInstance().preInit();

        LogHelper.info("Pre-Init Complete");
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("Init");

        proxy.init(event);
    
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
    
        CraftingHandler.initCraftingRecipes();

        PluginHandler.getInstance().init();
        
        LogHelper.info("Init Complete");
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Post-Init");

        RecordLengthDB.initRecordLengths();

        PluginHandler.getInstance().postInit();
        
        LogHelper.info("Post-Init Complete");
    }
}
