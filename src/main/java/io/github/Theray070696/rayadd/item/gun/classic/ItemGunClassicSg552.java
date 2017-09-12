package io.github.Theray070696.rayadd.item.gun.classic;

import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassic;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassicSg552;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class ItemGunClassicSg552 extends ItemGunClassic
{
    public ItemGunClassicSg552()
    {
        super();
        firingSound = "rayadd:gun.sg552";
        requiredBullet = ModItems.itemBulletMedium;
        numBullets = 1;
        damage = 8;
        muzzleVelocity = 6F;
        spread = 0.25F;
        useDelay = 5;
        recoil = 3F;
        setUnlocalizedName("itemGunSg552");
    }

    @Override
    public EntityBulletClassic getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletClassicSg552(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
