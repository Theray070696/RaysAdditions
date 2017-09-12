package io.github.Theray070696.rayadd.client.core;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class KeybindHandler
{
    public static KeyBinding reloadKey;
    public static KeyBinding zoomKey;

    public static void init()
    {
        reloadKey = new KeyBinding("Reload", Keyboard.KEY_R, "Rays Additions Keybinds");
        zoomKey = new KeyBinding("Zoom", Keyboard.KEY_Z, "Rays Additions Keybinds");

        ClientRegistry.registerKeyBinding(reloadKey);
        ClientRegistry.registerKeyBinding(zoomKey);
    }
}
