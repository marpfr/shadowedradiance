package marpfr.shadowedradiance.common.block.entity;

import marpfr.shadowedradiance.common.block.LuxAccumulatorBlock;
import marpfr.shadowedradiance.common.block.LuxRelayBlock;
import marpfr.shadowedradiance.common.block.SRBlocks;
import marpfr.shadowedradiance.common.particle.SRParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class LuxRelayBlockEntity extends BlockEntity {

    private final Set<BlockPos> connectedRelays = new HashSet<BlockPos>();

    public LuxRelayBlockEntity(BlockPos pos, BlockState blockState) {
        super(SRBlockEntities.LUX_RELAY_BLOCK_ENTITY.get(), pos, blockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level instanceof ServerLevel serverLevel) {
            connectedRelays.forEach(other -> {
                if (shouldRender(blockPos, other) && level.getBlockState(other) instanceof BlockState otherBlockState && otherBlockState.is(SRBlocks.LUX_RELAY)) {
                    Vec3 start = LuxRelayBlock.getCrystalOffset(blockPos, blockState);
                    Vec3 end = LuxRelayBlock.getCrystalOffset(other, otherBlockState);
                    getVectorBeam(start, end, 0.25).forEach(v -> {
                        serverLevel.sendParticles(SRParticles.LUX_TRANSFER_PARTICLE.get(), v.x, v.y, v.z, 1, 0.1d, 0.1d, 0.1d, 0.0d);
                    });
                }
            });
        }
    }

    private static boolean shouldRender(BlockPos current, BlockPos other) {
        if(current.getX() < other.getX()){
            return true;
        } else if (current.getX() == other.getX()) {
            if (current.getY() < other.getY()) {
                return true;
            } else if (current.getY() == other.getY() && current.getZ() < other.getZ()) {
                return true;
            }
        }
        return false;
    }

    public boolean connectRelay(BlockPos blockPos) {
        if (this.worldPosition == blockPos) {
            return false;
        }

        if (this.connectedRelays.contains(blockPos)) {
            return false;
        }

        var v1 = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        var v2 = new Vec3(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ());
        var blocksBetween = getBlocksInLine(this.worldPosition, blockPos);

        if (this.level instanceof ServerLevel serverLevel) {
            if (blocksBetween.stream().allMatch(b -> level.isEmptyBlock(b))) {

                this.connectedRelays.add(blockPos);

                blocksBetween.forEach(bp -> {
                    serverLevel.sendParticles(ParticleTypes.END_ROD, bp.getX() + 0.5, bp.getY() + 0.5, bp.getZ() + 0.5, 5, 0.1d, 0.1d, 0.1d, 0.0d);
                });

                return true;
            }
        }

        return false;
    }

    public void disconnectRelay(BlockPos blockPos) {
        this.connectedRelays.remove(blockPos);
    }

    public void remove() {
        this.connectedRelays.forEach(x -> {
            if (level.getBlockEntity(x) instanceof LuxRelayBlockEntity connectedRelay) {
                connectedRelay.disconnectRelay(this.worldPosition);
                connectedRelay.setChanged();
            }
        });

        this.setChanged();
    }

    private static Set<Vec3> getVectorBeam(Vec3 fromVec, Vec3 toVec, double step) {
        Set<Vec3> result = new HashSet<>();

        Vec3 dir = toVec.subtract(fromVec);
        double len = dir.length();

        dir = dir.normalize();

        for (double d = 0; d <= len; d += step) {
            result.add(fromVec.add(dir.scale(d)));
        }

        return result;
    }

    private static Set<BlockPos> getBlocksInLine(BlockPos from, BlockPos to) {
        return getVectorBeam(Vec3.atCenterOf(from), Vec3.atCenterOf(to), 0.25).stream()
                .map(BlockPos::containing)
                .filter(x -> !(x.equals(from) || x.equals(to)))
                .collect(Collectors.toSet());
    }
}
