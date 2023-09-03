package io.github.minifabric.minifabric_api.mixin.tiles;

import minicraft.level.tile.Tile;
import minicraft.level.tile.Tiles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;

@Mixin(Tiles.class)
public interface TilesAccessor {
	@Accessor("tiles")
	static HashMap<Short, Tile> getTilesIDMap() {
		throw new RuntimeException("Accessor method body called");
	}
}
