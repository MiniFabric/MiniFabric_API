package io.github.minifabric.minifabric_api.impl.resource;

import minicraft.screen.ResourcePackDisplay;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipFile;

public class FabricResourcePack extends ResourcePackDisplay.ResourcePack {
	public FabricResourcePack(URL packRoot) {
		super(packRoot, FabricResourcePackUtil.PACK_FORMAT_VERSION, "Fabric Mods", "Mod resources (Fabric mods)");
	}
}
