package com.example.taczhelper;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/** 建築ボトルの設計図。座標は x=右, y=上, z=前（プレイヤーが向いている方向） */
public final class Blueprints {

    public interface Placer {
        void set(int x, int y, int z, Block b);
    }

    public enum Palette {
        OAK("オーク", Blocks.OAK_PLANKS, Blocks.OAK_LOG, Blocks.SPRUCE_PLANKS, Blocks.GLASS, Blocks.GLOWSTONE),
        SPRUCE("トウヒ", Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_LOG, Blocks.DARK_OAK_PLANKS, Blocks.GLASS, Blocks.GLOWSTONE),
        BIRCH("シラカバ", Blocks.BIRCH_PLANKS, Blocks.BIRCH_LOG, Blocks.OAK_PLANKS, Blocks.GLASS, Blocks.SEA_LANTERN),
        COBBLE("丸石", Blocks.COBBLESTONE, Blocks.STONE_BRICKS, Blocks.STONE, Blocks.GLASS, Blocks.GLOWSTONE),
        STONE("石レンガ", Blocks.STONE_BRICKS, Blocks.POLISHED_ANDESITE, Blocks.SMOOTH_STONE, Blocks.GLASS, Blocks.SEA_LANTERN),
        BRICK("レンガ", Blocks.BRICKS, Blocks.STONE_BRICKS, Blocks.DARK_OAK_PLANKS, Blocks.GLASS, Blocks.GLOWSTONE),
        SAND("砂岩", Blocks.SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.GLASS, Blocks.GLOWSTONE),
        QUARTZ("クォーツ", Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR, Blocks.SMOOTH_QUARTZ, Blocks.GLASS, Blocks.SEA_LANTERN),
        DEEPSLATE("深層岩", Blocks.DEEPSLATE_BRICKS, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_TILES, Blocks.GLASS, Blocks.SHROOMLIGHT),
        CONCRETE("コンクリート", Blocks.GRAY_CONCRETE, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.GLASS, Blocks.SEA_LANTERN);

        public final String label;
        public final Block wall, frame, floor, glass, light;

        Palette(String label, Block wall, Block frame, Block floor, Block glass, Block light) {
            this.label = label;
            this.wall = wall;
            this.frame = frame;
            this.floor = floor;
            this.glass = glass;
            this.light = light;
        }
    }

    public enum Shape {
        HOUSE("家"), TOWER("塔"), SNIPER("狙撃台"), WALL("防壁"), PYRAMID("ピラミッド"),
        BRIDGE("橋"), DECK("高台"), BUNKER("掩体"), ARENA("闘技場"), CASTLE("城");

        public final String label;

        Shape(String label) {
            this.label = label;
        }
    }

    public static void build(Shape s, Palette c, Placer p) {
        switch (s) {
            case HOUSE -> house(c, p);
            case TOWER -> tower(c, p);
            case SNIPER -> sniper(c, p);
            case WALL -> wall(c, p);
            case PYRAMID -> pyramid(c, p);
            case BRIDGE -> bridge(c, p);
            case DECK -> deck(c, p);
            case BUNKER -> bunker(c, p);
            case ARENA -> arena(c, p);
            case CASTLE -> castle(c, p);
        }
    }

    // ---------- 道具 ----------

