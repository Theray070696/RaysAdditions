package io.github.Theray070696.rayadd.item.gun.classic;

import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassic;
import io.github.Theray070696.rayadd.entity.classic.EntityBulletClassicAk47;
import io.github.Theray070696.rayadd.entity.EntityBulletCasing;
import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemGunClassicAk47 extends ItemGunClassic
{
    public ItemGunClassicAk47()
    {
        super();
        firingSound = "rayadd:gun.ak47";
        requiredBullet = ModItems.itemBulletLight;
        numBullets = 1;
        damage = 5;
        muzzleVelocity = 4F;
        spread = 0.5F;
        useDelay = 2;
        recoil = 2.0F;
        setUnlocalizedName("itemGunAk47");
    }

    @Override
    public EntityBulletClassic getBulletEntity(World world, Entity entity)
    {
        return new EntityBulletClassicAk47(world, entity, this);
    }

    @Override
    public EntityBulletCasing getBulletCasingEntity(World world, Entity entity)
    {
        return new EntityBulletCasing(world, entity);
    }
}
