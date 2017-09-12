package io.github.Theray070696.rayadd.item.gun.classic;

import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassic;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.EntityBulletCasingShell;
import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassicShotgun;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ItemGunClassicShotgun extends ItemGunClassic
{
    public ItemGunClassicShotgun()
    {
        super();
        firingSound = "rayadd:gun.shotgun";
        requiredBullet = ModItems.itemBulletShell;
        numBullets = 10;
        damage = 7;
        muzzleVelocity = 3F;
        spread = 8F;
        useDelay = 16;
        recoil = 8F;
        setUnlocalizedName("itemGunShotgun");
    }

    @Override
    public EntityBulletClassic getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletClassicShotgun(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasingShell(world, entity);
    }
}
