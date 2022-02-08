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
import minicraft.gfx.Color;
import minicraft.gfx.Screen;
import minicraft.gfx.Sprite;
import minicraft.item.Item;
import minicraft.item.ToolItem;
import minicraft.item.ToolType;
import minicraft.level.Level;
import minicraft.level.tile.Tile;
import minicraft.level.tile.Tiles;

public class FabricOreTileImpl extends Tile {

    protected Sprite sprite;
    protected Item drop;

    public FabricOreTileImpl(String name, Item drop, Sprite sprite) {
        super(name, sprite);
        this.sprite = super.sprite;
        this.drop = drop;
    }

    public void render(Screen screen, Level level, int x, int y) {
        this.sprite.color = DirtTileInvoker.invokeDCol(level.depth);
        this.sprite.render(screen, x * 16, y * 16);
    }

    public boolean mayPass(Level level, int x, int y, Entity e) {
        return false;
    }

    public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
        this.hurt(level, x, y, 0);
        return true;
    }

    public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
        if (Game.isMode("Creative")) {
            return false;
        } else {
            if (item instanceof ToolItem) {
                ToolItem tool = (ToolItem) item;
                if (tool.type == ToolType.Pickaxe && player.payStamina(6 - tool.level) && tool.payDurability()) {
                    this.hurt(level, xt, yt, 1);
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
        if (Game.isMode("Creative")) {
            damage = oreH;
            dmg = oreH;
        }

        level.add(new SmashParticle(x * 16, y * 16));
        Sound.monsterHurt.play();
        level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.RED));
        if (dmg > 0) {
            int count = this.random.nextInt(2) + 0;
            if (damage >= oreH) {
                level.setTile(x, y, Tiles.get("Dirt"));
                count += 2;
            } else {
                level.setData(x, y, damage);
            }

            level.dropItem(x * 16 + 8, y * 16 + 8, count, new Item[]{this.drop});
        }

    }

    public void bumpedInto(Level level, int x, int y, Entity entity) {
    }

}
