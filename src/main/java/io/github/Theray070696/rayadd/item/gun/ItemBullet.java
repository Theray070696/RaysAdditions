package io.github.Theray070696.rayadd.item.gun;

import io.github.Theray070696.rayadd.gun.EnumBulletCaliber;
import io.github.Theray070696.rayadd.item.ItemRayAdd;

/**
 * Created by Theray070696 on 6/8/2017.
 */
public class ItemBullet extends ItemRayAdd
{
    public EnumBulletCaliber bulletCaliber;
    public int numProjectiles;
    public int damage;
    public float muzzleVelocity;

    public ItemBullet(EnumBulletCaliber bulletCaliber, int numBullets, int damage, float muzzleVelocity)
    {
        super();
        this.bulletCaliber = bulletCaliber;
        this.numProjectiles = numBullets;
        this.damage = damage;
        this.muzzleVelocity = muzzleVelocity;
    }
}