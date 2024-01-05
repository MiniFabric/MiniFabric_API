package io.github.minifabric.minifabric_api.mixin.events.level;

import io.github.minifabric.minifabric_api.api.events.level.TickEvents;
import minicraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public class LevelMixin {
	@Inject(method = "tick", at = @At(value = "HEAD"))
	private void injectStartTickEvent(boolean fullTick, CallbackInfo ci) {
		TickEvents.START_TICK.invoker().onStartTick((Level)(Object)this, fullTick);
	}

	@Inject(method = "tick", at = @At(value = "TAIL"))
	private void injectEndTickEvent(boolean fullTick, CallbackInfo ci) {
		TickEvents.END_TICK.invoker().onEndTick((Level)(Object)this, fullTick);
	}
}
