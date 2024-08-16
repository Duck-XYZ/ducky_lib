package dev.callumherr.modding.ducky_lib.entity.api;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class MultiPartMonster<T extends MultiPartEntity<?>> extends Monster {
    public T[] parts; //Must be set in the constructor

    /**
     * Basic constructor for a multipart entity make sure to set the parts property value. You must set the parts array filled with all parts.
     * @param entityType Type of entity that this class corresponds to
     * @param level Game world
     * @see MultiPartEntity All parts should extend this class
     */
    protected MultiPartMonster(EntityType<? extends MultiPartMonster<?>> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Must be called after creating the parts array so that all the parts are assigned IDs
     */
    protected void registerParts() {
        this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1);
    }

    /**
     * Called when the hitbox of a part is hit
     * @param part The part that was hit
     * @param source The source of the damage
     * @param damage the amount of damage dealt
     * @return true if damage was taken, false otherwise
     */
    public boolean hurtPart(T part, DamageSource source, float damage) {
        return this.hurt(source, damage);
    }

    /**
     * Call in tick method to keep the parts at the correct offsets while the mob moves
     */
    public void resetPartOffsets() {
        for (T part : parts) {
            part.offsetFromParent();
        }
    }

    /**
     * Gives each part a unique ID to differentiate them.
     * @param id the id to assign the mob itself, parts are automatically assigned ids using this.
     */
    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.parts.length; i++)
            this.parts[i].setId(id + i + 1);
    }

    /**
     * Gets the full list of parts
     * @return array of parts for this mob
     */
    @Override
    public T @NotNull [] getParts() {
        return parts;
    }

    /**
     * Specifies that this mob is multipart
     * @return true if multipart, false otherwise.
     */
    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public void tick() {
        //if (this.parts.length <= 0) throw new RuntimeException("Can't have a MultiPartMob with no parts.");
        super.tick();
    }
}
