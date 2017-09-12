package io.github.Theray070696.rayadd.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.github.Theray070696.rayadd.RaysAdditions;
import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.gun.GunTools;
import io.github.Theray070696.rayadd.item.ModItems;
import io.github.Theray070696.rayadd.item.gun.ItemGun;
import io.github.Theray070696.rayadd.network.PacketRocketExplode;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Theray070696 on 5/19/2017.
 */
public class EntityBulletRocketLaser extends EntityBullet
{
    protected static final float MAX_AIMING_ANGLE = 30F;
    protected static final float MAX_TURNING_ANGLE = 10F;

    public EntityBulletRocketLaser(World world)
    {
        super(world);
        setSize(0.25F, 0.25F);
    }

    public EntityBulletRocketLaser(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setSize(0.25F, 0.25F);
    }

    public EntityBulletRocketLaser(World world, Entity entity, ItemGun itemgun)
    {
        super(world, entity, itemgun);
        setSize(0.25F, 0.25F);
    }

    @Override
    public void playServerSound(World world)
    {
        //world.playSoundAtEntity(this, ((ItemGun) ModItems.itemGunRocketLauncherLaser).firingSound, ((ItemGun) ModItems.itemGunRocketLauncherLaser).soundRangeFactor, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
        SoundHandler.playSoundName(((ItemGun) ModItems.itemGunRocketLauncherLaser).firingSound, world, SoundCategory.PLAYERS, this.getPosition(), 1.0F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public void onUpdate()
    {
        onEntityUpdate();

        canRender = true;

        if(timeInAir > 5)
        {
            laserAim();
        }

        if(timeInAir == 200)
        {
            explode();
            return;
        }

        if(timeInAir % 2 == 0)
        {
            double d = 0.625D;
            double d1 = Math.sqrt(motionX * motionX + motionZ * motionZ + motionY * motionY);
            worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX - (motionX / d1) * d, posY - (motionY / d1) * d, posZ - (motionZ / d1) * d, 0.0D, 0.0D, 0.0D);
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
        double d2 = 0.0D;

        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);

            if(!entity1.canBeCollidedWith() || (entity1 == owner || owner != null && entity1 == owner.getRidingEntity()) && timeInAir < 5 || serverSpawned)
            {
                continue;
            }

            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f2, f2, f2);
            RayTraceResult movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

            if(movingobjectposition1 == null)
            {
                continue;
            }

            double d3 = vec3d.distanceTo(movingobjectposition1.hitVec);

            if(d3 < d2 || d2 == 0.0D)
            {
                entity = entity1;
                d2 = d3;
            }
        }

        if(entity != null)
        {
            movingobjectposition = new RayTraceResult(entity);
        }

        if(movingobjectposition != null && !worldObj.isRemote)
        {
            Block k = null;
            if(movingobjectposition.getBlockPos() != null)
            {
                k = worldObj.getBlockState(movingobjectposition.getBlockPos()).getBlock();
            }

            if(movingobjectposition.entityHit != null || k != null && !k.equals(Blocks.TALLGRASS) && !k.equals(Blocks.VINE))
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
                }