    private static void fill(Placer p, int x1, int y1, int z1, int x2, int y2, int z2, Block b) {
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++)
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++)
                for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++)
                    p.set(x, y, z, b);
    }

    /** 箱を作って中を空洞にする */
    private static void shell(Placer p, int x1, int y1, int z1, int x2, int y2, int z2, Block wall) {
        fill(p, x1, y1, z1, x2, y2, z2, wall);
        fill(p, x1 + 1, y1 + 1, z1 + 1, x2 - 1, y2 - 1, z2 - 1, Blocks.AIR);
    }

    // ---------- 各建物 ----------

    private static void house(Palette c, Placer p) {
        shell(p, -3, -1, 0, 3, 4, 6, c.wall);
        fill(p, -3, -1, 0, 3, -1, 6, c.floor);
        fill(p, -3, 4, 0, 3, 4, 6, c.floor);
        for (int x : new int[]{-3, 3})
            for (int z : new int[]{0, 6})
                fill(p, x, 0, z, x, 3, z, c.frame);
        fill(p, 0, 0, 0, 0, 1, 0, Blocks.AIR);
        for (int z : new int[]{2, 4}) {
            fill(p, -3, 1, z, -3, 2, z, c.glass);
            fill(p, 3, 1, z, 3, 2, z, c.glass);
        }
        fill(p, -1, 1, 6, 1, 2, 6, c.glass);
        p.set(0, 3, 3, c.light);
    }

    private static void tower(Palette c, Placer p) {
        shell(p, -2, -1, 0, 2, 13, 4, c.wall);
        fill(p, -2, -1, 0, 2, -1, 4, c.floor);
        for (int x : new int[]{-2, 2})
            for (int z : new int[]{0, 4})
                fill(p, x, 0, z, x, 12, z, c.frame);
        fill(p, 0, 0, 2, 0, 12, 2, c.frame);
        fill(p, 0, 0, 0, 0, 1, 0, Blocks.AIR);
        // 内側をぐるぐる登る階段（1ブロックずつジャンプで登る）
        int[][] ring = {{-1, 1}, {0, 1}, {1, 1}, {1, 2}, {1, 3}, {0, 3}, {-1, 3}, {-1, 2}};
        for (int k = 0; k <= 12; k++) {
            int[] r = ring[k % 8];
            p.set(r[0], k, r[1], c.floor);
        }
        // 屋上への出入口
        p.set(1, 13, 2, Blocks.AIR);
        p.set(1, 13, 3, Blocks.AIR);
        for (int y : new int[]{4, 8}) {
            p.set(-2, y, 2, c.glass);
            p.set(2, y, 2, c.glass);
            p.set(0, y, 4, c.glass);
        }
        for (int x = -2; x <= 2; x++)
            for (int z = 0; z <= 4; z++)
                if ((x == -2 || x == 2 || z == 0 || z == 4) && ((x + z) & 1) == 0)
                    p.set(x, 14, z, c.wall);
        p.set(0, 14, 2, c.light);
    }

    private static void sniper(Palette c, Placer p) {
        for (int x : new int[]{-1, 1})
            for (int z : new int[]{0, 2})
                fill(p, x, -1, z, x, 7, z, c.frame);
        fill(p, -1, 8, 0, 1, 8, 2, c.floor);
        for (int x = -1; x <= 1; x++)
            for (int z = 0; z <= 2; z++)
                if (!(x == 0 && z == 1) && !(x == 0 && z == 2))
                    p.set(x, 9, z, c.wall);
        // 後ろから登る階段
        for (int k = 0; k <= 7; k++) {
            int z = 10 - k;
            fill(p, 0, 0, z, 0, k, z, c.floor);
        }
    }

    private static void wall(Palette c, Placer p) {
        fill(p, -7, -1, 0, 7, -1, 1, c.frame);
        fill(p, -7, 0, 0, 7, 3, 1, c.wall);
        for (int x = -7; x <= 7; x++)
            if ((x & 1) == 0) p.set(x, 4, 0, c.wall);
        for (int x : new int[]{-7, 0, 7})
            fill(p, x, 0, 0, x, 4, 1, c.frame);
        p.set(-7, 5, 0, c.light);
        p.set(7, 5, 0, c.light);
    }

    private static void pyramid(Palette c, Placer p) {
        fill(p, -6, -1, 0, 6, -1, 12, c.floor);
        for (int l = 0; l <= 5; l++) {
            int h = 6 - l;
            fill(p, -h, l, 6 - h, h, l, 6 + h, (l % 2 == 0) ? c.wall : c.frame);
        }
        p.set(0, 6, 6, c.light);
        // 中の部屋と入口の通路
        fill(p, 0, 0, 0, 0, 1, 3, Blocks.AIR);
        fill(p, -2, 0, 4, 2, 3, 8, Blocks.AIR);
        p.set(0, 3, 6, c.light);
    }

    private static void bridge(Palette c, Placer p) {
        fill(p, -2, -1, 0, 2, -1, 19, c.floor);
        fill(p, -2, 0, 0, -2, 0, 19, c.wall);
        fill(p, 2, 0, 0, 2, 0, 19, c.wall);
        for (int z : new int[]{0, 5, 10, 15, 19})
            for (int x : new int[]{-2, 2}) {
                fill(p, x, -8, z, x, -2, z, c.frame);
                p.set(x, 1, z, c.light);
            }
    }

    private static void deck(Palette c, Placer p) {
        for (int x : new int[]{-4, 4})
            for (int z : new int[]{0, 8})
                fill(p, x, -1, z, x, 7, z, c.frame);
        fill(p, -4, 8, 0, 4, 8, 8, c.floor);
        for (int x = -4; x <= 4; x++)
            for (int z = 0; z <= 8; z++)
                if ((x == -4 || x == 4 || z == 0 || z == 8) && !(x == 0 && z == 0))
                    p.set(x, 9, z, c.wall);
        p.set(0, 9, 4, c.light);
        // 手前から登る階段
        for (int k = 0; k <= 7; k++) {
            int z = k - 8;
            fill(p, 0, 0, z, 0, k, z, c.floor);
        }
    }

    private static void bunker(Palette c, Placer p) {
        shell(p, -4, -1, 0, 4, 3, 8, c.wall);
        fill(p, -4, -1, 0, 4, -1, 8, c.floor);
        fill(p, -4, 4, 0, 4, 4, 8, c.floor);
        for (int x : new int[]{-4, 4})
            for (int z : new int[]{0, 8})
                fill(p, x, 0, z, x, 3, z, c.frame);
        fill(p, -3, 0, 4, 3, 2, 4, c.wall);
        fill(p, 0, 0, 4, 0, 1, 4, Blocks.AIR);
        fill(p, 0, 0, 0, 0, 1, 0, Blocks.AIR);
        // 銃撃用のスリット
        for (int x : new int[]{-3, -1, 1, 3}) {
            p.set(x, 2, 0, Blocks.AIR);
            p.set(x, 2, 8, Blocks.AIR);
        }
        for (int z : new int[]{2, 6}) {
            p.set(-4, 2, z, Blocks.AIR);
            p.set(4, 2, z, Blocks.AIR);
        }
        p.set(0, 2, 2, c.light);
        p.set(0, 2, 6, c.light);
    }

    private static void arena(Palette c, Placer p) {
        for (int x = -10; x <= 10; x++)
            for (int z = 0; z <= 20; z++) {
                double dx = x;
                double dz = z - 10;
                double d = Math.sqrt(dx * dx + dz * dz);
                if (d <= 10.5) p.set(x, -1, z, c.floor);
                if (d <= 9.5) {
                    fill(p, x, 0, z, x, 4, z, Blocks.AIR);
                } else if (d <= 10.5 && Math.abs(dx) > 1 && Math.abs(dz) > 1) {
                    fill(p, x, 0, z, x, 3, z, c.wall);
                    if (((x + z) & 1) == 0) p.set(x, 4, z, c.wall);
                }
            }
        for (int sx : new int[]{-1, 1})
            for (int sz : new int[]{-1, 1}) {
                int x = 6 * sx;
                int z = 10 + 6 * sz;
                fill(p, x, 0, z, x, 3, z, c.frame);
                p.set(x, 4, z, c.light);
            }
        p.set(0, -1, 10, c.light);
    }

    private static void castle(Palette c, Placer p) {
        fill(p, -9, -1, 0, 9, -1, 18, c.floor);
        fill(p, -9, 0, 0, 9, 4, 0, c.wall);
        fill(p, -9, 0, 18, 9, 4, 18, c.wall);
        fill(p, -9, 0, 0, -9, 4, 18, c.wall);
        fill(p, 9, 0, 0, 9, 4, 18, c.wall);
        for (int x = -9; x <= 9; x += 2) {
            p.set(x, 5, 0, c.wall);
            p.set(x, 5, 18, c.wall);
        }
        for (int z = 0; z <= 18; z += 2) {
            p.set(-9, 5, z, c.wall);
            p.set(9, 5, z, c.wall);
        }
        // 四隅の塔
        for (int cx : new int[]{-9, 9})
            for (int cz : new int[]{0, 18}) {
                shell(p, cx - 1, -1, cz - 1, cx + 1, 8, cz + 1, c.wall);
                fill(p, cx, -1, cz, cx, -1, cz, c.floor);
                p.set(cx, 6, cz, c.light);
                for (int dx = -1; dx <= 1; dx += 2)
                    for (int dz = -1; dz <= 1; dz += 2)
                        p.set(cx + dx, 9, cz + dz, c.wall);
            }
        // 正門
        fill(p, -1, 0, 0, 1, 2, 0, Blocks.AIR);
        fill(p, -2, 0, 0, -2, 3, 0, c.frame);
        fill(p, 2, 0, 0, 2, 3, 0, c.frame);
        // 中央の本丸
        shell(p, -3, -1, 6, 3, 6, 12, c.wall);
        fill(p, -3, -1, 6, 3, -1, 12, c.floor);
        for (int x : new int[]{-3, 3})
            for (int z : new int[]{6, 12})
                fill(p, x, 0, z, x, 5, z, c.frame);
        fill(p, 0, 0, 6, 0, 1, 6, Blocks.AIR);
        fill(p, -3, 1, 9, -3, 2, 9, c.glass);
        fill(p, 3, 1, 9, 3, 2, 9, c.glass);
        fill(p, -1, 1, 12, 1, 2, 12, c.glass);
        p.set(0, 5, 9, c.light);
        for (int x = -3; x <= 3; x++)
            for (int z = 6; z <= 12; z++)
                if ((x == -3 || x == 3 || z == 6 || z == 12) && ((x + z) & 1) == 0)
                    p.set(x, 7, z, c.wall);
    }

    private Blueprints() {}
}
