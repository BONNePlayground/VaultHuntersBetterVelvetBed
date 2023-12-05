//
// Created by BONNe
// Copyright - 2023
//

package lv.id.bonne.vaulthunters.bettervelvetbed.util;


import net.minecraft.world.effect.MobEffect;


/**
 * This class is used to store effect data.
 * @param effect Effect that should be applied
 * @param duration duration of effect in seconds
 * @param amplifier amplifier of the effect
 */
public record EffectRecord(MobEffect effect, int duration, int amplifier) {}