                explode();
            }
        }

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float) ((Math.atan2(motionX, motionZ) * 180D) / Math.PI);

        for(rotationPitch = (float) ((Math.atan2(motionY, f) * 180D) / Math.PI); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {}

        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {}

        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {}

        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {}

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 1.0F;
        float f3 = 0.0F;

        if(handleWaterMovement())
        {
            for(int i1 = 0; i1 < 4; i1++)
            {
                float f4 = 0.25F;
                worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * (double) f4, posY - motionY * (double) f4, posZ - motionZ * (double) f4, motionX, motionY, motionZ);
            }

            f1 = 0.95F;
            f3 = 0.03F;
        }

        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        motionY -= f3;
        setPosition(posX, posY, posZ);
    }

    private void explode()
    {
        Explosion explosion = new Explosion(worldObj, null, posX, posY, posZ, 3F, false, true);
        explosion.doExplosionA();

        if(ConfigHandler.explosionsDestroyBlocks)
        {
            explosion.doExplosionB(true);
        } else
        {
            worldObj.playSound((EntityPlayer) null, posX, posY, posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        }

        RaysAdditions.network.sendToAllAround(new PacketRocketExplode(posX, posY, posZ), new NetworkRegistry.TargetPoint(worldObj.provider.getDimension(), posX, posY, posZ, 40));

        setEntityDead();
    }

    private void laserAim()
    {
        if(owner != null)
        {
            double d = posX - owner.posX;
            double d1 = posY - owner.posY - (double) ((owner instanceof EntityPlayer) ? 0.0F : owner.getEyeHeight());
            double d2 = posZ - owner.posZ;
            float f = owner.rotationPitch;
            float f1 = (float) (Math.atan(Math.sqrt(d * d + d2 * d2) / d1) * (180D / Math.PI));

            if(d1 >= 0.0D)
            {
                f1 -= 90F;
            } else
            {
                f1 += 90F;
            }

            float f2 = f - f1;

            if(f2 > 0.0F)
            {
                f2 = Math.min(f2, 30F);
            } else
            {
                f2 = Math.max(f2, -30F);
            }

            f2 *= 3F;
            float f3 = f1 + f2;

            if(f3 < -90F)
            {
                f3 = -90F;
            } else if(f3 > 90F)
            {
                f3 = 90F;
            }

            float f4 = (owner.rotationYaw) % 360F;

            if(f4 < -180F)
            {
                f4 += 360F;
            }

            if(f4 < 0.0F)
            {
                f4 *= -1F;
            } else if(f4 < 180F)
            {
                f4 *= -1F;
            } else
            {
                f4 = 360F - f4;
            }

            float f5;

            if(d >= 0.0D && d2 >= 0.0D)
            {
                f5 = (float) (Math.atan(Math.abs(d / d2)) * (180D / Math.PI));
            } else if(d >= 0.0D && d2 <= 0.0D)
            {
                f5 = 90F + (float) (Math.atan(Math.abs(d2 / d)) * (180D / Math.PI));
            } else if(d <= 0.0D && d2 >= 0.0D)
            {
                f5 = -(90F - (float) (Math.atan(Math.abs(d2 / d)) * (180D / Math.PI)));
            } else
            {
                f5 = -(180F - (float) (Math.atan(Math.abs(d / d2)) * (180D / Math.PI)));
            }

            float f6 = f5 - f4;

            if(f6 > 180F)
            {
                f6 -= 360F;
            } else if(f6 < -180F)
            {
                f6 += 360F;
            }

            if(f6 > 30F)
            {
                f6 = 30F;
            } else if(f6 < -30F)
            {
                f6 = -30F;
            }

            f6 *= 3F;
            float f7 = f5 - f6;

            if(f7 > 180F)
            {
                f7 -= 360F;
            } else if(f7 < -180F)
            {
                f7 += 360F;
            }

            turnTowards(f3, f7);
        }
    }

    private void turnTowards(float f, float f1)
    {
        float f2 = rotationYaw;
        float f3 = f1 - f2;

        if(f3 > 180F)
        {
            f3 -= 360F;
        } else if(f3 < -180F)
        {
            f3 += 360F;
        }

        if(f3 > 10F)
        {
            f3 = 10F;
        } else if(f3 < -10F)
        {
            f3 = -10F;
        }

        float f4 = f2 + f3;

        if(f4 > 180F)
        {
            f4 -= 360F;
        } else if(f4 < -180F)
        {
            f4 += 360F;
        }

        float f5 = Math.abs(f4);

        if(f4 < -90F)
        {
            f5 = f4 + 180F;
        } else if(f4 < 0.0F)
        {
            f5 = f4 + 90F;
        } else if(f4 > 90F)
        {
            f5 -= 90F;
        }

        float f6 = rotationPitch * -1F;
        float f7 = f - f6;

        if(f7 > 90F)
        {
            f7 -= 180F;
        } else if(f7 < -90F)
        {
            f7 += 180F;
        }

        if(f7 > 10F)
        {
            f7 = 10F;
        } else if(f7 < -10F)
        {
            f7 = -10F;
        }

        float f8 = f6 + f7;

        if(f8 > 90F)
        {
            f8 -= 180F;
        } else if(f8 < -90F)
        {
            f8 += 180F;
        }

        double d = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        float f9 = Math.abs(f8);
        motionY = Math.sin((double) f9 / (180D / Math.PI)) * d;

        if(f8 > 0.0F)
        {
            motionY *= -1D;
        }

        double d1 = Math.cos((double) f9 / (180D / Math.PI)) * d;

        if(f4 <= -90F)
        {
            motionX = -Math.sin((double) f5 / (180D / Math.PI)) * d1;
            motionZ = -Math.cos((double) f5 / (180D / Math.PI)) * d1;
        } else if(f4 <= 0.0F)
        {
            motionX = -Math.cos((double) f5 / (180D / Math.PI)) * d1;
            motionZ = Math.sin((double) f5 / (180D / Math.PI)) * d1;
        } else if(f4 <= 90F)
        {
            motionX = Math.sin((double) f5 / (180D / Math.PI)) * d1;
            motionZ = Math.cos((double) f5 / (180D / Math.PI)) * d1;
        } else
        {
            motionX = Math.cos((double) f5 / (180D / Math.PI)) * d1;
            motionZ = -Math.sin((double) f5 / (180D / Math.PI)) * d1;
        }
    }
}
