package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.api.dimension.DimensionInfo;
import io.github.minifabric.minifabric_api.api.registry.DimensionRegistry;
import io.github.minifabric.minifabric_api.api.registry.EntityRegistry;
import io.github.minifabric.minifabric_api.api.registry.ItemRegistry;
import io.github.minifabric.minifabric_api.api.registry.TileRegistry;
import io.github.minifabric.minifabric_api.api.sprite.SpriteBuilder;
import io.github.minifabric.minifabric_api.impl.items.FabricTileItemImpl;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheet.SpriteSheetType;
import io.github.minifabric.minifabric_api.impl.tiles.FabricTileImpl;
import io.github.minifabric.minifabric_api.mixin.item.FoodItemInvoker;
import minicraft.entity.Entity;
import minicraft.entity.furniture.Spawner;
import minicraft.entity.mob.Player;
import minicraft.gfx.Sprite;
import minicraft.gfx.SpriteAnimation;
import minicraft.gfx.SpriteLinker;
import minicraft.item.FurnitureItem;
import minicraft.level.Level;
import minicraft.level.tile.Tile;
import net.fabricmc.api.ModInitializer;

public class TestEntrypoint implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Register entities");
		EntityRegistry.registerPassiveEntity(TestEntity.class, 48);
		EntityRegistry.registerEnemyEntity(TestHostileEntity.class, 48, false);
		
		System.out.println("Register Items");
		ItemRegistry.register(
			new FurnitureItem(new Spawner(new TestEntity())),
			new FurnitureItem(new Spawner(new TestHostileEntity(1))),
			FoodItemInvoker.invokeInit(
				"Tiny Potato",
				new SpriteLinker.LinkedSprite(SpriteLinker.SpriteType.Item, "tiny-potato"),
				10
			)
		);
		
		System.out.println("Register tiles");
		String name = "Tater Tile";
		Tile tile = new FabricTileImpl(name, new SpriteAnimation(SpriteLinker.SpriteType.Tile, "tater-tile"));
		TileRegistry.register(tile);
		//ItemRegistry.register(new FabricTileItemImpl(name, new SpriteLinker.LinkedSprite(SpriteLinker.SpriteType.Item, "tater-tile"), name, "grass"));
		
		DimensionInfo taterWorld = DimensionRegistry.register("Tater World");
		System.out.println(taterWorld.getName() + " " + taterWorld.getIndex() + " " + taterWorld.getDepth());
		
		taterWorld.setGenerator((dimension, width, height, seed, tiles, data) -> {
			Tile tater = TileRegistry.getByName("Tater Tile");
			for (int i = 0; i < tiles.length; i++) {
				if (((i + i / width) & 1) == 0) tiles[i] = tater.id;
			}
		});
		
		name = "Tater Teleport";
		tile = new Teleport(
			name,
			new SpriteAnimation(SpriteLinker.SpriteType.Tile, "tater-teleport"),
			taterWorld
		);
		TileRegistry.register(tile);
		//ItemRegistry.register(new FabricTileItemImpl(name, new SpriteLinker.LinkedSprite(SpriteLinker.SpriteType.Tile, "teleport"), name, "grass"));
	}
	
	private static class Teleport extends FabricTileImpl {
		private final DimensionInfo dimension;
		
		public Teleport(String name, SpriteAnimation sprite, DimensionInfo dimension) {
			super(name, sprite);
			this.dimension = dimension;
		}
		
		@Override
		public void steppedOn(Level level, int xt, int yt, Entity entity) {
			if (entity instanceof Player) {
				dimension.travelTo();
			}
		}
	}
}
