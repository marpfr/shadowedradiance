package marpfr.shadowedradiance.common.item;

import marpfr.shadowedradiance.ShadowedRadiance;
import marpfr.shadowedradiance.common.block.SRBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SRItems {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ShadowedRadiance.MODID);

    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", SRBlocks.EXAMPLE_BLOCK);

    public static final DeferredItem<BlockItem> LUX_IMBUED_STONE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("lux_imbued_stone", SRBlocks.LUX_IMBUED_STONE_BLOCK);

    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", p -> p.food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    public static void register(IEventBus modEventBus) {

        ITEMS.register(modEventBus);
    }
}
