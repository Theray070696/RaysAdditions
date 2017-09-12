package io.github.Theray070696.rayadd.item.gun.classic;

import io.github.Theray070696.rayadd.RaysAdditions;
import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassic;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.gun.GunHandler;
import io.github.Theray070696.rayadd.gun.GunTools;
import io.github.Theray070696.rayadd.item.ItemRayAdd;
import io.github.Theray070696.rayadd.network.PacketRecoil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class ItemGunClassic extends ItemRayAdd
{
    public String firingSound;
    public Item requiredBullet;
    public int useDelay;
    public int numBullets;
    public int damage;
    public float muzzleVelocity;
    public float spread;
    public float recoil;
    public int soundDelay;
    public float soundRangeFactor;
    protected long lastSound;
    protected long lastEmptySound;

    public ItemGunClassic()
    {
        super();
        numBullets = 1;
        damage = 0;
        muzzleVelocity = 1.5F;
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
        if(!GunHandler.reloadTimes.containsKey(entity))
        {
            int ammoUsed;

            if(entity instanceof EntityPlayer)
            {
            	boolean creative = ((EntityPlayer) entity).capabilities.isCreativeMode;
                ammoUsed = GunTools.useItemInInventory((EntityPlayer) entity, requiredBullet, !creative);
            } else
            {
                ammoUsed = 1;
            }

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
                    for(int j = 0; j < numBullets; j++)
                    {
                        EntityBulletClassic bullet = getBulletEntity(world, entity);

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
                    if(ammoUsed == 2 && !(this instanceof ItemGunClassicMinigun) /*&& !(this instanceof ItemGunLaser)*/)
                    {
                        GunHandler.handleReloadClassic(world, (EntityPlayer) entity, true);
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

    public abstract EntityBulletClassic getBulletEntity(World world, Entity entity);

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
		tag.setInteger("delay", ((ItemGunClassic) itemStack.getItem()).useDelay);
		itemStack.setTagCompound(tag);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean advanced)
    {
        list.add("Uses " + I18n.format(requiredBullet.getUnlocalizedName() + ".name") + "s as ammo");
        list.add("Damage: " + damage);

        if(this.useDelay <= 0)
        {
            list.add("Insane fire rate");
        } else if(this.useDelay == 1)
        {
            list.add("Very high fire rate");
        } else if(this.useDelay >= 2 && this.useDelay < 4)
        {
            list.add("High fire rate");
        } else if(this.useDelay >= 4 && this.useDelay < 10)
        {
            list.add("Medium fire rate");
        } else if(this.useDelay >= 10 && this.useDelay < 25)
        {
            list.add("Low fire rate");
        } else if(this.useDelay >= 25)
        {
            list.add("Very low fire rate");
        }
    }
}
