package io.github.Theray070696.rayadd.item.gun.classic;

import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassic;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassicMinigun;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class ItemGunClassicMinigun extends ItemGunClassic
{
    public ItemGunClassicMinigun()
    {
        super();
        firingSound = "rayadd:gun.minigun";
        requiredBullet = ModItems.itemBulletLight;
        numBullets = 1;
        damage = 4;
        muzzleVelocity = 4F;
        spread = 2.0F;
        useDelay = 1;
        recoil = 1.0F;
        setUnlocalizedName("itemGunMinigun");
    }

    @Override
    public EntityBulletClassic getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletClassicMinigun(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
