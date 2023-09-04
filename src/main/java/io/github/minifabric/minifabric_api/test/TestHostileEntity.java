package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheet;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheet.SpriteSheetType;
import minicraft.core.io.Settings;
import minicraft.entity.mob.EnemyMob;
import minicraft.gfx.MobSprite;
import minicraft.item.Items;

public class TestHostileEntity extends EnemyMob {
    static MobSprite testSprite = new MobSprite(2, 0, 2, 2, 0, FabricSpriteSheet.getSheetPos("minifabric-api", SpriteSheetType.ENTITIES));
    private static MobSprite[][][] sprites = new MobSprite[][][] {
            {{testSprite}, {testSprite}, {testSprite}, {testSprite}},
            {{testSprite}, {testSprite}, {testSprite}, {testSprite}}};

    public TestHostileEntity(int lvl) {
        super(lvl, sprites, 5, 100);
    }

    public void die() {
        if (Settings.get("diff").equals("Easy")) dropItem(2, 4, Items.get("Tiny Potato"));
        if (Settings.get("diff").equals("Normal")) dropItem(1, 3, Items.get("Tiny Potato"));
        if (Settings.get("diff").equals("Hard")) dropItem(1, 2, Items.get("Tiny Potato"));

        super.die();
    }
}
