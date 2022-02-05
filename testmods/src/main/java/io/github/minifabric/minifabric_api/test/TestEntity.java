package io.github.minifabric.minifabric_api.test;

import minicraft.core.io.Settings;
import minicraft.entity.mob.PassiveMob;
import minicraft.gfx.MobSprite;
import minicraft.item.Items;

public class TestEntity extends PassiveMob {
    private static MobSprite[][] sprites = MobSprite.compileMobSpriteAnimations(0, 29);

    public TestEntity() {
        super(sprites);
    }

    public void die() {
        int min = 0, max = 0;
        if (Settings.get("diff").equals("Easy")) {min = 1; max = 3;}
        if (Settings.get("diff").equals("Normal")) {min = 1; max = 2;}
        if (Settings.get("diff").equals("Hard")) {min = 0; max = 2;}

        dropItem(min, max, Items.get("raw pork"));

        super.die();
    }
}
