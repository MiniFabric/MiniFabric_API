package io.github.minifabric.minifabric_api.mixin.resource;

import io.github.minifabric.minifabric_api.impl.resource.FabricResourcePack;
import minicraft.screen.Display;
import minicraft.screen.ResourcePackDisplay;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

@Mixin(ResourcePackDisplay.class)
public class ResourcePackDisplayMixin extends Display {
	@Shadow private static ArrayList<ResourcePackDisplay.ResourcePack> loadedPacks;
	@Unique
	private static FabricResourcePack fabricPack;

	@Inject(method = "<clinit>", at = @At(value = "TAIL"))
	private static void initFabricPack(CallbackInfo ci) {
		ArrayList<FabricResourcePack> fabricPacks = new ArrayList<>(List.of());
		for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
			try {
				if (!container.getMetadata().getId().contains("java") &&
						!container.getMetadata().getId().equals("fabricloader") &&
						!container.getMetadata().getId().equals("minicraftplus") &&
						container.getContainingMod().isEmpty()) {
					URI fabricPackCandidate = container.getOrigin().getPaths().get(0).toUri();
					try (ZipFile zip = new ZipFile(new File(fabricPackCandidate))) {
						if (zip.getEntry("pack.json") != null) {
							fabricPacks.add(new FabricResourcePack(fabricPackCandidate.toURL()));
						}
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		for (FabricResourcePack pack : fabricPacks) {
			loadedPacks.add(0, pack);
		}
	}
}
