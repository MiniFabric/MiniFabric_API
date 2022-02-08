package io.github.minifabric.minifabric_api.mixin.tiles;

import minicraft.level.LevelGen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelGen.class)
public class LevelGenMixin {
    @Inject(method = "createUndergroundMap", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/Random;nextInt(I)I", ordinal = 1))
    private static void injectOres(int i, int val, int mval, CallbackInfoReturnable<byte[][]> cir) {

    }
}
