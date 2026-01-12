package marpfr.shadowedradiance.common.block;

import com.mojang.serialization.MapCodec;
import marpfr.shadowedradiance.ShadowedRadiance;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SRBlocks {

    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ShadowedRadiance.MODID);
    public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, ShadowedRadiance.MODID);



    public static final DeferredBlock<LuxImbuedStoneBlock> LUX_IMBUED_STONE_BLOCK = BLOCKS.registerBlock("lux_imbued_stone", LuxImbuedStoneBlock::new);
    public static final Supplier<MapCodec<LuxImbuedStoneBlock>> LUX_IMBUED_STONE_BLOCK_TYPE = BLOCK_TYPES.register("lux_imbued_stone", () -> LuxImbuedStoneBlock.CODEC);

    public static final DeferredBlock<LuxCrystalClusterBlock> LUX_CRYSTAL_CLUSTER = BLOCKS.registerBlock("lux_crystal_cluster", LuxCrystalClusterBlock::new);
    public static final Supplier<MapCodec<LuxCrystalClusterBlock>> LUX_CRYSTAL_CLUSTER_TYPE = BLOCK_TYPES.register("lux_crystal_cluster", () -> LuxCrystalClusterBlock.CODEC);

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
