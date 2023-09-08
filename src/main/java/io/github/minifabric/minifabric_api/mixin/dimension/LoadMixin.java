package io.github.minifabric.minifabric_api.mixin.dimension;

import io.github.minifabric.minifabric_api.api.dimension.DimensionInfo;
import io.github.minifabric.minifabric_api.api.dimension.WorldGenerator;
import io.github.minifabric.minifabric_api.impl.logger.InternalLogger;
import io.github.minifabric.minifabric_api.impl.registry.DimensionRegistryImpl;
import minicraft.core.World;
import minicraft.level.Level;
import minicraft.level.tile.Tiles;
import minicraft.saveload.Load;
import minicraft.screen.LoadingDisplay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;
import java.util.ArrayList;

@Mixin(Load.class)
public class LoadMixin {
	@Shadow @Final private static String extension;
	@Shadow private String location;
	@Shadow private ArrayList<String> data;
	@Shadow private ArrayList<String> extradata;
	
	@Shadow private float percentInc;
	@Unique private int levelIndex;
	
	@Inject(method = "<init>(Ljava/lang/String;Z)V", at = @At(
		value = "INVOKE",
		target = "Lminicraft/saveload/Load;loadFromFile(Ljava/lang/String;)V"
	))
	private void onLoad(String worldname, boolean loadGame, CallbackInfo info) {
		int count = World.maxLevelDepth - World.minLevelDepth + 1;
		if (World.levels.length != count) {
			Level[] levels = new Level[count];
			System.arraycopy(World.levels, 0, levels, 0, World.levels.length);
			World.levels = levels;
		}
	}
	
	@Inject(method = "loadWorld", at = @At(
		value = "INVOKE",
		target = "Lminicraft/saveload/Load;loadFromFile(Ljava/lang/String;)V",
		shift = Shift.BEFORE
	), locals = LocalCapture.CAPTURE_FAILHARD)
	private void genMissingWorld(String filename, CallbackInfo info, int l, int lvlidx) {
		levelIndex = lvlidx;
	}
	
	@Inject(method = "loadFromFile(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
	private void generateIfMissing(String filename, CallbackInfo info) {
		if (new File(filename).exists()) return;
		
		InternalLogger.warn("Level data is missing: " + filename);
		info.cancel();
		
		this.data.clear();
		this.extradata.clear();
		
		DimensionInfo dimension = DimensionRegistryImpl.getByIndex(levelIndex);
		
		this.data.add("128");
		this.data.add("128");
		this.data.add("0");
		this.data.add(Integer.toString(dimension.getDepth()));
		
		short[] tiles = new short[16384];
		short[] data = new short[16384];
		
		WorldGenerator generator = dimension.getGenerator();
		if (generator != null) {
			dimension.getGenerator().generate(dimension, 128, 128, 0, tiles, data);
		}
		
		for (int i = 0; i < tiles.length; i++) {
			this.data.add(Tiles.get(tiles[i]).name);
			this.extradata.add(Short.toString(data[i]));
		}
		
		LoadingDisplay.progress(this.percentInc);
	}
}
