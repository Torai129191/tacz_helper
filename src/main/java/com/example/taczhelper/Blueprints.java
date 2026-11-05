package com.example.taczhelper;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * 建築ボトルの設計図。25種類の建物 x 40種類の素材 = 1000個。
 * 座標は x=右, y=上, z=前（プレイヤーが向いている方向）。
 * 向き(dir): 0=前(+z) 1=右(+x) 2=後ろ(-z) 3=左(-x)
 */
public final class Blueprints {

    public interface Placer {
        void set(int x, int y, int z, Block b);
        /** dir = 階段が登っていく向き */
        void stair(int x, int y, int z, Block b, int dir, boolean flip);
        void slab(int x, int y, int z, Block b, boolean top);
        /** dir = はしごが向いている側（壁とは反対側） */
        void ladder(int x, int y, int z, int dir);
        /** ルート入りチェスト。dir = チェストの正面 */
        void chest(int x, int y, int z, int dir);
        /** オリジナル仕掛けブロック。kind は JUMP / SPEED / CHECK / GOAL / CRUMBLE / HEAL */
        void gimmick(int x, int y, int z, int kind);
    }

    public static final int JUMP = 0, SPEED = 1, CHECK = 2, GOAL = 3, CRUMBLE = 4, HEAL = 5;

