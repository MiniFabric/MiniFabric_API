package io.github.minifabric.minifabric_api.impl.logger;

import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.service.MixinService;

public class InternalLogger {
	public static final ILogger LOGGER = MixinService.getService().getLogger("minifabric_api");
}
