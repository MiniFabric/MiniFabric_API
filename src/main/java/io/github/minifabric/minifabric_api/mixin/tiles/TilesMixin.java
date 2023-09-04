package io.github.minifabric.minifabric_api.mixin.tiles;

import io.github.minifabric.minifabric_api.impl.registry.TileRegistryImpl;
import minicraft.level.tile.Tile;
import minicraft.level.tile.Tiles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Tiles.class)
public class TilesMixin {
	@Inject(method = "get(Ljava/lang/String;)Lminicraft/level/tile/Tile;", at = @At("HEAD"), cancellable = true)
	private static void getTileByName(String name, CallbackInfoReturnable<Tile> info) {
		if (!TileRegistryImpl.isLoaded()) return;
		info.setReturnValue(TileRegistryImpl.getByName(name));
	}
	
	@Inject(method = "initTileList", at = @At("TAIL"))
	private static void getTileByName(CallbackInfo info) {
		TileRegistryImpl.initDefaultTiles();
	}
}
