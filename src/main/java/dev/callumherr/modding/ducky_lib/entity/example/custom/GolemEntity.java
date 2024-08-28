package dev.callumherr.modding.ducky_lib.entity.example.custom;

import dev.callumherr.modding.ducky_lib.entity.api.MultiPartEntity;
import dev.callumherr.modding.ducky_lib.entity.api.MultiPartMonster;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class GolemEntity extends MultiPartMonster<MultiPartEntity<GolemEntity>> {
    public MultiPartEntity<GolemEntity> leftArm;
    public MultiPartEntity<GolemEntity> rightArm;

    public GolemEntity(EntityType<MultiPartMonster<?>> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected MultiPartEntity<GolemEntity>[] createParts() {
        this.leftArm = new MultiPartEntity<>(this, 0.8f, 2.1f, 1.2f, 0.4f, 0f);
        this.rightArm = new MultiPartEntity<>(this, 0.8f, 2.1f, -1.2f, 0.4f, 0f);
        return new MultiPartEntity[]{this.leftArm, this.rightArm};
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStrollGoal(this, 1f));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1f, true));
    }

    @Override
    public boolean hurtPart(MultiPartEntity<GolemEntity> part, DamageSource source, float damage) {
        if (part == rightArm) return hurt(source, 100000f);
        return super.hurtPart(part, source, damage);
    }

    @Override
    public void tick() {
        resetPartOffsets();
        super.tick();
    }
}
