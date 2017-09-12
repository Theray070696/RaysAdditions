package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletAk47;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ItemGunAk47 extends ItemGun
{
    public ItemGunAk47()
    {
        super();
        this.firingSound = "rayadd:gun.ak47";
        this.requiredBullet = ModItems.itemBulletLight;
        this.numBullets = 1;
        this.damage = 5;
        this.muzzleVelocity = 4F;
        this.spread = 0.5F;
        this.useDelay = 2;
        this.recoil = 2.0F;
        this.setUnlocalizedName("itemGunAk47");
    }

    @Override
    public EntityBullet getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletAk47(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
