package io.github.Theray070696.rayadd.entity.classic;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.github.Theray070696.rayadd.RaysAdditions;
import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.gun.GunHandler;
import io.github.Theray070696.rayadd.gun.GunTools;
import io.github.Theray070696.rayadd.item.gun.classic.ItemGunClassic;
import io.github.Theray070696.rayadd.item.gun.classic.ItemGunClassicMinigun;
import io.github.Theray070696.rayadd.item.gun.classic.ItemGunClassicSniper;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;

public abstract class EntityBulletClassic extends Entity implements IEntityAdditionalSpawnData
{
	public boolean canRender = false;
	
    protected int xTile;
    protected int yTile;
    protected int zTile;
    
    protected Block inTile;
    
    protected boolean inGround;
    
    public Entity owner;
    
    protected int timeInTile;
    protected int timeInAir;
    
    protected int damage;
    
    protected boolean serverSpawned;
    
    protected String firingSound;
    
    protected float soundRangeFactor;
    
    protected boolean serverSoundPlayed;

    public EntityBulletClassic(World world)
    {
        super(world);
        soundRangeFactor = 8F;
        serverSoundPlayed = false;
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = null;
        inGround = false;
        timeInAir = 0;
        setSize(0.0625F, 0.03125F);
    }

    public EntityBulletClassic(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
        serverSpawned = true;
    }

    public abstract void playServerSound(World world);

