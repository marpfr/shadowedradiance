package marpfr.shadowedradiance.common.block;

import com.mojang.serialization.MapCodec;
import marpfr.shadowedradiance.ShadowedRadiance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class SRBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ShadowedRadiance.MODID);
    public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, ShadowedRadiance.MODID);

    public static final DeferredBlock<LuxImbuedStoneBlock> LUX_IMBUED_STONE_BLOCK = BLOCKS.registerBlock("lux_imbued_stone", LuxImbuedStoneBlock::new);
    public static final Supplier<MapCodec<LuxImbuedStoneBlock>> LUX_IMBUED_STONE_BLOCK_TYPE = BLOCK_TYPES.register("lux_imbued_stone", () -> LuxImbuedStoneBlock.CODEC);

    public static final DeferredBlock<LuxCrystalClusterBlock> LUX_CRYSTAL_CLUSTER = BLOCKS.registerBlock("lux_crystal_cluster", LuxCrystalClusterBlock::new);
    public static final Supplier<MapCodec<LuxCrystalClusterBlock>> LUX_CRYSTAL_CLUSTER_TYPE = BLOCK_TYPES.register("lux_crystal_cluster", () -> LuxCrystalClusterBlock.CODEC);

    public static final DeferredBlock<LuxAccumulatorBlock> LUX_ACCUMULATOR = BLOCKS.registerBlock("lux_accumulator", LuxAccumulatorBlock::new);
    public static final Supplier<MapCodec<LuxAccumulatorBlock>> LUX_ACCUMULATOR_TYPE = BLOCK_TYPES.register("lux_accumulator", () -> LuxAccumulatorBlock.CODEC);

    public static final DeferredBlock<LuxRelayBlock> LUX_RELAY = BLOCKS.registerBlock("lux_relay", LuxRelayBlock::new);
    public static final Supplier<MapCodec<LuxRelayBlock>> LUX_RELAY_TYPE = BLOCK_TYPES.register("lux_relay", () -> LuxRelayBlock.CODEC);

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

    public static final DeferredBlock<Block> LUXWOOD_LOG
            = BLOCKS.registerBlock("luxwood_log", RotatedPillarBlock::new, woodProperties());

    public static final DeferredBlock<Block> STRIPPED_LUXWOOD_LOG
            = BLOCKS.registerBlock("stripped_luxwood_log", RotatedPillarBlock::new, woodProperties());

    public static final DeferredBlock<Block> LUXWOOD_WOOD
            = BLOCKS.registerBlock("luxwood_wood", RotatedPillarBlock::new, woodProperties());

    public static final DeferredBlock<Block> STRIPPED_LUXWOOD_WOOD
            = BLOCKS.registerBlock("stripped_luxwood_wood", RotatedPillarBlock::new, woodProperties());

    public static final DeferredBlock<Block> LUXWOOD_LEAVES
            = BLOCKS.registerBlock(
                    "luxwood_leaves",
            p -> new TintedParticleLeavesBlock(
                    0.1F,
                    p.mapColor(MapColor.PLANT)
                            .strength(0.2F)
                            .randomTicks()
                            .sound(SoundType.GRASS)
                            .noOcclusion()
                            .isValidSpawn(Blocks::ocelotOrParrot)
                            .isSuffocating(SRBlocks::bockPredicateNever)
                            .isViewBlocking(SRBlocks::bockPredicateNever)
                            .ignitedByLava()
                            .pushReaction(PushReaction.DESTROY)
                            .isRedstoneConductor(SRBlocks::bockPredicateNever)));

    public static final DeferredBlock<Block> LUXWOOD_PLANKS
            = BLOCKS.registerBlock("luxwood_planks", Block::new, woodProperties());

    private static UnaryOperator<BlockBehaviour.Properties> woodProperties() {
        return p -> p.mapColor(MapColor.COLOR_BROWN)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava();
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }

    private static boolean bockPredicateNever(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }
}
