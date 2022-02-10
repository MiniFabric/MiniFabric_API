package io.github.minifabric.minifabric_api.mixin.tiles;

import minicraft.level.tile.DirtTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DirtTile.class)
public interface DirtTileInvoker {
    @Invoker("dCol")
    static int invokeDCol(int depth) {
        throw new AssertionError();
    }
}
