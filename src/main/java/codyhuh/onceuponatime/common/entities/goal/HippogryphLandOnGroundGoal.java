package codyhuh.onceuponatime.common.entities.goal;

import codyhuh.onceuponatime.common.entities.Hippogryph;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class HippogryphLandOnGroundGoal extends WaterAvoidingRandomStrollGoal {
    private final Hippogryph mob;

    public HippogryphLandOnGroundGoal(Hippogryph goalOwner, double speedMod) {
        super(goalOwner, speedMod, 1);
        this.mob = goalOwner;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public boolean canUse() {
        return (forceTrigger || (mob.isFlying() && !mob.wantsToFly() && !mob.isVehicle())) && super.canUse();
    }

    public void trigger() {
        forceTrigger = true;
    }

    @Override
    public void tick() {
        super.tick();

        if (mob.onGround()) {
            stop();
        }
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        //return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 32, 24) : super.getPosition();
        return mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, mob.blockPosition()).getCenter();
    }

    @Override
    public void stop() {
        mob.setLanding(false);
    }
}
