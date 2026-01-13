package marpfr.shadowedradiance.datagen;

import marpfr.shadowedradiance.common.item.SRItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class SRRecipeProvider extends RecipeProvider {

    protected SRRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {

        this.shaped(RecipeCategory.MISC, SRItems.LUX_CRYSTAL_SHARD_ITEM.get(), 1)
                .pattern(" FF")
                .pattern("FFF")
                .pattern("FF ")
                .define('F', SRItems.LUX_CRYSTAL_FRAGMENT_ITEM.get())
                .unlockedBy("has_lux_crystal_fragement", this.has(SRItems.LUX_CRYSTAL_FRAGMENT_ITEM))
                .save(this.output);
    }

    // The runner to add to the data generator
    public static class Runner extends RecipeProvider.Runner {

        // Get the parameters from the `GatherDataEvent`s.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput output) {
            return new SRRecipeProvider(provider, output);
        }

        @Override
        public @NonNull String getName() {
            return "Shadowed Radiance Recipes";
        }
    }
}
