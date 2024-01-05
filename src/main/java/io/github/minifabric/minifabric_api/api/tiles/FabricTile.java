package io.github.minifabric.minifabric_api.api.tiles;

import io.github.minifabric.minifabric_api.impl.tiles.FabricTileImpl;
import minicraft.gfx.SpriteAnimation;
import minicraft.level.tile.Tile;

public class FabricTile extends FabricTileImpl {
	public FabricTile(String name, SpriteAnimation sheet, boolean mayPass, boolean canSpawn, int lightBrightness) {
		super(name, sheet, mayPass, canSpawn, lightBrightness);
	}
}
