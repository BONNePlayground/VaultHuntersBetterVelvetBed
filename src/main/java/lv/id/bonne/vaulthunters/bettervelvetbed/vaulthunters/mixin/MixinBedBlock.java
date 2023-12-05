//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.bettervelvetbed.vaulthunters.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;


/**
 * This Mixin is used to modify {@link BedBlock} class, so it could allow overwriting canSetSpawn
 * method in {@link MixinVelvetBed} class.
 */
@Mixin(value = BedBlock.class)
public abstract class MixinBedBlock
{
    /**
     * This method is used to redirect canSetSpawn method to {@link MixinVelvetBed} class.
     * @param p_49489_ level.
     */
    @Redirect(method = "use",
        at = @At(value = "INVOKE", target = "net/minecraft/world/level/block/BedBlock.canSetSpawn(Lnet/minecraft/world/level/Level;)Z"))
    protected boolean canSetSpawn(Level p_49489_)
    {
        return p_49489_.dimensionType().bedWorks();
    }
}
