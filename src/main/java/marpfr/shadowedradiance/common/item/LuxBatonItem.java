package marpfr.shadowedradiance.common.item;

import marpfr.shadowedradiance.common.block.entity.LuxAccumulatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class LuxBatonItem extends Item {

    public LuxBatonItem(Properties properties) {
        super(properties.stacksTo(1));
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide()) {

            BlockPos pos = context.getClickedPos();

            System.out.println(pos);
            System.out.println(level);

            if (level.getBlockEntity(pos) instanceof LuxAccumulatorBlockEntity accumulatorBE) {
                System.out.println(accumulatorBE.getCurrentPotential());
            }
        }

        return InteractionResult.SUCCESS;
    }
}
