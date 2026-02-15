package marpfr.shadowedradiance.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record LuxBatonData(BlockPos blockPos) implements TooltipProvider {
    public static final Codec<LuxBatonData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockPos.CODEC.fieldOf("blockPos").forGetter(LuxBatonData::blockPos)
            ).apply(instance, LuxBatonData::new));

    public static final StreamCodec<ByteBuf, LuxBatonData> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, LuxBatonData::blockPos,
            LuxBatonData::new
    );

    public static LuxBatonData fromBlockPos(BlockPos blockPos){
        return new LuxBatonData(blockPos);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        if (this.blockPos != null) {
            tooltipAdder.accept(Component.literal(this.blockPos.toString()));
        }

        tooltipAdder.accept(Component.literal("random text lol"));
    }
}
