package io.github.minifabric.minifabric_api.api.tiles;

import minicraft.item.Item;

public class OreTileBuilder {
	private String name;
	private Item drop;
	private String sprite_id;
	private boolean isCloud = false;

	public OreTileBuilder(String name, Item drop, String sprite_id) {
		this.name = name;
		this.drop = drop;
		this.sprite_id = sprite_id;
	}

	public OreTileBuilder isCloud() {
		this.isCloud = true;
		return this;
	}

	public FabricOreTile create() {
		return new FabricOreTile(name, drop, sprite_id, isCloud);
	}
}