    public EntityBulletClassic(World world, Entity entity, ItemGunClassic itemgun)
    {
        this(world);
        owner = entity;
        damage = itemgun.damage;
        
        setLocationAndAngles(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);
        
        posX -= MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        posY -= 0.1D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        
        setPosition(posX, posY, posZ);
        
        float f7 = itemgun.spread;

        if(entity instanceof EntityLiving)
        {
            boolean flag = Math.abs(entity.motionX) > 0.1D || Math.abs(entity.motionY) > 0.1D || Math.abs(entity.motionZ) > 0.1D;

            if(flag)
            {
                f7 *= 2.0F;

                if(itemgun instanceof ItemGunClassicMinigun)
                {
                    f7 *= 2.0F;
                }
            }

            if(!entity.onGround)
            {
                f7 *= 2.0F;

                if(itemgun instanceof ItemGunClassicMinigun)
                {
                    f7 *= 2.0F;
                }
            }

            if((entity instanceof EntityPlayer) && (itemgun instanceof ItemGunClassicSniper))
            {
                EntityPlayer entityplayer = (EntityPlayer)entity;

                if(flag)
                {
                    f7 = (float)(f7 + 0.25D);
                }

                if(!entity.onGround)
                {
                    f7 = (float)(f7 + 0.25D);
                }

                if(!entityplayer.isSneaking())
                {
                    f7 = (float)(f7 + 0.25D);
                }

                if(!GunHandler.getSniperZoomedIn(entityplayer))
                {
                    f7 = 8F;
                }
            }
        }

        if(entity.isBeingRidden() && (entity instanceof EntityPlayer))
        {
            owner = entity.getControllingPassenger();
        }

        motionX = -MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI);
        motionZ = MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI);
        motionY = -MathHelper.sin((rotationPitch / 180F) * (float)Math.PI);
        
        setBulletHeading(motionX, motionY, motionZ, itemgun.muzzleVelocity, f7 / 2.0F);
        
        double d2 = 0.0D;
        double d3 = 0.0D;
        double d4 = 0.0D;

        if(entity.isRiding() && entity.getRidingEntity() != null)
        {
            d2 = entity.getRidingEntity().motionX;
            d3 = entity.getRidingEntity().onGround ? 0.0D : entity.getRidingEntity().motionY;
            d4 = entity.getRidingEntity().motionZ;
        } else if(entity.isBeingRidden())
        {
            d2 = entity.motionX;
            d3 = entity.onGround ? 0.0D : entity.motionY;
            d4 = entity.motionZ;
        }

        motionX += d2;
        motionY += d3;
        motionZ += d4;
    }

    @Override
    protected void entityInit() {}

    public void setBulletHeading(double d, double d1, double d2, float spread, float speed)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        
        d += rand.nextGaussian() * 0.005D * speed;
        d1 += rand.nextGaussian() * 0.005D * speed;
        d2 += rand.nextGaussian() * 0.005D * speed;
        
        d *= spread;
        d1 *= spread;
        d2 *= spread;
        
        motionX = d;
        motionY = d1;
        motionZ = d2;
        
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / Math.PI);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / Math.PI);
        
        timeInTile = 0;
    }

    @Override
    public boolean isInRangeToRenderDist(double d)
    {
        return true;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        
        canRender = true;

        if(serverSpawned && !serverSoundPlayed)
        {
        	if(!worldObj.isRemote || owner != RaysAdditions.proxy.getClientPlayer())
        	{
                playServerSound(worldObj);
                serverSoundPlayed = true;
        	}
        }

        if(timeInAir == 200)
        {
            setEntityDead();
        }

        if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            
            prevRotationYaw = rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / Math.PI);
        }

        if(inGround)
        {
            Block i = worldObj.getBlockState(new BlockPos(xTile, yTile, zTile)).getBlock();

            if(i != inTile)
            {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                timeInTile = 0;
                timeInAir = 0;
            } else
            {
                timeInTile++;

                if(timeInTile == 200)
                {
                    setEntityDead();
                }

                return;
            }
        } else
        {
            timeInAir++;
        }

        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        Vec3d vec3d1 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
        RayTraceResult movingobjectposition = worldObj.rayTraceBlocks(vec3d, vec3d1, false, true, false);
        vec3d = new Vec3d(posX, posY, posZ);
        vec3d1 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

        if(movingobjectposition != null)
        {
            vec3d1 = new Vec3d(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        Entity entity = null;
        List list = worldObj.getEntitiesInAABBexcluding(this, getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expandXyz(1.0D), Predicates.and(new Predicate[] {EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>()
        {
            public boolean apply(@Nullable Entity p_apply_1_)
            {
                return p_apply_1_.canBeCollidedWith();
            }
        }}));
        double d = 0.0D;

        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity) list.get(j);

            if(!entity1.canBeCollidedWith() || (entity1 == owner || owner != null && entity1 == owner.getRidingEntity() || owner != null && owner.getControllingPassenger() == entity1) && timeInAir < 5 || serverSpawned)
            {
                continue;
            }
            
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.3F);
            RayTraceResult movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

            if(movingobjectposition1 == null)
            {
                continue;
            }

            double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);

            if(d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }

        if(entity != null)
        {
            movingobjectposition = new RayTraceResult(entity);
        }

        if(movingobjectposition != null)
        {
            Block k = null;
            if(movingobjectposition.getBlockPos() != null)
            {
                k = worldObj.getBlockState(movingobjectposition.getBlockPos()).getBlock();
            }

            if(movingobjectposition.entityHit != null || (k != null && !k.equals(Blocks.TALLGRASS)))
            {
                if(movingobjectposition.entityHit != null)
                {
                    int l = damage;

                    if((owner instanceof IMob) && (movingobjectposition.entityHit instanceof EntityPlayer))
                    {
                        if(worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
                        {
                            l = 0;
                        }

                        if(worldObj.getDifficulty() == EnumDifficulty.EASY)
                        {
                            l = l / 3 + 1;
                        }

                        if(worldObj.getDifficulty() == EnumDifficulty.HARD)
                        {
                            l = (l * 3) / 2;
                        }
                    }

                    if(movingobjectposition.entityHit instanceof EntityLiving)
                    {
                        GunTools.attackEntityIgnoreDelay((EntityLiving) movingobjectposition.entityHit, DamageSource.causeThrownDamage(this, owner), l);
                    } else
                    {
                        movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, owner), l);
                    }
                } else
                {
                    BlockPos blockPos = movingobjectposition.getBlockPos();
                    xTile = blockPos.getX();
                    yTile = blockPos.getY();
                    zTile = blockPos.getZ();
                    
                    inTile = k;
                    
                    motionX = (float) (movingobjectposition.hitVec.xCoord - posX);
                    motionY = (float) (movingobjectposition.hitVec.yCoord - posY);
                    motionZ = (float) (movingobjectposition.hitVec.zCoord - posZ);
                    
                    float f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                    
                    posX -= (motionX / f2) * 0.05D;
                    posY -= (motionY / f2) * 0.05D;
                    posZ -= (motionZ / f2) * 0.05D;
                    inGround = true;

                    if(ConfigHandler.bulletsDestroyGlass && (inTile == Blocks.GLASS || inTile == Blocks.GLASS_PANE || inTile == Blocks.STAINED_GLASS || inTile == Blocks.STAINED_GLASS_PANE))
                    {
                        /*Block block;

                        if(inTile == Blocks.GLASS)
                        {
                            block = Blocks.GLASS;
                        } else
                        {
                            block = Blocks.GLASS_PANE;
                        }*/

                        worldObj.destroyBlock(blockPos, false);
                        //WarTools.minecraft.effectRenderer.addBlockDestroyEffects(blockPos, block.getDefaultState());
                        //worldObj.playSound(null, xTile + 0.5F, yTile + 0.5F, zTile + 0.5F, block.getSoundType().getBreakSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);
                        //WarTools.minecraft.sndManager.playSound(block.stepSound.getBreakSound(), (float)xTile + 0.5F, (float)yTile + 0.5F, (float)zTile + 0.5F, (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                        //worldObj.setBlockToAir(new BlockPos(xTile, yTile, zTile));
                        //block.onBlockDestroyedByPlayer(worldObj, xTile, yTile, zTile, worldObj.getBlockMetadata(xTile, yTile, zTile));
                    }
                }

                //worldObj.playSoundAtEntity(this, "rayadd:gun.impact", 0.2F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
                SoundHandler.playSoundName("rayadd:gun.impact", worldObj, SoundCategory.PLAYERS, this.getPosition(), 0.2F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
                setEntityDead();
            }
        }

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);

        for(rotationPitch = (float)((Math.atan2(motionY, f1) * 180D) / Math.PI); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {}

        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {}

        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {}

        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {}

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f3 = 1.0F;
        float f5 = 0.0F;

        if(handleWaterMovement())
        {
            for(int i1 = 0; i1 < 4; i1++)
            {
                float f6 = 0.25F;
                worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * (double)f6, posY - motionY * (double)f6, posZ - motionZ * (double)f6, motionX, motionY, motionZ);
            }

            f3 = 0.8F;
            f5 = 0.03F;
        }

        motionX *= f3;
        motionY *= f3;
        motionZ *= f3;
        motionY -= f5;
        setPosition(posX, posY, posZ);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)xTile);
        nbttagcompound.setShort("yTile", (short)yTile);
        nbttagcompound.setShort("zTile", (short)zTile);
        ResourceLocation resourcelocation = (ResourceLocation) Block.REGISTRY.getNameForObject(this.inTile);
        nbttagcompound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        nbttagcompound.setByte("inGround", (byte)(inGround ? 1 : 0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
        if(nbttagcompound.hasKey("inTile", 8))
        {
            inTile = Block.getBlockFromName(nbttagcompound.getString("inTile"));
        } else
        {
            inTile = Block.getBlockById(nbttagcompound.getByte("inTile") & 255);
        }
        inGround = nbttagcompound.getByte("inGround") == 1;
    }
    
	@Override
	public void writeSpawnData(ByteBuf data)
	{
		if(owner == null)
		{
            ByteBufUtils.writeUTF8String(data, "null");
		} else
        {
            ByteBufUtils.writeUTF8String(data, owner.getName());
		}
	}

	@Override
	public void readSpawnData(ByteBuf data)
	{
		try
        {
			String name = ByteBufUtils.readUTF8String(data);
			
			for(Object obj : worldObj.playerEntities)
			{
				if(((EntityPlayer) obj).getName().equals(name))
				{
					owner = (EntityPlayer) obj;
				}
			}
		} catch(Exception e)
        {
			setEntityDead();
		}
	}

    public void setEntityDead()
    {
        super.setDead();
        owner = null;
    }
}
