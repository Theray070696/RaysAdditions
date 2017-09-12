package io.github.Theray070696.rayadd.item.gun.classic;

import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassic;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassicRocketLaser;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 5/19/2017.
 */
public class ItemGunClassicRocketLauncherLaser extends ItemGunClassic
{
    public ItemGunClassicRocketLauncherLaser()
    {
        super();
        firingSound = "rayadd:gun.rocket";
        requiredBullet = ModItems.itemBulletRocketLaser;
        numBullets = 1;
        damage = 10;
        muzzleVelocity = 1.0F;
        spread = 0.0F;
        useDelay = 40;
        recoil = 0.0F;
        setUnlocalizedName("itemGunRocketLauncherLaser");
    }

    @Override
    public EntityBulletClassic getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletClassicRocketLaser(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return null;
    }
}
