package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.api.entity.EntityRegistry;
import io.github.minifabric.minifabric_api.api.tiles.TileRegistry;
import io.github.minifabric.minifabric_api.impl.items.FabricTileItem;
import io.github.minifabric.minifabric_api.impl.items.ItemsRegistry;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheets;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheets.Types;
import io.github.minifabric.minifabric_api.impl.tiles.FabricTile;
import io.github.minifabric.minifabric_api.mixin.item.FoodItemInvoker;
import minicraft.entity.furniture.Spawner;
import minicraft.gfx.Sprite;
import minicraft.item.FurnitureItem;
import minicraft.level.tile.Tile;
import net.fabricmc.api.ModInitializer;

public class TestEntrypoint implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Register entities");
		EntityRegistry.registerPassiveEntity(TestEntity.class, 48);
		EntityRegistry.registerEnemyEntity(TestHostileEntity.class, 48, false);
		
		System.out.println("Register Items");
		ItemsRegistry.register(
			new FurnitureItem(new Spawner(new TestEntity()), 1, 28),
			new FurnitureItem(new Spawner(new TestHostileEntity(1)), 1, 28),
			FoodItemInvoker.invokeInit(
				"Tiny Potato",
				new Sprite(0, 0, FabricSpriteSheets.getSheetPos("minifabric-api", FabricSpriteSheets.Types.ITEMS)),
				10
			)
		);
		
		System.out.println("Register tiles");
		String name = "Test Tile";
		Tile testTile = new FabricTile(
			name,
			new Sprite(0, 0, 3, 3, FabricSpriteSheets.getSheetPos("minifabric-api", Types.TILES))
		);
		TileRegistry.register(testTile);
		ItemsRegistry.register(new FabricTileItem(name, (new Sprite(0, 1)), name, "grass"));
	}
}
