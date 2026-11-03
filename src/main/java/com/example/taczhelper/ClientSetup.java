package com.example.taczhelper;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

/** ボトルの本体を素材の色に染める（クライアント側のみ） */
@Mod.EventBusSubscriber(modid = TaczHelper.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup {

    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.Item e) {
        Item[] items = ModItems.BOTTLES.stream().map(RegistryObject::get).toArray(Item[]::new);
        e.register((stack, tintIndex) -> {
            if (tintIndex == 0 && stack.getItem() instanceof BuildBottleItem b) {
                return 0xFF000000 | b.color();
            }
            return 0xFFFFFFFF;
        }, items);
    }

    private ClientSetup() {}
}
