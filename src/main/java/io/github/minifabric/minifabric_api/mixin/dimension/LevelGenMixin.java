package io.github.minifabric.minifabric_api.mixin.dimension;

import io.github.minifabric.minifabric_api.api.dimension.DimensionInfo;
import io.github.minifabric.minifabric_api.api.dimension.WorldGenerator;
import io.github.minifabric.minifabric_api.impl.registry.DimensionRegistryImpl;
import minicraft.level.LevelGen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelGen.class)
public class LevelGenMixin {
	@Inject(method = "createAndValidateMap", at = @At("HEAD"), cancellable = true)
	private static void onMapGenerate(int w, int h, int level, long seed, CallbackInfoReturnable<short[][]> info) {
		DimensionInfo container = DimensionRegistryImpl.getByDepth(level);
		if (container != null) {
			WorldGenerator generator = container.getGenerator();
			
			// If level is vanilla without generator - skip it
			if (generator == null && container.isVanilla()) {
				return;
			}
			
			short[] tiles = new short[w * h];
			short[] data = new short[tiles.length];
			
			if (generator != null) {
				generator.generate(container, w, h, seed, tiles, data);
			}
			
			info.setReturnValue(new short[][] {tiles, data});
		}
	}
}

