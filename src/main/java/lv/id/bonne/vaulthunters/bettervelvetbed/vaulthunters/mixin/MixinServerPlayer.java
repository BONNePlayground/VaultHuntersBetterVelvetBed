//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.bettervelvetbed.vaulthunters.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import iskallia.vault.init.ModBlocks;
import lv.id.bonne.vaulthunters.bettervelvetbed.BetterVelvetBedMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;


/**
 * This Mixin is used to modify {@link ServerPlayer} class, so it could allow sleeping in Velvet Bed
 * at the end dimension and add a reward when sleeping was finished.
 */
@Mixin(value = ServerPlayer.class)
public abstract class MixinServerPlayer
{
    /**
     * This method is used to get level.
     * @return level.
     */
    @Shadow
    public abstract ServerLevel getLevel();


    /**
     * This method is used to check if player is allowed to sleep in velvet bed in end dimension.
     * @param instance dimension type.
     * @param pos position of the bed.
     * @return true if player is allowed to sleep in velvet bed in any dimension.
     */
    @Redirect(method = "startSleepInBed",
        at = @At(value = "INVOKE", target = "net/minecraft/world/level/dimension/DimensionType.natural()Z"))
    private boolean replaceSpawn(DimensionType instance, BlockPos pos)
    {
        return instance.natural() || this.getLevel().getBlockState(pos).is(ModBlocks.VELVET_BED);
    }


    /**
     * This method is used to add reward when player is finished sleeping in velvet bed.
     * @param p_9166_ boolean indicates if player cancelled sleeping.
     * @param ci callback info.
     */
    @Inject(method = "stopSleepInBed", at = @At(value = "HEAD"))
    private void stopSleepingInBed(boolean p_9165_, boolean p_9166_, CallbackInfo ci)
    {
        if (!p_9165_ && !p_9166_)
        {
            if (((ServerPlayer) (Object) this).getFeetBlockState().is(ModBlocks.VELVET_BED) &&
                (BetterVelvetBedMod.CONFIGURATION.isRewardEverywhere() || this.getLevel().dimension() == Level.END))
            {
                // This is our custom bed... now REWARD COSMOVOLI
                BetterVelvetBedMod.CONFIGURATION.getRewards().getRandom().ifPresent(reward ->
                    ((ServerPlayer) (Object) this).addEffect(
                        new MobEffectInstance(reward.effect(),
                            reward.duration() * 20,
                            reward.amplifier())));
            }
        }
    }
}
