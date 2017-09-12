package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletSniper;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class ItemGunSniper extends ItemGun
{
    public ItemGunSniper()
    {
        super();
        this.firingSound = "rayadd:gun.sniper";
        this.requiredBullet = ModItems.itemBulletHeavy;
        this.numBullets = 1;
        this.damage = 12;
        this.muzzleVelocity = 8F;
        this.spread = 0.0F;
        this.useDelay = 20;
        this.recoil = 8F;
        this.soundRangeFactor = 8F;
        this.setUnlocalizedName("itemGunSniper");
    }

    @Override
    public EntityBullet getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletSniper(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
