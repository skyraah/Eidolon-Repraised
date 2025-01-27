package elucent.eidolon.datagen;

import elucent.eidolon.Eidolon;
import elucent.eidolon.registries.Registry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EidBlockTagProvider extends BlockTagsProvider {
    public EidBlockTagProvider(DataGenerator pGenerator, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator.getPackOutput(), provider, Eidolon.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.LEAVES).add(Registry.ILLWOOD_LEAVES.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(Registry.ILLWOOD_LEAVES.get());
        tag(BlockTags.SAPLINGS).add(Registry.ILLWOOD_SAPLING.get());
        tag(BlockTags.WOODEN_BUTTONS).add(Registry.ILLWOOD_PLANKS.getButton(), Registry.POLISHED_PLANKS.getButton());
        tag(BlockTags.WALL_SIGNS).add(Registry.ILLWOOD_PLANKS.getWallSign(), Registry.POLISHED_PLANKS.getWallSign());
        tag(BlockTags.STANDING_SIGNS).add(Registry.ILLWOOD_PLANKS.getStandingSign(), Registry.POLISHED_PLANKS.getStandingSign());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(Registry.ILLWOOD_PLANKS.getPressurePlate(), Registry.POLISHED_PLANKS.getPressurePlate());
        //tag(BlockTags.WOODEN_TRAPDOORS).add(Registry.ILLWOOD_PLANKS.getTrapdoor(), Registry.POLISHED_PLANKS.getTrapdoor());
        tag(Registry.CRUCIBLE_HOT_BLOCKS).add(Blocks.MAGMA_BLOCK,
                Blocks.FIRE,
                Blocks.SOUL_FIRE,
                Blocks.LAVA);
        tag(BlockTags.CANDLES).add(Registry.CANDLE.get(), Registry.CANDLESTICK.get(), Registry.MAGIC_CANDLE.get(), Registry.MAGIC_CANDLESTICK.get());
        tag(Registry.PLANTER_PLANTS).add(Registry.MERAMMER_ROOT.get(),
                Registry.OANNA_BLOOM.get(),
                Registry.AVENNIAN_SPRIG.get(),
                Registry.SILDRIAN_SEED.get()
        );
    }

    void logsTag(Block... blocks) {
        tag(BlockTags.LOGS).add(blocks);
        tag(BlockTags.LOGS_THAT_BURN).add(blocks);
        tag(BlockTags.MINEABLE_WITH_AXE).add(blocks);
    }

    void addPickMineable(int level, Block... blocks) {
        for (Block block : blocks) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
            switch (level) {
                case 1 -> tag(BlockTags.NEEDS_STONE_TOOL).add(block);
                case 2 -> tag(BlockTags.NEEDS_IRON_TOOL).add(block);
                case 3 -> tag(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
                case 4 -> tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(block);
            }
        }

    }

    @Override
    public @NotNull String getName() {
        return "Eidolon Block Tags";
    }

}

