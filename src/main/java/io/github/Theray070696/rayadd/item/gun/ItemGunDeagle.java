package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletDeagle;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ItemGunDeagle extends ItemGun
{
    public ItemGunDeagle()
    {
        super();
        this.firingSound = "rayadd:gun.deagle";
        this.requiredBullet = ModItems.itemBulletMedium;
        this.numBullets = 1;
        this.damage = 9;
        this.muzzleVelocity = 4F;
        this.spread = 2.0F;
        this.useDelay = 8;
        this.recoil = 4F;
        this.setUnlocalizedName("itemGunDeagle");
    }

    @Override
    public EntityBullet getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletDeagle(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
