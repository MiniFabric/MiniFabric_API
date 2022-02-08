package io.github.minifabric.minifabric_api.mixin.tiles;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import minicraft.level.tile.Tiles;

@Mixin(Tiles.class)
public abstract class TilesMixin {
    @ModifyConstant(method = "initTileList()V", constant = @Constant(intValue = 256))
    private static int add(int stackLimit) {
        return 4096;
    }
}
