package codyhuh.onceuponatime.common.entities;

import codyhuh.onceuponatime.common.entities.movecontrol.HydraMoveControl;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

import java.util.Arrays;
import java.util.List;

public class Hydra extends Monster {
    private static final EntityDataAccessor<Boolean> LEFT_KILLED = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> MIDDLE_KILLED = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RIGHT_KILLED = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LEFT_KILLED_BY_FIRE = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> MIDDLE_KILLED_BY_FIRE = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RIGHT_KILLED_BY_FIRE = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ATTACK_ANIMATION_TYPE = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_ANIMATION_TIMER = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SELECTED_HEAD = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.INT); // 0: none, 1: left, 2: middle, 3: right

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState leftHeadAnimationState = new AnimationState();
    public final AnimationState middleHeadAnimationState = new AnimationState();
    public final AnimationState rightHeadAnimationState = new AnimationState();
    private final HydraPart[] subEntities;
    private final HydraPart body;
    private final HydraPart headLeft;
    private final HydraPart headMiddle;
    private final HydraPart headRight;
    public float leftHealth = 30;
    public float middleHealth = 30;
    public float rightHealth = 30;
    private int leftRegenTimer = 0;
    private int middleRegenTimer = 0;
    private int rightRegenTimer = 0;
    private static final int HEAD_REGEN_COOLDOWN = 20 * 10; // 10 seconds
    private static final int SMOKE_TICK_INTERVAL = 10; // Emit smoke every 10 ticks (0.5 seconds)
    public float prevTilt;
    public float tilt;

    public Hydra(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.body = new HydraPart(this, "body", 1.15F, 0.95F);
        this.headLeft = new HydraPart(this, "headLeft", 0.4F, 1.5F);
        this.headMiddle = new HydraPart(this, "headMiddle", 0.4F, 1.5F);
        this.headRight = new HydraPart(this, "headRight", 0.4F, 1.5F);
        this.subEntities = new HydraPart[]{this.body, this.headLeft, this.headMiddle, this.headRight};
        this.setId(ENTITY_COUNTER.getAndAdd(this.subEntities.length + 1) + 1);
        this.moveControl = new HydraMoveControl(this, 15); // Max turn angle of 15 degrees
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HydraRangedAttackGoal(this, 0.6D, 40, 4.0F, 12.0F)); // Further reduced speed for stability
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createHydraAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5F);
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (isLeftHeadKilled() && isMiddleHeadKilled() && isRightHeadKilled()) {
            return false; // Can't attack with no heads
        }
        return false; // Damage handled in HydraRangedAttackGoal
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LEFT_KILLED, false);
        this.entityData.define(MIDDLE_KILLED, false);
        this.entityData.define(RIGHT_KILLED, false);
        this.entityData.define(LEFT_KILLED_BY_FIRE, false);
        this.entityData.define(MIDDLE_KILLED_BY_FIRE, false);
        this.entityData.define(RIGHT_KILLED_BY_FIRE, false);
        this.entityData.define(ATTACK_ANIMATION_TYPE, 0); // 0: none, 1: Bite, 2: Spit, 3: Spray
        this.entityData.define(ATTACK_ANIMATION_TIMER, 0);
        this.entityData.define(SELECTED_HEAD, 0); // 0: none, 1: left, 2: middle, 3: right
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setLeftHeadKilled(pCompound.getBoolean("LeftHeadKilled"));
        this.setMiddleHeadKilled(pCompound.getBoolean("MiddleHeadKilled"));
        this.setRightHeadKilled(pCompound.getBoolean("RightHeadKilled"));
        this.setLeftKilledByFire(pCompound.getBoolean("LeftKilledByFire"));
        this.setMiddleKilledByFire(pCompound.getBoolean("MiddleKilledByFire"));
        this.setRightKilledByFire(pCompound.getBoolean("RightKilledByFire"));
        this.setAttackAnimation(pCompound.getInt("AttackAnimationType"), pCompound.getInt("AttackAnimationTimer"));
        this.entityData.set(SELECTED_HEAD, pCompound.getInt("SelectedHead"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("LeftHeadKilled", this.isLeftHeadKilled());
        pCompound.putBoolean("MiddleHeadKilled", this.isMiddleHeadKilled());
        pCompound.putBoolean("RightHeadKilled", this.isRightHeadKilled());
        pCompound.putBoolean("LeftKilledByFire", this.isLeftKilledByFire());
        pCompound.putBoolean("MiddleKilledByFire", this.isMiddleKilledByFire());
        pCompound.putBoolean("RightKilledByFire", this.isRightKilledByFire());
        pCompound.putInt("AttackAnimationType", this.getAttackAnimationType());
        pCompound.putInt("AttackAnimationTimer", this.getAttackAnimationTimer());
        pCompound.putInt("SelectedHead", this.getSelectedHead());
    }

    public void setAttackAnimation(int type, int timer) {
        this.entityData.set(ATTACK_ANIMATION_TYPE, type);
        this.entityData.set(ATTACK_ANIMATION_TIMER, timer);
    }

    public int getAttackAnimationType() {
        return this.entityData.get(ATTACK_ANIMATION_TYPE);
    }

    public int getAttackAnimationTimer() {
        return this.entityData.get(ATTACK_ANIMATION_TIMER);
    }

    public int getSelectedHead() {
        return this.entityData.get(SELECTED_HEAD);
    }

    private void setSelectedHead(int head) {
        this.entityData.set(SELECTED_HEAD, head);
    }

    private void selectRandomHead() {
        List<HydraPart> livingHeads = Arrays.stream(subEntities)
                .filter(part -> part != body &&
                        !(part == headLeft && (isLeftHeadKilled() || isLeftKilledByFire())) &&
                        !(part == headMiddle && (isMiddleHeadKilled() || isMiddleKilledByFire())) &&
                        !(part == headRight && (isRightHeadKilled() || isRightKilledByFire())))
                .toList();
        if (!livingHeads.isEmpty()) {
            HydraPart selected = livingHeads.get(random.nextInt(livingHeads.size()));
            int headId = 0;
            if (selected == headLeft) headId = 1;
            else if (selected == headMiddle) headId = 2;
            else if (selected == headRight) headId = 3;
            setSelectedHead(headId);
            if (!level().isClientSide()) {
                System.out.println("Selected head for attack: " + selected.name + " (ID: " + headId + ")");
            }
        } else {
            setSelectedHead(0);
        }
    }

    @Override
    public void setId(int pId) {
        super.setId(pId);
        for (int i = 0; i < this.subEntities.length; i++) {
            this.subEntities[i].setId(pId + i + 1);
        }
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        HydraPart[] parts = this.getSubEntities();
        for (int i = 0; i < parts.length; ++i) {
            parts[i].setId(packet.getId() + i + 1);
        }
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.subEntities;
    }

    public HydraPart[] getSubEntities() {
        return this.subEntities;
    }

    protected void movePart(HydraPart part, double dX, double dY, double dZ) {
        Vec3 lastPos = new Vec3(part.getX(), part.getY(), part.getZ());
        part.setPos(this.getX() + dX, this.getY() + dY, this.getZ() + dZ);
        part.xo = lastPos.x;
        part.yo = lastPos.y;
        part.zo = lastPos.z;
        part.xOld = lastPos.x;
        part.yOld = lastPos.y;
        part.zOld = lastPos.z;
    }

    protected void updateParts() {
        Vec3 body = Vec3.ZERO;
        float yawRad = -this.yBodyRot * ((float) Math.PI / 180f);
        Vec3 headLeft = new Vec3(0.6D, 0.4D, 0.8D).yRot(yawRad);
        movePart(this.headLeft, headLeft.x, headLeft.y, headLeft.z);
        Vec3 headMiddle = new Vec3(0.0D, 0.4D, 0.8D).yRot(yawRad);
        movePart(this.headMiddle, headMiddle.x, headMiddle.y, headMiddle.z);
        Vec3 headRight = new Vec3(-0.6D, 0.4D, 0.8D).yRot(yawRad);
        movePart(this.headRight, headRight.x, headRight.y, headRight.z);
        movePart(this.body, body.x, body.y, body.z);
    }

    public float getLeftHeadHealth() {
        return this.leftHealth;
    }

    private void setLeftHeadHealth(float health) {
        this.leftHealth = health;
    }

    public float getMiddleHeadHealth() {
        return this.middleHealth;
    }

    private void setMiddleHeadHealth(float health) {
        this.middleHealth = health;
    }

    public float getRightHeadHealth() {
        return this.rightHealth;
    }

    private void setRightHeadHealth(float health) {
        this.rightHealth = health;
    }

    public boolean isLeftHeadKilled() {
        return this.entityData.get(LEFT_KILLED);
    }

    private void setLeftHeadKilled(boolean killed) {
        this.entityData.set(LEFT_KILLED, killed);
    }

    public boolean isMiddleHeadKilled() {
        return this.entityData.get(MIDDLE_KILLED);
    }

    private void setMiddleHeadKilled(boolean killed) {
        this.entityData.set(MIDDLE_KILLED, killed);
    }

    public boolean isRightHeadKilled() {
        return this.entityData.get(RIGHT_KILLED);
    }

    private void setRightHeadKilled(boolean killed) {
        this.entityData.set(RIGHT_KILLED, killed);
    }

    public boolean isLeftKilledByFire() {
        return this.entityData.get(LEFT_KILLED_BY_FIRE);
    }

    private void setLeftKilledByFire(boolean killedByFire) {
        this.entityData.set(LEFT_KILLED_BY_FIRE, killedByFire);
    }

    public boolean isMiddleKilledByFire() {
        return this.entityData.get(MIDDLE_KILLED_BY_FIRE);
    }

    private void setMiddleKilledByFire(boolean killedByFire) {
        this.entityData.set(MIDDLE_KILLED_BY_FIRE, killedByFire);
    }

    public boolean isRightKilledByFire() {
        return this.entityData.get(RIGHT_KILLED_BY_FIRE);
    }

    private void setRightKilledByFire(boolean killedByFire) {
        this.entityData.set(RIGHT_KILLED_BY_FIRE, killedByFire);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!isInvulnerableTo(pSource)) {
            if (pSource.equals(damageSources().genericKill())) {
                return super.hurt(pSource, pAmount);
            } else if (isRightHeadKilled() && isLeftHeadKilled() && isMiddleHeadKilled()) {
                return super.hurt(pSource, pAmount);
            }
        }
        return !level().isClientSide();
    }

    public boolean damagePart(HydraPart part, DamageSource source, float damage) {
        if (!level().isClientSide()) System.out.println("Damage to: " + part.name);
        boolean isFire = source.is(DamageTypeTags.IS_FIRE) || source.getDirectEntity() instanceof ThrownPotion
                || source.getDirectEntity() instanceof FireworkRocketEntity
                || (source.getEntity() instanceof LivingEntity le && le.getMainHandItem().getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0);

        if (isAlive()) {
            if (part.equals(this.headLeft) && isFire && isLeftHeadKilled() && !isLeftKilledByFire()) {
                setLeftKilledByFire(true);
                spawnBurnParticles(part);
                level().playSound(null, part.getX(), part.getY(), part.getZ(), SoundEvents.GENERIC_BURN, SoundSource.NEUTRAL, 1.0F, 1.0F);
                if (!level().isClientSide()) System.out.println("Fire Aspect cauterized: headLeft");
                return true;
            }
            if (part.equals(this.headMiddle) && isFire && isMiddleHeadKilled() && !isMiddleKilledByFire()) {
                setMiddleKilledByFire(true);
                spawnBurnParticles(part);
                level().playSound(null, part.getX(), part.getY(), part.getZ(), SoundEvents.GENERIC_BURN, SoundSource.NEUTRAL, 1.0F, 1.0F);
                if (!level().isClientSide()) System.out.println("Fire Aspect cauterized: headMiddle");
                return true;
            }
            if (part.equals(this.headRight) && isFire && isRightHeadKilled() && !isRightKilledByFire()) {
                setRightKilledByFire(true);
                spawnBurnParticles(part);
                level().playSound(null, part.getX(), part.getY(), part.getZ(), SoundEvents.GENERIC_BURN, SoundSource.NEUTRAL, 1.0F, 1.0F);
                if (!level().isClientSide()) System.out.println("Fire Aspect cauterized: headRight");
                return true;
            }
        }

        if (isLeftHeadKilled() && isMiddleHeadKilled() && isRightHeadKilled()) {
            return super.hurt(source, damage * 0.5F);
        } else {
            if (part.equals(this.headRight)) {
                if (this.getRightHeadHealth() - damage <= 0) {
                    this.setRightHeadHealth(0);
                    this.setRightHeadKilled(true);
                    this.rightRegenTimer = HEAD_REGEN_COOLDOWN;
                    return super.hurt(source, 0.0F);
                } else {
                    this.setRightHeadHealth(this.getRightHeadHealth() - damage);
                    return super.hurt(source, 0.0F);
                }
            }
            if (part.equals(this.headMiddle)) {
                if (this.getMiddleHeadHealth() - damage <= 0) {
                    this.setMiddleHeadHealth(0);
                    this.setMiddleHeadKilled(true);
                    this.middleRegenTimer = HEAD_REGEN_COOLDOWN;
                    return super.hurt(source, 0.0F);
                } else {
                    this.setMiddleHeadHealth(this.getMiddleHeadHealth() - damage);
                    return super.hurt(source, 0.0F);
                }
            }
            if (part.equals(this.headLeft)) {
                if (this.getLeftHeadHealth() - damage <= 0) {
                    this.setLeftHeadHealth(0);
                    this.setLeftHeadKilled(true);
                    this.leftRegenTimer = HEAD_REGEN_COOLDOWN;
                    return super.hurt(source, 0.0F);
                } else {
                    this.setLeftHeadHealth(this.getLeftHeadHealth() - damage);
                    return super.hurt(source, 0.0F);
                }
            }
        }
        return false;
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height - 0.1F;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        updateParts();
        prevTilt = tilt;
        LivingEntity target = this.getTarget();
        if (target != null) {
            // Calculate target yaw
            double dx = target.getX() - this.getX();
            double dz = target.getZ() - this.getZ();
            float targetYaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
            // Normalize yaw difference
            float deltaYaw = Mth.wrapDegrees(targetYaw - this.yBodyRot);
            // Dynamic lerp factor based on yaw difference
            float lerpFactor = Math.abs(deltaYaw) > 45F ? 0.05F : 0.1F;
            // Interpolate body rotation with tighter clamp
            this.yBodyRot = Mth.lerp(lerpFactor, this.yBodyRot, this.yBodyRot + Mth.clamp(deltaYaw, -15F, 15F));
            this.yRot = Mth.lerp(lerpFactor, this.yRot, this.yBodyRot);
            // Update tilt for oscillation damping
            if (Math.abs(deltaYaw) > 1) {
                if (Math.abs(tilt) < 15) {
                    tilt -= Math.signum(deltaYaw) * 0.5F;
                }
            } else {
                if (Math.abs(tilt) > 0) {
                    float tiltSign = Math.signum(tilt);
                    tilt -= tiltSign * 0.85F;
                    if (tilt * tiltSign < 0) {
                        tilt = 0;
                    }
                }
            }
            // Update look control only if not in animation
            if (getAttackAnimationTimer() == 0) {
                this.getLookControl().setLookAt(target, 15.0F, 15.0F);
            }
        } else {
            // Gradually reduce tilt when no target
            if (Math.abs(tilt) > 0) {
                float tiltSign = Math.signum(tilt);
                tilt -= tiltSign * 0.85F;
                if (tilt * tiltSign < 0) {
                    tilt = 0;
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide() && isAlive()) {
            this.setupAnimationStates();
            // Emit smoke particles for cauterized heads on client-side
            if (isLeftKilledByFire() && isLeftHeadKilled() && tickCount % SMOKE_TICK_INTERVAL == 0) {
                level().addParticle(ParticleTypes.SMOKE, headLeft.getX(), headLeft.getY() + 0.8, headLeft.getZ(), 0, 0.05, 0);
            }
            if (isMiddleKilledByFire() && isMiddleHeadKilled() && tickCount % SMOKE_TICK_INTERVAL == 0) {
                level().addParticle(ParticleTypes.SMOKE, headMiddle.getX(), headMiddle.getY() + 0.8, headMiddle.getZ(), 0, 0.05, 0);
            }
            if (isRightKilledByFire() && isRightHeadKilled() && tickCount % SMOKE_TICK_INTERVAL == 0) {
                level().addParticle(ParticleTypes.SMOKE, headRight.getX(), headRight.getY() + 0.8, headRight.getZ(), 0, 0.05, 0);
            }
            return;
        }
        // Server-side: Update attack animation timer
        if (getAttackAnimationTimer() > 0) {
            setAttackAnimation(getAttackAnimationType(), getAttackAnimationTimer() - 1);
        } else {
            setSelectedHead(0); // Reset selected head after animation
        }
        if (isLeftHeadKilled() && !isLeftKilledByFire()) {
            if (leftRegenTimer > 0) {
                leftRegenTimer--;
            } else {
                System.out.println("leftHead Regen");
                this.setLeftHeadKilled(false);
                this.setLeftHeadHealth(30);
                this.setLeftKilledByFire(false);
            }
        }
        if (isMiddleHeadKilled() && !isMiddleKilledByFire()) {
            if (middleRegenTimer > 0) {
                middleRegenTimer--;
            } else {
                System.out.println("middleHead Regen");
                this.setMiddleHeadKilled(false);
                this.setMiddleHeadHealth(30);
                this.setMiddleKilledByFire(false);
            }
        }
        if (isRightHeadKilled() && !isRightKilledByFire()) {
            if (rightRegenTimer > 0) {
                rightRegenTimer--;
            } else {
                System.out.println("rightHead Regen");
                this.setRightHeadKilled(false);
                this.setRightHeadHealth(30);
                this.setRightKilledByFire(false);
            }
        }
        if (isLeftHeadKilled() && isMiddleHeadKilled() && isRightHeadKilled()
                && isLeftKilledByFire() && isMiddleKilledByFire() && isRightKilledByFire()) {
            System.out.println("Hydra killed: All heads cauterized.");
            this.kill();
        }
    }

    private void setupAnimationStates() {
        if (getAttackAnimationTimer() > 0) {
            int head = getSelectedHead();
            AnimationState state = head == 1 ? leftHeadAnimationState : head == 2 ? middleHeadAnimationState : head == 3 ? rightHeadAnimationState : idleAnimationState;
            state.startIfStopped(this.tickCount);
        } else {
            leftHeadAnimationState.stop();
            middleHeadAnimationState.stop();
            rightHeadAnimationState.stop();
            if (this.getDeltaMovement().horizontalDistanceSqr() > 0.01) {
                // Walk animation handled in HydraModel
            } else {
                idleAnimationState.startIfStopped(this.tickCount);
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!level().isClientSide()) {
            System.out.println("Hydra mobInteract: Body interacted");
        }
        return super.mobInteract(player, hand);
    }

    public InteractionResult interactPart(HydraPart part, Player player, InteractionHand hand) {
        if (!level().isClientSide()) {
            System.out.println("Interacting with part: " + part.name);
        }
        if (isAlive() && player.getItemInHand(hand).getItem() == Items.FLINT_AND_STEEL && !level().isClientSide()) {
            boolean success = false;
            if (part.equals(this.headLeft) && isLeftHeadKilled() && !isLeftKilledByFire()) {
                setLeftKilledByFire(true);
                success = true;
            } else if (part.equals(this.headMiddle) && isMiddleHeadKilled() && !isMiddleKilledByFire()) {
                setMiddleKilledByFire(true);
                success = true;
            } else if (part.equals(this.headRight) && isRightHeadKilled() && !isRightKilledByFire()) {
                setRightKilledByFire(true);
                success = true;
            }
            if (success) {
                spawnBurnParticles(part);
                player.getItemInHand(hand).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                level().playSound(null, part.getX(), part.getY(), part.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                level().playSound(null, part.getX(), part.getY(), part.getZ(), SoundEvents.GENERIC_BURN, SoundSource.NEUTRAL, 1.0F, 1.0F);
                if (!level().isClientSide()) {
                    System.out.println("Flint and steel cauterized: " + part.name);
                }
                return InteractionResult.SUCCESS;
            } else {
                if (!level().isClientSide()) {
                    System.out.println("Failed to cauterize: " + part.name);
                }
                level().addParticle(ParticleTypes.SMOKE, part.getX(), part.getY() + 0.8, part.getZ(), 0, 0.05, 0);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (isAlive()) {
            HydraPart targetPart = null;
            if (id == 10) {
                targetPart = headLeft;
            } else if (id == 11) {
                targetPart = headMiddle;
            } else if (id == 12) {
                targetPart = headRight;
            }
            if (targetPart != null) {
                for (int i = 0; i < 5; i++) {
                    double offsetX = level().random.nextGaussian() * 0.1;
                    double offsetY = level().random.nextGaussian() * 0.1 + 0.8;
                    double offsetZ = level().random.nextGaussian() * 0.1;
                    level().addParticle(ParticleTypes.SMOKE, targetPart.getX() + offsetX, targetPart.getY() + offsetY, targetPart.getZ() + offsetZ, 0, 0.1, 0);
                    level().addParticle(ParticleTypes.FLAME, targetPart.getX() + offsetX, targetPart.getY() + offsetY, targetPart.getZ() + offsetZ, 0, 0.1, 0);
                }
            }
        }
        super.handleEntityEvent(id);
    }

    private void spawnBurnParticles(Entity part) {
        if (!level().isClientSide() && isAlive()) {
            byte eventId = -1;
            if (part.equals(headLeft)) {
                eventId = 10;
            } else if (part.equals(headMiddle)) {
                eventId = 11;
            } else if (part.equals(headRight)) {
                eventId = 12;
            }
            if (eventId != -1) {
                level().broadcastEntityEvent(this, eventId);
            }
        } else if (level().isClientSide()) {
            for (int i = 0; i < 5; i++) {
                double offsetX = level().random.nextGaussian() * 0.1;
                double offsetY = level().random.nextGaussian() * 0.1 + 0.8;
                double offsetZ = level().random.nextGaussian() * 0.1;
                level().addParticle(ParticleTypes.SMOKE, part.getX() + offsetX, part.getY() + offsetY, part.getZ() + offsetZ, 0, 0.1, 0);
                level().addParticle(ParticleTypes.FLAME, part.getX() + offsetX, part.getY() + offsetY, part.getZ() + offsetZ, 0, 0.1, 0);
            }
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.EMPTY;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.EMPTY;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.EMPTY;
    }

    private static class HydraRangedAttackGoal extends Goal {
        private final Hydra hydra;
        private final double speedModifier;
        private final int attackInterval;
        private final float minAttackRange;
        private final float maxAttackRange;
        private int attackTime = -1;
        private Vec3 lastTargetPos = null;
        private int pathUpdateTimer = 0;

        public HydraRangedAttackGoal(Hydra hydra, double speedModifier, int attackInterval, float minAttackRange, float maxAttackRange) {
            this.hydra = hydra;
            this.speedModifier = speedModifier;
            this.attackInterval = attackInterval;
            this.minAttackRange = minAttackRange;
            this.maxAttackRange = maxAttackRange;
        }

        @Override
        public boolean canUse() {
            return hydra.getTarget() != null && (!hydra.isLeftHeadKilled() || !hydra.isMiddleHeadKilled() || !hydra.isRightHeadKilled());
        }

        @Override
        public void start() {
            this.attackTime = -1;
            this.lastTargetPos = null;
            this.pathUpdateTimer = 0;
        }

        @Override
        public void stop() {
            this.attackTime = -1;
            hydra.setAttackAnimation(0, 0);
            hydra.setSelectedHead(0);
            hydra.getNavigation().stop();
            this.lastTargetPos = null;
        }

        @Override
        public void tick() {
            LivingEntity target = hydra.getTarget();
            if (target == null) return;

            double distance = hydra.distanceToSqr(target);
            float attackRange = minAttackRange * minAttackRange;
            float maxRange = maxAttackRange * maxAttackRange;

            // Stop movement during attack animations
            if (hydra.getAttackAnimationTimer() > 0) {
                hydra.getNavigation().stop();
                // Apply damage for bite at timer == 3
                if (hydra.getAttackAnimationType() == 1 && hydra.getAttackAnimationTimer() == 3) {
                    if (distance <= attackRange) {
                        target.hurt(hydra.damageSources().mobAttack(hydra), (float) hydra.getAttributeValue(Attributes.ATTACK_DAMAGE));
                        if (!hydra.level().isClientSide()) {
                            System.out.println("Bite damage applied");
                        }
                    }
                }
                return;
            }

            // Update path less frequently for stability
            if (--pathUpdateTimer <= 0) {
                // Predict target position based on velocity
                Vec3 targetPos = new Vec3(
                        target.getX() + target.getDeltaMovement().x * 10,
                        target.getY(),
                        target.getZ() + target.getDeltaMovement().z * 10
                );
                if (lastTargetPos == null || lastTargetPos.distanceToSqr(targetPos) > 16.0D) { // Update path if target moved > 4 blocks
                    hydra.getNavigation().moveTo(targetPos.x, targetPos.y, targetPos.z, distance <= attackRange ? 0.4D : speedModifier);
                    lastTargetPos = targetPos;
                    pathUpdateTimer = 20; // Update path every 1 second
                }
            }

            // Only apply look control when not attacking
            if (hydra.getAttackAnimationTimer() == 0) {
                hydra.getLookControl().setLookAt(target, 15.0F, 15.0F);
            }

            if (--this.attackTime <= 0 && hydra.getAttackAnimationTimer() == 0) {
                this.attackTime = this.attackInterval;
                if (distance <= attackRange) { // Close range: Bite
                    hydra.getNavigation().stop();
                    if (!hydra.level().isClientSide()) {
                        System.out.println("Bite attack initiated");
                        hydra.setAttackAnimation(1, 15); // Bite, 0.75s = 15 ticks
                        hydra.selectRandomHead();
                    }
                } else if (distance <= maxRange) {
                    hydra.getNavigation().stop();
                    if (distance <= 8.0 * 8.0) { // Mid range: Spray (4–8 blocks)
                        if (!hydra.level().isClientSide()) {
                            System.out.println("Spray attack initiated");
                            hydra.setAttackAnimation(3, 40); // Spray, 2.0s = 40 ticks
                            hydra.selectRandomHead();
                        }
                    } else { // Long range: Spit (9–12 blocks)
                        if (!hydra.level().isClientSide()) {
                            System.out.println("Spit attack initiated");
                            hydra.setAttackAnimation(2, 20); // Spit, 1.0s = 20 ticks
                            hydra.selectRandomHead();
                        }
                    }
                }
            }
        }
    }
}