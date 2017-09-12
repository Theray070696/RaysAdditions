package io.github.Theray070696.rayadd.entity;

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
import java.util.List;

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
        this.droppedItem = ModItems.itemBulletCasing;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = null;
        this.inGround = false;
        this.timeInAir = 0;
        setSize(0.0625F, 0.03125F);
    }

    public EntityBulletCasing(World world, double d, double d1, double d2)
    {
        super(world);
        this.droppedItem = ModItems.itemBulletCasing;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = null;
        this.inGround = false;
        this.timeInAir = 0;
        setSize(0.0625F, 0.03125F);
        setPosition(d, d1, d2);
    }

    public EntityBulletCasing(World world, Entity entity)
    {
        super(world);
        this.droppedItem = ModItems.itemBulletCasing;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = null;
        this.inGround = false;
        this.timeInAir = 0;
        this.owner = entity;
        this.createdByPlayer = owner instanceof EntityPlayer;
        setSize(0.0625F, 0.03125F);
        setLocationAndAngles(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);

        if(entity instanceof EntityPlayer)
        {
            this.posX -= MathHelper.cos((this.rotationYaw / 180F) * (float) Math.PI) * 0.16F;
            this.posY -= 0.10000000000000001D;
            this.posZ -= MathHelper.sin((this.rotationYaw / 180F) * (float) Math.PI) * 0.16F;
        }

        this.rotationYaw += 90F;

        if(this.rotationYaw > 360F)
        {
            this.rotationYaw -= 360F;
        }

        this.rotationPitch -= 30F;

        if(this.rotationPitch < -90F)
        {
            this.rotationPitch = (this.rotationPitch + 180F) * -1F;
            this.rotationYaw *= -1F;
        }

        this.posX -= MathHelper.cos((this.rotationYaw / 180F) * (float) Math.PI) * 0.16F;
        this.posY -= 0.10000000000000001D;
        this.posZ -= MathHelper.sin((this.rotationYaw / 180F) * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = -MathHelper.sin((this.rotationYaw / 180F) * (float) Math.PI) * MathHelper.cos((this.rotationPitch / 180F) * (float) Math.PI);
        this.motionZ = MathHelper.cos((this.rotationYaw / 180F) * (float) Math.PI) * MathHelper.cos((this.rotationPitch / 180F) * (float) Math.PI);
        this.motionY = -MathHelper.sin((this.rotationPitch / 180F) * (float) Math.PI);
        this.setPositionAndRotation(this.motionX, this.motionY, this.motionZ, 0.25F, 1.0F);
    }

    protected void entityInit()
    {
    }

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
        d += this.rand.nextGaussian() * 0.0074999999999999997D * (double) f1;
        d1 += this.rand.nextGaussian() * 0.0074999999999999997D * (double) f1;
        d2 += this.rand.nextGaussian() * 0.0074999999999999997D * (double) f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        this.motionX = d;
        this.motionY = d1;
        this.motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        this.prevRotationYaw = this.rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float) ((Math.atan2(d1, f3) * 180D) / Math.PI);
        this.timeInTile = 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if(this.inGround)
        {
            if(!(this.owner instanceof EntityPlayer) && !this.worldObj.isRemote)
            {
                setEntityDead();
            }

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

                if(this.timeInTile == 1200)
                {
                    this.setEntityDead();
                }

                return;
            }
        } else
        {
            this.timeInAir++;
        }

        Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult rayTraceResult = worldObj.rayTraceBlocks(vec3d, vec3d1, false, true, false);
        vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if(rayTraceResult != null)
        {
            vec3d1 = new Vec3d(rayTraceResult.hitVec.xCoord, rayTraceResult.hitVec.yCoord, rayTraceResult.hitVec.zCoord);
        }

        Entity entity = null;
        List list = this.worldObj.getEntitiesInAABBexcluding(this, getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expandXyz(1.0D),
                Predicates.and(EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>()
                {
                    public boolean apply(@Nullable Entity p_apply_1_)
                    {
                        return p_apply_1_.canBeCollidedWith();
                    }
                }));
        double d = 0.0D;

        for(Object aList : list)
        {
            Entity entity1 = (Entity) aList;

            if(!entity1.canBeCollidedWith() || entity1 == this.owner && this.timeInAir < 5)
            {
                continue;
            }

            AxisAlignedBB entityBoundingBox = entity1.getEntityBoundingBox().expandXyz(0.3F);
            RayTraceResult rayTraceResult1 = entityBoundingBox.calculateIntercept(vec3d, vec3d1);

            if(rayTraceResult1 == null)
            {
                continue;
            }

            double d2 = vec3d.distanceTo(rayTraceResult1.hitVec);

            if(d2 < d || d == 0.0D)
            {
                entity = entity1;
                d = d2;
            }
        }

        if(entity != null)
        {
            rayTraceResult = new RayTraceResult(entity);
        }

        if(rayTraceResult != null && rayTraceResult.entityHit == null)
        {
            BlockPos blockPos = rayTraceResult.getBlockPos();
            this.xTile = blockPos.getX();
            this.yTile = blockPos.getY();
            this.zTile = blockPos.getZ();

            this.inTile = this.worldObj.getBlockState(blockPos).getBlock();

            this.motionX = (float) (rayTraceResult.hitVec.xCoord - this.posX);
            this.motionY = (float) (rayTraceResult.hitVec.yCoord - this.posY);
            this.motionZ = (float) (rayTraceResult.hitVec.zCoord - this.posZ);
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            double d1 = 0.025000000000000001D;
            this.posX -= (this.motionX / (double) f) * d1;
            this.posY -= (this.motionY / (double) f) * d1;
            this.posZ -= (this.motionZ / (double) f) * d1;
            this.inGround = true;
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

        for(; this.rotationYaw - this.prevRotationYaw >= 180F; this.prevRotationYaw += 360F)
        {
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f2 = 0.99F;
        float f4 = 0.1F;

        if(handleWaterMovement())
        {
            for(int k = 0; k < 4; k++)
            {
                float f5 = 0.25F;
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) f5, this.posY - this.motionY *
                        (double) f5, this.posZ - this.motionZ * (double) f5, this.motionX, this.motionY, this.motionZ);
            }

            f2 = 0.8F;
        }

        this.motionX *= f2;
        this.motionY *= f2;
        this.motionZ *= f2;
        this.motionY -= f4;
        setPosition(this.posX, this.posY, this.posZ);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short) this.xTile);
        nbttagcompound.setShort("yTile", (short) this.yTile);
        nbttagcompound.setShort("zTile", (short) this.zTile);
        ResourceLocation resourcelocation = (ResourceLocation) Block.REGISTRY.getNameForObject(this.inTile);
        nbttagcompound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        nbttagcompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        nbttagcompound.setByte("createdByPlayer", (byte) (this.createdByPlayer ? 1 : 0));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        this.xTile = nbttagcompound.getShort("xTile");
        this.yTile = nbttagcompound.getShort("yTile");
        this.zTile = nbttagcompound.getShort("zTile");
        if(nbttagcompound.hasKey("inTile", 8))
        {
            this.inTile = Block.getBlockFromName(nbttagcompound.getString("inTile"));
        } else
        {
            this.inTile = Block.getBlockById(nbttagcompound.getByte("inTile") & 255);
        }
        this.inGround = nbttagcompound.getByte("inGround") == 1;
        this.createdByPlayer = nbttagcompound.getByte("createdByPlayer") == 1;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        if(this.worldObj.isRemote)
        {
            return;
        }

        if(ConfigHandler.ammoRestrictions && this.droppedItem != null && this.createdByPlayer && this.timeInTile > 5 && this.inGround &&
                entityplayer.inventory.addItemStackToInventory(new ItemStack(this.droppedItem, 1, 0)))
        {
            SoundHandler.playSoundName("random.pop", this.worldObj, SoundCategory.PLAYERS, this.getPosition(), 0.2F, ((this.rand.nextFloat() - this
                    .rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.onItemPickup(this, 1);
            setEntityDead();
        }
    }

    public void setEntityDead()
    {
        super.setDead();
        this.owner = null;
    }
}
