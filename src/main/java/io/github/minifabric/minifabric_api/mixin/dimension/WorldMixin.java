package io.github.minifabric.minifabric_api.mixin.dimension;

import io.github.minifabric.minifabric_api.api.dimension.DimensionInfo;
import io.github.minifabric.minifabric_api.impl.registry.DimensionRegistryImpl;
import minicraft.core.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class WorldMixin {
	@ModifyConstant(method = "initWorld", constant = @Constant(intValue = 6))
	private static int changeLevelCount(int arraySize) {
		return World.maxLevelDepth - World.minLevelDepth + 1;
	}
	
	@Inject(method = "lvlIdx", at = @At("HEAD"), cancellable = true)
	private static void depthToIndex(int depth, CallbackInfoReturnable<Integer> info) {
		DimensionInfo dimension = DimensionRegistryImpl.getByDepth(depth);
		if (dimension != null) info.setReturnValue(dimension.getIndex());
	}
}