    public enum Palette {
        OAK("オーク", Blocks.OAK_PLANKS, Blocks.OAK_LOG, Blocks.SPRUCE_PLANKS, Blocks.OAK_STAIRS, Blocks.OAK_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        SPRUCE("トウヒ", Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_LOG, Blocks.DARK_OAK_PLANKS, Blocks.SPRUCE_STAIRS, Blocks.SPRUCE_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        BIRCH("シラカバ", Blocks.BIRCH_PLANKS, Blocks.BIRCH_LOG, Blocks.OAK_PLANKS, Blocks.BIRCH_STAIRS, Blocks.BIRCH_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        JUNGLE("ジャングル", Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_LOG, Blocks.OAK_PLANKS, Blocks.JUNGLE_STAIRS, Blocks.JUNGLE_SLAB, Blocks.GLASS, Blocks.SHROOMLIGHT),
        ACACIA("アカシア", Blocks.ACACIA_PLANKS, Blocks.ACACIA_LOG, Blocks.DARK_OAK_PLANKS, Blocks.ACACIA_STAIRS, Blocks.ACACIA_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        DARKOAK("ダークオーク", Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_LOG, Blocks.SPRUCE_PLANKS, Blocks.DARK_OAK_STAIRS, Blocks.DARK_OAK_SLAB, Blocks.GLASS, Blocks.SHROOMLIGHT),
        MANGROVE("マングローブ", Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_LOG, Blocks.SPRUCE_PLANKS, Blocks.MANGROVE_STAIRS, Blocks.MANGROVE_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        CHERRY("サクラ", Blocks.CHERRY_PLANKS, Blocks.CHERRY_LOG, Blocks.PINK_CONCRETE, Blocks.CHERRY_STAIRS, Blocks.CHERRY_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        BAMBOO("竹", Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_BLOCK, Blocks.BAMBOO_MOSAIC, Blocks.BAMBOO_STAIRS, Blocks.BAMBOO_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        CRIMSON("クリムゾン", Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_STEM, Blocks.NETHER_BRICKS, Blocks.CRIMSON_STAIRS, Blocks.CRIMSON_SLAB, Blocks.GLASS, Blocks.SHROOMLIGHT),
        WARPED("歪んだ木", Blocks.WARPED_PLANKS, Blocks.WARPED_STEM, Blocks.CRIMSON_PLANKS, Blocks.WARPED_STAIRS, Blocks.WARPED_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        COBBLE("丸石", Blocks.COBBLESTONE, Blocks.STONE_BRICKS, Blocks.STONE, Blocks.COBBLESTONE_STAIRS, Blocks.COBBLESTONE_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        MOSSY("苔むした石", Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_STONE_BRICKS, Blocks.STONE_BRICKS, Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.GLASS, Blocks.SHROOMLIGHT),
        STONEBRICK("石レンガ", Blocks.STONE_BRICKS, Blocks.POLISHED_ANDESITE, Blocks.SMOOTH_STONE, Blocks.STONE_BRICK_STAIRS, Blocks.STONE_BRICK_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        BRICK("レンガ", Blocks.BRICKS, Blocks.STONE_BRICKS, Blocks.DARK_OAK_PLANKS, Blocks.BRICK_STAIRS, Blocks.BRICK_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        SAND("砂岩", Blocks.SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        REDSAND("赤い砂岩", Blocks.RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE, Blocks.RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        QUARTZ("クォーツ", Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR, Blocks.SMOOTH_QUARTZ, Blocks.QUARTZ_STAIRS, Blocks.QUARTZ_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        PRISMARINE("プリズマリン", Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE, Blocks.PRISMARINE, Blocks.PRISMARINE_BRICK_STAIRS, Blocks.PRISMARINE_BRICK_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        DEEPSLATE("深層岩", Blocks.DEEPSLATE_BRICKS, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.DEEPSLATE_BRICK_SLAB, Blocks.GLASS, Blocks.SHROOMLIGHT),
        BLACKSTONE("ブラックストーン", Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.GILDED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.GLASS, Blocks.SHROOMLIGHT),
        NETHERBRICK("ネザーレンガ", Blocks.NETHER_BRICKS, Blocks.RED_NETHER_BRICKS, Blocks.CHISELED_NETHER_BRICKS, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_BRICK_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        ENDSTONE("エンドストーン", Blocks.END_STONE_BRICKS, Blocks.PURPUR_PILLAR, Blocks.PURPUR_BLOCK, Blocks.PURPUR_STAIRS, Blocks.PURPUR_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        OBSIDIAN("黒曜石", Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_STAIRS, Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.GLASS, Blocks.SHROOMLIGHT),
        MUD("泥レンガ", Blocks.MUD_BRICKS, Blocks.PACKED_MUD, Blocks.MUD_BRICKS, Blocks.MUD_BRICK_STAIRS, Blocks.MUD_BRICK_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        WHITE("ホワイト", Blocks.WHITE_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE, Blocks.SMOOTH_QUARTZ, Blocks.QUARTZ_STAIRS, Blocks.QUARTZ_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        PINK("ピンク", Blocks.PINK_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.PINK_TERRACOTTA, Blocks.CHERRY_STAIRS, Blocks.CHERRY_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        RED("レッド", Blocks.RED_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.BLACK_CONCRETE, Blocks.RED_NETHER_BRICK_STAIRS, Blocks.RED_NETHER_BRICK_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        ORANGE("オレンジ", Blocks.ORANGE_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.ORANGE_TERRACOTTA, Blocks.RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        YELLOW("イエロー", Blocks.YELLOW_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.YELLOW_TERRACOTTA, Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        LIME("ライム", Blocks.LIME_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        GREEN("グリーン", Blocks.GREEN_CONCRETE, Blocks.LIME_CONCRETE, Blocks.MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.GLASS, Blocks.SHROOMLIGHT),
        CYAN("シアン", Blocks.CYAN_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE, Blocks.PRISMARINE_STAIRS, Blocks.PRISMARINE_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        LIGHTBLUE("ライトブルー", Blocks.LIGHT_BLUE_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.BLUE_CONCRETE, Blocks.QUARTZ_STAIRS, Blocks.QUARTZ_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        BLUE("ブルー", Blocks.BLUE_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.BLUE_TERRACOTTA, Blocks.PRISMARINE_BRICK_STAIRS, Blocks.PRISMARINE_BRICK_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        PURPLE("パープル", Blocks.PURPLE_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.PURPLE_TERRACOTTA, Blocks.PURPUR_STAIRS, Blocks.PURPUR_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        MAGENTA("マゼンタ", Blocks.MAGENTA_CONCRETE, Blocks.PINK_CONCRETE, Blocks.PURPLE_CONCRETE, Blocks.PURPUR_STAIRS, Blocks.PURPUR_SLAB, Blocks.GLASS, Blocks.GLOWSTONE),
        GRAY("グレー", Blocks.GRAY_CONCRETE, Blocks.IRON_BLOCK, Blocks.BLACK_CONCRETE, Blocks.POLISHED_ANDESITE_STAIRS, Blocks.POLISHED_ANDESITE_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        BLACK("ブラック", Blocks.BLACK_CONCRETE, Blocks.GRAY_CONCRETE, Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_STAIRS, Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.GLASS, Blocks.SEA_LANTERN),
        GOLD("ゴールド", Blocks.GOLD_BLOCK, Blocks.QUARTZ_PILLAR, Blocks.SMOOTH_QUARTZ, Blocks.QUARTZ_STAIRS, Blocks.QUARTZ_SLAB, Blocks.GLASS, Blocks.GLOWSTONE);
        public final String label;
        public final Block wall, frame, floor, stair, slab, glass, light;

        Palette(String label, Block wall, Block frame, Block floor, Block stair, Block slab, Block glass, Block light) {
            this.label = label;
            this.wall = wall;
            this.frame = frame;
            this.floor = floor;
            this.stair = stair;
            this.slab = slab;
            this.glass = glass;
            this.light = light;
        }

        /** 葉っぱ（ツリーハウス用） */
        public Block leaf() {
            int m = ordinal() % 3;
            return m == 0 ? Blocks.CHERRY_LEAVES : (m == 1 ? Blocks.OAK_LEAVES : Blocks.AZALEA_LEAVES);
        }

        /** アイコンの色（壁ブロックの地図色を少し明るくしたもの） */
        public int color() {
            int c = wall.defaultMapColor().col;
            int r = (c >> 16) & 255, g = (c >> 8) & 255, b = c & 255;
            r = Math.min(255, (int) (r * 0.75 + 64));
            g = Math.min(255, (int) (g * 0.75 + 64));
            b = Math.min(255, (int) (b * 0.75 + 64));
            return (r << 16) | (g << 8) | b;
        }
    }

    public enum Shape {
        HOUSE("家", "屋根つきの家。奥にルートチェスト入り"),
        TOWER("塔", "らせん階段で屋上まで登れる見張り塔"),
        SNIPER("狙撃台", "細い高台。後ろの階段から登る"),
        WALL("防壁", "胸壁つきの防壁。灯りつき"),
        PYRAMID("ピラミッド", "中にチェストとクモの巣の部屋がある"),
        BRIDGE("橋", "灯りつきの長い橋"),
        DECK("高台", "階段で登る高台。チェスト入り"),
        BUNKER("掩体", "銃眼つきの掩体。奥にチェスト"),
        ARENA("闘技場", "スライムのジャンプ台つきの円形闘技場"),
        CASTLE("城", "四隅に塔、中央に本丸。チェスト入り"),
        COTTAGE("小屋", "お花とケーキの小さな小屋。チェスト入り"),
        MUSHROOM("キノコの家", "光る水玉のキノコの家。チェスト入り"),
        PAGODA("三重の塔", "反り屋根の塔。1階にチェスト"),
        TORII("鳥居", "3つ並んだ鳥居と参道"),
        DOME("ドーム", "ガラス窓つきのドーム。チェスト入り"),
        LIGHTHOUSE("灯台", "はしごで展望台まで登れる"),
        TREEHOUSE("ツリーハウス", "はしごで登る木の上のデッキ。ケーキとチェスト"),
        CAMP("キャンプ", "テント2つと焚き火。チェスト入り"),
        ROCKET("ロケット", "中に入ってはしごで登れるロケット"),
        UFO("UFO", "はしごで登る空飛ぶ円盤。チェスト入り"),
        SLIDE("スライダー", "塔の上から氷の滑り台で一気に下る"),
        JUMPPARK("ジャンプ広場", "塔から飛び込むスライムの大きなジャンプ台"),
        MAZE("迷路", "中央にチェストがある迷路"),
        TRAPROOM("罠の部屋", "TNT地雷とクモの巣。奥のチェストを狙え"),
        FOUNTAIN("噴水", "水が流れる噴水広場"),
        CHURCH("教会", "ベンチと祭壇のある教会。チェスト入り"),
        WINDMILL("風車", "はしごで登れる風車。羽根が回りそう"),
        MARKET("屋台市場", "しましまテントの屋台が8つ並ぶ市場"),
        GARDEN("庭園", "花畑と池と噴水のある庭園"),
        SHRINE("神社", "鳥居の参道と本殿。賽銭チェスト入り"),
        PK_STEPS("アスレ：空中ステップ", "空中の足場を登っていくアスレ。チェックポイントとゴール付き"),
        PK_JUMP("アスレ：ジャンプ台島", "ジャンプ台で島から島へ飛び移るアスレ"),
        PK_DASH("アスレ：ダッシュ", "ダッシュ台で大きな穴を飛び越えるアスレ"),
        PK_CRUMBLE("アスレ：崩れる足場", "踏むと崩れる足場の橋。下はスライムのネット"),
        PK_SPIRAL("アスレ：らせん", "塔のまわりをぐるぐる登るアスレ。下はスライム"),
        PK_ICE("アスレ：氷の島", "つるつる氷の島を渡るアスレ。下はスライム"),
        TOWN("町", "鳥居の入口、噴水広場、家8軒、塔、城まである大きな町"),
        VILLAGE("村", "コテージとキノコ、風車、市場、庭園、神社のある村"),
        FORTRESS("要塞", "外壁と四隅の塔、本丸、掩体と高台のある大きな要塞");

        public final String label;
        public final String desc;

        Shape(String label, String desc) {
            this.label = label;
            this.desc = desc;
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
            case COTTAGE -> cottage(c, p);
            case MUSHROOM -> mushroom(c, p);
            case PAGODA -> pagoda(c, p);
            case TORII -> torii(c, p);
            case DOME -> dome(c, p);
            case LIGHTHOUSE -> lighthouse(c, p);
            case TREEHOUSE -> treehouse(c, p);
            case CAMP -> camp(c, p);
            case ROCKET -> rocket(c, p);
            case UFO -> ufo(c, p);
            case SLIDE -> slide(c, p);
            case JUMPPARK -> jumppark(c, p);
            case MAZE -> maze(c, p);
            case TRAPROOM -> traproom(c, p);
            case FOUNTAIN -> fountain(c, p);
            case CHURCH -> church(c, p);
            case WINDMILL -> windmill(c, p);
            case MARKET -> market(c, p);
            case GARDEN -> garden(c, p);
            case SHRINE -> shrine(c, p);
            case PK_STEPS -> pkSteps(c, p);
            case PK_JUMP -> pkJump(c, p);
            case PK_DASH -> pkDash(c, p);
            case PK_CRUMBLE -> pkCrumble(c, p);
            case PK_SPIRAL -> pkSpiral(c, p);
            case PK_ICE -> pkIce(c, p);
            case TOWN -> town(c, p);
            case VILLAGE -> village(c, p);
            case FORTRESS -> fortress(c, p);
        }
    }

    // ================= 道具 =================

    private static final Block AIR = Blocks.AIR;

    private static void fill(Placer p, int x1, int y1, int z1, int x2, int y2, int z2, Block b) {
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++)
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++)
                for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++)
                    p.set(x, y, z, b);
    }

    /** 箱を作って中を空洞にする */
    private static void shell(Placer p, int x1, int y1, int z1, int x2, int y2, int z2, Block wall) {
        fill(p, x1, y1, z1, x2, y2, z2, wall);
        fill(p, x1 + 1, y1 + 1, z1 + 1, x2 - 1, y2 - 1, z2 - 1, AIR);
    }

    /** 円柱 / 円筒。rIn < d <= rOut の範囲を y1..y2 で埋める（rIn=-1なら中まで詰まる） */
    private static void cyl(Placer p, int cx, int cz, double rIn, double rOut, int y1, int y2, Block b) {
        int r = (int) Math.ceil(rOut);
        for (int x = cx - r; x <= cx + r; x++)
            for (int z = cz - r; z <= cz + r; z++) {
                double d = Math.sqrt((x - cx) * (x - cx) + (z - cz) * (z - cz));
                if (d > rIn && d <= rOut)
                    for (int y = y1; y <= y2; y++) p.set(x, y, z, b);
            }
    }

    /** z方向に伸びる切妻屋根（階段）。両端の三角形の壁は gz1, gz2 に作る */
    private static void gable(Placer p, Palette c, int xl, int xr, int z1, int z2, int y, int gz1, int gz2) {
        for (int i = 0; xl + i <= xr - i; i++) {
            int a = xl + i, b = xr - i, yy = y + i;
            if (a == b) {
                for (int z = z1; z <= z2; z++) p.slab(a, yy, z, c.slab, false);
            } else {
                for (int z = z1; z <= z2; z++) {
                    p.stair(a, yy, z, c.stair, 1, false);
                    p.stair(b, yy, z, c.stair, 3, false);
                }
                for (int x = a + 1; x <= b - 1; x++) {
                    p.set(x, yy, gz1, c.wall);
                    p.set(x, yy, gz2, c.wall);
                }
            }
        }
    }

    /** 別の建物を、ずらして・回転して置くための包み紙（町や村を作るのに使う） */
    private static final class Shift implements Placer {
        private final Placer p;
        private final int dx, dz, rot;

        Shift(Placer p, int dx, int dz, int rot) {
            this.p = p;
            this.dx = dx;
            this.dz = dz;
            this.rot = rot & 3;
        }

        private int tx(int x, int z) {
            return switch (rot) { case 1 -> z; case 2 -> -x; case 3 -> -z; default -> x; } + dx;
        }

        private int tz(int x, int z) {
            return switch (rot) { case 1 -> -x; case 2 -> -z; case 3 -> x; default -> z; } + dz;
        }

        public void set(int x, int y, int z, Block b) { p.set(tx(x, z), y, tz(x, z), b); }
        public void stair(int x, int y, int z, Block b, int dir, boolean flip) { p.stair(tx(x, z), y, tz(x, z), b, (dir + rot) & 3, flip); }
        public void slab(int x, int y, int z, Block b, boolean top) { p.slab(tx(x, z), y, tz(x, z), b, top); }
        public void ladder(int x, int y, int z, int dir) { p.ladder(tx(x, z), y, tz(x, z), (dir + rot) & 3); }
        public void chest(int x, int y, int z, int dir) { p.chest(tx(x, z), y, tz(x, z), (dir + rot) & 3); }
        public void gimmick(int x, int y, int z, int kind) { p.gimmick(tx(x, z), y, tz(x, z), kind); }
    }

    /** dx, dz だけずらし、rot回(90度ずつ)回して置く */
    private static Placer at(Placer p, int dx, int dz, int rot) {
        return new Shift(p, dx, dz, rot);
    }

    /** 反り屋根のひさし（外周に階段、中は床） */
    private static void eave(Placer p, Palette c, int x1, int x2, int z1, int z2, int y) {
        fill(p, x1 + 1, y, z1 + 1, x2 - 1, y, z2 - 1, c.floor);
        for (int x = x1; x <= x2; x++) {
            p.stair(x, y, z1, c.stair, 0, false);
            p.stair(x, y, z2, c.stair, 2, false);
        }
        for (int z = z1 + 1; z <= z2 - 1; z++) {
            p.stair(x1, y, z, c.stair, 1, false);
            p.stair(x2, y, z, c.stair, 3, false);
        }
    }

    // ================= 建物 1〜10 =================

    private static void house(Palette c, Placer p) {
        shell(p, -3, -1, 0, 3, 3, 6, c.wall);
        fill(p, -3, -1, 0, 3, -1, 6, c.floor);
        fill(p, -3, 3, 0, 3, 3, 6, c.floor);
        for (int x : new int[]{-3, 3})
            for (int z : new int[]{0, 6})
                fill(p, x, 0, z, x, 2, z, c.frame);
        gable(p, c, -4, 4, -1, 7, 4, 0, 6);
        fill(p, 0, 0, 0, 0, 1, 0, AIR);
        for (int z : new int[]{2, 4}) {
            fill(p, -3, 1, z, -3, 2, z, c.glass);
            fill(p, 3, 1, z, 3, 2, z, c.glass);
        }
        fill(p, -1, 1, 6, 1, 2, 6, c.glass);
        p.set(0, 2, 3, c.light);
        p.chest(2, 0, 5, 2);
        p.set(-2, 0, -1, Blocks.POTTED_POPPY);
        p.set(2, 0, -1, Blocks.POTTED_DANDELION);
    }

    private static void tower(Palette c, Placer p) {
        shell(p, -2, -1, 0, 2, 13, 4, c.wall);
        fill(p, -2, -1, 0, 2, -1, 4, c.floor);
        for (int x : new int[]{-2, 2})
            for (int z : new int[]{0, 4})
                fill(p, x, 0, z, x, 12, z, c.frame);
        fill(p, 0, 0, 2, 0, 12, 2, c.frame);
        fill(p, 0, 0, 0, 0, 1, 0, AIR);
        // 内側をぐるぐる登る階段（1ブロックずつジャンプで登る）
        int[][] ring = {{-1, 1}, {0, 1}, {1, 1}, {1, 2}, {1, 3}, {0, 3}, {-1, 3}, {-1, 2}};
        for (int k = 0; k <= 12; k++) {
            int[] r = ring[k % 8];
            p.set(r[0], k, r[1], c.floor);
        }
        p.set(1, 13, 2, AIR);
        p.set(1, 13, 3, AIR);
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
        for (int k = 0; k <= 7; k++) {
            int z = 10 - k;
            fill(p, 0, 0, z, 0, k, z, c.floor);
        }
        p.set(0, 9, 1, c.light);
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
        p.set(0, 5, 0, c.light);
    }

    private static void pyramid(Palette c, Placer p) {
        fill(p, -6, -1, 0, 6, -1, 12, c.floor);
        for (int l = 0; l <= 5; l++) {
            int h = 6 - l;
            fill(p, -h, l, 6 - h, h, l, 6 + h, (l % 2 == 0) ? c.wall : c.frame);
        }
        p.set(0, 6, 6, c.light);
        fill(p, 0, 0, 0, 0, 1, 3, AIR);
        fill(p, -2, 0, 4, 2, 3, 8, AIR);
        p.set(0, 3, 6, c.light);
        p.set(-2, 2, 4, Blocks.COBWEB);
        p.set(2, 2, 4, Blocks.COBWEB);
        p.set(-1, 1, 6, Blocks.COBWEB);
        p.chest(0, 0, 8, 2);
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
        p.chest(2, 9, 6, 2);
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
        fill(p, 0, 0, 4, 0, 1, 4, AIR);
        fill(p, 0, 0, 0, 0, 1, 0, AIR);
        for (int x : new int[]{-3, -1, 1, 3}) {
            p.set(x, 2, 0, AIR);
            p.set(x, 2, 8, AIR);
        }
        for (int z : new int[]{2, 6}) {
            p.set(-4, 2, z, AIR);
            p.set(4, 2, z, AIR);
        }
        p.set(0, 2, 2, c.light);
        p.set(0, 2, 6, c.light);
        p.chest(0, 0, 7, 2);
    }

    private static void arena(Palette c, Placer p) {
        for (int x = -10; x <= 10; x++)
            for (int z = 0; z <= 20; z++) {
                double dx = x;
                double dz = z - 10;
                double d = Math.sqrt(dx * dx + dz * dz);
                if (d <= 10.5) p.set(x, -1, z, c.floor);
                if (d <= 9.5) {
                    fill(p, x, 0, z, x, 4, z, AIR);
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
        // ジャンプ台
        p.set(-3, -1, 10, Blocks.SLIME_BLOCK);
        p.set(3, -1, 10, Blocks.SLIME_BLOCK);
        p.set(0, -1, 7, Blocks.SLIME_BLOCK);
        p.set(0, -1, 13, Blocks.SLIME_BLOCK);
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
        for (int cx : new int[]{-9, 9})
            for (int cz : new int[]{0, 18}) {
                shell(p, cx - 1, -1, cz - 1, cx + 1, 8, cz + 1, c.wall);
                p.set(cx, -1, cz, c.floor);
                p.set(cx, 6, cz, c.light);
                for (int dx = -1; dx <= 1; dx += 2)
                    for (int dz = -1; dz <= 1; dz += 2)
                        p.set(cx + dx, 9, cz + dz, c.wall);
            }
        fill(p, -1, 0, 0, 1, 2, 0, AIR);
        fill(p, -2, 0, 0, -2, 3, 0, c.frame);
        fill(p, 2, 0, 0, 2, 3, 0, c.frame);
        shell(p, -3, -1, 6, 3, 6, 12, c.wall);
        fill(p, -3, -1, 6, 3, -1, 12, c.floor);
        for (int x : new int[]{-3, 3})
            for (int z : new int[]{6, 12})
                fill(p, x, 0, z, x, 5, z, c.frame);
        fill(p, 0, 0, 6, 0, 1, 6, AIR);
        fill(p, -3, 1, 9, -3, 2, 9, c.glass);
        fill(p, 3, 1, 9, 3, 2, 9, c.glass);
        fill(p, -1, 1, 12, 1, 2, 12, c.glass);
        p.set(0, 5, 9, c.light);
        p.chest(0, 0, 11, 2);
        for (int x = -3; x <= 3; x++)
            for (int z = 6; z <= 12; z++)
                if ((x == -3 || x == 3 || z == 6 || z == 12) && ((x + z) & 1) == 0)
                    p.set(x, 7, z, c.wall);
        // 中庭の飾り
        p.set(-5, 0, 3, Blocks.POTTED_POPPY);
        p.set(5, 0, 3, Blocks.POTTED_CORNFLOWER);
    }

    // ================= 建物 11〜18 =================

    private static void cottage(Palette c, Placer p) {
        shell(p, -2, -1, 0, 2, 3, 4, c.wall);
        fill(p, -2, -1, 0, 2, -1, 4, c.floor);
        fill(p, -2, 3, 0, 2, 3, 4, c.floor);
        for (int x : new int[]{-2, 2})
            for (int z : new int[]{0, 4})
                fill(p, x, 0, z, x, 2, z, c.frame);
        gable(p, c, -3, 3, -1, 5, 4, 0, 4);
        fill(p, 0, 0, 0, 0, 1, 0, AIR);
        fill(p, -2, 1, 2, -2, 1, 2, c.glass);
        fill(p, 2, 1, 2, 2, 1, 2, c.glass);
        fill(p, 0, 1, 4, 0, 1, 4, c.glass);
        p.set(0, 2, 2, c.light);
        p.set(0, 0, 3, Blocks.CAKE);
        p.chest(-1, 0, 3, 1);
        p.set(-3, 0, 2, Blocks.POTTED_POPPY);
        p.set(3, 0, 2, Blocks.POTTED_BLUE_ORCHID);
        p.set(-1, 0, -1, Blocks.POTTED_DANDELION);
        p.set(1, 0, -1, Blocks.POTTED_CORNFLOWER);
    }

    private static void mushroom(Palette c, Placer p) {
        shell(p, -2, -1, 0, 2, 5, 4, c.frame);
        fill(p, -2, -1, 0, 2, -1, 4, c.floor);
        fill(p, 0, 0, 0, 0, 1, 0, AIR);
        fill(p, -2, 1, 2, -2, 2, 2, c.glass);
        fill(p, 2, 1, 2, 2, 2, 2, c.glass);
        fill(p, 0, 1, 4, 0, 2, 4, c.glass);
        p.set(0, 4, 2, c.light);
        p.chest(1, 0, 3, 2);
        // かさ
        cyl(p, 0, 2, -1, 5.5, 6, 6, c.wall);
        cyl(p, 0, 2, -1, 5.2, 7, 7, c.wall);
        cyl(p, 0, 2, -1, 4.5, 8, 8, c.wall);
        cyl(p, 0, 2, -1, 3.5, 9, 9, c.wall);
        cyl(p, 0, 2, -1, 2.2, 10, 10, c.wall);
        cyl(p, 0, 2, -1, 1.0, 11, 11, c.wall);
        // 光る水玉
        for (int[] s : new int[][]{{3, 2}, {-3, 2}, {0, 5}, {0, -1}})
            p.set(s[0], 8, s[1], c.light);
        for (int sx : new int[]{-2, 2})
            for (int sz : new int[]{0, 4})
                p.set(sx, 9, sz, c.light);
    }

    private static void pagoda(Palette c, Placer p) {
        for (int t = 0; t < 3; t++) {
            int w = 4 - t;
            int z1 = t, z2 = 8 - t;
            int yb = (t == 0) ? -1 : 5 + (t - 1) * 6;
            int top = yb + 4;
            shell(p, -w, yb, z1, w, top, z2, c.wall);
            fill(p, -w, yb, z1, w, yb, z2, c.floor);
            for (int x : new int[]{-w, w})
                for (int z : new int[]{z1, z2})
                    fill(p, x, yb + 1, z, x, top - 1, z, c.frame);
            int wy = yb + 2;
            fill(p, -w, wy, 4, -w, wy, 4, c.glass);
            fill(p, w, wy, 4, w, wy, 4, c.glass);
            fill(p, 0, wy, z2, 0, wy, z2, c.glass);
            if (t == 0) {
                fill(p, 0, 0, 0, 0, 1, 0, AIR);
                fill(p, -1, 0, 4, 1, 0, 4, AIR);
            } else {
                fill(p, 0, yb + 1, z1, 0, yb + 2, z1, c.glass);
            }
            eave(p, c, -w - 1, w + 1, z1 - 1, z2 + 1, top + 1);
        }
        p.set(0, 1, 4, c.light);
        p.chest(2, 0, 6, 2);
        // 屋根の飾り
        fill(p, 0, 17, 4, 0, 20, 4, c.frame);
        p.set(0, 21, 4, c.light);
    }

    private static void torii(Palette c, Placer p) {
        fill(p, -1, -1, 0, 1, -1, 12, c.floor);
        for (int z : new int[]{0, 5, 10}) {
            for (int x : new int[]{-3, 3})
                fill(p, x, -1, z, x, 5, z, c.frame);
            fill(p, -4, 4, z, 4, 4, z, c.frame);
            fill(p, -5, 6, z, 5, 6, z, c.wall);
            for (int x = -6; x <= 6; x++)
                p.slab(x, 7, z, c.slab, false);
            p.set(-2, 3, z, c.light);
            p.set(2, 3, z, c.light);
        }
        for (int z : new int[]{3, 8}) {
            for (int x : new int[]{-2, 2}) {
                fill(p, x, 0, z, x, 1, z, c.frame);
                p.set(x, 2, z, Blocks.LANTERN);
            }
        }
        p.chest(0, 0, 12, 2);
    }

    private static void dome(Palette c, Placer p) {
        // 中心 (0, 0, 6) 半径5の半球
        for (int x = -6; x <= 6; x++)
            for (int z = 0; z <= 12; z++)
                for (int y = 0; y <= 6; y++) {
                    double d = Math.sqrt(x * x + y * y + (z - 6) * (z - 6));
                    if (d <= 4.5) {
                        p.set(x, y, z, AIR);
                    } else if (d <= 5.5) {
                        Block b = c.wall;
                        if (y >= 3 && ((x + z + y) & 1) == 0) b = c.glass;
                        else if (y % 2 == 1) b = c.frame;
                        p.set(x, y, z, b);
                    }
                }
        cyl(p, 0, 6, -1, 5.5, -1, -1, c.floor);
        fill(p, 0, 0, 1, 0, 1, 1, AIR);
        p.set(0, 4, 6, c.light);
        p.chest(0, 0, 8, 2);
        p.set(-2, 0, 5, Blocks.POTTED_POPPY);
        p.set(2, 0, 5, Blocks.POTTED_DANDELION);
    }

    private static void lighthouse(Palette c, Placer p) {
        // 中心 (0, 4)
        cyl(p, 0, 4, -1, 3.5, -1, -1, c.floor);
        for (int y = 0; y <= 15; y++)
            cyl(p, 0, 4, 2.5, 3.5, y, y, ((y / 4) % 2 == 0) ? c.wall : c.frame);
        cyl(p, 0, 4, -1, 2.5, 0, 16, AIR);
        fill(p, 1, 0, 1, 1, 1, 1, AIR); // 入口は脇に（正面の壁ははしごの支え）
        for (int y = 5; y <= 11; y += 6) {
            p.set(3, y, 4, c.glass);
            p.set(-3, y, 4, c.glass);
        }
        // 展望台
        cyl(p, 0, 4, -1, 4.5, 16, 16, c.floor);
        p.set(0, 16, 2, AIR);
        for (int y = 0; y <= 16; y++)
            p.ladder(0, y, 2, 0);
        cyl(p, 0, 4, 4.0, 4.5, 17, 17, c.wall);
        shell(p, -1, 17, 3, 1, 19, 5, c.glass);
        p.set(0, 18, 4, c.light);
        cyl(p, 0, 4, -1, 3.0, 20, 20, c.floor);
        cyl(p, 0, 4, -1, 1.5, 21, 21, c.frame);
        p.set(0, 22, 4, c.light);
        p.chest(1, 0, 3, 2);
    }

    // ================= 建物 19〜25 =================

    private static void treehouse(Palette c, Placer p) {
        // 中心 (0, 3)。葉っぱ -> デッキ -> 幹の順で置く（幹が最後に上書き）
        for (int x = -5; x <= 5; x++)
            for (int y = 8; y <= 18; y++)
                for (int z = -2; z <= 8; z++) {
                    double d = Math.sqrt(x * x + (y - 13) * (y - 13) + (z - 3) * (z - 3));
                    if (d <= 5.0) p.set(x, y, z, c.leaf());
                }
        cyl(p, 0, 3, -1, 5.0, 6, 6, c.floor);
        cyl(p, 0, 3, 4.0, 5.0, 7, 7, c.wall);
        fill(p, -1, -1, 2, 1, 14, 4, c.frame);
        p.set(0, 6, 1, AIR);
        for (int y = 0; y <= 7; y++)
            p.ladder(0, y, 1, 2);
        p.set(4, 8, 3, Blocks.LANTERN);
        p.set(-4, 8, 3, Blocks.LANTERN);
        p.set(0, 8, 7, Blocks.LANTERN);
        p.chest(3, 7, 3, 3);
        p.set(-3, 7, 3, Blocks.CAKE);
        p.set(0, 7, 6, Blocks.POTTED_POPPY);
    }

    private static void tent(Placer p, Palette c, int cx, Block carpet) {
        for (int z = 0; z <= 4; z++) {
            p.set(cx - 2, 0, z, c.frame);
            p.set(cx + 2, 0, z, c.frame);
        }
        for (int i = 0; i <= 2; i++) {
            int a = cx - 2 + i, b = cx + 2 - i, y = 1 + i;
            if (a == b) {
                for (int z = 0; z <= 4; z++) p.slab(a, y, z, c.slab, false);
            } else {
                for (int z = 0; z <= 4; z++) {
                    p.stair(a, y, z, c.stair, 1, false);
                    p.stair(b, y, z, c.stair, 3, false);
                }
                for (int x = a + 1; x <= b - 1; x++) p.set(x, y, 4, c.wall);
            }
        }
        fill(p, cx - 1, 0, 4, cx + 1, 0, 4, c.wall);
        for (int x = cx - 1; x <= cx + 1; x++)
            for (int z = 0; z <= 3; z++)
                p.set(x, 0, z, carpet);
    }

    private static void camp(Palette c, Placer p) {
        tent(p, c, -5, Blocks.RED_CARPET);
        tent(p, c, 5, Blocks.LIGHT_BLUE_CARPET);
        p.set(0, 0, 2, Blocks.CAMPFIRE);
        p.stair(-2, 0, 2, c.stair, 3, false);
        p.stair(2, 0, 2, c.stair, 1, false);
        p.stair(0, 0, 4, c.stair, 0, false);
        p.chest(0, 0, 0, 2);
        for (int x : new int[]{-2, 2}) {
            fill(p, x, 0, -1, x, 1, -1, c.frame);
            p.set(x, 2, -1, Blocks.LANTERN);
        }
        p.set(-5, 0, 5, Blocks.POTTED_POPPY);
        p.set(5, 0, 5, Blocks.POTTED_DANDELION);
    }

    private static void rocket(Palette c, Placer p) {
        // 中心 (0, 3)
        cyl(p, 0, 3, -1, 2.5, -1, -1, c.floor);
        cyl(p, 0, 3, 1.5, 2.5, 0, 13, c.wall);
        for (int y : new int[]{3, 7, 11})
            cyl(p, 0, 3, 1.5, 2.5, y, y, c.frame);
        cyl(p, 0, 3, -1, 1.5, 0, 12, AIR);
        cyl(p, 0, 3, -1, 2.5, 13, 13, c.floor);
        fill(p, 0, 0, 1, 0, 1, 1, AIR);
        // 窓
        fill(p, 2, 9, 3, 2, 10, 3, c.glass);
        fill(p, -2, 9, 3, -2, 10, 3, c.glass);
        fill(p, 0, 9, 1, 0, 10, 1, c.glass);
        fill(p, 0, 9, 5, 0, 10, 5, c.glass);
        // 先端
        cyl(p, 0, 3, -1, 1.5, 14, 14, c.wall);
        cyl(p, 0, 3, -1, 0.5, 15, 15, c.frame);
        p.set(0, 16, 3, c.light);
        // 羽
        for (int y = 0; y <= 2; y++) {
            p.set(3, y, 3, c.frame);
            p.set(-3, y, 3, c.frame);
            p.set(0, y, 6, c.frame);
        }
        for (int y = 0; y <= 1; y++) {
            p.set(4, y, 3, c.frame);
            p.set(-4, y, 3, c.frame);
            p.set(0, y, 7, c.frame);
        }
        p.set(5, 0, 3, c.frame);
        p.set(-5, 0, 3, c.frame);
        p.set(0, 0, 8, c.frame);
        // 中
        for (int y = 0; y <= 12; y++)
            p.ladder(0, y, 4, 2);
        p.set(0, 12, 3, c.light);
        p.chest(0, 0, 3, 2);
    }

    private static void ufo(Palette c, Placer p) {
        // 中心 (0, 7)
        for (int sx : new int[]{-4, 4})
            for (int sz : new int[]{-4, 4})
                fill(p, sx, -1, 7 + sz, sx, 3, 7 + sz, c.frame);
        for (int[] s : new int[][]{{5, 0}, {-5, 0}, {0, 5}, {0, -5}})
            p.set(s[0], 3, 7 + s[1], c.light);
        cyl(p, 0, 7, -1, 6.5, 4, 4, c.floor);
        cyl(p, 0, 7, -1, 4.5, 5, 9, AIR);
        cyl(p, 0, 7, 4.5, 7.0, 5, 5, c.wall);
        cyl(p, 0, 7, 4.5, 6.0, 6, 6, c.wall);
        // ガラスドーム
        for (int x = -5; x <= 5; x++)
            for (int y = 5; y <= 10; y++)
                for (int z = 2; z <= 12; z++) {
                    double d = Math.sqrt(x * x + (y - 5) * (y - 5) + (z - 7) * (z - 7));
                    if (d > 4.0 && d <= 5.0 && y >= 6) p.set(x, y, z, c.glass);
                }
        // はしご（脚の内側）
        p.set(3, 4, 11, AIR);
        for (int y = 0; y <= 4; y++)
            p.ladder(3, y, 11, 3);
        p.chest(0, 5, 7, 2);
        p.set(0, 8, 7, c.light);
    }

    private static void slide(Palette c, Placer p) {
        shell(p, -2, -1, 0, 2, 11, 4, c.wall);
        fill(p, -2, -1, 0, 2, -1, 4, c.floor);
        for (int x : new int[]{-2, 2})
            for (int z : new int[]{0, 4})
                fill(p, x, 0, z, x, 10, z, c.frame);
        fill(p, -2, 11, 0, 2, 11, 4, c.floor);
        for (int x = -2; x <= 2; x++)
            for (int z = 0; z <= 4; z++) {
                boolean edge = (x == -2 || x == 2 || z == 0 || z == 4);
                boolean gapFront = (z == 4 && x >= -1 && x <= 1);
                boolean gapBack = (z == 0 && x == 0);
                if (edge && !gapFront && !gapBack) p.set(x, 12, z, c.wall);
            }
        p.set(-2, 13, 0, c.light);
        p.set(2, 13, 0, c.light);
        for (int y = 0; y <= 11; y++)
            p.ladder(0, y, -1, 2);
        // 氷の滑り台
        for (int s = 0; s <= 11; s++) {
            int y = 11 - s;
            int z1 = 5 + 3 * s, z2 = 7 + 3 * s;
            fill(p, -1, y, z1, 1, y, z2, Blocks.BLUE_ICE);
            fill(p, -2, y, z1, -2, y + 1, z2, c.wall);
            fill(p, 2, y, z1, 2, y + 1, z2, c.wall);
            fill(p, 0, -1, z1 + 1, 0, y - 1, z1 + 1, c.frame);
            if (s % 3 == 0) {
                p.set(-2, y + 2, z1 + 1, c.light);
                p.set(2, y + 2, z1 + 1, c.light);
            }
        }
    }

    private static void jumppark(Palette c, Placer p) {
        fill(p, -9, -1, -1, 9, -1, 9, c.floor);
        fill(p, -4, -1, 0, 4, -1, 8, Blocks.SLIME_BLOCK);
        for (int s : new int[]{-1, 1}) {
            int xo = 8 * s, xi = 6 * s;
            int x1 = Math.min(xo, xi), x2 = Math.max(xo, xi);
            fill(p, x1, -1, 3, x2, 8, 5, c.frame);
            fill(p, x1, 9, 3, x2, 9, 5, c.floor);
            for (int x = x1; x <= x2; x++)
                for (int z = 3; z <= 5; z++) {
                    boolean edge = (x == x1 || x == x2 || z == 3 || z == 5);
                    boolean open = (z == 4 && (x == xo || x == xi));
                    if (edge && !open) p.set(x, 10, z, c.wall);
                }
            for (int y = 0; y <= 9; y++)
                p.ladder(xo + s, y, 4, s > 0 ? 1 : 3);
            p.chest(7 * s, 10, 4, s > 0 ? 3 : 1);
            p.set(x1, 11, 3, c.light);
            p.set(x1, 11, 5, c.light);
            p.set(x2, 11, 3, c.light);
            p.set(x2, 11, 5, c.light);
            // 広場の街灯
            for (int z : new int[]{-1, 9}) {
                fill(p, 9 * s, 0, z, 9 * s, 2, z, c.frame);
                p.set(9 * s, 3, z, Blocks.LANTERN);
            }
        }
    }

    private static final String[] MAZE = {
            "#######.#######",
            "#.....#.......#",
            "#.#.#########.#",
            "#.#...........#",
            "#.#############",
            "#.......#.....#",
            "#######.#.###.#",
            "#.#...#...#...#",
            "#.#.#.#####.###",
            "#...#...#...#.#",
            "#.###.#.#.###.#",
            "#...#.#.#.#...#",
            "###.#.###.###.#",
            "#...#.........#",
            "###############"
    };

    private static void maze(Palette c, Placer p) {
        fill(p, -7, -1, 0, 7, -1, 14, c.floor);
        for (int j = 0; j < 15; j++)
            for (int i = 0; i < 15; i++) {
                int x = i - 7;
                if (MAZE[j].charAt(i) == '#') {
                    fill(p, x, 0, j, x, 2, j, c.wall);
                    if (i % 2 == 0 && j % 2 == 0 && ((i + j) % 4 == 0)) p.set(x, 3, j, c.light);
                } else {
                    fill(p, x, 0, j, x, 3, j, AIR);
                }
            }
        p.chest(0, 0, 7, 2);
        p.set(-1, 0, -1, Blocks.POTTED_POPPY);
        p.set(1, 0, -1, Blocks.POTTED_DANDELION);
    }

    private static void traproom(Palette c, Placer p) {
        shell(p, -4, -1, 0, 4, 3, 10, c.wall);
        fill(p, -4, -1, 0, 4, -1, 10, c.floor);
        fill(p, -4, 4, 0, 4, 4, 10, c.floor);
        for (int x : new int[]{-4, 4})
            for (int z : new int[]{0, 10})
                fill(p, x, 0, z, x, 3, z, c.frame);
        fill(p, 0, 0, 0, 0, 1, 0, AIR);
        for (int z : new int[]{3, 7}) {
            p.set(-4, 2, z, c.glass);
            p.set(4, 2, z, c.glass);
        }
        // 地雷：感圧板の下の下にTNT
        int[][] mines = {{0, 2}, {-1, 4}, {1, 4}, {0, 6}, {-2, 7}, {2, 7}, {0, 8}};
        for (int[] m : mines) {
            p.set(m[0], -2, m[1], Blocks.TNT);
            p.set(m[0], 0, m[1], Blocks.STONE_PRESSURE_PLATE);
        }
        // クモの巣
        for (int[] w : new int[][]{{-3, 0, 3}, {3, 0, 3}, {-3, 1, 5}, {3, 1, 5}, {-2, 2, 9}, {2, 2, 9}, {0, 2, 4}})
            p.set(w[0], w[1], w[2], Blocks.COBWEB);
        p.set(0, 2, 2, c.light);
        p.set(0, 2, 8, c.light);
        p.chest(0, 0, 9, 2);
    }

    private static void fountain(Palette c, Placer p) {
        // 中心 (0, 7)
        cyl(p, 0, 7, -1, 8.0, -1, -1, c.floor);
        cyl(p, 0, 7, 5.5, 6.5, -1, -1, c.frame);
        cyl(p, 0, 7, -1, 3.0, -2, -2, c.floor);
        cyl(p, 0, 7, -1, 3.0, -1, -1, Blocks.WATER);
        cyl(p, 0, 7, 3.0, 4.0, -1, 0, c.wall);
        fill(p, 0, 0, 7, 0, 3, 7, c.frame);
        p.set(0, 4, 7, Blocks.WATER);
        for (int sx : new int[]{-1, 1})
            for (int sz : new int[]{-1, 1}) {
                int x = 5 * sx, z = 7 + 5 * sz;
                fill(p, x, 0, z, x, 2, z, c.frame);
                p.set(x, 3, z, Blocks.LANTERN);
            }
        p.stair(6, 0, 7, c.stair, 1, false);
        p.stair(-6, 0, 7, c.stair, 3, false);
        p.stair(0, 0, 1, c.stair, 2, false);
        p.stair(0, 0, 13, c.stair, 0, false);
        p.set(5, 0, 3, Blocks.POTTED_POPPY);
        p.set(-5, 0, 3, Blocks.POTTED_DANDELION);
        p.set(5, 0, 11, Blocks.POTTED_CORNFLOWER);
        p.set(-5, 0, 11, Blocks.POTTED_BLUE_ORCHID);
    }

    // ================= 建物 26〜30 =================

    private static void church(Palette c, Placer p) {
        shell(p, -3, -1, 2, 3, 5, 12, c.wall);
        fill(p, -3, -1, 2, 3, -1, 12, c.floor);
        fill(p, -3, 5, 2, 3, 5, 12, c.floor);
        for (int x : new int[]{-3, 3})
            for (int z : new int[]{2, 7, 12})
                fill(p, x, 0, z, x, 4, z, c.frame);
        for (int x : new int[]{-3, 3}) {
            fill(p, x, 1, 4, x, 3, 5, c.glass);
            fill(p, x, 1, 9, x, 3, 10, c.glass);
        }
        fill(p, -1, 1, 12, 1, 3, 12, c.glass);
        gable(p, c, -4, 4, 1, 13, 6, 2, 12);
        // 鐘楼
        shell(p, -2, -1, 0, 2, 9, 4, c.wall);
        fill(p, -2, -1, 0, 2, -1, 4, c.floor);
        for (int x : new int[]{-2, 2})
            for (int z : new int[]{0, 4})
                fill(p, x, 0, z, x, 8, z, c.frame);
        fill(p, 0, 0, 0, 0, 2, 0, AIR);
        fill(p, -1, 5, 0, 1, 7, 0, c.glass);
        gable(p, c, -3, 3, -1, 5, 10, 0, 4);
        p.set(0, 8, 2, c.light);
        // 中
        for (int z : new int[]{5, 7, 9})
            for (int x : new int[]{-2, -1, 1, 2})
                p.stair(x, 0, z, c.stair, 2, false);
        p.set(0, 4, 7, c.light);
        p.set(0, 4, 10, c.light);
        p.chest(0, 0, 11, 2);
        p.set(-2, 0, 11, Blocks.POTTED_POPPY);
        p.set(2, 0, 11, Blocks.POTTED_CORNFLOWER);
    }

    private static void windmill(Palette c, Placer p) {
        cyl(p, 0, 4, -1, 3.5, -1, -1, c.floor);
        for (int y = 0; y <= 14; y++)
            cyl(p, 0, 4, 2.5, 3.5, y, y, ((y / 3) % 2 == 0) ? c.wall : c.frame);
        cyl(p, 0, 4, -1, 2.5, 0, 14, AIR);
        fill(p, 1, 0, 1, 1, 1, 1, AIR);
        cyl(p, 0, 4, -1, 4.0, 15, 15, c.floor);
        p.set(0, 15, 2, AIR);
        for (int y = 0; y <= 15; y++)
            p.ladder(0, y, 2, 0);
        cyl(p, 0, 4, -1, 3.0, 16, 16, c.floor);
        cyl(p, 0, 4, -1, 2.0, 17, 17, c.floor);
        cyl(p, 0, 4, -1, 1.0, 18, 18, c.frame);
        p.set(0, 14, 5, c.light);
        p.chest(1, 0, 3, 2);
        // 羽根
        p.set(0, 12, 0, c.frame);
        fill(p, -7, 12, -1, 7, 12, -1, c.frame);
        fill(p, 0, 5, -1, 0, 19, -1, c.frame);
        for (int k = 2; k <= 7; k++) {
            p.set(k, 13, -1, Blocks.WHITE_WOOL);
            p.set(-k, 11, -1, Blocks.WHITE_WOOL);
        }
        for (int k = 1; k <= 7; k++) {
            p.set(1, 12 + k, -1, Blocks.WHITE_WOOL);
            p.set(-1, 12 - k, -1, Blocks.WHITE_WOOL);
        }
    }

    private static void marketRow(Palette c, Placer p, boolean withChest) {
        int[] xs = {-6, -2, 2, 6};
        Block[] wool = {Blocks.RED_WOOL, Blocks.BLUE_WOOL, Blocks.YELLOW_WOOL, Blocks.GREEN_WOOL};
        for (int i = 0; i < 4; i++) {
            int cx = xs[i];
            for (int x = cx - 1; x <= cx + 1; x += 2)
                for (int z : new int[]{2, 4})
                    fill(p, x, 0, z, x, 2, z, c.frame);
            fill(p, cx - 1, 0, 4, cx + 1, 2, 4, c.wall);
            fill(p, cx - 1, 0, 2, cx + 1, 0, 2, c.wall);
            for (int x = cx - 2; x <= cx + 2; x++)
                for (int z = 1; z <= 5; z++)
                    p.set(x, 3, z, (((x + z) & 1) == 0) ? wool[i] : Blocks.WHITE_WOOL);
            for (int x = cx - 2; x <= cx + 2; x++)
                p.set(x, 2, 1, (x & 1) == 0 ? wool[i] : Blocks.WHITE_WOOL);
            p.set(cx, 1, 2, (i % 2 == 0) ? Blocks.CAKE : Blocks.POTTED_POPPY);
            p.set(cx, 2, 3, c.light);
            if (withChest && i == 1) p.chest(cx, 0, 3, 2);
        }
    }

    private static void market(Palette c, Placer p) {
        fill(p, -9, -1, -1, 9, -1, 14, c.floor);
        marketRow(c, p, true);
        marketRow(c, at(p, 0, 13, 2), false);
        fill(p, 0, 0, 6, 0, 2, 6, c.frame);
        p.set(0, 3, 6, Blocks.LANTERN);
        fill(p, 0, 0, 7, 0, 2, 7, c.frame);
        p.set(0, 3, 7, Blocks.LANTERN);
    }

    private static void garden(Palette c, Placer p) {
        Block[] fl = {Blocks.POPPY, Blocks.DANDELION, Blocks.AZURE_BLUET, Blocks.CORNFLOWER, Blocks.PINK_TULIP,
                Blocks.ALLIUM, Blocks.OXEYE_DAISY, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.BLUE_ORCHID};
        fill(p, -7, -1, 0, 7, -1, 14, Blocks.GRASS_BLOCK);
        fill(p, -1, -1, 0, 1, -1, 14, c.floor);
        fill(p, -7, -1, 6, 7, -1, 8, c.floor);
        for (int x = -7; x <= 7; x++)
            for (int z = 0; z <= 14; z++) {
                boolean path = Math.abs(x) <= 1 || (z >= 6 && z <= 8);
                if (!path && ((x + z) & 1) == 0)
                    p.set(x, 0, z, fl[Math.floorMod(x * 7 + z * 3, fl.length)]);
            }
        // 池
        fill(p, -1, -2, 6, 1, -2, 8, c.floor);
        fill(p, -1, -1, 6, 1, -1, 8, Blocks.WATER);
        for (int x = -2; x <= 2; x++)
            for (int z = 5; z <= 9; z++)
                if (x == -2 || x == 2 || z == 5 || z == 9)
                    p.set(x, -1, z, c.wall);
        // 外のさく
        for (int x = -7; x <= 7; x++)
            for (int z = 0; z <= 14; z++)
                if ((x == -7 || x == 7 || z == 0 || z == 14) && !(Math.abs(x) <= 1 && z == 0))
                    p.set(x, 0, z, Blocks.OAK_FENCE);
        for (int sx : new int[]{-1, 1})
            for (int sz : new int[]{3, 11}) {
                fill(p, 5 * sx, 0, sz, 5 * sx, 1, sz, c.frame);
                p.set(5 * sx, 2, sz, Blocks.LANTERN);
            }
        p.stair(-3, 0, 7, c.stair, 3, false);
        p.stair(3, 0, 7, c.stair, 1, false);
        p.chest(6, 0, 2, 3);
    }

    private static void toriiGate(Palette c, Placer p, int z) {
        for (int x : new int[]{-3, 3})
            fill(p, x, -1, z, x, 5, z, c.frame);
        fill(p, -4, 4, z, 4, 4, z, c.frame);
        fill(p, -5, 6, z, 5, 6, z, c.wall);
        for (int x = -6; x <= 6; x++)
            p.slab(x, 7, z, c.slab, false);
        p.set(-2, 3, z, c.light);
        p.set(2, 3, z, c.light);
    }

    private static void shrine(Palette c, Placer p) {
        fill(p, -1, -1, -14, 1, -1, 0, c.floor);
        toriiGate(c, p, -12);
        toriiGate(c, p, -7);
        for (int z : new int[]{-10, -4})
            for (int x : new int[]{-2, 2}) {
                fill(p, x, 0, z, x, 1, z, c.frame);
                p.set(x, 2, z, Blocks.LANTERN);
            }
        fill(p, -5, 0, 0, 5, 0, 10, c.floor);
        for (int x = -1; x <= 1; x++) p.stair(x, 0, -1, c.stair, 0, false);
        shell(p, -3, 0, 2, 3, 5, 9, c.wall);
        for (int x : new int[]{-3, 3})
            for (int z : new int[]{2, 9})
                fill(p, x, 1, z, x, 4, z, c.frame);
        fill(p, 0, 1, 2, 0, 2, 2, AIR);
        for (int x : new int[]{-3, 3}) fill(p, x, 2, 5, x, 3, 5, c.glass);
        gable(p, c, -5, 5, 1, 10, 6, 2, 9);
        p.set(0, 4, 5, c.light);
        p.chest(0, 1, 8, 2);
        p.chest(-2, 1, 1, 2);
        p.set(2, 1, 1, Blocks.POTTED_CORNFLOWER);
    }

    // ================= アスレ 31〜36 =================

    /** ゴール台（5x5）とゴールブロック */
    private static void goalPad(Palette c, Placer p, int cx, int y, int z) {
        fill(p, cx - 2, y, z, cx + 2, y, z + 4, c.floor);
        p.gimmick(cx, y, z + 2, GOAL);
        for (int sx : new int[]{-2, 2}) {
            fill(p, cx + sx, y + 1, z, cx + sx, y + 2, z, c.frame);
            p.set(cx + sx, y + 3, z, c.light);
            fill(p, cx + sx, y + 1, z + 4, cx + sx, y + 2, z + 4, c.frame);
            p.set(cx + sx, y + 3, z + 4, c.light);
        }
    }

    private static void pkSteps(Palette c, Placer p) {
        fill(p, -2, -1, -2, 2, -1, 2, c.floor);
        p.gimmick(0, -1, 0, CHECK);
        int n = 24;
        for (int i = 0; i < n; i++) {
            int m = i % 4;
            int x = (m == 1) ? 2 : (m == 3 ? -2 : 0);
            int z = 4 + 3 * i;
            int y = -1 + (i + 1) / 2;
            if (i % 5 == 4) {
                p.set(x, y, z, c.frame);
            } else {
                fill(p, x - 1, y, z, x, y, z + 1, c.wall);
                p.set(x, y, z, c.frame);
            }
            p.set(x, y - 1, z, c.light);
            if (i % 6 == 5) p.gimmick(x, y, z, CHECK);
        }
        goalPad(c, p, 0, -1 + n / 2, 4 + 3 * n);
    }

    private static void pkJump(Palette c, Placer p) {
        fill(p, -3, -1, -3, 3, -1, 3, c.floor);
        p.gimmick(0, -1, 0, CHECK);
        int n = 8;
        for (int i = 0; i < n; i++) {
            int ox = (i % 2 == 0) ? 0 : (i % 4 == 1 ? 3 : -3);
            int z = 9 + 10 * i;
            int y = -1 + 2 * (i + 1);
            fill(p, ox - 2, y, z, ox + 2, y, z + 4, c.floor);
            for (int x = ox - 2; x <= ox + 2; x++) {
                p.set(x, y, z, c.frame);
                p.set(x, y, z + 4, c.frame);
            }
            p.set(ox, y - 1, z + 2, c.light);
            p.gimmick(ox, y, z, CHECK);
            p.gimmick(ox, y, z + 4, JUMP);
        }
        // 最初の島へ行くジャンプ台
        p.gimmick(0, -1, 3, JUMP);
        // ゴール
        int zg = 9 + 10 * n;
        goalPad(c, p, 0, -1 + 2 * (n + 1), zg);
    }

    private static void pkDash(Palette c, Placer p) {
        // 先に穴を掘る（落ちたらスライムで跳ね返る）
        fill(p, -4, -5, 4, 4, -1, 9 + 9 * 8 + 5, AIR);
        fill(p, -4, -6, 4, 4, -6, 9 + 9 * 8 + 5, Blocks.SLIME_BLOCK);
        fill(p, -3, -1, -3, 3, -1, 3, c.floor);
        p.gimmick(0, -1, 0, CHECK);
        p.gimmick(0, -1, 3, SPEED);
        int n = 8;
        for (int i = 0; i < n; i++) {
            int z = 9 + 9 * i;
            fill(p, -1, -1, z, 1, -1, z + 2, c.wall);
            for (int x : new int[]{-1, 1}) p.set(x, -1, z + 1, c.frame);
            p.set(0, -2, z + 1, c.light);
            if (i % 3 == 2) p.gimmick(0, -1, z, CHECK);
            p.gimmick(0, -1, z + 2, SPEED);
        }
        goalPad(c, p, 0, -1, 9 + 9 * n);
    }

    private static void startTower(Palette c, Placer p, int y) {
        fill(p, 0, -1, -3, 0, y - 1, -3, c.frame);
        fill(p, -2, y, -3, 2, y, 1, c.floor);
        for (int k = 0; k <= y; k++) p.ladder(0, k, -4, 2);
        p.gimmick(0, y, 0, CHECK);
        p.set(-2, y + 1, -3, c.light);
        p.set(2, y + 1, -3, c.light);
    }

    private static void pkCrumble(Palette c, Placer p) {
        int y = 8;
        startTower(c, p, y);
        int len = 32;
        for (int z = 2; z <= len; z++)
            for (int x = -1; x <= 1; x++) {
                boolean hole = (z % 4 == 2) && (x == ((z / 4) % 3) - 1) && z % 10 != 0;
                if (z % 10 == 0) {
                    p.set(x, y, z, c.floor);
                    if (x == 0) p.gimmick(0, y, z, CHECK);
                } else if (!hole) {
                    p.gimmick(x, y, z, CRUMBLE);
                }
            }
        for (int z : new int[]{10, 20, 30}) {
            fill(p, 0, -1, z, 0, y - 1, z, c.frame);
            p.set(-1, y + 1, z, Blocks.LANTERN);
            p.set(1, y + 1, z, Blocks.LANTERN);
        }
        goalPad(c, p, 0, y, len + 1);
        fill(p, -3, -1, -4, 3, -1, len + 7, Blocks.SLIME_BLOCK);
        fill(p, 0, -1, -3, 0, -1, -3, c.frame);
    }

    private static void pkSpiral(Palette c, Placer p) {
        // 中心 (0, 6)
        cyl(p, 0, 6, -1, 8.0, -1, -1, Blocks.SLIME_BLOCK);
        cyl(p, 0, 6, -1, 1.2, -1, 22, c.frame);
        for (int i = 0; i < 24; i++) {
            double a = i * 0.7;
            int x = (int) Math.round(4.2 * Math.cos(a));
            int z = 6 + (int) Math.round(4.2 * Math.sin(a));
            int y = i - 1;
            fill(p, x, y, z, x + 1, y, z + 1, c.wall);
            p.set(x, y, z, c.frame);
            p.set(x, y - 1, z, c.light);
            if (i % 8 == 7) p.gimmick(x, y, z, CHECK);
        }
        cyl(p, 0, 6, -1, 3.0, 23, 23, c.floor);
        p.gimmick(0, 23, 9, GOAL);
        p.set(0, 24, 6, c.light);
    }

    private static void pkIce(Palette c, Placer p) {
        int y = 6;
        startTower(c, p, y);
        int n = 10;
        for (int i = 0; i < n; i++) {
            int ox = (i % 2 == 0) ? -2 : 2;
            int z = 5 + 5 * i;
            fill(p, ox - 1, y, z, ox + 1, y, z + 2, Blocks.BLUE_ICE);
            p.set(ox, y - 1, z + 1, c.light);
            if (i % 3 == 2) p.gimmick(ox, y, z + 1, CHECK);
        }
        goalPad(c, p, 0, y, 5 + 5 * n);
        fill(p, -6, -1, -4, 6, -1, 5 + 5 * n + 6, Blocks.SLIME_BLOCK);
        fill(p, 0, -1, -3, 0, -1, -3, c.frame);
    }

    // ================= 町サイズ 37〜39 =================

    private static void lampPost(Palette c, Placer p, int x, int z) {
        fill(p, x, 0, z, x, 2, z, c.frame);
        p.set(x, 3, z, Blocks.LANTERN);
    }

    private static void town(Palette c, Placer p) {
        // 道路
        fill(p, -2, -1, 0, 2, -1, 49, c.floor);
        fill(p, -24, -1, 22, 24, -1, 26, c.floor);
        // 入口の鳥居
        fill(p, -1, -1, -9, 1, -1, -1, c.floor);
        toriiGate(c, p, -4);
        toriiGate(c, p, -9);
        // 噴水広場
        fountain(c, at(p, 0, 17, 0));
        // 道ぞいの家 (左は rot=3、右は rot=1 で道のほうに玄関を向ける)
        for (int dz : new int[]{8, 17, 31, 40}) {
            house(c, at(p, -4, dz, 3));
            house(c, at(p, 4, dz, 1));
        }
        // 街灯
        for (int z : new int[]{4, 12, 20, 28, 36, 44}) {
            lampPost(c, p, -3, z);
            lampPost(c, p, 3, z);
        }
        // 左の区画
        mushroom(c, at(p, -17, 3, 0));
        pagoda(c, at(p, -17, 12, 0));
        lighthouse(c, at(p, -17, 29, 0));
        camp(c, at(p, -18, 40, 0));
        // 右の区画
        cottage(c, at(p, 17, 3, 0));
        dome(c, at(p, 17, 10, 0));
        treehouse(c, at(p, 17, 31, 0));
        bunker(c, at(p, 17, 40, 0));
        // 北の端に城
        castle(c, at(p, 0, 50, 0));
    }

    private static void village(Palette c, Placer p) {
        fill(p, -2, -1, 0, 2, -1, 44, c.floor);
        fill(p, -22, -1, 18, 22, -1, 22, c.floor);
        fountain(c, at(p, 0, 13, 0));
        for (int dz : new int[]{5, 12, 28, 36}) {
            cottage(c, at(p, -5, dz, 3));
            cottage(c, at(p, 5, dz, 1));
        }
        for (int z : new int[]{4, 10, 26, 32, 38}) {
            lampPost(c, p, -3, z);
            lampPost(c, p, 3, z);
        }
        mushroom(c, at(p, -17, 5, 0));
        garden(c, at(p, 18, 6, 0));
        windmill(c, at(p, -17, 29, 0));
        market(c, at(p, 17, 27, 0));
        camp(c, at(p, -17, 41, 0));
        church(c, at(p, 17, 46, 0));
        shrine(c, at(p, 0, 56, 0));
        torii(c, at(p, 0, -14, 0));
    }

    private static void fortress(Palette c, Placer p) {
        // 外壁 (x -20..20, z 0..40)
        fill(p, -20, -1, 0, 20, -1, 40, c.floor);
        fill(p, -20, 0, 0, 20, 5, 0, c.wall);
        fill(p, -20, 0, 40, 20, 5, 40, c.wall);
        fill(p, -20, 0, 0, -20, 5, 40, c.wall);
        fill(p, 20, 0, 0, 20, 5, 40, c.wall);
        for (int x = -20; x <= 20; x += 2) {
            p.set(x, 6, 0, c.wall);
            p.set(x, 6, 40, c.wall);
        }
        for (int z = 0; z <= 40; z += 2) {
            p.set(-20, 6, z, c.wall);
            p.set(20, 6, z, c.wall);
        }
        for (int x = -20; x <= 20; x += 8) {
            fill(p, x, 0, 0, x, 6, 0, c.frame);
            fill(p, x, 0, 40, x, 6, 40, c.frame);
        }
        // 四隅の塔
        tower(c, at(p, -20, -2, 0));
        tower(c, at(p, 20, -2, 0));
        tower(c, at(p, -20, 38, 0));
        tower(c, at(p, 20, 38, 0));
        // 正門
        fill(p, -2, 0, 0, 2, 4, 0, AIR);
        fill(p, -3, 0, 0, -3, 5, 0, c.frame);
        fill(p, 3, 0, 0, 3, 5, 0, c.frame);
        fill(p, -3, 5, 0, 3, 5, 0, c.frame);
        // 本丸
        castle(c, at(p, 0, 20, 0));
        // 庭の施設
        fill(p, -2, -1, 1, 2, -1, 19, c.floor);
        bunker(c, at(p, -14, 2, 0));
        bunker(c, at(p, 14, 2, 0));
        deck(c, at(p, -15, 19, 0));
        deck(c, at(p, 15, 19, 0));
        sniper(c, at(p, -16, 28, 0));
        sniper(c, at(p, 16, 28, 0));
        for (int z : new int[]{6, 12, 18}) {
            lampPost(c, p, -4, z);
            lampPost(c, p, 4, z);
        }
    }

    private Blueprints() {}
}
