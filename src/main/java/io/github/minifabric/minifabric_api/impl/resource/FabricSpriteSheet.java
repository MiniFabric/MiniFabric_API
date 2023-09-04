package io.github.minifabric.minifabric_api.impl.resource;

import java.util.ArrayList;
import java.util.List;

public class FabricSpriteSheet {
	public static final List<FabricSpriteSheet> SHEETS = new ArrayList<>();
	public static boolean spriteSheetsProcessed = false;
	protected SpriteSheetType type;
	protected String modid;
	protected int sheetPos;
	
	public FabricSpriteSheet(String modid, int sheetPosition, SpriteSheetType type) {
		this.modid = modid;
		this.sheetPos = sheetPosition;
		this.type = type;
		SHEETS.add(this);
	}
	
	public static int getSheetPos(String modid, SpriteSheetType type) {
		for(FabricSpriteSheet entry : SHEETS) {
			if(entry.modid.equals(modid) && entry.type.equals(type))
				return entry.sheetPos;
		}
		return 0;
	}
	
	static {
		SHEETS.add(new FabricSpriteSheet("minicraftplus", 0, SpriteSheetType.ITEMS));
		SHEETS.add(new FabricSpriteSheet("minicraftplus", 1, SpriteSheetType.TILES));
		SHEETS.add(new FabricSpriteSheet("minicraftplus", 2, SpriteSheetType.ENTITIES));
		SHEETS.add(new FabricSpriteSheet("minicraftplus", 3, SpriteSheetType.GUI));
		SHEETS.add(new FabricSpriteSheet("minicraftplus", 4, SpriteSheetType.SKINS));
	}
	
	public enum SpriteSheetType {
		ITEMS("items"),
		TILES("tiles"),
		ENTITIES("entities"),
		GUI("gui"),
		SKINS("skins");
		
		final String fileName;
		
		SpriteSheetType(String fileName) {
			this.fileName = fileName;
		}
		
		public String getFileName() {
			return this.fileName;
		}
	}
}
