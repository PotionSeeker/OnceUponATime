package codyhuh.onceuponatime.common.blocks;

import codyhuh.onceuponatime.common.entities.Hippogryph;
import codyhuh.onceuponatime.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public class HippogryphEggBlock extends Block {
    private static final VoxelShape AABB = Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;

    public HippogryphEggBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0));
    }

    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (!pEntity.isSteppingCarefully()) {
            this.destroyEgg(pLevel, pState, pPos, pEntity, 100);
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (!(pEntity instanceof Zombie)) {
            this.destroyEgg(pLevel, pState, pPos, pEntity, 3);
        }

        super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
    }

    private void destroyEgg(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, int pChance) {
        if (this.canDestroyEgg(pLevel, pEntity)) {
            if (!pLevel.isClientSide && pLevel.random.nextInt(pChance) == 0 && pState.is(Blocks.TURTLE_EGG)) {
                this.decreaseEggs(pLevel, pPos, pState);
            }

        }
    }

    private void decreaseEggs(Level pLevel, BlockPos pPos, BlockState pState) {
        pLevel.playSound(null, pPos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + pLevel.random.nextFloat() * 0.2F);
        pLevel.destroyBlock(pPos, false);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (this.shouldUpdateHatchLevel(pLevel) && onSand(pLevel, pPos)) {
            int i = pState.getValue(HATCH);
            if (i < 2) {
                pLevel.playSound(null, pPos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + pRandom.nextFloat() * 0.2F);
                pLevel.setBlock(pPos, pState.setValue(HATCH, i + 1), 2);
            }
            else {
                pLevel.playSound(null, pPos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + pRandom.nextFloat() * 0.2F);
                pLevel.removeBlock(pPos, false);

                for(int j = 0; j < 1; ++j) {
                    pLevel.levelEvent(2001, pPos, Block.getId(pState));
                    Hippogryph hippogryph = ModEntities.HIPPOGRYPH.get().create(pLevel);

                    if (hippogryph != null) {
                        hippogryph.setAge(-24000);
                        hippogryph.moveTo((double)pPos.getX() + 0.3D + (double)j * 0.2D, pPos.getY(), (double)pPos.getZ() + 0.3D, 0.0F, 0.0F);
                        pLevel.addFreshEntity(hippogryph);
                    }
                }
            }
        }

    }

    public static boolean onSand(BlockGetter pLevel, BlockPos pPos) {
        return isSand(pLevel, pPos.below());
    }

    public static boolean isSand(BlockGetter pReader, BlockPos pPos) {
        return pReader.getBlockState(pPos).is(BlockTags.SAND);
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (onSand(pLevel, pPos) && !pLevel.isClientSide) {
            pLevel.levelEvent(2005, pPos, 0);
        }
    }

    private boolean shouldUpdateHatchLevel(Level pLevel) {
        float f = pLevel.getTimeOfDay(1.0F);
        if ((double)f < 0.69D && (double)f > 0.65D) {
            return true;
        } else {
            return pLevel.random.nextInt(500) == 0;
        }
    }

    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pTe, ItemStack pStack) {
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pTe, pStack);
        this.decreaseEggs(pLevel, pPos, pState);
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.isSecondaryUseActive() && pUseContext.getItemInHand().is(this.asItem()) || super.canBeReplaced(pState, pUseContext);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABB;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HATCH);
    }

    private boolean canDestroyEgg(Level pLevel, Entity pEntity) {
        if (!(pEntity instanceof Turtle) && !(pEntity instanceof Bat)) {
            if (!(pEntity instanceof LivingEntity)) {
                return false;
            } else {
                return pEntity instanceof Player || ForgeEventFactory.getMobGriefingEvent(pLevel, pEntity);
            }
        } else {
            return false;
        }
    }
}
