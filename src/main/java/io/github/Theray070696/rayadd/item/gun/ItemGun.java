package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.RaysAdditions;
import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.gun.GunHandler;
import io.github.Theray070696.rayadd.gun.GunTools;
import io.github.Theray070696.rayadd.item.ItemRayAdd;
import io.github.Theray070696.rayadd.network.PacketRecoil;
import io.github.Theray070696.rayadd.util.LogHelper;
import io.github.Theray070696.raycore.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public abstract class ItemGun extends ItemRayAdd
{
    public String firingSound;
    public ItemMag requiredMag;
    public int useDelay;
    public float spread;
    public float recoil;
    public int soundDelay;
    public float soundRangeFactor;
    protected long lastSound;
    protected long lastEmptySound;
    public ItemStack currentMag;

    public ItemGun()
    {
        super();
        spread = 1.0F;
        recoil = 1.0F;
        soundDelay = -1;
        soundRangeFactor = 4F;
        lastSound = 0L;
        lastEmptySound = 0L;
        setMaxStackSize(1);
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
    	if(!world.isRemote)
    	{
    		if(!stack.hasTagCompound() || stack.getTagCompound() == null)
    		{ 
    			return;
    		}
    		
    		if(stack.getTagCompound().getInteger("delay") > 0)
    		{
    		    NBTTagCompound tag = stack.getTagCompound();
                tag.setInteger("delay", tag.getInteger("delay") - 1);

    			stack.setTagCompound(tag);
    		}
    	}
    }

    public boolean fireBullet(World world, Entity entity, ItemStack itemstack)
    {
        if(currentMag == null || !(currentMag.getItem() instanceof ItemMag) || ((ItemMag) currentMag.getItem()).getInventory() == null)
        {
            currentMag = GunHandler.handleReload(world, (EntityPlayer) entity, true);
            return false;
        }

        if(!GunHandler.reloadTimes.containsKey(entity))
        {
            int ammoUsed;
            ItemBullet bulletUsed;

            Pair<Integer, ItemBullet> pair = GunTools.useItemInMag(((ItemMag) currentMag.getItem()).getInventory(), requiredMag.bulletCaliber, !((EntityPlayer) entity).capabilities.isCreativeMode);

            ammoUsed = pair.getA();
            bulletUsed = pair.getB();

            if(ammoUsed > 0)
            {
                if(world.getWorldTime() - lastSound < 0L)
                {
                    lastSound = world.getWorldTime() - (long) soundDelay;
                }

                if(soundDelay == 0 || lastSound == 0L || world.getWorldTime() - lastSound > (long) soundDelay)
                {
                    SoundHandler.playSoundName(firingSound, entity.worldObj, SoundCategory.PLAYERS, entity.getPosition(), 1.0F, 1.0F / (itemRand.nextFloat() * 0.1F + 0.95F));
                    lastSound = world.getWorldTime();
                }

                if(!world.isRemote)
                {
                    for(int j = 0; j < bulletUsed.numProjectiles; j++)
                    {
                        EntityBullet bullet = getBulletEntity(world, entity, bulletUsed);

                        if(bullet != null)
                        {
                            world.spawnEntityInWorld(bullet);
                        }
                    }

                    EntityBulletCasing bulletCasing = getBulletCasingEntity(world, entity);

                    if((entity instanceof EntityPlayer) && ConfigHandler.ammoCasings && bulletCasing != null)
                    {
                        world.spawnEntityInWorld(bulletCasing);
                    }

                    if(entity instanceof EntityPlayerMP)
                    {
                        RaysAdditions.network.sendTo(new PacketRecoil(recoil, recoil / 2), (EntityPlayerMP) entity);
                    }
                }

                if(entity instanceof EntityPlayer)
                {
                    if(ammoUsed == 2 /*&& !(this instanceof ItemGunMinigun) && !(this instanceof ItemGunLaser)*/)
                    {
                        GunHandler.handleReload(world, (EntityPlayer) entity, true);
                    }
                }

                return true;
            }

            if(lastEmptySound == 0L || world.getWorldTime() - lastEmptySound > 20L)
            {
                SoundHandler.playSoundName("rayadd:gun.empty", entity.worldObj, SoundCategory.PLAYERS, entity.getPosition(), 1.0F, 1.0F / (itemRand.nextFloat() * 0.1F + 0.95F));
                lastEmptySound = world.getWorldTime();
            }
        }

        return false;
    }

    @Override
    public boolean isFull3D()
    {
        return true;
    }

    public abstract EntityBullet getBulletEntity(World world, Entity entity, ItemBullet bullet);

    public abstract EntityBulletCasing getBulletCasingEntity(World world, Entity entity);
    
    public static boolean canFire(ItemStack itemStack)
    {
		if(!itemStack.hasTagCompound() || itemStack.getTagCompound() == null)
		{ 
			return true;
		}

		return itemStack.getTagCompound().getInteger("delay") == 0;
    }
    
    public static void addDelay(ItemStack itemStack)
    {
		if(!itemStack.hasTagCompound() || itemStack.getTagCompound() == null)
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}

		NBTTagCompound tag = itemStack.getTagCompound();
		tag.setInteger("delay", ((ItemGun) itemStack.getItem()).useDelay);
		itemStack.setTagCompound(tag);
    }
}
