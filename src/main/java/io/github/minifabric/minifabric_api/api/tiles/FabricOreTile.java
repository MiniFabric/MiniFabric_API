package io.github.minifabric.minifabric_api.api.tiles;

import io.github.minifabric.minifabric_api.impl.tiles.FabricOreTileImpl;
import minicraft.gfx.SpriteAnimation;
import minicraft.gfx.SpriteLinker;
import minicraft.item.Item;

public class FabricOreTile extends FabricOreTileImpl {
	public FabricOreTile(String name, Item drop, SpriteAnimation sheet, boolean isCloud) {
		super(name, drop, sheet, isCloud);
	}

	public FabricOreTile(String name, Item drop, String spriteId, boolean isCloud) {
		this(name, drop, new SpriteAnimation(SpriteLinker.SpriteType.Tile, spriteId), isCloud);
	}
}
