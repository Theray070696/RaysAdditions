package io.github.Theray070696.rayadd.entity;

import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.item.ModItems;
import io.github.Theray070696.rayadd.item.gun.ItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class EntityBulletSniper extends EntityBullet
{
    public EntityBulletSniper(World world)
    {
        super(world);
    }

    public EntityBulletSniper(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public EntityBulletSniper(World world, Entity entity, ItemGun itemgun)
    {
        super(world, entity, itemgun);
    }

    @Override
    public void playServerSound(World world)
    {
        SoundHandler.playSoundName(((ItemGun) ModItems.itemGunSniper).firingSound, world, SoundCategory.PLAYERS, this.getPosition(), 1.0F, 1.0F /
                (this.rand.nextFloat() * 0.1F + 0.95F));
    }
}