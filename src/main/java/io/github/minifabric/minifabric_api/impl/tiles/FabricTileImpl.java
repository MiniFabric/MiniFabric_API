package io.github.minifabric.minifabric_api.impl.tiles;

import minicraft.entity.Entity;
import minicraft.gfx.SpriteAnimation;
import minicraft.level.Level;
import minicraft.level.tile.Tile;

public class FabricTileImpl extends Tile {
	boolean mayPass = false;

	public FabricTileImpl(String name, SpriteAnimation sheet, boolean mayPass, boolean canSpawn, int lightBrightness) {
		super(name, sheet);
		this.mayPass = mayPass;
	}

	@Override
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return mayPass;
	}
}
