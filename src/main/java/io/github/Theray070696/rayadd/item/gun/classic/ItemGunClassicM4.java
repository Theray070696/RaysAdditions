package io.github.Theray070696.rayadd.item.gun.classic;

import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassic;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassicM4;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ItemGunClassicM4 extends ItemGunClassic
{
    public ItemGunClassicM4()
    {
        super();
        firingSound = "rayadd:gun.m4";
        requiredBullet = ModItems.itemBulletLight;
        numBullets = 1;
        damage = 5;
        muzzleVelocity = 4F;
        spread = 0.5F;
        useDelay = 2;
        recoil = 1.0F;
        setUnlocalizedName("itemGunM4");
    }

    @Override
    public EntityBulletClassic getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletClassicM4(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
