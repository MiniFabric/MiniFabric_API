package io.github.minifabric.minifabric_api.test;

import io.github.minifabric.minifabric_api.api.entity.EntityRegistry;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheets;
import io.github.minifabric.minifabric_api.mixin.item.FoodItemInvoker;
import io.github.minifabric.minifabric_api.mixin.item.ItemsInvoker;
import minicraft.entity.furniture.Spawner;
import minicraft.gfx.Sprite;
import minicraft.item.FoodItem;
import minicraft.item.FurnitureItem;
import minicraft.item.Item;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;

public class TestEntrypoint implements ModInitializer {

    ArrayList<Item> items = new ArrayList<>();

    @Override
    public void onInitialize() {
        EntityRegistry.registerPassiveEntity(TestEntity.class, 48);
        items.add(new FurnitureItem(new Spawner(new TestEntity()), 1, 28));
        items.add(FoodItemInvoker.invokeInit("Tiny Potato", new Sprite(0, 0, FabricSpriteSheets.getSheetPos("minifabric-api", FabricSpriteSheets.Types.ITEMS)), 10));
        ItemsInvoker.invokeAddAll(items);
    }
}
