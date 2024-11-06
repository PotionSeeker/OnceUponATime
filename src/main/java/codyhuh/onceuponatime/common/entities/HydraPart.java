package codyhuh.onceuponatime.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HydraPart extends PartEntity<Hydra> {
    public final Hydra parentMob;
    public final String name;
    public final EntityDimensions size;

    public HydraPart(Hydra entity, String name, float x, float y) {
        super(entity);
        this.size = EntityDimensions.scalable(x, y);
        this.parentMob = entity;
        this.name = name;
        this.refreshDimensions();
    }

    public boolean is(Entity p_31031_) {
        return this == p_31031_ || this.parentMob == p_31031_;
    }

    public EntityDimensions getDimensions(Pose p_31023_) {
        return this.size;
    }

    public boolean shouldBeSaved() {
        return false;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.parentMob.getPickResult();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float damage) {
        return parentMob.damagePart(this, source, damage);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }
}