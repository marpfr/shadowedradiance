package marpfr.shadowedradiance.common;

import marpfr.shadowedradiance.ShadowedRadiance;
import marpfr.shadowedradiance.common.block.SRBlocks;
import marpfr.shadowedradiance.common.item.SRItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SRCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ShadowedRadiance.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.shadowedradiance"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> SRItems.LUX_CRYSTAL_CLUSTER_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {

                output.accept(SRItems.LUX_CRYSTAL_CLUSTER_ITEM.get());
                output.accept(SRItems.LUX_IMBUED_STONE_BLOCK_ITEM.get());
                output.accept(SRItems.LUX_IMBUED_STONE_BRICK_BLOCK_ITEM.get());
                output.accept(SRItems.LUX_IMBUED_STONE_BRICK_SLAB_BLOCK_ITEM.get());
                output.accept(SRItems.LUX_IMBUED_STONE_BRICK_STAIR_BLOCK_ITEM.get());
                output.accept(SRItems.LUX_IMBUED_STONE_BRICK_WALL_BLOCK_ITEM.get());
                output.accept(SRItems.LUX_CRYSTAL_FRAGMENT_ITEM.get());
                output.accept(SRItems.LUX_CRYSTAL_SHARD_ITEM.get());
                output.accept(SRItems.LUX_CRYSTAL_LENS_ITEM.get());
                output.accept(SRItems.LUX_ACCUMULATOR_ITEM.get());
                output.accept(SRItems.LUX_RELAY_ITEM.get());


            }).build());

    public static void register(IEventBus modEventBus) {

        CREATIVE_MODE_TABS.register(modEventBus);
    }

    // Add the example block item to the building blocks tab
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
//
//        }
    }
}
