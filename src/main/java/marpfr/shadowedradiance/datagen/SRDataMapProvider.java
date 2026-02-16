package marpfr.shadowedradiance.datagen;

import marpfr.shadowedradiance.common.block.SRBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class SRDataMapProvider extends DataMapProvider {

    protected SRDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.@NonNull Provider provider) {
        this.builder(NeoForgeDataMaps.STRIPPABLES)
                .add(SRBlocks.LUXWOOD_LOG, new Strippable(SRBlocks.STRIPPED_LUXWOOD_LOG.get()), false)
                .add(SRBlocks.LUXWOOD_WOOD, new Strippable(SRBlocks.STRIPPED_LUXWOOD_WOOD.get()), false);
    }
}
