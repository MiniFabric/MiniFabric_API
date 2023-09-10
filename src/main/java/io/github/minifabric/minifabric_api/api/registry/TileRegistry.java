package io.github.minifabric.minifabric_api.api.registry;

import io.github.minifabric.minifabric_api.impl.registry.TileRegistryImpl;
import minicraft.level.tile.Tile;

public class TileRegistry {
	/**
	 * Register new tile and assign it ID.
	 * @param tile {@link Tile} to register
	 */
	public static void register(Tile tile) {
		TileRegistryImpl.register(tile);
	}
	
	/**
	 * Get tile by its name
	 * @param name {@link String} tile name
	 * @return {@link Tile}
	 */
	public static Tile getByName(String name) {
		return TileRegistryImpl.getByName(name);
	}
}
