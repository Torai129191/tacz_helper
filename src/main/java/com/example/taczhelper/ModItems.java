package com.example.taczhelper;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

/** 建築ボトル 10種類の建物 x 10種類の素材 = 100個 */
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TaczHelper.MODID);

    public static final List<RegistryObject<Item>> BOTTLES = new ArrayList<>();

    static {
        for (Blueprints.Shape s : Blueprints.Shape.values()) {
            for (Blueprints.Palette c : Blueprints.Palette.values()) {
                String id = "bottle_" + s.name().toLowerCase() + "_" + c.name().toLowerCase();
                BOTTLES.add(ITEMS.register(id,
                        () -> new BuildBottleItem(new Item.Properties().stacksTo(16), s, c)));
            }
        }
    }

    private ModItems() {}
}
