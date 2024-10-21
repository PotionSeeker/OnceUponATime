package codyhuh.onceuponatime.common.level.feature;


import codyhuh.onceuponatime.common.level.feature.config.StructureFeatureConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class HippogryphNestFeature extends Feature<StructureFeatureConfiguration> {

    public HippogryphNestFeature(Codec<StructureFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<StructureFeatureConfiguration> context) {
        RandomSource rand = context.random();
        WorldGenLevel level = context.level();
        BlockPos genAt = context.origin();
        genAt = genAt.above();

        BlockPos structurePos = genAt.below();
        Rotation rotation = Rotation.getRandom(rand);
        ResourceLocation structureLocation = context.config().structure.get(rand.nextInt(context.config().structure.size()));
        StructureTemplateManager structuretemplatemanager = level.getLevel().getServer().getStructureManager();

        StructureTemplate template = structuretemplatemanager.getOrCreate(structureLocation);
        StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(rotation).setRandom(rand);
        Vec3i rotatedSize = template.getSize(rotation);
        BlockPos blockpos1 = structurePos.offset(-Math.round(rotatedSize.getX() / 2F - 1), 0, (int) -Math.ceil(rotatedSize.getZ() / 2F - 1));
        BlockPos blockpos2 = template.getZeroPositionWithTransform(blockpos1, Mirror.NONE, rotation);
        template.placeInWorld(level, blockpos2, blockpos2, settings, rand, 4);
        return true;
    }
}