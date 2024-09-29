package codyhuh.onceuponatime.common.entities;

import codyhuh.onceuponatime.client.ClientEvents;
import codyhuh.onceuponatime.common.entities.goal.HippogryphLandOnGroundGoal;
import codyhuh.onceuponatime.common.entities.goal.HippogryphWanderGoal;
import codyhuh.onceuponatime.common.entities.lookcontrol.FlyingLookControl;
import codyhuh.onceuponatime.common.entities.movecontrol.GroundAndFlyingMoveControl;
import codyhuh.onceuponatime.common.items.HippogryphArmorItem;
import codyhuh.onceuponatime.registry.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

// todo - untamed hippogrpyhs should not be able to be equipped with armor
public class Hippogryph extends AbstractHorse {
    private static final EntityDataAccessor<Boolean> IS_FLYING = SynchedEntityData.defineId(Hippogryph.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_LANDING = SynchedEntityData.defineId(Hippogryph.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_ARMOR = SynchedEntityData.defineId(Hippogryph.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> FLIGHT_TICKS = SynchedEntityData.defineId(Hippogryph.class, EntityDataSerializers.INT);
    private static final UUID ARMOR_MODIFIER_UUID = UUID.randomUUID();
    public final int MAX_FLIGHT_TICKS = 1200;
    public HippogryphWanderGoal wanderGoal;
    public HippogryphLandOnGroundGoal landGoal;
    public float prevTilt;
    public float tilt;

    public Hippogryph(EntityType<? extends AbstractHorse> type, Level level) {
        super(type, level);
        this.moveControl = new GroundAndFlyingMoveControl(this, 10, MAX_FLIGHT_TICKS);
        this.lookControl = new FlyingLookControl(this, 10);
    }

    @Override
    protected void registerGoals() {
        landGoal = new HippogryphLandOnGroundGoal(this, 1.0D);
        wanderGoal = new HippogryphWanderGoal(this, 1.0D, 1.0F);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(5, landGoal);
        this.goalSelector.addGoal(6, wanderGoal);
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createHippogryphAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.JUMP_STRENGTH, 1.0D).add(Attributes.FLYING_SPEED, 0.25F).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public void travel(Vec3 vec3d) {
        boolean flying = this.isFlying();
        float speed = (float) this.getAttributeValue(flying ? Attributes.FLYING_SPEED : Attributes.MOVEMENT_SPEED);

        if (isNoAi()) {
            return;
        }
        else if (isControlledByLocalInstance() && getControllingPassenger() != null && getControllingPassenger() instanceof Player rider) {
            double moveX = rider.xxa * 0.5;
            double moveY = vec3d.y;
            double moveZ = rider.zza;

            yHeadRot = rider.yHeadRot;

            getLookControl().setLookAt(position().add(0.0D, 2.0D,0.0D));
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
            else {
                calculateEntityAnimation(true);
                setDeltaMovement(Vec3.ZERO);
                if (!level().isClientSide && isFlying())
                    ((ServerPlayer) rider).connection.aboveGroundVehicleTickCount = 0;
                return;
            }
        }
        if (flying && !isNoAi()) {
            this.moveRelative(speed, vec3d);
            this.move(MoverType.SELF, getDeltaMovement());
            double down = 0.0F;
            if (level().isClientSide()) {
                down = (Minecraft.getInstance().options.keyLeft.isDown() || Minecraft.getInstance().options.keyRight.isDown() || Minecraft.getInstance().options.keyUp.isDown() || Minecraft.getInstance().options.keyJump.isDown()) ? -0.01F : -0.02F;
            }
            this.setDeltaMovement(getDeltaMovement().scale(0.91F).add(0.0F, down, 0.0F));
            this.calculateEntityAnimation(true);
        }
        else {
            super.travel(vec3d);
        }
    }

    @Override
    protected boolean canAddPassenger(Entity pPassenger) {
        return pPassenger instanceof Player;
    }

    protected float rotateTowards(float pFrom, float pTo, float pMaxDelta) {
        float f = Mth.degreesDifference(pFrom, pTo);
        float f1 = Mth.clamp(f, -pMaxDelta, pMaxDelta);
        return pFrom + f1;
    }

    public boolean wantsToFly() {
        return /*canFly() &&*/ getFlightTicks() <= MAX_FLIGHT_TICKS;
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
        this.entityData.define(HAS_ARMOR, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("IsHippogryphFlying", this.isFlying());
        pCompound.putInt("FlightTicks", this.getFlightTicks());
        pCompound.putBoolean("IsLanding", this.isLanding());
        if (!this.inventory.getItem(1).isEmpty()) {
            pCompound.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFlying(pCompound.getBoolean("IsHippogryphFlying"));
        this.setFlightTicks(pCompound.getInt("FlightTicks"));
        this.setLanding(pCompound.getBoolean("IsLanding"));
        if (pCompound.contains("ArmorItem", 10)) {
            ItemStack itemstack = ItemStack.of(pCompound.getCompound("ArmorItem"));
            if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
                this.inventory.setItem(1, itemstack);
            }
        }
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

    public ItemStack getArmor() {
        return this.getItemBySlot(EquipmentSlot.CHEST);
    }

    private void setArmor(ItemStack pStack) {
        this.setItemSlot(EquipmentSlot.CHEST, pStack);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
    }

    public boolean canFly() {
        BlockPos pos = blockPosition();

        return !level().getBlockState(pos.offset(0, -1, 0)).isSolid();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.HIPPOGRYPH.get().create(pLevel);
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
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 1.15F;
    }

    @Override
    public void tick() {
        super.tick();

        if (isVehicle() && getControllingPassenger() != null && isFlying() || isLanding()) {
            float added = (float) position().y() * (float) getDeltaMovement().y();
            float xTilt = Mth.clamp(added, -25.0F, 20.0F);

            setXRot(-Mth.lerp(getXRot(), xTilt, xTilt));
        }

        if (!isVehicle() && level().getBlockState(blockPosition().below(1)).isAir() && !isFlying() && !isLanding()) {
            if (landGoal != null) {
                landGoal.trigger();
            }
        }

        if (getFlightTicks() <= MAX_FLIGHT_TICKS && (isFlying() || isLanding()) && !isVehicle() && !isNoAi()) {
            setFlightTicks(getFlightTicks() + 1);
        }

        if (onGround() || getFlightTicks() >= MAX_FLIGHT_TICKS || isUnderWater()) {
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
    public void aiStep() {
        super.aiStep();
        prevTilt = tilt;
        if (isFlying() || isLanding()) {
            final float v = Mth.degreesDifference(this.getYRot(), yRotO);
            if (Math.abs(v) > 1) {
                if (Math.abs(tilt) < 25) {
                    tilt -= Math.signum(v);
                }
            } else {
                if (Math.abs(tilt) > 0) {
                    final float tiltSign = Math.signum(tilt);
                    tilt -= tiltSign * 0.85F;
                    if (tilt * tiltSign < 0) {
                        tilt = 0;
                    }
                }
            }
        } else {
            tilt = 0;
        }
    }

    @Override
    protected Vec2 getRiddenRotation(LivingEntity pEntity) {
        return new Vec2(getXRot(), pEntity.getYRot());
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (!this.isVehicle() && !this.isBaby()) {
            if (this.isTamed() && pPlayer.isSecondaryUseActive()) {
                this.openCustomInventoryScreen(pPlayer);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            else {
                if (!itemstack.isEmpty()) {
                    InteractionResult interactionresult = itemstack.interactLivingEntity(pPlayer, this, pHand);
                    if (interactionresult.consumesAction()) {
                        return interactionresult;
                    }

                    if (this.canWearArmor() && this.isArmor(itemstack) && !this.isWearingArmor()) {
                        this.equipArmor(pPlayer, itemstack);
                        return InteractionResult.sidedSuccess(this.level().isClientSide);
                    }
                }

                this.doPlayerRide(pPlayer);
            }
            if (!isTamed() && itemstack.is(Items.RABBIT) && !ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                tameWithName(pPlayer);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    public boolean canWearArmor() {
        return true;
    }

    public boolean isArmor(ItemStack pStack) {
        return pStack.getItem() instanceof HippogryphArmorItem;
    }

    protected void updateContainerEquipment() {
        if (!this.level().isClientSide) {
            super.updateContainerEquipment();
            this.setArmorEquipment(this.inventory.getItem(1));
            this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }

    private void setArmorEquipment(ItemStack pStack) {
        this.setArmor(pStack);
        if (!this.level().isClientSide) {
            this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
            if (this.isArmor(pStack)) {
                int i = ((HippogryphArmorItem)pStack.getItem()).getProtection();
                if (i != 0) {
                    this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Hippogryph armor bonus", i, AttributeModifier.Operation.ADDITION));
                }
            }
        }

    }

    public void containerChanged(Container pInvBasic) {
        ItemStack itemstack = this.getArmor();
        super.containerChanged(pInvBasic);
        ItemStack itemstack1 = this.getArmor();
        if (this.tickCount > 20 && this.isArmor(itemstack1) && itemstack != itemstack1) {
            this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
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
