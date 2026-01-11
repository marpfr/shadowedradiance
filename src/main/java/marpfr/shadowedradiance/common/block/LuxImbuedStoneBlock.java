package marpfr.shadowedradiance.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

public class LuxImbuedStoneBlock extends Block {

    public static final MapCodec<LuxImbuedStoneBlock> CODEC = simpleCodec(LuxImbuedStoneBlock::new);

    @Override
    public @NonNull MapCodec<? extends LuxImbuedStoneBlock> codec() {
        return CODEC;
    }

    public LuxImbuedStoneBlock(Properties properties) {
        super(properties);
    }
}
