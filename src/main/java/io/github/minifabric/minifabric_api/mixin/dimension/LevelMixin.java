package io.github.minifabric.minifabric_api.mixin.dimension;

import io.github.minifabric.minifabric_api.api.dimension.DimensionInfo;
import io.github.minifabric.minifabric_api.api.dimension.WorldSpawner;
import io.github.minifabric.minifabric_api.impl.registry.DimensionRegistryImpl;
import minicraft.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin {
	@Shadow @Final public int depth;
	
	@Inject(method = "getLevelName", at = @At("HEAD"), cancellable = true)
	private static void getName(int depth, CallbackInfoReturnable<String> info) {
		DimensionInfo dimension = DimensionRegistryImpl.getByDepth(depth);
		if (dimension != null) info.setReturnValue(dimension.getName());
	}
	
	@Inject(method = "trySpawn", at = @At("HEAD"), cancellable = true)
	private void netherMobSpawn(CallbackInfo info) {
		DimensionInfo dimension = DimensionRegistryImpl.getByDepth(this.depth);
		if (dimension == null) return;
		WorldSpawner spawner = dimension.getSpawner();
		if (spawner == null) return;
		spawner.spawnEntities(dimension);
		info.cancel();
	}
}
