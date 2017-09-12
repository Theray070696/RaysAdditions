package io.github.Theray070696.rayadd.entity;

import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.item.ModItems;
import io.github.Theray070696.rayadd.item.gun.ItemBullet;
import io.github.Theray070696.rayadd.item.gun.ItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 6/13/2017.
 */
public class EntityBulletDeagle extends EntityBullet
{
    public EntityBulletDeagle(World world)
    {
        super(world);
    }

    public EntityBulletDeagle(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public EntityBulletDeagle(World world, Entity entity, ItemGun itemGun, ItemBullet itemBullet)
    {
        super(world, entity, itemGun, itemBullet);
    }

    @Override
    public void playServerSound(World world)
    {
        //world.playSoundAtEntity(this, ((ItemGunClassic) ModItems.itemGunDeagle).firingSound, ((ItemGunClassic) ModItems.itemGunDeagle).soundRangeFactor, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
        SoundHandler.playSoundName(((ItemGun) ModItems.itemGunDeagle).firingSound, world, SoundCategory.PLAYERS, this.getPosition(), 1.0F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
    }
}
