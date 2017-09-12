package io.github.Theray070696.rayadd.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.github.Theray070696.rayadd.RaysAdditions;
import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.gun.GunHandler;
import io.github.Theray070696.rayadd.gun.GunTools;
import io.github.Theray070696.rayadd.item.gun.ItemGun;
import io.github.Theray070696.rayadd.item.gun.ItemGunMinigun;
import io.github.Theray070696.rayadd.item.gun.ItemGunSniper;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import java.util.List;

public abstract class EntityBullet extends Entity implements IEntityAdditionalSpawnData
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

    public EntityBullet(World world)
    {
        super(world);
        this.soundRangeFactor = 8F;
        this.serverSoundPlayed = false;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = null;
        this.inGround = false;
        this.timeInAir = 0;
        setSize(0.0625F, 0.03125F);
    }

    public EntityBullet(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
        this.serverSpawned = true;
    }

    public abstract void playServerSound(World world);

    public EntityBullet(World world, Entity entity, ItemGun itemgun)
    {
        this(world);
        this.owner = entity;
        this.damage = itemgun.damage;

        setLocationAndAngles(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);

        this.posX -= MathHelper.cos((this.rotationYaw / 180F) * (float) Math.PI) * 0.16F;
        this.posY -= 0.1D;
        this.posZ -= MathHelper.sin((this.rotationYaw / 180F) * (float) Math.PI) * 0.16F;

        setPosition(this.posX, this.posY, this.posZ);

        float f7 = itemgun.spread;

        if(entity instanceof EntityLiving)
        {
            boolean flag = Math.abs(entity.motionX) > 0.1D || Math.abs(entity.motionY) > 0.1D || Math.abs(entity.motionZ) > 0.1D;

            if(flag)
            {
                f7 *= 2.0F;

                if(itemgun instanceof ItemGunMinigun)
                {
                    f7 *= 2.0F;
                }
            }

            if(!entity.onGround)
            {
                f7 *= 2.0F;

                if(itemgun instanceof ItemGunMinigun)
                {
                    f7 *= 2.0F;
                }
            }

            if((entity instanceof EntityPlayer) && (itemgun instanceof ItemGunSniper))
            {
                EntityPlayer entityplayer = (EntityPlayer) entity;

                if(flag)
                {
                    f7 = (float) (f7 + 0.25D);
                }

                if(!entity.onGround)
                {
                    f7 = (float) (f7 + 0.25D);
                }

                if(!entityplayer.isSneaking())
                {
                    f7 = (float) (f7 + 0.25D);
                }

                if(!GunHandler.getSniperZoomedIn(entityplayer))
                {
                    f7 = 8F;
                }
            }
        }

        if(entity.isBeingRidden() && (entity instanceof EntityPlayer))
        {
            this.owner = entity.getControllingPassenger();
        }

        this.motionX = -MathHelper.sin((this.rotationYaw / 180F) * (float) Math.PI) * MathHelper.cos((this.rotationPitch / 180F) * (float) Math.PI);
        this.motionZ = MathHelper.cos((this.rotationYaw / 180F) * (float) Math.PI) * MathHelper.cos((this.rotationPitch / 180F) * (float) Math.PI);
        this.motionY = -MathHelper.sin((this.rotationPitch / 180F) * (float) Math.PI);

        setBulletHeading(this.motionX, this.motionY, this.motionZ, itemgun.muzzleVelocity, f7 / 2.0F);

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

        this.motionX += d2;
        this.motionY += d3;
        this.motionZ += d4;
    }

    @Override
    protected void entityInit()
    {
    }

    public void setBulletHeading(double d, double d1, double d2, float spread, float speed)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);

        d /= f2;
        d1 /= f2;
        d2 /= f2;

        d += this.rand.nextGaussian() * 0.005D * speed;
        d1 += this.rand.nextGaussian() * 0.005D * speed;
        d2 += this.rand.nextGaussian() * 0.005D * speed;

        d *= spread;
        d1 *= spread;
        d2 *= spread;

        this.motionX = d;
        this.motionY = d1;
        this.motionZ = d2;

        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);

        this.prevRotationYaw = this.rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float) ((Math.atan2(d1, f3) * 180D) / Math.PI);

        this.timeInTile = 0;
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

        this.canRender = true;

        if(this.serverSpawned && !this.serverSoundPlayed)
        {
            if(!this.worldObj.isRemote || this.owner != RaysAdditions.proxy.getClientPlayer())
            {
                playServerSound(this.worldObj);
                this.serverSoundPlayed = true;
            }
        }

        if(this.timeInAir == 200)
        {
            setEntityDead();
        }

        if(this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

            this.prevRotationYaw = this.rotationYaw = (float) ((Math.atan2(this.motionX, this.motionZ) * 180D) / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) ((Math.atan2(this.motionY, f) * 180D) / Math.PI);
        }

        if(this.inGround)
        {
            Block i = this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock();

            if(i != this.inTile)
            {
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2F;
                this.motionY *= this.rand.nextFloat() * 0.2F;
                this.motionZ *= this.rand.nextFloat() * 0.2F;
                this.timeInTile = 0;
                this.timeInAir = 0;
            } else
            {
                this.timeInTile++;

                if(this.timeInTile == 200)
                {
                    setEntityDead();
                }

                return;
            }
        } else
        {
            this.timeInAir++;
        }

        Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult rayTraceResult = this.worldObj.rayTraceBlocks(vec3d, vec3d1, false, true, false);
        vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if(rayTraceResult != null)
        {
            vec3d1 = new Vec3d(rayTraceResult.hitVec.xCoord, rayTraceResult.hitVec.yCoord, rayTraceResult.hitVec.zCoord);
        }

        Entity entity = null;
        List list = this.worldObj.getEntitiesInAABBexcluding(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ)
                .expandXyz(1.0D), Predicates.and(new Predicate[]{EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>()
        {
            public boolean apply(@Nullable Entity p_apply_1_)
            {
                return p_apply_1_.canBeCollidedWith();
            }
        }}));
        double d = 0.0D;

        for(Object aList : list)
        {
            Entity entity1 = (Entity) aList;

            if(!entity1.canBeCollidedWith() || (entity1 == this.owner || this.owner != null && entity1 == this.owner.getRidingEntity() || this
                    .owner != null && this.owner.getControllingPassenger() == entity1) && this.timeInAir < 5 || this.serverSpawned)
            {
                continue;
            }

            AxisAlignedBB entityBoundingBox = entity1.getEntityBoundingBox().expandXyz(0.3F);
            RayTraceResult rayTraceResult1 = entityBoundingBox.calculateIntercept(vec3d, vec3d1);

            if(rayTraceResult1 == null)
            {
                continue;
            }

            double d1 = vec3d.distanceTo(rayTraceResult1.hitVec);

            if(d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }

        if(entity != null)
        {
            rayTraceResult = new RayTraceResult(entity);
        }

        if(rayTraceResult != null)
        {
            Block k = null;
            if(rayTraceResult.getBlockPos() != null)
            {
                k = worldObj.getBlockState(rayTraceResult.getBlockPos()).getBlock();
            }

            if(rayTraceResult.entityHit != null || (k != null && !k.equals(Blocks.TALLGRASS)))
            {
                if(rayTraceResult.entityHit != null)
                {
                    int l = this.damage;

                    if((this.owner instanceof IMob) && (rayTraceResult.entityHit instanceof EntityPlayer))
                    {
                        if(this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
                        {
                            l = 0;
                        }

                        if(this.worldObj.getDifficulty() == EnumDifficulty.EASY)
                        {
                            l = l / 3 + 1;
                        }

                        if(this.worldObj.getDifficulty() == EnumDifficulty.HARD)
                        {
                            l = (l * 3) / 2;
                        }
                    }

                    if(rayTraceResult.entityHit instanceof EntityLiving)
                    {
                        GunTools.attackEntityIgnoreDelay((EntityLiving) rayTraceResult.entityHit, DamageSource.causeThrownDamage(this, owner), l);
                    } else
                    {
                        rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, owner), l);
                    }
                } else
                {
                    BlockPos blockPos = rayTraceResult.getBlockPos();
                    this.xTile = blockPos.getX();
                    this.yTile = blockPos.getY();
                    this.zTile = blockPos.getZ();

                    this.inTile = k;

                    this.motionX = (float) (rayTraceResult.hitVec.xCoord - this.posX);
                    this.motionY = (float) (rayTraceResult.hitVec.yCoord - this.posY);
                    this.motionZ = (float) (rayTraceResult.hitVec.zCoord - this.posZ);

                    float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);

                    this.posX -= (this.motionX / f2) * 0.05D;
                    this.posY -= (this.motionY / f2) * 0.05D;
                    this.posZ -= (this.motionZ / f2) * 0.05D;
                    this.inGround = true;

                    if(ConfigHandler.bulletsDestroyGlass && (this.inTile == Blocks.GLASS || this.inTile == Blocks.GLASS_PANE || this.inTile ==
                            Blocks.STAINED_GLASS || this.inTile == Blocks.STAINED_GLASS_PANE || this.inTile.getDefaultState().getMaterial().equals
                            (Material.GLASS)))
                    {
                        this.worldObj.destroyBlock(blockPos, false);
                    }
                }

                SoundHandler.playSoundName("rayadd:gun.impact", this.worldObj, SoundCategory.PLAYERS, this.getPosition(), 0.2F, 1.0F / (rand
                        .nextFloat() * 0.1F + 0.95F));
                setEntityDead();
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) ((Math.atan2(this.motionX, this.motionZ) * 180D) / Math.PI);

        for(this.rotationPitch = (float) ((Math.atan2(this.motionY, f1) * 180D) / Math.PI); this.rotationPitch - this.prevRotationPitch < -180F;
            this.prevRotationPitch -= 360F)
        {
        }

        for(; this.rotationPitch - this.prevRotationPitch >= 180F; this.prevRotationPitch += 360F)
        {
        }

        for(; this.rotationYaw - this.prevRotationYaw < -180F; this.prevRotationYaw -= 360F)
        {
        }

        for(; this.rotationYaw - prevRotationYaw >= 180F; this.prevRotationYaw += 360F)
        {
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f3 = 1.0F;
        float f5 = 0.0F;

        if(handleWaterMovement())
        {
            for(int i1 = 0; i1 < 4; i1++)
            {
                float f6 = 0.25F;
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) f6, this.posY - this.motionY * (double) f6, this.posZ - this.motionZ * (double) f6, this.motionX, this.motionY, this.motionZ);
            }

            f3 = 0.8F;
            f5 = 0.03F;
        }

        this.motionX *= f3;
        this.motionY *= f3;
        this.motionZ *= f3;
        this.motionY -= f5;
        setPosition(this.posX, this.posY, this.posZ);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setShort("xTile", (short) this.xTile);
        tagCompound.setShort("yTile", (short) this.yTile);
        tagCompound.setShort("zTile", (short) this.zTile);
        ResourceLocation resourcelocation = (ResourceLocation) Block.REGISTRY.getNameForObject(this.inTile);
        tagCompound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        tagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound)
    {
        this.xTile = tagCompound.getShort("xTile");
        this.yTile = tagCompound.getShort("yTile");
        this.zTile = tagCompound.getShort("zTile");
        if(tagCompound.hasKey("inTile", 8))
        {
            this.inTile = Block.getBlockFromName(tagCompound.getString("inTile"));
        } else
        {
            this.inTile = Block.getBlockById(tagCompound.getByte("inTile") & 255);
        }
        this.inGround = tagCompound.getByte("inGround") == 1;
    }

    @Override
    public void writeSpawnData(ByteBuf data)
    {
        if(this.owner == null)
        {
            ByteBufUtils.writeUTF8String(data, "null");
        } else
        {
            ByteBufUtils.writeUTF8String(data, this.owner.getName());
        }
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        try
        {
            String name = ByteBufUtils.readUTF8String(data);

            for(Object obj : this.worldObj.playerEntities)
            {
                if(((EntityPlayer) obj).getName().equals(name))
                {
                    this.owner = (EntityPlayer) obj;
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
        this.owner = null;
    }
}
