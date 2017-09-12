package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletDeagle;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 6/13/2017.
 */
public class ItemGunDeagle extends ItemGun
{
    public ItemGunDeagle()
    {
        super();
        firingSound = "rayadd:gun.deagle";
        requiredMag = ModItems.itemMagDeagle50;
        spread = 2.0F;
        useDelay = 8;
        recoil = 4F;
        setUnlocalizedName("itemGunDeagle");
    }

    @Override
    public EntityBullet getBulletEntity(World world, Entity entity, ItemBullet bullet)
    {
        return new EntityBulletDeagle(world, entity, this, bullet);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
