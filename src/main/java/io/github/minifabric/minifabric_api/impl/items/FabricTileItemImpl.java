package io.github.minifabric.minifabric_api.impl.items;

import minicraft.gfx.Sprite;
import minicraft.item.TileItem;

import java.util.List;

public class FabricTileItemImpl extends TileItem {
	public FabricTileItemImpl(String name, Sprite sprite, String model, String... validTiles) {
		super(name, sprite, model, validTiles);
	}
	
	public FabricTileItemImpl(String name, Sprite sprite, int count, String model, String... validTiles) {
		super(name, sprite, count, model, validTiles);
	}
	
	public FabricTileItemImpl(String name, Sprite sprite, int count, String model, List<String> validTiles) {
		super(name, sprite, count, model, validTiles);
	}
}
