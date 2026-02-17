package marpfr.shadowedradiance;

import marpfr.shadowedradiance.common.SRCreativeTabs;
import marpfr.shadowedradiance.common.block.SRBlocks;
import marpfr.shadowedradiance.common.block.entity.SRBlockEntities;
import marpfr.shadowedradiance.common.component.SRDataComponents;
import marpfr.shadowedradiance.common.item.SRItems;
import marpfr.shadowedradiance.common.particle.SRParticles;
import marpfr.shadowedradiance.common.world.biome.LuminousGrovesRegion;
import marpfr.shadowedradiance.common.world.biome.SRSurfaceRules;
import marpfr.shadowedradiance.common.world.feature.SRFeatures;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforgespi.language.IModInfo;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(ShadowedRadiance.MODID)
public class ShadowedRadiance {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "shadowedradiance";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // Dependencies
    public static boolean terrablenderLoaded = false;
    public static boolean lambdynlights_apiLoaded = false;


    public ShadowedRadiance(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        terrablenderLoaded = ModList.get().isLoaded("terrablender");
        lambdynlights_apiLoaded = ModList.get().isLoaded("lambdynlights_api");

        LOGGER.debug("LOADED MODS");
        ModList.get().getMods().stream().map(IModInfo::getNamespace).forEach(LOGGER::debug);

        SRBlocks.register(modEventBus);
        SRItems.register(modEventBus);
        SRCreativeTabs.register(modEventBus);
        SRFeatures.register(modEventBus);
        SRBlockEntities.register(modEventBus);
        SRDataComponents.register(modEventBus);
        SRParticles.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ShadowedRadiance) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(SRCreativeTabs::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (terrablenderLoaded) {
            LOGGER.info("QUEUING TERRABLENDER TASKS");
            event.enqueueWork(() -> {
                Regions.register(new LuminousGrovesRegion(Identifier.fromNamespaceAndPath(MODID, "overworld"), 2));
                SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, ShadowedRadiance.MODID, SRSurfaceRules.makeRules());
            });
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
