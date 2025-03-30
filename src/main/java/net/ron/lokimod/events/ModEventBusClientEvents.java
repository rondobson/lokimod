package net.ron.lokimod.events;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ron.lokimod.LokiMod;
import net.ron.lokimod.entity.client.LokiModel;
import net.ron.lokimod.entity.client.ModModelLayers;

@Mod.EventBusSubscriber(modid = LokiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.LOKI_LAYER, LokiModel::createBodyLayer);
    }
}
