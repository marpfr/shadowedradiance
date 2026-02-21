package marpfr.shadowedradiance;

import marpfr.shadowedradiance.common.block.SRBlocks;
import marpfr.shadowedradiance.common.block.entity.SRBlockEntities;
import marpfr.shadowedradiance.common.block.entity.renderer.LuxAccumulatorBlockEntityRenderer;
import marpfr.shadowedradiance.common.particle.LuxTransferParticle;
import marpfr.shadowedradiance.common.particle.SRParticles;
import marpfr.shadowedradiance.datagen.SRModelProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.data.event.GatherDataEvent;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = ShadowedRadiance.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = ShadowedRadiance.MODID, value = Dist.CLIENT)
public class ShadowedRadianceClient {
    public ShadowedRadianceClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        ShadowedRadiance.LOGGER.info("HELLO FROM CLIENT SETUP");
        ShadowedRadiance.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        ItemBlockRenderTypes.setRenderLayer(SRBlocks.LUX_CRYSTAL_CLUSTER.get(), ChunkSectionLayer.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(SRBlocks.LUX_RELAY.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(SRBlocks.LUXWOOD_LEAVES.get(), ChunkSectionLayer.CUTOUT);
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SRParticles.LUX_TRANSFER_PARTICLE.get(), LuxTransferParticle.Provider::new);
    }

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                // The block entity type to register the renderer for.
                SRBlockEntities.LUX_ACCUMULATOR_BLOCK_ENTITY.get(),
                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                LuxAccumulatorBlockEntityRenderer::new
        );
    }

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        // Parameters are the block's state, the level the block is in, the block's position, and the tint index.
        // The level and position may be null.
        event.register(
                (p_386202_, p_386203_, p_386204_, p_386205_) -> p_386203_ != null && p_386204_ != null
                        ? BiomeColors.getAverageFoliageColor(p_386203_, p_386204_)
                        : -12012264,
                SRBlocks.LUXWOOD_LEAVES.get()
        );
    }
}
