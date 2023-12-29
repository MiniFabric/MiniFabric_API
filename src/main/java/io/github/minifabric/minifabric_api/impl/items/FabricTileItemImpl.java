package io.github.minifabric.minifabric_api.impl.items;

import minicraft.gfx.SpriteLinker;
import minicraft.item.TileItem;

import java.util.List;

public class FabricTileItemImpl extends TileItem {
	public FabricTileItemImpl(String name, SpriteLinker.LinkedSprite sprite, String model, String... validTiles) {
		super(name, sprite, model, validTiles);
	}
	
	public FabricTileItemImpl(String name, SpriteLinker.LinkedSprite sprite, int count, String model, String... validTiles) {
		super(name, sprite, count, model, validTiles);
	}
	
	public FabricTileItemImpl(String name, SpriteLinker.LinkedSprite sprite, int count, String model, List<String> validTiles) {
		super(name, sprite, count, model, validTiles);
	}
}
