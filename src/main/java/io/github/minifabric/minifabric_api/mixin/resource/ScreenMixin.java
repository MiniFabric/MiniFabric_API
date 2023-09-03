package io.github.minifabric.minifabric_api.mixin.resource;

import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheets;
import io.github.minifabric.minifabric_api.impl.resource.FabricSpriteSheets.Types;
import minicraft.gfx.Screen;
import minicraft.gfx.SpriteSheet;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.include.com.google.common.io.ByteStreams;
import org.tinylog.Logger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(value = Screen.class, priority = 750)
public abstract class ScreenMixin {

	@Shadow
	private SpriteSheet[] sheets;

	@Inject(at = @At("RETURN"), method = "<init>(Lminicraft/gfx/SpriteSheet;Lminicraft/gfx/SpriteSheet;Lminicraft/gfx/SpriteSheet;Lminicraft/gfx/SpriteSheet;Lminicraft/gfx/SpriteSheet;)V")
	private void init(SpriteSheet itemSheet, SpriteSheet tileSheet, SpriteSheet entitySheet, SpriteSheet guiSheet, SpriteSheet skinsSheet, CallbackInfo info) {
		if (!FabricSpriteSheets.spriteSheetsProcessed) {
			for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
				Path path = container.getRootPath();
				path = path.toAbsolutePath().normalize();

				addToSheets(container, path, Types.ITEMS);
				addToSheets(container, path, Types.TILES);
				addToSheets(container, path, Types.ENTITIES);
				addToSheets(container, path, Types.GUI);
				addToSheets(container, path, Types.SKINS);
				FabricSpriteSheets.spriteSheetsProcessed = true;
			}
		}
	}
	
	private void addToSheets(ModContainer container, Path path, Types type) {
		/* TODO: Still mostly coolsim's impl, need to get it working with multiple sheets.
		 * It could probably be done by scanning the path for any files containing the type name,
		 * allowing for something like `entities2.png`, or `tiles_mechanical.png`, or even
		 * `magical_items_wands.png`.
		 */
		Path childPath = path.resolve(("assets/"+container.getMetadata().getId()+"/textures/" + type.getFileName() + ".png").replace("/", path.getFileSystem().getSeparator())).toAbsolutePath().normalize();
		if(childPath.startsWith(path) && Files.exists(childPath)){
			Logger.debug("Sheet found in " + container.getMetadata().getId() + " for " + type.name());
			try {
				InputStream in = Files.newInputStream(childPath);
				File tempFile;
				tempFile = File.createTempFile("PREFIX", "SUFFIX");
				tempFile.deleteOnExit();
				FileOutputStream out = new FileOutputStream(tempFile);
				ByteStreams.copy(in, out);

				List<SpriteSheet> tempSheets = new ArrayList<SpriteSheet>(Arrays.asList(this.sheets));
				
				tempSheets.add(new SpriteSheet(ImageIO.read(tempFile)));
				
				this.sheets = tempSheets.toArray(new SpriteSheet[0]);
				FabricSpriteSheets.sheets.add(new FabricSpriteSheets(container.getMetadata().getId(), this.sheets.length - 1, type));
				
			} catch(IOException ignored) {
			}

		}
	}

}
