package io.github.minifabric.minifabric_api.impl.items;

import io.github.minifabric.minifabric_api.mixin.item.ItemsInvoker;
import minicraft.item.Item;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemsRegistry {
	public static void register(Item item) {
		ItemsInvoker.invokeAdd(item);
	}
	
	public static void register(Item... items) {
		register(new ArrayList<>(Arrays.asList(items)));
	}
	
	public static void register(ArrayList<Item> items) {
		ItemsInvoker.invokeAddAll(items);
	}
}
