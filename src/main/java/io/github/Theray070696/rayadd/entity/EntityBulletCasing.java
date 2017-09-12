package io.github.Theray070696.rayadd.entity;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.configuration.ConfigHandler;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityBulletCasing extends Entity
{
    public Item droppedItem;
    private int xTile;
    private int yTile;
    private int zTile;
    private Block inTile;
    private boolean inGround;
    public Entity owner;
    private int timeInTile;
    private int timeInAir;
    private boolean createdByPlayer;

    public EntityBulletCasing(World world)
    {
        super(world);
        droppedItem = ModItems.itemBulletCasing;
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = null;
        inGround = false;
        timeInAir = 0;
        setSize(0.0625F, 0.03125F);
    }

    public EntityBulletCasing(World world, double d, double d1, double d2)
    {
        super(world);
        droppedItem = ModItems.itemBulletCasing;
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = null;
        inGround = false;
        timeInAir = 0;
        setSize(0.0625F, 0.03125F);
        setPosition(d, d1, d2);
    }

    public EntityBulletCasing(World world, Entity entity)
    {
        super(world);
        droppedItem = ModItems.itemBulletCasing;
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = null;
        inGround = false;
        timeInAir = 0;
        owner = entity;
        createdByPlayer = owner instanceof EntityPlayer;
        setSize(0.0625F, 0.03125F);
        setLocationAndAngles(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);

        if(entity instanceof EntityPlayer)
        {
            posX -= MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
            posY -= 0.10000000000000001D;
            posZ -= MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        }

        rotationYaw += 90F;

        if(rotationYaw > 360F)
        {
            rotationYaw -= 360F;
        }

        rotationPitch -= 30F;

        if(rotationPitch < -90F)
        {
            rotationPitch = (rotationPitch + 180F) * -1F;
            rotationYaw *= -1F;
        }

        posX -= MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        posY -= 0.10000000000000001D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        setPosition(posX, posY, posZ);
        motionX = -MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI);
        motionZ = MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI);
        motionY = -MathHelper.sin((rotationPitch / 180F) * (float)Math.PI);
        setPositionAndRotation(motionX, motionY, motionZ, 0.25F, 1.0F);
    }

    protected void entityInit() {}

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    public boolean isInRangeToRenderDist(double d)
    {
        return true;
    }

    /**
     * Sets the entity's position and rotation. Args: posX, posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation(double d, double d1, double d2, float f, float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999999999999997D * (double)f1;
        d1 += rand.nextGaussian() * 0.0074999999999999997D * (double)f1;
        d2 += rand.nextGaussian() * 0.0074999999999999997D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / Math.PI);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / Math.PI);
        timeInTile = 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if(inGround)
        {
            if(!(owner instanceof EntityPlayer) && !worldObj.isRemote)
            {
                setEntityDead();
            }

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

                if(timeInTile == 1200)
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
            Entity entity1 = (Entity)list.get(j);

            if(!entity1.canBeCollidedWith() || entity1 == owner && timeInAir < 5)
            {
                continue;
            }

            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.3F);
            RayTraceResult movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

            if(movingobjectposition1 == null)
            {
                continue;
            }

            double d2 = vec3d.distanceTo(movingobjectposition1.hitVec);

            if(d2 < d || d == 0.0D)
            {
                entity = entity1;
                d = d2;
            }
        }

        if(entity != null)
        {
            movingobjectposition = new RayTraceResult(entity);
        }

        if(movingobjectposition != null && movingobjectposition.entityHit == null)
        {
            BlockPos blockPos = movingobjectposition.getBlockPos();
            xTile = blockPos.getX();
            yTile = blockPos.getY();
            zTile = blockPos.getZ();

            inTile = worldObj.getBlockState(blockPos).getBlock();

            motionX = (float) (movingobjectposition.hitVec.xCoord - posX);
            motionY = (float) (movingobjectposition.hitVec.yCoord - posY);
            motionZ = (float) (movingobjectposition.hitVec.zCoord - posZ);
            float f = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
            double d1 = 0.025000000000000001D;
            posX -= (motionX / (double) f) * d1;
            posY -= (motionY / (double) f) * d1;
            posZ -= (motionZ / (double) f) * d1;
            inGround = true;
        }

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);

        for(rotationPitch = (float) ((Math.atan2(motionY, f1) * 180D) / Math.PI); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {}

        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {}

        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {}

        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {}

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f2 = 0.99F;
        float f4 = 0.1F;

        if(handleWaterMovement())
        {
            for(int k = 0; k < 4; k++)
            {
                float f5 = 0.25F;
                worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * (double) f5, posY - motionY * (double) f5, posZ - motionZ * (double) f5, motionX, motionY, motionZ);
            }

            f2 = 0.8F;
        }

        motionX *= f2;
        motionY *= f2;
        motionZ *= f2;
        motionY -= f4;
        setPosition(posX, posY, posZ);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short) xTile);
        nbttagcompound.setShort("yTile", (short) yTile);
        nbttagcompound.setShort("zTile", (short) zTile);
        ResourceLocation resourcelocation = (ResourceLocation) Block.REGISTRY.getNameForObject(this.inTile);
        nbttagcompound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        nbttagcompound.setByte("inGround", (byte) (inGround ? 1 : 0));
        nbttagcompound.setByte("createdByPlayer", (byte) (createdByPlayer ? 1 : 0));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
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
        createdByPlayer = nbttagcompound.getByte("createdByPlayer") == 1;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        if(worldObj.isRemote)
        {
            return;
        }

        if(ConfigHandler.ammoRestrictions && droppedItem != null && createdByPlayer && timeInTile > 5 && inGround && entityplayer.inventory.addItemStackToInventory(new ItemStack(droppedItem, 1, 0)))
        {
            //worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            SoundHandler.playSoundName("random.pop", worldObj, SoundCategory.PLAYERS, this.getPosition(), 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.onItemPickup(this, 1);
            setEntityDead();
        }
    }

    public void setEntityDead()
    {
        super.setDead();
        owner = null;
    }
}
