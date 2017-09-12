package io.github.Theray070696.rayadd.entity;

import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.item.ModItems;
import io.github.Theray070696.rayadd.item.gun.ItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityBulletShotgun extends EntityBullet
{
    public EntityBulletShotgun(World world)
    {
        super(world);
        this.setSize(0.03125F, 0.03125F);
    }

    public EntityBulletShotgun(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        this.setSize(0.03125F, 0.03125F);
    }

    public EntityBulletShotgun(World world, Entity entity, ItemGun itemgun)
    {
        super(world, entity, itemgun);
        this.setSize(0.03125F, 0.03125F);
    }

    @Override
    public void playServerSound(World world)
    {
        SoundHandler.playSoundName(((ItemGun) ModItems.itemGunShotgun).firingSound, world, SoundCategory.PLAYERS, this.getPosition(), 1.0F, 1.0F /
                (this.rand.nextFloat() * 0.1F + 0.95F));
    }
}
