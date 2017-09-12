package io.github.Theray070696.rayadd.item.gun.classic;

import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassic;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassicMp5;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ItemGunClassicMp5 extends ItemGunClassic
{
    public ItemGunClassicMp5()
    {
        super();
        firingSound = "rayadd:gun.mp5";
        requiredBullet = ModItems.itemBulletLight;
        numBullets = 1;
        damage = 4;
        muzzleVelocity = 3F;
        spread = 1.0F;
        useDelay = 2;
        recoil = 1.0F;
        setUnlocalizedName("itemGunMp5");
    }

    @Override
    public EntityBulletClassic getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletClassicMp5(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
