package codyhuh.onceuponatime.common.entities;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

public class Hydra extends PathfinderMob {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private final HydraPart[] heads;
    private final HydraPart headLeft;
    private final HydraPart headMiddle;
    private final HydraPart headRight;

    public Hydra(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.headLeft = new HydraPart(this, "head", 0.4F, 1.5F);
        this.headMiddle = new HydraPart(this, "head", 0.4F, 1.5F);
        this.headRight = new HydraPart(this, "head", 0.4F, 1.5F);
        this.heads = new HydraPart[]{this.headLeft, this.headMiddle, this.headRight};
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createHydraAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.25F).add(Attributes.KNOCKBACK_RESISTANCE, 0.5F);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket p_149572_) {
        super.recreateFromPacket(p_149572_);
        HydraPart[] parts = this.getHeads();

        for(int i = 0; i < parts.length; ++i) {
            parts[i].setId(i + p_149572_.getId());
        }
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.heads;
    }

    public HydraPart[] getHeads() {
        return this.heads;
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
        Vec3 headLeft = new Vec3(0.6D, 0.4D, 0.8D).yRot(-yBodyRot * ((float) Math.PI / 180f));
        Vec3 headMiddle = new Vec3(0.0D, 0.4D, 0.8D).yRot(-yBodyRot * ((float) Math.PI / 180f));
        Vec3 headRight = new Vec3(-0.6D, 0.4D, 0.8D).yRot(-yBodyRot * ((float) Math.PI / 180f));

        movePart(this.headLeft, headLeft.x, headLeft.y, headLeft.z);
        movePart(this.headMiddle, headMiddle.x, headMiddle.y, headMiddle.z);
        movePart(this.headRight, headRight.x, headRight.y, headRight.z);
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        updateParts();
    }

    private void setupAnimationStates() {
        if (walkAnimation.speed() >  0.005F) {
            idleAnimationState.stop();
        }
        else {
            idleAnimationState.startIfStopped(this.tickCount);
        }

        if (this.idleAnimationTimeout == 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        }
        else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        return super.mobInteract(pPlayer, pHand);
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
}
