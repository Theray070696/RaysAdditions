package io.github.Theray070696.rayadd.entity.classic;

import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.item.ModItems;
import io.github.Theray070696.rayadd.item.gun.classic.ItemGunClassic;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityBulletClassicDeagle extends EntityBulletClassic
{
    public EntityBulletClassicDeagle(World world)
    {
        super(world);
    }

    public EntityBulletClassicDeagle(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public EntityBulletClassicDeagle(World world, Entity entity, ItemGunClassic itemgun)
    {
        super(world, entity, itemgun);
    }

    @Override
    public void playServerSound(World world)
    {
        //world.playSoundAtEntity(this, ((ItemGunClassic) ModItems.itemGunDeagle).firingSound, ((ItemGunClassic) ModItems.itemGunDeagle).soundRangeFactor, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
        SoundHandler.playSoundName(((ItemGunClassic) ModItems.itemGunDeagle).firingSound, world, SoundCategory.PLAYERS, this.getPosition(), 1.0F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
    }
}
