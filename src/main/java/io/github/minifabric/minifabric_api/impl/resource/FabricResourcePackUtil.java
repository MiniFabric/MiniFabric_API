package io.github.minifabric.minifabric_api.impl.resource;

import io.github.minifabric.minifabric_api.impl.logger.InternalLogger;
import minicraft.screen.ResourcePackDisplay;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

public class FabricResourcePackUtil {
	public static final int PACK_FORMAT_VERSION = 1;

	private FabricResourcePackUtil() {

	}

	public static void appendModResourcePacks(List<FabricResourcePack> packList) {
		for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
			if(container.getMetadata().getType().equals("builtin")){
				continue;
			}
			Path path = container.getOrigin().getPaths().get(0);
			try (FabricResourcePack pack = new FabricResourcePack(path.toUri().toURL())) {
				if (!(pack.getResourceAsStream("assets") == null)) {
					packList.add(pack);
				}
			} catch (IOException e) {
				InternalLogger.LOGGER.debug( "Mod \"" + container.getMetadata().getId() + "\" has no built-in resource pack!");
			}
		}
	}
}
