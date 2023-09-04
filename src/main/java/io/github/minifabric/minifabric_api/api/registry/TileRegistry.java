package io.github.minifabric.minifabric_api.api.registry;

import io.github.minifabric.minifabric_api.impl.registry.TileRegistryImpl;
import minicraft.level.tile.Tile;

public class TileRegistry {
	public static void register(Tile tile) {
		TileRegistryImpl.register(tile);
	}
	
	public static Tile getByName(String name) {
		return TileRegistryImpl.getByName(name);
	}
}
