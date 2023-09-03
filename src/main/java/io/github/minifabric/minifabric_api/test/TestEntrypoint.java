package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.api.entity.EntityRegistry;
import io.github.minifabric.minifabric_api.api.tiles.TileRegistry;
import io.github.minifabric.minifabric_api.impl.items.FabricTileItem;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheets;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheets.Types;
import io.github.minifabric.minifabric_api.impl.tiles.FabricTile;
import io.github.minifabric.minifabric_api.mixin.item.FoodItemInvoker;
import io.github.minifabric.minifabric_api.mixin.item.ItemsInvoker;
import minicraft.entity.furniture.Spawner;
import minicraft.gfx.Sprite;
import minicraft.item.FurnitureItem;
import minicraft.item.Item;
import minicraft.level.tile.Tile;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;

public class TestEntrypoint implements ModInitializer {
	ArrayList<Item> items = new ArrayList<>();
	
	@Override
	public void onInitialize() {
		System.out.println("Register entities");
		EntityRegistry.registerPassiveEntity(TestEntity.class, 48);
		EntityRegistry.registerEnemyEntity(TestHostileEntity.class, 48, false);
		
		System.out.println("Register Items");
		items.add(new FurnitureItem(new Spawner(new TestEntity()), 1, 28));
		items.add(new FurnitureItem(new Spawner(new TestHostileEntity(1)), 1, 28));
		items.add(FoodItemInvoker.invokeInit("Tiny Potato", new Sprite(0, 0, FabricSpriteSheets.getSheetPos("minifabric-api", FabricSpriteSheets.Types.ITEMS)), 10));
		
		System.out.println("Register tiles");
		String name = "Test Tile";
		Tile testTile = new FabricTile(name, new Sprite(0, 0, 3, 3, FabricSpriteSheets.getSheetPos("minifabric-api", Types.TILES)));
		TileRegistry.register(testTile);
		items.add(new FabricTileItem(name, (new Sprite(0, 1)), name, "grass"));
		
		ItemsInvoker.invokeAddAll(items);
	}
}
