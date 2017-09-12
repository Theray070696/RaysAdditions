package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletMinigun;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class ItemGunMinigun extends ItemGun
{
    public ItemGunMinigun()
    {
        super();
        this.firingSound = "rayadd:gun.minigun";
        this.requiredBullet = ModItems.itemBulletLight;
        this.numBullets = 1;
        this.damage = 4;
        this.muzzleVelocity = 4F;
        this.spread = 2.0F;
        this.useDelay = 1;
        this.recoil = 1.0F;
        this.setUnlocalizedName("itemGunMinigun");
    }

    @Override
    public EntityBullet getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletMinigun(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
