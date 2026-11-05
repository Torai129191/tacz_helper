package com.example.taczhelper;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayDeque;

/** 大きな建物でカクつかないように、1tickに置く量を制限して少しずつ建てる */
@Mod.EventBusSubscriber(modid = TaczHelper.MODID)
public final class BuildQueue {
    private static final ArrayDeque<Runnable> QUEUE = new ArrayDeque<>();
    private static final int PER_TICK = 700;

    public static void add(Runnable r) {
        QUEUE.add(r);
    }

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        for (int i = 0; i < PER_TICK && !QUEUE.isEmpty(); i++) {
            QUEUE.poll().run();
        }
    }

    @SubscribeEvent
    public static void onStop(ServerStoppedEvent e) {
        QUEUE.clear();
    }

    private BuildQueue() {}
}
