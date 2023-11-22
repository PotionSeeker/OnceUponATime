package codyhuh.onceuponatime.common.entities;

import codyhuh.onceuponatime.client.ClientEvents;
import codyhuh.onceuponatime.common.entities.goal.GriffinLandOnGroundGoal;
import codyhuh.onceuponatime.common.entities.goal.GriffinWanderGoal;
import codyhuh.onceuponatime.common.entities.movecontrol.GroundAndFlyingMoveControl;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class Griffin extends AbstractHorse {
    private static final EntityDataAccessor<Boolean> IS_FLYING = SynchedEntityData.defineId(Griffin.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_LANDING = SynchedEntityData.defineId(Griffin.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> FLIGHT_TICKS = SynchedEntityData.defineId(Griffin.class, EntityDataSerializers.INT);
    public final int MAX_FLIGHT_TICKS = 1200;
    public GriffinWanderGoal wanderGoal;
    public GriffinLandOnGroundGoal landGoal;

    public Griffin(EntityType<? extends AbstractHorse> type, Level level) {
        super(type, level);
        this.moveControl = new GroundAndFlyingMoveControl(this, 10, MAX_FLIGHT_TICKS);
    }

    @Override
    protected void registerGoals() {
        wanderGoal = new GriffinWanderGoal(this, 1.0D, 1.0F);
        landGoal = new GriffinLandOnGroundGoal(this, 1.0D);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D, AbstractHorse.class));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(5, landGoal);
        this.goalSelector.addGoal(6, wanderGoal);
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createGriffinAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.JUMP_STRENGTH, 1.0D).add(Attributes.FLYING_SPEED, 0.25F).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public void travel(Vec3 vec3d) {
        boolean flying = this.isFlying();
        float speed = (float) this.getAttributeValue(flying ? Attributes.FLYING_SPEED : Attributes.MOVEMENT_SPEED);

        if (isControlledByLocalInstance() && getControllingPassenger() != null) {
            LivingEntity rider = getControllingPassenger();
            double moveX = rider.xxa * 0.5;
            double moveY = vec3d.y;
            double moveZ = rider.zza;

            yHeadRot = rider.yHeadRot;
            xRot = rider.xRot * 0.65f;
            yRot = Mth.rotateIfNecessary(yHeadRot, yRot, isFlying() ? 5 : 7);

            if (isControlledByLocalInstance()) {
                if (isFlying()) {
                    moveX = vec3d.x;
                    moveY = Minecraft.getInstance().options.keyJump.isDown() ? 0.5F : ClientEvents.descendKey.isDown() ? -0.5 : 0F;
                    moveZ = moveZ > 0 ? moveZ : 0;
                }
                else {
                    if (rider.jumping) {
                        jumpFromGround();
                        setFlying(true);
                    }
                }

                speed *= 0.5F;

                vec3d = new Vec3(moveX, moveY, moveZ);
                setSpeed(speed);
            }
            else if (rider instanceof Player) {
                calculateEntityAnimation(true);
                setDeltaMovement(Vec3.ZERO);
                if (!level().isClientSide && isFlying())
                    ((ServerPlayer) rider).connection.aboveGroundVehicleTickCount = 0;
                return;
            }
        }
        if (flying) {
            this.moveRelative(speed, vec3d);
            this.move(MoverType.SELF, getDeltaMovement());
            this.setDeltaMovement(getDeltaMovement().scale(0.91f));
            this.calculateEntityAnimation(true);
        }
        else {
            super.travel(vec3d);
        }
    }

    public boolean wantsToFly() {
        return canFly() && getFlightTicks() <= MAX_FLIGHT_TICKS;
    }

    @Override
    public void handleStartJump(int pJumpPower) {
    }

    @Override
    public void onPlayerJump(int pJumpPower) {
    }

    @Override
    protected void executeRidersJump(float pPlayerJumpPendingScale, Vec3 pTravelVector) {
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_FLYING, false);
        this.entityData.define(FLIGHT_TICKS, 0);
        this.entityData.define(IS_LANDING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("IsGriffinFlying", this.isFlying());
        pCompound.putInt("FlightTicks", this.getFlightTicks());
        pCompound.putBoolean("IsLanding", this.isLanding());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFlying(pCompound.getBoolean("IsGriffinFlying"));
        this.setFlightTicks(pCompound.getInt("FlightTicks"));
        this.setLanding(pCompound.getBoolean("IsLanding"));
    }

    public boolean isFlying() {
        return this.entityData.get(IS_FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(IS_FLYING, flying);
    }

    public boolean isLanding() {
        return this.entityData.get(IS_LANDING);
    }

    public void setLanding(boolean landing) {
        this.entityData.set(IS_LANDING, landing);
    }

    public int getFlightTicks() {
        return this.entityData.get(FLIGHT_TICKS);
    }

    public void setFlightTicks(int flightTicks) {
        this.entityData.set(FLIGHT_TICKS, flightTicks);
    }

    public boolean canFly() {
        BlockPos pos = blockPosition();

        return !level().getBlockState(pos.offset(0, -1, 0)).isSolid();
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        pPassenger.setPos(position().add(0.0D, 0.85D, 0.0D));
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (wanderGoal != null) {
            wanderGoal.trigger();
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void setNoGravity(boolean pNoGravity) {
        super.setNoGravity(isFlying() || isLanding());
    }

    @Override
    public void tick() {
        super.tick();

        if (level().getBlockState(blockPosition().below(1   )).isAir() && !isFlying() && !isLanding()) {
            setLanding(true);
            wanderGoal.trigger();
        }

        if (getFlightTicks() <= MAX_FLIGHT_TICKS && (isFlying() || isLanding()) && !isVehicle() && !isNoAi()) {
            setFlightTicks(getFlightTicks() + 1);
        }

        if (onGround() || getFlightTicks() >= MAX_FLIGHT_TICKS) {
            setFlying(false);
        }

        if (onGround() && isLanding()) {
            setLanding(false);
        }

        if (getFlightTicks() > 0 && !isFlying() && !isVehicle()) {
            setFlightTicks(getFlightTicks() - 1);
        }

        double x = getDeltaMovement().x();
        double z = getDeltaMovement().z();

        boolean notMoving = Math.abs(x) < 0.1D && Math.abs(z) < 0.1D;

        if (wanderGoal != null && isFlying() && !isLanding() && wantsToFly() && !isVehicle() && notMoving) {
            wanderGoal.trigger();
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (pPlayer.getItemInHand(pHand).is(Items.RABBIT) && !ForgeEventFactory.onAnimalTame(this, pPlayer)) {
            tameWithName(pPlayer);
        }

        return super.mobInteract(pPlayer, pHand);
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
