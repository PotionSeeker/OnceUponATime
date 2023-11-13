package codyhuh.onceuponatime.common.entities;

import codyhuh.onceuponatime.common.entities.goal.GriffinLandOnGroundGoal;
import codyhuh.onceuponatime.common.entities.goal.GriffinWanderGoal;
import codyhuh.onceuponatime.common.entities.movecontrol.GroundAndFlyingMoveControl;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Griffin extends AbstractHorse {
    private static final EntityDataAccessor<Boolean> IS_FLYING = SynchedEntityData.defineId(Griffin.class, EntityDataSerializers.BOOLEAN);
    public final int MAX_FLIGHT_TICKS = 1200;
    private GriffinWanderGoal wanderGoal;
    public int flightTicks;
    public int onGroundTicks;

    public Griffin(EntityType<? extends AbstractHorse> type, Level level) {
        super(type, level);
        this.moveControl = new GroundAndFlyingMoveControl(this, 10, MAX_FLIGHT_TICKS);
    }

    @Override
    protected void registerGoals() {
        wanderGoal = new GriffinWanderGoal(this, 1.0D, 1.0F);
        this.goalSelector.addGoal(0, new GriffinLandOnGroundGoal(this, 1.0D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D, AbstractHorse.class));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(6, wanderGoal);
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createGriffinAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.FLYING_SPEED, 1.0F).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    public boolean wantsToFly() {
        return canFly() && flightTicks <= 1200;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_FLYING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("IsFlying", this.isFlying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFlying(pCompound.getBoolean("IsFlying"));
    }

    public boolean isFlying() {
        return this.entityData.get(IS_FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(IS_FLYING, flying);
    }

    public boolean canFly() {
        BlockPos pos = blockPosition();

        return !level().getBlockState(pos.offset(0, -1, 0)).isSolid();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (wanderGoal != null) {
            wanderGoal.trigger();
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void tick() {
        super.tick();

        if (isFlying() && !isVehicle()) {
            flightTicks++;
        }

        if (!canFly()) {
            setFlying(false);
        }

        if (!onGround() && isFlying() && !wantsToFly()) {
            setDeltaMovement(getDeltaMovement().subtract(0.0D, 0.005D, 0.0D));
        }

        if (!isFlying() && !isVehicle()) {
            flightTicks--;
        }

        if (flightTicks <= -MAX_FLIGHT_TICKS) {
            flightTicks = 0;
        }

        double x = getDeltaMovement().x();
        double z = getDeltaMovement().z();

        boolean notMoving = Math.abs(x) < 0.1D && Math.abs(z) < 0.1D;

        if (wanderGoal != null && isFlying() && wantsToFly() && !isVehicle() && notMoving) {
            wanderGoal.trigger();
        }
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, pLevel);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        nav.setCanPassDoors(true);
        return nav;
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public boolean onClimbable() {
        return false;
    }
}
