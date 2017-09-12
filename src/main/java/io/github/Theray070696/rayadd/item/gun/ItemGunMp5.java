package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletMp5;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ItemGunMp5 extends ItemGun
{
    public ItemGunMp5()
    {
        super();
        this.firingSound = "rayadd:gun.mp5";
        this.requiredBullet = ModItems.itemBulletLight;
        this.numBullets = 1;
        this.damage = 4;
        this.muzzleVelocity = 3F;
        this.spread = 1.0F;
        this.useDelay = 2;
        this.recoil = 1.0F;
        this.setUnlocalizedName("itemGunMp5");
    }

    @Override
    public EntityBullet getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletMp5(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
