package io.github.Theray070696.rayadd.entity.classic;

import io.github.Theray070696.rayadd.audio.SoundHandler;
import io.github.Theray070696.rayadd.item.ModItems;
import io.github.Theray070696.rayadd.item.gun.classic.ItemGunClassic;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 5/18/2017.
 */
public class EntityBulletClassicSniper extends EntityBulletClassic
{
    public EntityBulletClassicSniper(World world)
    {
        super(world);
    }

    public EntityBulletClassicSniper(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public EntityBulletClassicSniper(World world, Entity entity, ItemGunClassic itemgun)
    {
        super(world, entity, itemgun);
    }

    @Override
    public void playServerSound(World world)
    {
        //world.playSoundAtEntity(this, ((ItemGunClassic) ModItems.itemGunSniper).firingSound, ((ItemGunClassic) ModItems.itemGunSniper).soundRangeFactor, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
        SoundHandler.playSoundName(((ItemGunClassic) ModItems.itemGunSniper).firingSound, world, SoundCategory.PLAYERS, this.getPosition(), 1.0F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
    }
}
