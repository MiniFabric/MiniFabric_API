package io.github.minifabric.minifabric_api.api.events.entity;

import io.github.minifabric.minifabric_api.api.base.event.Event;
import io.github.minifabric.minifabric_api.api.base.event.EventFactory;
import minicraft.entity.mob.Mob;
import minicraft.entity.mob.MobAi;

public class EntityEvents {
	public static final Event<OnEntityDamaged> ON_ENTITY_DAMAGED = EventFactory.createArrayBacked(OnEntityDamaged.class, callbacks -> (mob, damagedMob) -> {
		for (OnEntityDamaged callback : callbacks) {
			callback.onEntityDamaged(mob, damagedMob);
		}
	});

	public static final Event<OnEntityKilled> ON_ENTITY_KILLED = EventFactory.createArrayBacked(OnEntityKilled.class, callbacks -> (mob, killedMob) -> {
		for (OnEntityKilled callback : callbacks) {
			callback.onEntityKilled(mob, killedMob);
		}
	});

	@FunctionalInterface
	public interface OnEntityDamaged {
		/**
		 * Called after a mob has damaged another mob.
		 * @param mob the mob
		 * @param damagedMob the entity which was damaged by the {@code mob}
		 */
		void onEntityDamaged(Mob mob, MobAi damagedMob);
	}

	@FunctionalInterface
	public interface OnEntityKilled {
		/**
		 * Called after a mob has damaged another mob.
		 * @param mob the mob
		 * @param killedMob the entity which was damaged by the {@code mob}
		 */
		void onEntityKilled(Mob mob, MobAi killedMob);
	}
}
