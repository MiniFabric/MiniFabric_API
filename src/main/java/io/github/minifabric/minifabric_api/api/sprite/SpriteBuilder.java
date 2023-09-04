package io.github.minifabric.minifabric_api.api.sprite;

import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheet;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheet.SpriteSheetType;
import minicraft.gfx.Sprite;

public class SpriteBuilder {
	private static final SpriteBuilder INSTANCE = new SpriteBuilder();
	
	int sx;
	int sy;
	int sw;
	int sh;
	int sheet;
	int mirror;
	boolean onePixel;
	
	private SpriteBuilder() {}
	
	/**
	 * Starts sprite building process
	 * @param modID Sprite sheet mod id, "minicraftplus" for default game sprite sheet
	 * @param type {@link SpriteSheetType}
	 * @return {@link SpriteBuilder} instance
	 */
	public static SpriteBuilder start(String modID, SpriteSheetType type) {
		INSTANCE.sheet = FabricSpriteSheet.getSheetPos(modID, type);
		INSTANCE.sx = 0;
		INSTANCE.sy = 0;
		INSTANCE.sw = 1;
		INSTANCE.sh = 1;
		INSTANCE.mirror = 0;
		INSTANCE.onePixel = false;
		return INSTANCE;
	}
	
	/**
	 * Set sprite position in sheet, coordinates are specified in tiles.
	 * Default sprite sheets are 32x32 tiles with 8x8 pixel tile size.
	 * @param x X tile position (Horizontal)
	 * @param y Y tile position (Vertical)
	 * @return same {@link SpriteBuilder} instance
	 */
	public SpriteBuilder position(int x, int y) {
		sx = x;
		sy = y;
		return this;
	}
	
	/**
	 * Set sprite size in sheet in tiles.
	 * Default sprite sheets are 32x32 tiles with 8x8 pixel tile size.
	 * @param width Tile width (Horizontal tiles)
	 * @param height Tile height (Vertical tiles)
	 * @return same {@link SpriteBuilder} instance
	 */
	public SpriteBuilder size(int width, int height) {
		sw = width;
		sh = height;
		return this;
	}
	
	/**
	 * Set sprite mirroring on X axis (Horizontal)
	 * @return same {@link SpriteBuilder} instance
	 */
	public SpriteBuilder mirrorX() {
		mirror |= 1;
		return this;
	}
	
	/**
	 * Set sprite mirroring on Y axis (Vertical)
	 * @return same {@link SpriteBuilder} instance
	 */
	public SpriteBuilder mirrorY() {
		mirror |= 2;
		return this;
	}
	
	/**
	 * Finish Sprite building
	 * @return {@link Sprite}
	 */
	public Sprite build() {
		return new Sprite(sx, sy, sw, sh, sheet, mirror, onePixel);
	}
}
