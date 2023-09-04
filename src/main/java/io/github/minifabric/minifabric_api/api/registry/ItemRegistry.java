package io.github.minifabric.minifabric_api.api.registry;

import io.github.minifabric.minifabric_api.mixin.item.ItemsInvoker;
import minicraft.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemRegistry {
	/**
	 * Register new item.
	 * @param item {@link Item} to register
	 */
	public static void register(Item item) {
		ItemsInvoker.invokeAdd(item);
	}
	
	/**
	 * Register multiple items.
	 * @param items {@link Item} array to register
	 */
	public static void register(Item... items) {
		register(Arrays.asList(items));
	}
	
	/**
	 * Register multiple items.
	 * @param items {@link List} of {@link Item} to register
	 */
	public static void register(List<Item> items) {
		ItemsInvoker.invokeAddAll(new ArrayList<>(items));
	}
}
