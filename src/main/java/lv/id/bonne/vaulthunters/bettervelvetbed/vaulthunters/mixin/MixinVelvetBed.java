//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.bettervelvetbed.vaulthunters.mixin;


import org.spongepowered.asm.mixin.Mixin;

import iskallia.vault.block.VelvetBed;
import net.minecraft.world.level.Level;


/**
 * This Mixin is used to modify {@link VelvetBed} class, so it could allow sleeping in Velvet Bed
 * at the end dimension.
 */
@Mixin(value = VelvetBed.class)
public abstract class MixinVelvetBed extends MixinBedBlock
{
    /**
     * This method is used to check if player is allowed to sleep in velvet bed in end dimension.
     * @param level level.
     * @return true if player is allowed to sleep in velvet bed in end dimension.
     */
    @Override
    protected boolean canSetSpawn(Level level)
    {
        return level.dimensionType().bedWorks() || level.dimension() == Level.END;
    }
}
