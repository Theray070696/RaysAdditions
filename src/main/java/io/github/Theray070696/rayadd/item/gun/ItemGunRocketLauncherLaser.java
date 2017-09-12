package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletRocketLaser;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 5/19/2017.
 */
public class ItemGunRocketLauncherLaser extends ItemGun
{
    public ItemGunRocketLauncherLaser()
    {
        super();
        this.firingSound = "rayadd:gun.rocket";
        this.requiredBullet = ModItems.itemBulletRocketLaser;
        this.numBullets = 1;
        this.damage = 10;
        this.muzzleVelocity = 1.0F;
        this.spread = 0.0F;
        this.useDelay = 40;
        this.recoil = 0.0F;
        this.setUnlocalizedName("itemGunRocketLauncherLaser");
    }

    @Override
    public EntityBullet getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletRocketLaser(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return null;
    }
}
