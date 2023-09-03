package io.github.minifabric.minifabric_api.api.tiles;

import io.github.minifabric.minifabric_api.mixin.tiles.TilesAccessor;
import minicraft.level.tile.Tile;

import java.util.Map;

public class TileRegistry {
	private static final Map<Short, Tile> TILES = TilesAccessor.getTilesIDMap();
	private static short startID = 47;
	
	private static short getID() {
		while (TILES.containsKey(startID) && startID < Short.MAX_VALUE) startID++;
		return startID;
	}
	
	public static void register(Tile tile) {
		short id = getID();
		TILES.put(id, tile);
		tile.id = id;
	}
}
