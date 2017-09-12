package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.entity.EntityBullet;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletCasingShell;
import io.github.Theray070696.rayadd.entity.EntityBulletShotgun;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ItemGunShotgun extends ItemGun
{
    public ItemGunShotgun()
    {
        super();
        this.firingSound = "rayadd:gun.shotgun";
        this.requiredBullet = ModItems.itemBulletShell;
        this.numBullets = 10;
        this.damage = 7;
        this.muzzleVelocity = 3F;
        this.spread = 8F;
        this.useDelay = 16;
        this.recoil = 8F;
        this.setUnlocalizedName("itemGunShotgun");
    }

    @Override
    public EntityBullet getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletShotgun(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasingShell(world, entity);
    }
}
