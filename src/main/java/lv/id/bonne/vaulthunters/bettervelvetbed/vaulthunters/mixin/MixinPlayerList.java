//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.bettervelvetbed.vaulthunters.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.Optional;

import iskallia.vault.init.ModBlocks;
import lv.id.bonne.vaulthunters.bettervelvetbed.BetterVelvetBedMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.Vec3;


/**
 * This Mixin is used to modify {@link PlayerList} class, so it could punish player when he is
 * respawning at the velvet bed.
 */
@Mixin(value = PlayerList.class)
public abstract class MixinPlayerList
{
    /**
     * This method is used to punish player when he is respawning at the velvet bed.
     * @param p_11237_ the player who are respawned.
     * @param cir callback info.
     * @param blockpos position of the bed.
     * @param serverlevel the world of the bed.
     * @param serverlevel1 the world of the respawn.
     * @param serverplayer the new server player instance.
     */
    @Inject(method = "respawn", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void respawnWithSomeFun(ServerPlayer p_11237_,
        boolean p_11238_,
        CallbackInfoReturnable<ServerPlayer> cir,
        BlockPos blockpos,
        float f,
        boolean flag,
        ServerLevel serverlevel,
        Optional<Vec3> optional,
        ServerLevel serverlevel1,
        ServerPlayer serverplayer,
        boolean flag2,
        LevelData leveldata)
    {
        if ((!serverlevel.dimensionType().natural() || BetterVelvetBedMod.CONFIGURATION.isPunishEverywhere()) &&
            serverlevel.getBlockState(blockpos).is(ModBlocks.VELVET_BED) &&
            serverlevel1 == serverlevel &&
            serverlevel.getRandom().nextFloat() < BetterVelvetBedMod.CONFIGURATION.getPunishmentChance())
        {
            // This is our custom bed... now PUNISH COSMOVOLI
            BetterVelvetBedMod.CONFIGURATION.getPunishments().getRandom().ifPresent(punishment ->
                serverplayer.addEffect(
                    new MobEffectInstance(punishment.effect(),
                        punishment.duration() * 20,
                        punishment.amplifier())));
        }
    }
}
