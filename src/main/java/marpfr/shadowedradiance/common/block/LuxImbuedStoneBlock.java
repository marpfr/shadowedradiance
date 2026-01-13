package marpfr.shadowedradiance.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jspecify.annotations.NonNull;

public class LuxImbuedStoneBlock extends Block {

    public static final MapCodec<LuxImbuedStoneBlock> CODEC = simpleCodec(LuxImbuedStoneBlock::new);

    @Override
    public @NonNull MapCodec<? extends LuxImbuedStoneBlock> codec() {
        return CODEC;
    }

    public LuxImbuedStoneBlock(Properties properties) {

        super(properties
                .mapColor(MapColor.STONE)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops()
                .strength(1.5F, 6.0F));
    }
}
