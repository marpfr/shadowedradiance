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

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ShadowedRadiance.MODID);

    public static final DeferredItem<BlockItem> LUX_IMBUED_STONE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("lux_imbued_stone", SRBlocks.LUX_IMBUED_STONE_BLOCK);

    public static final DeferredItem<BlockItem> LUX_IMBUED_STONE_BRICK_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("lux_imbued_stone_brick", SRBlocks.LUX_IMBUED_STONE_BRICK_BLOCK);

    public static final DeferredItem<BlockItem> LUX_IMBUED_STONE_BRICK_STAIR_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("lux_imbued_stone_brick_stairs", SRBlocks.LUX_IMBUED_STONE_BRICK_STAIR_BLOCK);

    public static final DeferredItem<BlockItem> LUX_IMBUED_STONE_BRICK_SLAB_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("lux_imbued_stone_brick_slab", SRBlocks.LUX_IMBUED_STONE_BRICK_SLAB_BLOCK);

    public static final DeferredItem<BlockItem> LUX_IMBUED_STONE_BRICK_WALL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("lux_imbued_stone_brick_wall", SRBlocks.LUX_IMBUED_STONE_BRICK_WALL_BLOCK);

    public static final DeferredItem<BlockItem> LUX_CRYSTAL_CLUSTER_ITEM = ITEMS.registerSimpleBlockItem("lux_crystal_cluster", SRBlocks.LUX_CRYSTAL_CLUSTER);

    public static final DeferredItem<BlockItem> LUX_ACCUMULATOR_ITEM = ITEMS.registerSimpleBlockItem("lux_accumulator", SRBlocks.LUX_ACCUMULATOR);

    public static final DeferredItem<Item> LUX_CRYSTAL_FRAGMENT_ITEM = ITEMS.registerSimpleItem("lux_crystal_fragment");

    public static final DeferredItem<Item> LUX_CRYSTAL_SHARD_ITEM = ITEMS.registerSimpleItem("lux_crystal_shard");

    public static final DeferredItem<LuxCrystalLensItem> LUX_CRYSTAL_LENS_ITEM = ITEMS.registerItem("lux_crystal_lens", LuxCrystalLensItem::new);

    public static void register(IEventBus modEventBus) {

        ITEMS.register(modEventBus);
    }
}
