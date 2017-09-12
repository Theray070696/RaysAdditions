package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletSg552;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class ItemGunSg552 extends ItemGun
{
    public ItemGunSg552()
    {
        super();
        this.firingSound = "rayadd:gun.sg552";
        this.requiredBullet = ModItems.itemBulletMedium;
        this.numBullets = 1;
        this.damage = 8;
        this.muzzleVelocity = 6F;
        this.spread = 0.25F;
        this.useDelay = 5;
        this.recoil = 3F;
        this.setUnlocalizedName("itemGunSg552");
    }

    @Override
    public EntityBullet getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletSg552(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
