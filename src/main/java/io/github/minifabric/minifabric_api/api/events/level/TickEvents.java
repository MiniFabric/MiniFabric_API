package io.github.minifabric.minifabric_api.api.events.level;

import io.github.minifabric.minifabric_api.api.base.event.Event;
import io.github.minifabric.minifabric_api.api.base.event.EventFactory;
import minicraft.level.Level;

public class TickEvents {
	public static final Event<TickEvents.OnStartTick> START_TICK = EventFactory.createArrayBacked(OnStartTick.class, callbacks -> (level, fullTick) -> {
		for (TickEvents.OnStartTick callback : callbacks) {
			callback.onStartTick(level, fullTick);
		}
	});

	public static final Event<TickEvents.OnEndTick> END_TICK = EventFactory.createArrayBacked(OnEndTick.class, callbacks -> (level, fullTick) -> {
		for (TickEvents.OnEndTick callback : callbacks) {
			callback.onEndTick(level, fullTick);
		}
	});

	@FunctionalInterface
	public interface OnStartTick {
		/**
		 * Called at the start of every level tick
		 * @param level the level
		 * @param fullTick whether the last tick was a full tick
		 */
		void onStartTick(Level level, boolean fullTick);
	}

	@FunctionalInterface
	public interface OnEndTick {
		/**
		 * Called at the start of every level tick
		 * @param level the level
		 * @param fullTick whether the last tick was a full tick
		 */
		void onEndTick(Level level, boolean fullTick);
	}
}
