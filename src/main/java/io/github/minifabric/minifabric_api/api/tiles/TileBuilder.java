package io.github.minifabric.minifabric_api.api.tiles;

import minicraft.gfx.SpriteAnimation;

public class TileBuilder {
	private final String name;
	private final SpriteAnimation sheet;
	private boolean mayPass = false;
	private boolean canSpawn = true;
	private int lightBrightness = 0;

	public TileBuilder(String name, SpriteAnimation sheet) {
		this.name = name;
		this.sheet = sheet;
	}


	public TileBuilder mayPass() {
		this.mayPass = true;
		return this;
	}

	public TileBuilder spawnSafe() {
		this.canSpawn = false;
		return this;
	}

	public TileBuilder lightBrightness(int brightness) {
		this.lightBrightness = brightness;
		return this;
	}

	public FabricTile create() {
		return new FabricTile(name, sheet, mayPass, canSpawn, lightBrightness);
	}
}
