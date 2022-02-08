package io.github.minifabric.minifabric_api.impl.resource;

import java.util.ArrayList;
import java.util.List;

public class FabricSpriteSheets {
	
	protected String modid;
	protected int sheetPos;
	protected Types type;

	public static boolean spriteSheetsProcessed = false;

	public static List<FabricSpriteSheets> sheets = new ArrayList<FabricSpriteSheets>();
	
	public FabricSpriteSheets(String modid, int sheetPosition, Types type) {
		this.modid = modid;
		this.sheetPos = sheetPosition;
		this.type = type;
		
		sheets.add(this);
	}
	
	public static int getSheetPos(String modid, Types type) {
		for(FabricSpriteSheets entry : sheets) {
			if(entry.modid.equals(modid) && entry.type.equals(type))
				return entry.sheetPos;
		}
		return 0;
	}
	
	static {
		sheets.add(new FabricSpriteSheets("minicraftplus", 0, Types.ITEMS));
		sheets.add(new FabricSpriteSheets("minicraftplus", 1, Types.TILES));
		sheets.add(new FabricSpriteSheets("minicraftplus", 2, Types.ENTITIES));
		sheets.add(new FabricSpriteSheets("minicraftplus", 3, Types.GUI));
		sheets.add(new FabricSpriteSheets("minicraftplus", 4, Types.SKINS));
	}
	
	public enum Types {
		ITEMS("items"),
		TILES("tiles"),
		ENTITIES("entities"),
		GUI("gui"),
		SKINS("skins");
		
		protected String fileName;
		
		Types(String fileName) {
			this.fileName = fileName;
		}
		
		public String getFileName() {
			return this.fileName;
		}
	}
}
