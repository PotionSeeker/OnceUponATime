package codyhuh.onceuponatime.client.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HippogryphAnimation {

    public static final AnimationDefinition BABY_TRANSFORM = AnimationDefinition.Builder.withLength(0.0F)
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.5F, 1.5F, 1.5F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition FLY = AnimationDefinition.Builder.withLength(16.0F).looping()
            .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -10.0F, -4.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("l_leg_1", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-65.6F, -10.26F, 4.91F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("l_leg_1", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 3.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("r_leg_1", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-65.6F, 10.26F, -4.91F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("r_leg_1", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 3.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("l_wing_1", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 75.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("l_wing_2", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, -25.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("r_wing_1", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -75.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("r_wing_2", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 25.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("l_tight", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(51.49F, 11.84F, -9.27F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("r_tight", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(51.49F, -11.84F, 9.27F), AnimationChannel.Interpolations.LINEAR)))
            .build();
}
