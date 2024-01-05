package io.github.minifabric.minifabric_api.api.item;

import io.github.minifabric.minifabric_api.api.registry.ItemRegistry;
import io.github.minifabric.minifabric_api.mixin.item.FoodItemInvoker;
import minicraft.gfx.SpriteLinker;
import minicraft.item.FoodItem;
import minicraft.item.Item;
import org.jetbrains.annotations.NotNull;

public class Food {
	public static FoodItem addFood(String name, String spriteName, int regen) {
		return FoodItemInvoker.invokeInit(name, new SpriteLinker.LinkedSprite(SpriteLinker.SpriteType.Item, spriteName), regen);
	}
}
