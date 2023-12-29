package io.github.minifabric.minifabric_api.mixin.item;

import minicraft.gfx.SpriteLinker;
import minicraft.item.FoodItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoodItem.class)
public interface FoodItemInvoker {
    @Invoker("<init>")
    static FoodItem invokeInit(String string, SpriteLinker.LinkedSprite sprite, int i) {
        throw new AssertionError();
    }

}
