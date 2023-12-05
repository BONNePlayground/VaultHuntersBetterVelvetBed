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
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;


/**
 * This Mixin is used to modify {@link Player} class, so it could allow set spawn in Velvet Bed in
 * the end dimension
 */
@Mixin(value = Player.class)
public class MixinPlayer
{
    /**
     * This method is used to allow set spawn in Velvet Bed in the end dimension.
     */
    @Inject(method = "findRespawnPositionAndUseSpawnBlock",
        at = @At(value = "INVOKE_ASSIGN",
            target = "net/minecraft/world/level/block/state/BlockState.getBlock()Lnet/minecraft/world/level/block/Block;",
            ordinal = 0),
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void findRespawnPositionAndUseSpawnBlock(ServerLevel p_36131_,
        BlockPos p_36132_,
        float p_36133_,
        boolean p_36134_,
        boolean p_36135_,
        CallbackInfoReturnable<Optional<Vec3>> cir,
        BlockState blockstate)
    {
        if (blockstate.is(ModBlocks.VELVET_BED))
        {
            cir.setReturnValue(BedBlock.findStandUpPosition(EntityType.PLAYER, p_36131_, p_36132_, p_36133_));
            cir.cancel();
        }
    }
}
