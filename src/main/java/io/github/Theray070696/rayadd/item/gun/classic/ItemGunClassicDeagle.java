package io.github.Theray070696.rayadd.item.gun.classic;

import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassic;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassicDeagle;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemGunClassicDeagle extends ItemGunClassic
{
    public ItemGunClassicDeagle()
    {
        super();
        firingSound = "rayadd:gun.deagle";
        requiredBullet = ModItems.itemBulletMedium;
        numBullets = 1;
        damage = 9;
        muzzleVelocity = 4F;
        spread = 2.0F;
        useDelay = 8;
        recoil = 4F;
        setUnlocalizedName("itemGunDeagle");
    }

    @Override
    public EntityBulletClassic getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletClassicDeagle(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
