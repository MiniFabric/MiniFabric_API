package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheet;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheet.SpriteSheetType;
import minicraft.core.io.Settings;
import minicraft.entity.mob.EnemyMob;
import minicraft.entity.mob.Mob;
import minicraft.gfx.SpriteLinker;
import minicraft.item.Items;

public class TestHostileEntity extends EnemyMob {
    private static SpriteLinker.LinkedSprite[][][] sprites = new SpriteLinker.LinkedSprite[][][]{Mob.compileMobSpriteAnimations(0, 0, "anger_tater"),
            Mob.compileMobSpriteAnimations(2, 0, "anger_tater"),
            Mob.compileMobSpriteAnimations(4, 0, "anger_tater"),
            Mob.compileMobSpriteAnimations(6, 0, "anger_tater")};

    public TestHostileEntity(int lvl) {
        super(lvl, sprites, 5, 100);
    }

    public void die() {
        switch (Settings.get("diff").toString()) {
            case "minicraft.settings.difficulty.easy" -> dropItem(2, 4, TestEntrypoint.tiny_potato);
            case "minicraft.settings.difficulty.normal" -> dropItem(1, 3, TestEntrypoint.tiny_potato);
            case "minicraft.settings.difficulty.hard" -> dropItem(1, 2, TestEntrypoint.tiny_potato);
            default -> dropItem(2, 4, TestEntrypoint.tiny_potato);
        }
        super.die();
    }
}
