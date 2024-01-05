package io.github.minifabric.minifabric_api.impl.tiles;

import io.github.minifabric.minifabric_api.mixin.tiles.DirtTileInvoker;
import minicraft.core.Game;
import minicraft.core.io.Sound;
import minicraft.entity.Direction;
import minicraft.entity.Entity;
import minicraft.entity.mob.Mob;
import minicraft.entity.mob.Player;
import minicraft.entity.particle.SmashParticle;
import minicraft.entity.particle.TextParticle;
import minicraft.gfx.*;
import minicraft.item.Item;
import minicraft.item.ToolItem;
import minicraft.item.ToolType;
import minicraft.level.Level;
import minicraft.level.tile.OreTile;
import minicraft.level.tile.Tile;
import minicraft.level.tile.Tiles;
import minicraft.util.AdvancementElement;

public class FabricOreTileImpl extends Tile {
	protected SpriteAnimation sheet;
	protected boolean isCloudType;
	protected Item drop;

	public FabricOreTileImpl(String name, Item drop, SpriteAnimation sheet, boolean isCloud) {
		super(name, sheet);
		this.isCloudType = isCloud;
		this.sheet = sheet;
		this.drop = drop;
	}
	
	public void render(Screen screen, Level level, int x, int y) {
		Tiles.get(isCloudType ? "cloud" : "dirt").render(screen, level, x, y);
		this.sheet.render(screen, level, x, y);
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return false;
	}
	
	public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
		this.hurt(level, x, y, 0);
		return true;
	}
	
	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (Game.isMode("minicraft.settings.mode.creative")) {
			return false;
		}
		else {
			if (item instanceof ToolItem tool) {
				if (tool.type == ToolType.Pickaxe && player.payStamina(6 - tool.level) && tool.payDurability()) {
					int data = level.getData(xt, yt);
					this.hurt(level, xt, yt, 1);
					AdvancementElement.AdvancementTrigger.ItemUsedOnTileTrigger.INSTANCE.trigger(new AdvancementElement.AdvancementTrigger.ItemUsedOnTileTrigger.ItemUsedOnTileTriggerConditionHandler.ItemUsedOnTileTriggerConditions(item, this, data, xt, yt, level.depth));
					return true;
				}
			}

			return false;
		}
	}
	
	public Item getOre() {
		return this.drop;
	}
	
	public void hurt(Level level, int x, int y, int dmg) {
		int damage = level.getData(x, y) + 1;
		int oreH = this.random.nextInt(10) + 3;
		if (Game.isMode("minicraft.settings.mode.creative")) {
			damage = oreH;
			dmg = oreH;
		}
		
		level.add(new SmashParticle(x * 16, y * 16));
		Sound.play("monsterhurt");
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.RED));
		if (dmg > 0) {
			int count = this.random.nextInt(2);
			if (damage >= oreH) {
				level.setTile(x, y, Tiles.get("Dirt"));
				count += 2;
			}
			else {
				level.setData(x, y, damage);
			}
			level.dropItem(x * 16 + 8, y * 16 + 8, count, new Item[]{this.drop});
		}
	}
	
	public void bumpedInto(Level level, int x, int y, Entity entity) {}
}
