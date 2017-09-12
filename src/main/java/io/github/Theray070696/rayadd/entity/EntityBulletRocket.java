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
public class EntityBulletRocket extends EntityBullet
{
    public EntityBulletRocket(World world)
    {
        super(world);
        this.setSize(0.25F, 0.25F);
    }

    public EntityBulletRocket(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        this.setSize(0.25F, 0.25F);
    }

    public EntityBulletRocket(World world, Entity entity, ItemGun itemgun)
    {
        super(world, entity, itemgun);
        this.setSize(0.25F, 0.25F);
    }

    @Override
    public void playServerSound(World world)
    {
        SoundHandler.playSoundName(((ItemGun) ModItems.itemGunRocketLauncher).firingSound, world, SoundCategory.PLAYERS, this.getPosition(), 1.0F,
                1.0F / (this.rand.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public void onUpdate()
    {
        this.onEntityUpdate();

        this.canRender = true;

        if(this.timeInAir == 200)
        {
            this.explode();
            return;
        }

        if(this.timeInAir % 2 == 0)
        {
            double d = 0.625D;
            double d1 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ + this.motionY * this.motionY);
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX - (this.motionX / d1) * d, this.posY - (this.motionY / d1) * d,
                    this.posZ - (this.motionZ / d1) * d, 0.0D, 0.0D, 0.0D);
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
                        .expandXyz(1.0D),
                Predicates.and(EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>()
                {
                    public boolean apply(@Nullable Entity p_apply_1_)
                    {
                        return p_apply_1_.canBeCollidedWith();
                    }
                }));
        double d2 = 0.0D;

        for(Object aList : list)
        {
            Entity entity1 = (Entity) aList;

            if(!entity1.canBeCollidedWith() || (entity1 == this.owner || this.owner != null && entity1 == this.owner.getRidingEntity()) && this
                    .timeInAir < 5 || this.serverSpawned)
            {
                continue;
            }

            AxisAlignedBB entityBoundingBox = entity1.getEntityBoundingBox().expandXyz(0.3F);
            RayTraceResult rayTraceResult1 = entityBoundingBox.calculateIntercept(vec3d, vec3d1);

            if(rayTraceResult1 == null)
            {
                continue;
            }

            double d3 = vec3d.distanceTo(rayTraceResult1.hitVec);

            if(d3 < d2 || d2 == 0.0D)
            {
                entity = entity1;
                d2 = d3;
            }
        }

        if(entity != null)
        {
            rayTraceResult = new RayTraceResult(entity);
        }

        if(rayTraceResult != null && !this.worldObj.isRemote)
        {
            Block k = null;
            if(rayTraceResult.getBlockPos() != null)
            {
                k = this.worldObj.getBlockState(rayTraceResult.getBlockPos()).getBlock();
            }

            if(rayTraceResult.entityHit != null || k != null && !k.equals(Blocks.TALLGRASS) && !k.equals(Blocks.VINE))
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
                        GunTools.attackEntityIgnoreDelay((EntityLiving) rayTraceResult.entityHit, DamageSource.causeThrownDamage(this, this.owner),
                                l);
                    } else
                    {
                        rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.owner), l);
                    }
                }

                explode();
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) ((Math.atan2(this.motionX, this.motionZ) * 180D) / Math.PI);

        for(this.rotationPitch = (float) ((Math.atan2(this.motionY, f) * 180D) / Math.PI); this.rotationPitch - this.prevRotationPitch < -180F;
            this.prevRotationPitch -= 360F)
        {
        }

        for(; this.rotationPitch - this.prevRotationPitch >= 180F; this.prevRotationPitch += 360F)
        {
        }

        for(; this.rotationYaw - this.prevRotationYaw < -180F; this.prevRotationYaw -= 360F)
        {
        }

        for(; this.rotationYaw - this.prevRotationYaw >= 180F; this.prevRotationYaw += 360F)
        {
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f1 = 1.002557F;
        float f3 = 0.0F;

        if(handleWaterMovement())
        {
            for(int i1 = 0; i1 < 4; i1++)
            {
                float f4 = 0.25F;
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) f4, this.posY - this.motionY *
                        (double) f4, this.posZ - this.motionZ * (double) f4, this.motionX, this.motionY, this.motionZ);
            }

            f1 = 0.95F;
            f3 = 0.03F;
        }

        this.motionX *= f1;
        this.motionY *= f1;
        this.motionZ *= f1;
        this.motionY -= f3;
        setPosition(this.posX, this.posY, this.posZ);
    }

    private void explode()
    {
        Explosion explosion = new Explosion(this.worldObj, null, this.posX, this.posY, this.posZ, 3F, false, true);
        explosion.doExplosionA();

        if(ConfigHandler.explosionsDestroyBlocks)
        {
            explosion.doExplosionB(true);
        } else
        {
            this.worldObj.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F +
                    (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        }

        RaysAdditions.network.sendToAllAround(new PacketRocketExplode(this.posX, this.posY, this.posZ), new NetworkRegistry.TargetPoint(this
                .worldObj.provider.getDimension(), this.posX, this.posY, this.posZ, 40));

        setEntityDead();
    }
}
