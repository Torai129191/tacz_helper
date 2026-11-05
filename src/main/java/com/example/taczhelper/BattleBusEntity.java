package com.example.taczhelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

/** 空を飛ぶバトルバス。みんなを乗せて一直線に飛び、Shiftで飛び降りられる */
public class BattleBusEntity extends Entity {
    private static final EntityDataAccessor<Float> VX =
            SynchedEntityData.defineId(BattleBusEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> VZ =
            SynchedEntityData.defineId(BattleBusEntity.class, EntityDataSerializers.FLOAT);

    private int life = 0;
    private int maxLife = 0;

    public BattleBusEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    /** 速度(ブロック/tick)と、飛び続ける時間(tick)を決める */
    public void launch(double vx, double vz, int ticks) {
        this.entityData.set(VX, (float) vx);
        this.entityData.set(VZ, (float) vz);
        this.maxLife = ticks;
        float yaw = (float) (Mth.atan2(-vx, vz) * 57.29578F);
        this.setYRot(yaw);
        this.yRotO = yaw;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(VX, 0.0F);
        this.entityData.define(VZ, 0.0F);
    }

    @Override
    public void tick() {
        super.tick();
        float vx = this.entityData.get(VX);
        float vz = this.entityData.get(VZ);
        this.setPos(this.getX() + vx, this.getY(), this.getZ() + vz);
        if (!this.level().isClientSide) {
            life++;
            if (life >= maxLife) {
                this.discard(); // 乗っている人は自動で降りる
            }
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < 120;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.8;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(5.0, 7.0, 5.0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
