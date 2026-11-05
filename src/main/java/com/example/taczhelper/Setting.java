package com.example.taczhelper;

public enum Setting {
    KIT("キット", 0, 1, 2),
    SPAWN_HEIGHT("スポーン高度(0=地上)", 150, 0, 100, 200, 250),
    SPREAD("スポーン地点の散らばり半径", 10, 0, 30, 60, 100),
    COUNTDOWN("開始カウントダウン(秒)", 5, 0, 10, 30),
    SLOW_FALL("低速落下(秒)", 30, 0, 10, 20, 60),
    LOOT_CHANCE("ルートブロックの出現率(%)", 100, 75, 50, 25),
    SCATTER_COUNT("ランダム散布チェスト数", 20, 0, 10, 40, 80),
    MAP_RADIUS("マップ半径", 100, 50, 200, 300),
    PROTECT("ブロック保護", 1, 0),
    BUS("バスで降下", 1, 0),
    BUS_HEIGHT("バスの高さ", 200, 150, 250, 120),
    BUS_SPEED("バスの速さ(0.1ブロック/tick)", 8, 5, 12, 16),
    AUTO_GLIDE("自動グライダー高度(0=手動)", 70, 0, 100, 40);

    public final String label;
    public final int[] values;

    Setting(String label, int... values) {
        this.label = label;
        this.values = values;
    }

    public String display(int v) {
        return switch (this) {
            case KIT -> Kits.NAMES.get(v);
            case PROTECT, BUS -> v == 1 ? "ON" : "OFF";
            default -> String.valueOf(v);
        };
    }
}
