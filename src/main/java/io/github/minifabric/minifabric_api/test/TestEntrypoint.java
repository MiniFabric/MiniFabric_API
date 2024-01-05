package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.api.dimension.DimensionInfo;
import io.github.minifabric.minifabric_api.api.events.entity.EntityEvents;
import io.github.minifabric.minifabric_api.api.events.level.TickEvents;
import io.github.minifabric.minifabric_api.api.item.Food;
import io.github.minifabric.minifabric_api.api.registry.DimensionRegistry;
import io.github.minifabric.minifabric_api.api.registry.EntityRegistry;
import io.github.minifabric.minifabric_api.api.registry.ItemRegistry;
import io.github.minifabric.minifabric_api.api.registry.TileRegistry;
import io.github.minifabric.minifabric_api.api.tiles.FabricTile;
import io.github.minifabric.minifabric_api.api.tiles.OreTileBuilder;
import io.github.minifabric.minifabric_api.impl.items.FabricTileItemImpl;
import io.github.minifabric.minifabric_api.impl.tiles.FabricTileImpl;
import minicraft.core.Updater;
import minicraft.entity.Entity;
import minicraft.entity.furniture.Spawner;
import minicraft.entity.furniture.Tnt;
import minicraft.entity.mob.AirWizard;
import minicraft.entity.mob.Pig;
import minicraft.entity.mob.Player;
import minicraft.gfx.SpriteAnimation;
import minicraft.gfx.SpriteLinker;
import minicraft.item.FurnitureItem;
import minicraft.item.Item;
import minicraft.item.TileItem;
import minicraft.level.Level;
import minicraft.level.tile.Tile;
import net.fabricmc.api.ModInitializer;

public class TestEntrypoint implements ModInitializer {

	public static Tile tater_ore;
	public static Item tiny_potato;

	Pig steve;

	long counter = 0l;

	@Override
	public void onInitialize() {
		System.out.println("Register entities");
		EntityRegistry.registerPassiveEntity(TestEntity.class, 480);
		EntityRegistry.registerEnemyEntity(TestHostileEntity.class, 48, false);
		
		System.out.println("Register Items");
		tiny_potato = Food.addFood("Tiny Potato", "tiny_potato", 6);

		ItemRegistry.register(
			new FurnitureItem(new Spawner(new TestEntity())),
			new FurnitureItem(new Spawner(new TestHostileEntity(1))),
				tiny_potato);
		
		System.out.println("Register tiles");
		tater_ore = new OreTileBuilder("Tater Ore", tiny_potato, "tater_tile").create();
		TileRegistry.register(tater_ore);
		TileRegistry.registerOreTile(tater_ore, 400, 1);
		ItemRegistry.register(new FabricTileItemImpl("Tater Ore", new SpriteLinker.LinkedSprite(SpriteLinker.SpriteType.Item, "tiny-potato"), new TileItem.TileModel("Tater Ore"), "grass"));
		
		DimensionInfo taterWorld = DimensionRegistry.register("Tater World");
		System.out.println(taterWorld.getName() + " " + taterWorld.getIndex() + " " + taterWorld.getDepth());
		
		taterWorld.setGenerator((dimension, width, height, seed, tiles, data) -> {
			Tile tater = tater_ore;
			for (int i = 0; i < tiles.length; i++) {
				if (((i + i / width) & 1) == 0) tiles[i] = tater.id;
			}
		});

		Tile tile = new Teleport("Tater Teleport", new SpriteAnimation(SpriteLinker.SpriteType.Tile, "tater-teleport"), taterWorld);
		TileRegistry.register(tile);
		ItemRegistry.register(new FabricTileItemImpl("Tater Teleport", new SpriteLinker.LinkedSprite(SpriteLinker.SpriteType.Tile, "teleport"), new TileItem.TileModel("Tater Teleport"), "grass"));

		EntityEvents.ON_ENTITY_DAMAGED.register((mob, mobe) -> {
			if (mob instanceof Player && mobe instanceof TestEntity) {
				Updater.notifyAll("YOU DARE HARM THE TATER?!??!?!");
				mob.getLevel().add(new AirWizard(), mob.x, mob.y);
			}
		});


		EntityEvents.ON_ENTITY_KILLED.register(((mob, mobe) -> {
			if (mob instanceof Player && mobe instanceof TestEntity) {
				Updater.notifyAll("THE TATER HAS BEEN SLAIN, PREPARE TO DIE!!!!!!!!!!!!!!!!!!!!!!!!!!");
				for (int x = 0; x < 43; x++) {
					mob.getLevel().add(new AirWizard(), mob.x, mob.y);
				}
			}
		}));



		TickEvents.START_TICK.register(((level, fullTick) -> {
			steve = new Pig();
			Player[] players = level.getPlayers();
			if (players.length == 1) {
				level.add(steve, players[0].x, players[0].y + 3);
				Tnt jerry = new Tnt();
				if (counter++ % 30 == 0) {
					//level.add(jerry, players[0].x, players[0].y + 3);
					//jerry.interact(players[0], players[0].activeItem, players[0].attackDir);
				}
			}
		}));

		TickEvents.END_TICK.register(((level, fullTick) -> {
			Player[] players = level.getPlayers();
			if (players.length == 1) {
				level.remove(steve);
			}
		}));
	}
	
	private static class Teleport extends FabricTile {
		private final DimensionInfo dimension;
		
		public Teleport(String name, SpriteAnimation sprite, DimensionInfo dimension) {
			super(name, sprite, true, false, 0);
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
