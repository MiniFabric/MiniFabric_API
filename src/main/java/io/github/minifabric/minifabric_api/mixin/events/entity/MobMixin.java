package io.github.minifabric.minifabric_api.mixin.events.entity;

import io.github.minifabric.minifabric_api.api.events.entity.EntityEvents;
import minicraft.entity.Direction;
import minicraft.entity.mob.Mob;
import minicraft.entity.mob.MobAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {
	@Shadow public int health;

	@Inject(method = "hurt(Lminicraft/entity/mob/Mob;ILminicraft/entity/Direction;)V", at = @At(value = "HEAD"))
	private void injectHurtEvent(Mob mob, int damage, Direction attackDir, CallbackInfo ci) {
		if ((Mob) (Object) this instanceof MobAi mobe) {
			EntityEvents.ON_ENTITY_DAMAGED.invoker().onEntityDamaged(mob, mobe);
		}
	}

	@Inject(method = "hurt(Lminicraft/entity/mob/Mob;ILminicraft/entity/Direction;)V", at = @At(value = "TAIL"))
	private void injectKilledEvent(Mob mob, int damage, Direction attackDir, CallbackInfo ci) {
		if ((((Mob)(Object)this).isRemoved() || this.health == 0) && (Mob) (Object) this instanceof MobAi mobe) {
			EntityEvents.ON_ENTITY_KILLED.invoker().onEntityKilled(mob, mobe);
		}
	}
}
