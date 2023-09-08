package io.github.minifabric.minifabric_api.impl.logger;

import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.logging.Level;
import org.spongepowered.asm.service.MixinService;

public class InternalLogger {
	public static final ILogger LOGGER = MixinService.getService().getLogger("minifabric_api");
	
	public static void info(String message) {
		LOGGER.log(Level.INFO, message);
	}
	
	public static void warn(String message) {
		LOGGER.log(Level.WARN, message);
	}
}
