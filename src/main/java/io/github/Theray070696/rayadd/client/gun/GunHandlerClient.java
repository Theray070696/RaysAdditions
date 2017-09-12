package io.github.Theray070696.rayadd.client.gun;

import io.github.Theray070696.rayadd.gun.GunHandler;
import io.github.Theray070696.rayadd.util.LogHelper;
import net.minecraft.client.Minecraft;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class GunHandlerClient extends GunHandler
{
    public static float currentUtilityZoom = 1.0F;
    public static float currentGunZoom = 1.0F;

    public static int currentUtilityZoomIndex = 0;

    public static float lastUtilityZoom;
    public static float lastGunZoom;

    public static int lastUtilityZoomSlot = 0;
    public static int lastGunZoomSlot = 0;

    public static int currentZoomDelay = 0;

    public static boolean zoomEnabled = false;
    public static boolean sniperZoomedIn = false;

    public static final float MAX_ZOOMS[] =
    {
            1.0F, 0.5F, 0.25F, 0.125F, 0.0625F
    };

    public static double currentRecoilV = 0.0D;
    public static double currentRecoilH = 0.0D;

    public static void useZoom()
    {
        if(currentZoomDelay == 0)
        {
            currentUtilityZoomIndex = (currentUtilityZoomIndex + 1) % MAX_ZOOMS.length;
        }
    }

    public static void toggleZoomEnabled()
    {
        if(currentZoomDelay == 0)
        {
            zoomEnabled = !zoomEnabled;
        }
    }

    public static void handleRecoil(Minecraft minecraft)
    {
        double d = 0.0D;
        double d1 = currentRecoilV;

        if(minecraft.thePlayer != null && currentRecoilV > 0.0D)
        {
            d = Math.min(Math.max(currentRecoilV * 1.25D, 1.0D), currentRecoilV);
            currentRecoilV -= d;
            minecraft.thePlayer.rotationPitch -= d;
        }

        if(minecraft.thePlayer != null && Math.abs(currentRecoilH) > 0.0D)
        {
            double d2;

            if(currentRecoilH > 0.0D)
            {
                d2 = Math.min(Math.max((currentRecoilH) / 1.5D, 1.0D), currentRecoilH);
            } else
            {
                d2 = Math.max(Math.min((currentRecoilH) / 1.5D, -1.0D), currentRecoilH);
            }

            if(d != 0.0D)
            {
                double d3 = (d / d1) * currentRecoilH;

                if(currentRecoilH > 0.0D)
                {
                    d2 = Math.min(d3, d2);
                } else
                {
                    d2 = Math.max(d3, d2);
                }
            }

            currentRecoilH -= d2;
            minecraft.thePlayer.rotationYaw -= d2;
        }
    }
}
