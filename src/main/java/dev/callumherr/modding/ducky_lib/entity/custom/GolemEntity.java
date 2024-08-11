package dev.callumherr.modding.ducky_lib.entity.custom;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.PartEntity;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GolemEntity extends Animal {
    public final AnimationState anim = new AnimationState();
    public final GolemPart leftArm;
    public final GolemPart rightArm;
    public final GolemPart[] parts;

    public GolemEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.leftArm = new GolemPart(this, 0.75f, 2.1f);
        this.rightArm = new GolemPart(this, 0.75f, 2.1f);
        this.parts = new GolemPart[]{this.leftArm, this.rightArm};
        this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1);
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.parts.length; i++)
            this.parts[i].setId(id + i + 1);
    }

    public boolean hurtPart(GolemPart part, DamageSource source, float damage) {
        return this.hurt(source, damage);
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?> @NotNull [] getParts() {
        return parts;
    }

    @Override
    public void tick() {
        if (level().isClientSide) return;
        for (GolemPart part : parts) {
            part.setPos(-20, -60, -20);
            //updateParts(part, 0, 0, 0);
        }

        super.tick();
    }

    public void updateParts(GolemPart part, double x, double y, double z) {
        part.setPos(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }
}
