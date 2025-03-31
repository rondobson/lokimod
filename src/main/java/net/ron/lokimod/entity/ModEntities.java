package net.ron.lokimod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ron.lokimod.LokiMod;
import net.ron.lokimod.entity.custom.LokiEntity;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LokiMod.MODID);

    public static final RegistryObject<EntityType<LokiEntity>> LOKI =
            ENTITY_TYPES.register("loki", () -> EntityType.Builder.of(LokiEntity:: new, MobCategory.CREATURE).sized(0.6F, 0.85F).build("loki"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
