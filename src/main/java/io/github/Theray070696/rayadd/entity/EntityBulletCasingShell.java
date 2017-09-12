package io.github.Theray070696.rayadd.entity;

import io.github.Theray070696.rayadd.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityBulletCasingShell extends EntityBulletCasing
{
    public EntityBulletCasingShell(World world)
    {
        super(world);
        this.droppedItem = null;
    }

    public EntityBulletCasingShell(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public EntityBulletCasingShell(World world, Entity entity)
    {
        super(world, entity);
        this.droppedItem = ModItems.itemBulletCasingShell;
    }
}
