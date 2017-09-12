package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletM4;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ItemGunM4 extends ItemGun
{
    public ItemGunM4()
    {
        super();
        this.firingSound = "rayadd:gun.m4";
        this.requiredBullet = ModItems.itemBulletLight;
        this.numBullets = 1;
        this.damage = 5;
        this.muzzleVelocity = 4F;
        this.spread = 0.5F;
        this.useDelay = 2;
        this.recoil = 1.0F;
        this.setUnlocalizedName("itemGunM4");
    }

    @Override
    public EntityBullet getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletM4(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
