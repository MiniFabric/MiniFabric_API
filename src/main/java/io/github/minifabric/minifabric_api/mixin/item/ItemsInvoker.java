package io.github.minifabric.minifabric_api.mixin.item;

import minicraft.item.Item;
import minicraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;

@Mixin(Items.class)
public interface ItemsInvoker {
	@Invoker("addAll")
	static void invokeAddAll(ArrayList<Item> items) {
		throw new AssertionError();
	}
	
	@Invoker("add")
	static void invokeAdd(Item i) {
		throw new AssertionError();
	}
}
