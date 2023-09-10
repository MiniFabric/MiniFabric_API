package io.github.minifabric.minifabric_api.impl.registry;

import io.github.minifabric.minifabric_api.impl.logger.InternalLogger;
import io.github.minifabric.minifabric_api.mixin.tiles.TilesAccessor;
import minicraft.level.tile.Tile;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TileRegistryImpl {
	private static final Map<Short, Tile> TILE_BY_ID = TilesAccessor.getTilesIDMap();
	private static final Map<String, Tile> TILE_BY_NAME = new HashMap<>();
	private static final Tile DEFAULT = TILE_BY_ID.get((short) 0);
	private static short startID = 47;
	private static boolean loaded;
	
	private static short getID() {
		while (TILE_BY_ID.containsKey(startID) && startID < Short.MAX_VALUE) startID++;
		return startID;
	}
	
	public static void register(Tile tile) {
		short id = getID();
		TILE_BY_ID.put(id, tile);
		TILE_BY_NAME.put(tile.name.toUpperCase(Locale.ROOT), tile);
		tile.id = id;
	}
	
	public static Tile getByName(String name) {
		name = name.toUpperCase(Locale.ROOT);
		Tile tile = TILE_BY_NAME.get(name);
		if (tile == null) {
			InternalLogger.warn("Tile with name " + name + " is missing");
			return DEFAULT;
		}
		return tile;
	}
	
	public static boolean isLoaded() {
		return loaded;
	}
	
	public static void initDefaultTiles() {
		TILE_BY_ID.forEach((id, tile) ->
			TILE_BY_NAME.put(tile.name.toUpperCase(Locale.ROOT), tile)
		);
		loaded = true;
	}
}
