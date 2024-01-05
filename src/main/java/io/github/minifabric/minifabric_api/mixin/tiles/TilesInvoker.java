package io.github.minifabric.minifabric_api.mixin.tiles;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import minicraft.level.tile.Tile;
import minicraft.level.tile.Tiles;

@Mixin(Tiles.class)
public interface TilesInvoker {
	@Invoker("add")
	public static void invokeAddTile(int id, Tile tile) {
		throw new AssertionError();
	}
}
