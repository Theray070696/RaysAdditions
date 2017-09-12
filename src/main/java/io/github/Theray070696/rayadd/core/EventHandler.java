package io.github.Theray070696.rayadd.core;

import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.gun.GunHandler;
import io.github.Theray070696.rayadd.item.ModItems;
import io.github.Theray070696.rayadd.item.gun.ItemGun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Theray070696 on 5/18/2017.
 */
@SuppressWarnings("unused")
public class EventHandler
{
    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event)
    {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        if(FMLCommonHandler.instance().getEffectiveSide().isServer())
        {
            for(Object obj : server.getPlayerList().getPlayerList())
            {
                if(obj instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer)obj;

                    if(!player.worldObj.isRemote && GunHandler.shooting.contains(player))
                    {
                        if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemGun)
                        {
                            ItemGun gun = (ItemGun) player.getHeldItemMainhand().getItem();

                            if(ItemGun.canFire(player.getHeldItemMainhand()))
                            {
                                ItemGun.addDelay(player.getHeldItemMainhand());
                                gun.fireBullet(player.worldObj, player, player.getHeldItemMainhand());
                            }
                        } else
                        {
                            GunHandler.shooting.remove(player);
                        }
                    }
                }
            }
        }

        Set<ItemStack> toRemove = new HashSet<ItemStack>();

        Iterator iterator = server.getPlayerList().getPlayerList().iterator();

        /*do
        {
            if(!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP) iterator.next();
            ItemStack itemstack = entityplayermp.inventory.armorInventory[2];

            if(itemstack != null && itemstack.getItem().equals(ModItems.itemScubaTank))
            {
                entityplayermp.setAir(300);
            }
        }
        while(true);*/

        GunHandler.handleReload();

        /*for(Object obj : server.getConfigurationManager().playerEntityList)
        {
            if(obj instanceof EntityPlayerMP)
            {
                EntityPlayerMP player = (EntityPlayerMP) obj;
                GunHandler.handleJetPack(player.worldObj, player);
            }
        }*/
    }

    @SubscribeEvent
    public void cloneEvent(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
        if(event.getEntityPlayer().worldObj.getGameRules().getBoolean("keepInventory"))
        {
            NBTTagCompound oldData = getTagSafe(event.getOriginal().getEntityData(), EntityPlayer.PERSISTED_NBT_TAG);
            NBTTagCompound newData = getTagSafe(event.getEntityPlayer().getEntityData(), EntityPlayer.PERSISTED_NBT_TAG);

            newData.setBoolean(TAG_PLAYER_HAS_DEAGLE, oldData.getBoolean(TAG_PLAYER_HAS_DEAGLE));

            event.getEntityPlayer().getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, newData);
        }
    }

    @SubscribeEvent
    public void respawnEvent(PlayerEvent.PlayerRespawnEvent event)
    {
        if(ConfigHandler.spawnWithDeagle)
        {
            NBTTagCompound playerData = event.player.getEntityData();
            NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);

            if(!event.player.worldObj.getGameRules().getBoolean("keepInventory") || !data.getBoolean(TAG_PLAYER_HAS_DEAGLE))
            {
                ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack(ModItems.itemGunDeagle));
                ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack(ModItems.itemBulletMedium, 8));
                data.setBoolean(TAG_PLAYER_HAS_DEAGLE, true);
                playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
            }
        }
    }

    public static final String TAG_PLAYER_HAS_DEAGLE = "rayadd.spawned_deagle";

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if(ConfigHandler.spawnWithDeagle)
        {
            NBTTagCompound playerData = event.player.getEntityData();
            NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);

            if(!data.getBoolean(TAG_PLAYER_HAS_DEAGLE))
            {
                ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack(ModItems.itemGunDeagle));
                ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack(ModItems.itemBulletMedium, 8));
                data.setBoolean(TAG_PLAYER_HAS_DEAGLE, true);
                playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
            }
        }
    }

    private static NBTTagCompound getTagSafe(NBTTagCompound tag, String key)
    {
        if(tag == null || !tag.hasKey(key))
        {
            return new NBTTagCompound();
        }

        return tag.getCompoundTag(key);
    }
}
