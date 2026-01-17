package marpfr.shadowedradiance.common.block.entity;

import marpfr.shadowedradiance.ShadowedRadiance;
import marpfr.shadowedradiance.common.block.SRBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SRBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ShadowedRadiance.MODID);

    public static final Supplier<BlockEntityType<LuxAccumulatorBlockEntity>> LUX_ACCUMULATOR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "lux_accumulator_entity",
            () -> new BlockEntityType<>(
                    LuxAccumulatorBlockEntity::new,
                    false,
                    SRBlocks.LUX_ACCUMULATOR.get()
            )
    );

    public static void register(IEventBus modBus) { BLOCK_ENTITY_TYPES.register(modBus); }
}
