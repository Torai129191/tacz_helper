package com.example.taczhelper;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TaczHelper.MODID);

    public static final RegistryObject<EntityType<BattleBusEntity>> BATTLE_BUS = ENTITIES.register("battle_bus",
            () -> EntityType.Builder.<BattleBusEntity>of(BattleBusEntity::new, MobCategory.MISC)
                    .sized(3.0F, 3.0F)
                    .clientTrackingRange(16)
                    .updateInterval(1)
                    .fireImmune()
                    .build("battle_bus"));

    private ModEntities() {}
}
