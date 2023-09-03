package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheets;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheets.Types;
import minicraft.core.io.Settings;
import minicraft.entity.mob.PassiveMob;
import minicraft.gfx.MobSprite;
import minicraft.item.Items;

public class TestEntity extends PassiveMob {
    static MobSprite testSprite = new MobSprite(0, 0, 2, 2, 0, FabricSpriteSheets.getSheetPos("minifabric-api", Types.ENTITIES));

    private static MobSprite[][] sprites = new MobSprite[][] {
            {testSprite, testSprite},
            {testSprite, testSprite},
            {testSprite, testSprite},
            {testSprite, testSprite}};

    public TestEntity() {
        super(sprites);
    }

    public void die() {
        int min = 0, max = 0;
        if (Settings.get("diff").equals("Easy")) {min = 1; max = 3;}
        if (Settings.get("diff").equals("Normal")) {min = 1; max = 2;}
        if (Settings.get("diff").equals("Hard")) {min = 0; max = 2;}

        dropItem(min, max, Items.get("Tiny Potato"));

        super.die();
    }
}
