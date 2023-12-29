package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheet;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheet.SpriteSheetType;
import minicraft.core.io.Settings;
import minicraft.entity.mob.PassiveMob;
import minicraft.gfx.SpriteLinker;
import minicraft.item.Items;

public class TestEntity extends PassiveMob {
    static SpriteLinker.LinkedSprite testSprite = new SpriteLinker.LinkedSprite(SpriteLinker.SpriteType.Entity, "test-entity");

    private static SpriteLinker.LinkedSprite[][] sprites = new SpriteLinker.LinkedSprite[][] {
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
