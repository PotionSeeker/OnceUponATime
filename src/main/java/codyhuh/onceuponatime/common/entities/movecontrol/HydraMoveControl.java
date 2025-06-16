package codyhuh.onceuponatime.common.entities.movecontrol;

import codyhuh.onceuponatime.common.entities.Hydra;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class HydraMoveControl extends MoveControl {
    private final Hydra hydra;
    private final int maxTurn;

    public HydraMoveControl(Hydra hydra, int maxTurn) {
        super(hydra);
        this.hydra = hydra;
        this.maxTurn = maxTurn;
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            this.operation = Operation.WAIT;
            double dx = this.wantedX - hydra.getX();
            double dz = this.wantedZ - hydra.getZ();
            double dy = this.wantedY - hydra.getY();
            double distanceSqr = dx * dx + dy * dy + dz * dz;

            if (distanceSqr < 2.5000003E-7F) {
                hydra.setZza(0.0F);
                return;
            }

            // Calculate target yaw
            float targetYaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
            // Smoothly rotate using rotlerp
            hydra.setYRot(this.rotlerp(hydra.getYRot(), targetYaw, maxTurn));
            // Set speed
            float speed = (float) (this.speedModifier * hydra.getAttributeValue(Attributes.MOVEMENT_SPEED));
            hydra.setSpeed(speed);

            // Move forward
            double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
            if (Math.abs(dy) > 1.0E-5F || Math.abs(horizontalDistance) > 1.0E-5F) {
                float targetPitch = (float) (-(Mth.atan2(dy, horizontalDistance) * (180F / Math.PI)));
                hydra.setXRot(this.rotlerp(hydra.getXRot(), targetPitch, maxTurn));
                hydra.setYya(dy > 0.0D ? speed : -speed);
            }
        } else if (this.operation == Operation.STRAFE) {
            float speed = (float) (this.speedModifier * hydra.getAttributeValue(Attributes.MOVEMENT_SPEED));
            float forward = this.strafeForwards;
            float strafe = this.strafeRight;
            float magnitude = Mth.sqrt(forward * forward + strafe * strafe);
            if (magnitude < 1.0F) {
                magnitude = 1.0F;
            }

            magnitude = speed / magnitude;
            forward *= magnitude;
            strafe *= magnitude;

            float sinYaw = Mth.sin(hydra.getYRot() * ((float) Math.PI / 180F));
            float cosYaw = Mth.cos(hydra.getYRot() * ((float) Math.PI / 180F));
            float moveX = forward * cosYaw - strafe * sinYaw;
            float moveZ = strafe * cosYaw + forward * sinYaw;

            if (!this.isWalkable(moveX, moveZ)) {
                this.strafeForwards = 1.0F;
                this.strafeRight = 0.0F;
            }

            hydra.setSpeed(speed);
            hydra.setZza(this.strafeForwards);
            hydra.setXxa(this.strafeRight);
            this.operation = Operation.WAIT;
        } else {
            hydra.setSpeed(0.0F);
            hydra.setZza(0.0F);
            hydra.setXxa(0.0F);
        }
    }

    public boolean isWalkable(float moveX, float moveZ) {
        if (hydra.getNavigation() == null) {
            return true;
        }
        return hydra.getNavigation().isStableDestination(hydra.blockPosition().offset((int) moveX, 0, (int) moveZ));
    }
}