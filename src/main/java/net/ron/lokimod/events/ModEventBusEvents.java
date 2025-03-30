package net.ron.lokimod.events;


import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.ron.lokimod.LokiMod;
import net.ron.lokimod.entity.ModEntities;
import net.ron.lokimod.entity.custom.LokiEntity;
import org.openjdk.nashorn.internal.ir.annotations.Ignore;

@Mod.EventBusSubscriber(modid = LokiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LOKI.get(), LokiEntity.createAttributes().build());
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            System.out.println("[DEBUG] Registering Loki spawn placement...\n");
            SpawnPlacements.register(
                    ModEntities.LOKI.get(),
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules
            );
        });
    }
}
