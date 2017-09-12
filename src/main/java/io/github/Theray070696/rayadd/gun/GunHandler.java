package io.github.Theray070696.rayadd.gun;

import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.item.gun.ItemGun;
import io.github.Theray070696.rayadd.item.gun.classic.ItemGunClassic;
import io.github.Theray070696.rayadd.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.*;

/**
 * Created by Theray070696 on 5/17/2017.
 */
public class GunHandler
{
    public static Map reloadTimes = new HashMap();
    public static Map isSniperZoomedIn = new HashMap();

    public static Set<EntityPlayer> shooting = new HashSet<EntityPlayer>();

    public static void handleReloadClassic()
    {
        for(Iterator iterator = reloadTimes.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            int i = ((Integer)entry.getValue()).intValue() - 1;

            if(i <= 0)
            {
                iterator.remove();
            } else
            {
                entry.setValue(Integer.valueOf(i));
            }
        }
    }

    public static void handleReload()
    {
        for(Iterator iterator = reloadTimes.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            int i = ((Integer)entry.getValue()).intValue() - 1;

            if(i <= 0)
            {
                iterator.remove();
            } else
            {
                entry.setValue(Integer.valueOf(i));
            }
        }
    }

    public static ItemStack handleReload(World world, EntityPlayer entityplayer, boolean flag)
    {
        if(!reloadTimes.containsKey(entityplayer))
        {
            ItemStack itemstack = entityplayer.getHeldItemMainhand();

            if(itemstack != null && (itemstack.getItem() instanceof ItemGun))
            {
                Item item = ((ItemGun) itemstack.getItem()).requiredMag;
                int i = -1;
                int j = -1;
                int k;
                boolean flag1 = false;

                if(item == null)
                {
                    return null;
                }

                do
                {
                    k = -1;
                    InventoryPlayer inventoryplayer = entityplayer.inventory;

                    for(int l = i + 1; l < inventoryplayer.mainInventory.length; l++)
                    {
                        if(inventoryplayer.mainInventory[l] == null || !inventoryplayer.mainInventory[l].getItem().equals(item))
                        {
                            continue;
                        }

                        if(i == -1)
                        {
                            j = inventoryplayer.mainInventory[l].stackSize;

                            if(!flag && item.getMaxDamage() == 0 && j == item.getItemStackLimit(itemstack))
                            {
                                ItemStack stack = inventoryplayer.mainInventory[l];
                                inventoryplayer.mainInventory[l] = null;
                                return stack;
                            }
                        } else
                        {
                            if(!flag1)
                            {
                                reload(world, entityplayer);
                                flag1 = true;
                            }

                            k = inventoryplayer.mainInventory[l].stackSize;
                            int j1 = Math.min(item.getItemStackLimit(itemstack) - j, k);
                            j += j1;
                            k -= j1;
                            inventoryplayer.mainInventory[i].stackSize = j;
                            inventoryplayer.mainInventory[l].stackSize = k;

                            if(k == 0)
                            {
                                inventoryplayer.mainInventory[l] = null;
                            }

                            ItemStack stack = inventoryplayer.mainInventory[l];
                            inventoryplayer.mainInventory[l] = null;

                            return stack;
                        }

                        if(i == -1)
                        {
                            i = l;
                        }
                    }

                    if(i == -1)
                    {
                        break;
                    }

                    if(flag1 || !flag)
                    {
                        continue;
                    }

                    reload(world, entityplayer);
                    break;
                } while(k != -1 && (item.getMaxDamage() != 0 || j != item.getItemStackLimit(itemstack)) && (item.getMaxDamage() <= 0 || j != item.getMaxDamage() + 1));
            }
        }

        return null;
    }

    public static void handleReloadClassic(World world, EntityPlayer entityplayer, boolean flag)
    {
        if(!reloadTimes.containsKey(entityplayer))
        {
            ItemStack itemstack = entityplayer.getHeldItemMainhand();

            if(itemstack != null && (itemstack.getItem() instanceof ItemGunClassic))
            {
                Item item = ((ItemGunClassic) itemstack.getItem()).requiredBullet;
                int i = -1;
                int j = -1;
                int k;
                boolean flag1 = false;

                if(item == null)
                {
                    return;
                }

                do
                {
                    k = -1;
                    InventoryPlayer inventoryplayer = entityplayer.inventory;

                    for(int l = i + 1; l < inventoryplayer.mainInventory.length; l++)
                    {
                        if(inventoryplayer.mainInventory[l] == null || !inventoryplayer.mainInventory[l].getItem().equals(item))
                        {
                            continue;
                        }

                        if(item.getMaxDamage() > 0)
                        {
                            int i1 = item.getMaxDamage() + 1;

                            if(i == -1)
                            {
                                j = i1 - inventoryplayer.mainInventory[l].getItemDamage();

                                if (!flag && item.getMaxDamage() > 0 && j == item.getMaxDamage() + 1)
                                {
                                    break;
                                }
                            } else
                            {
                                if(!flag1)
                                {
                                    reload(world, entityplayer);
                                    flag1 = true;
                                }

                                k = i1 - inventoryplayer.mainInventory[l].getItemDamage();
                                int k1 = Math.min(i1 - j, k);
                                j += k1;
                                k -= k1;
                                inventoryplayer.mainInventory[i].setItemDamage(i1 - j);
                                inventoryplayer.mainInventory[l].setItemDamage(i1 - k);

                                if(k == 0)
                                {
                                    inventoryplayer.mainInventory[l] = new ItemStack(Items.BUCKET);
                                }

                                break;
                            }
                        } else if(i == -1)
                        {
                            j = inventoryplayer.mainInventory[l].stackSize;

                            if(!flag && item.getMaxDamage() == 0 && j == item.getItemStackLimit(itemstack))
                            {
                                break;
                            }
                        } else
                        {
                            if(!flag1)
                            {
                                reload(world, entityplayer);
                                flag1 = true;
                            }

                            k = inventoryplayer.mainInventory[l].stackSize;
                            int j1 = Math.min(item.getItemStackLimit(itemstack) - j, k);
                            j += j1;
                            k -= j1;
                            inventoryplayer.mainInventory[i].stackSize = j;
                            inventoryplayer.mainInventory[l].stackSize = k;

                            if(k == 0)
                            {
                                inventoryplayer.mainInventory[l] = null;
                            }

                            break;
                        }

                        if(i == -1)
                        {
                            i = l;
                        }
                    }

                    if(i == -1)
                    {
                        break;
                    }

                    if(flag1 || !flag)
                    {
                        continue;
                    }

                    reload(world, entityplayer);
                    break;
                } while(k != -1 && (item.getMaxDamage() != 0 || j != item.getItemStackLimit(itemstack)) && (item.getMaxDamage() <= 0 || j != item.getMaxDamage() + 1));
            }
        }
    }

    public static void reload(World world, EntityPlayer entityplayer)
    {
        SoundHandler.playSoundName("rayadd:gun.reload", world, SoundCategory.PLAYERS, entityplayer.getPosition(), 1.0F, 1.0F / (entityplayer.getRNG().nextFloat() * 0.1F + 0.95F));
        reloadTimes.put(entityplayer, Integer.valueOf(40));
    }

    public static boolean getSniperZoomedIn(EntityPlayer entityplayer)
    {
        Boolean boolean1 = (Boolean)isSniperZoomedIn.get(entityplayer);
        return boolean1 == null ? false : boolean1.booleanValue();
    }
}