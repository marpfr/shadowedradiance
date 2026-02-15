package marpfr.shadowedradiance.common.item;

import marpfr.shadowedradiance.common.block.entity.LuxAccumulatorBlockEntity;
import marpfr.shadowedradiance.common.block.entity.LuxRelayBlockEntity;
import marpfr.shadowedradiance.common.component.LuxBatonData;
import marpfr.shadowedradiance.common.component.SRDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class LuxBatonItem extends Item {

    public LuxBatonItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide() && context.getPlayer() instanceof ServerPlayer serverPlayer) {

            BlockPos pos = context.getClickedPos();

            if (level.getBlockEntity(pos) instanceof LuxRelayBlockEntity relayBE) {
                var itemStack = context.getItemInHand();
                if (!itemStack.has(SRDataComponents.LUX_BATON_DATA)) {
                    itemStack.set(SRDataComponents.LUX_BATON_DATA, LuxBatonData.fromBlockPos(context.getClickedPos()));
                } else {
                    LuxBatonData comp = itemStack.get(SRDataComponents.LUX_BATON_DATA);
                    if (level.getBlockEntity(comp.blockPos()) instanceof LuxRelayBlockEntity otherRelayBE) {
                        if (relayBE.connectRelay(comp.blockPos())) {
                            if (otherRelayBE.connectRelay(pos)) {
                                itemStack.remove(SRDataComponents.LUX_BATON_DATA);
                                serverPlayer.displayClientMessage(Component.literal("Successfully Connected!"), true);
                                return InteractionResult.SUCCESS;
                            }
                        }
                        serverPlayer.displayClientMessage(Component.literal("Invalid Connection!"), true);
                        return InteractionResult.FAIL;
                    } else {
                        itemStack.remove(SRDataComponents.LUX_BATON_DATA);
                        serverPlayer.displayClientMessage(Component.literal("First Relay Missing!"), true);
                        return InteractionResult.FAIL;
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        if (stack.has(SRDataComponents.LUX_BATON_DATA)) {
            var data = stack.get(SRDataComponents.LUX_BATON_DATA);
            if (data.blockPos() != null) {
                tooltipAdder.accept(Component.literal(data.blockPos().toString()));
            }
        }
    }
}
