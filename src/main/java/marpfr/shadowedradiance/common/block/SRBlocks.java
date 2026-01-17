package marpfr.shadowedradiance.common.block;

import com.mojang.serialization.MapCodec;
import marpfr.shadowedradiance.ShadowedRadiance;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SRBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ShadowedRadiance.MODID);
    public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, ShadowedRadiance.MODID);

    public static final DeferredBlock<LuxImbuedStoneBlock> LUX_IMBUED_STONE_BLOCK = BLOCKS.registerBlock("lux_imbued_stone", LuxImbuedStoneBlock::new);
    public static final Supplier<MapCodec<LuxImbuedStoneBlock>> LUX_IMBUED_STONE_BLOCK_TYPE = BLOCK_TYPES.register("lux_imbued_stone", () -> LuxImbuedStoneBlock.CODEC);

    public static final DeferredBlock<LuxCrystalClusterBlock> LUX_CRYSTAL_CLUSTER = BLOCKS.registerBlock("lux_crystal_cluster", LuxCrystalClusterBlock::new);
    public static final Supplier<MapCodec<LuxCrystalClusterBlock>> LUX_CRYSTAL_CLUSTER_TYPE = BLOCK_TYPES.register("lux_crystal_cluster", () -> LuxCrystalClusterBlock.CODEC);

    public static final DeferredBlock<LuxAccumulatorBlock> LUX_ACCUMULATOR = BLOCKS.registerBlock("lux_accumulator", LuxAccumulatorBlock::new);
    public static final Supplier<MapCodec<LuxAccumulatorBlock>> LUX_ACCUMULATOR_TYPE = BLOCK_TYPES.register("lux_accumulator", () -> LuxAccumulatorBlock.CODEC);

    public  static final DeferredBlock<Block> LUX_IMBUED_STONE_BRICK_BLOCK
            = BLOCKS.registerSimpleBlock(
                    "lux_imbued_stone_brick",
            p -> p.mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F));

    public static final DeferredBlock<StairBlock> LUX_IMBUED_STONE_BRICK_STAIR_BLOCK
            = BLOCKS.registerBlock(
                    "lux_imbued_stone_brick_stairs",
                        properties -> new StairBlock(
                                LUX_IMBUED_STONE_BRICK_BLOCK.get().defaultBlockState(),
                                properties.mapColor(MapColor.STONE)
                                        .instrument(NoteBlockInstrument.BASEDRUM)
                                        .requiresCorrectToolForDrops()
                                        .strength(1.5F, 6.0F))
            );

    public static final DeferredBlock<SlabBlock> LUX_IMBUED_STONE_BRICK_SLAB_BLOCK
            = BLOCKS.registerBlock(
            "lux_imbued_stone_brick_slab",
            properties -> new SlabBlock(
                    properties.mapColor(MapColor.STONE)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F))
    );

    public static final DeferredBlock<WallBlock> LUX_IMBUED_STONE_BRICK_WALL_BLOCK
            = BLOCKS.registerBlock(
            "lux_imbued_stone_brick_wall",
            properties -> new WallBlock(
                    properties.mapColor(MapColor.STONE)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F))
    );

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
