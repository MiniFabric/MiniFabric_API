package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.api.entity.EntityRegistry;
import io.github.minifabric.minifabric_api.api.tiles.TileRegistry;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheets;
import io.github.minifabric.minifabric_api.impl.tiles.FabricOreTileImpl;
import io.github.minifabric.minifabric_api.mixin.item.FoodItemInvoker;
import io.github.minifabric.minifabric_api.mixin.item.ItemsInvoker;
import io.github.minifabric.minifabric_api.mixin.tiles.TilesInvoker;
import minicraft.entity.furniture.Spawner;
import minicraft.gfx.Sprite;
import minicraft.item.FoodItem;
import minicraft.item.FurnitureItem;
import minicraft.item.Item;
import minicraft.item.Items;
import minicraft.level.tile.Tile;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;

public class TestEntrypoint implements ModInitializer {

    public static Tile tater_ore;

    public static FoodItem tiny_potato;

    ArrayList<Item> items = new ArrayList<>();

    @Override
    public void onInitialize() {
        tiny_potato = FoodItemInvoker.invokeInit("Tiny Potato",
                new Sprite(0, 0, FabricSpriteSheets.getSheetPos("minifabric-api", FabricSpriteSheets.Types.ITEMS)), 10);

        tater_ore = new FabricOreTileImpl("Tater Ore", tiny_potato,
                new Sprite(0, 0, 2, 2, FabricSpriteSheets
                        .getSheetPos("minifabric-api", FabricSpriteSheets.Types.TILES)));

        TileRegistry.registerOreTile(tater_ore, 400, 1);


        EntityRegistry.registerPassiveEntity(TestEntity.class, 48);
        EntityRegistry.registerEnemyEntity(TestHostileEntity.class, 48, false);
        items.add(new FurnitureItem(new Spawner(new TestEntity()), 1, 28));
        items.add(new FurnitureItem(new Spawner(new TestHostileEntity(1)), 1, 28));
        items.add(tiny_potato);
        ItemsInvoker.invokeAddAll(items);
    }
}
