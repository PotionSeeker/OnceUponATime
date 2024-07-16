package codyhuh.onceuponatime.common.entities;

import codyhuh.onceuponatime.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Unicorn extends AbstractHorse implements PowerableMob {

    public Unicorn(EntityType<? extends AbstractHorse> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createUnicornAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.JUMP_STRENGTH, 1.25D).add(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    public boolean isPowered() {
        return true;
    }

    @Override
    protected boolean canAddPassenger(Entity pPassenger) {
        return false;
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.UNICORN.get().create(pLevel);
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 1.15F;
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide() && tickCount % 15 == 0) {
            Vec3 pos = getYawPitchVec(getYHeadRot(), getXRot(), 0.0F, 1.85F, 1.15F).add(position());
            Vec3 viewVec = getLookAngle();

            //level().addParticle(ParticleTypes.FIREWORK, pos.x, pos.y, pos.z, 0.1D * -viewVec.x, 0.1D, 0.1D * -viewVec.z);
            //level().addParticle(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, 0.0D, 0.175D, 0.0D);
        }
    }

    public static Vec3 getYawVec(float yaw, double xOffset, double yOffset, double zOffset) {
        return new Vec3(xOffset, yOffset, zOffset).yRot(-yaw * (Mth.PI / 180f));
    }

    public static Vec3 getYawPitchVec(float yaw, float pitch, double xOffset, double yOffset, double zOffset) {
        return new Vec3(xOffset, yOffset, zOffset).xRot(-pitch * (Mth.PI / 180f) * 0.5F).yRot(-yaw * (Mth.PI / 180f));
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        return super.mobInteract(pPlayer, pHand);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.HORSE_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.HORSE_DEATH;
    }

    @Nullable
    protected SoundEvent getEatingSound() {
        return SoundEvents.HORSE_EAT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.HORSE_HURT;
    }

    protected SoundEvent getAngrySound() {
        return SoundEvents.HORSE_ANGRY;
    }

    @Override
    public float getVoicePitch() {
        return 2.0F;
    }
}
