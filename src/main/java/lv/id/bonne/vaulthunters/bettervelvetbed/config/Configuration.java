//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.bettervelvetbed.config;


import java.util.Arrays;
import java.util.List;

import iskallia.vault.core.util.WeightedList;
import lv.id.bonne.vaulthunters.bettervelvetbed.BetterVelvetBedMod;
import lv.id.bonne.vaulthunters.bettervelvetbed.util.EffectRecord;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;


/**
 * The configuration handling class. Holds all the config values.
 */
public class Configuration
{
    /**
     * The constructor for the config.
     */
    public Configuration()
    {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("Punishments");

        this.punishmentChance = builder.
            comment("The chance of the effect being applied on player when player respawns on velvet bed.").
            comment("Default value = 0.75 which means 75%").
            defineInRange("punishmentChance", 0.75, 0.0, 1.0);
        this.punishments = builder.
            comment("This list holds the effects that can be applied on player for respawning on velvet bed.").
            comment("Format: <effect name>:<duration in s>:<amplifier>:<weight>").
            defineList("punishments",
                Arrays.asList(
                    MobEffects.BLINDNESS.getRegistryName().toString() + ":15:3:3",
                    MobEffects.CONFUSION.getRegistryName().toString() + ":10:4:1",
                    MobEffects.DIG_SLOWDOWN.getRegistryName().toString() + ":60:3:3",
                    MobEffects.HUNGER.getRegistryName().toString() + ":60:1:5",
                    MobEffects.POISON.getRegistryName().toString() + ":10:2:5",
                    MobEffects.WEAKNESS.getRegistryName().toString() + ":120:1:10",
                    MobEffects.MOVEMENT_SLOWDOWN.getRegistryName().toString() + ":120:1:10",
                    MobEffects.LEVITATION.getRegistryName().toString() + ":15:2:1"
                ),
                o -> o instanceof String);
        this.punishEverywhere = builder.
            comment("If true, then punishments will be applied in always when respawning on velvet beds.").
            comment("If false, then punishments will be applied only in end dimension.").
            define("punishEverywhere", true);

        builder.pop();
        builder.push("Rewards");

        this.rewardChance = builder.
            comment("The chance of the effect being applied on player when he sleeps in velvet bed.").
            comment("Default value = 0.1 which means 10%").
            defineInRange("rewardChance", 0.1, 0.0, 1.0);
        this.rewards = builder.
            comment("This list holds the effects that can be applied on player for sleeping in velvet bed.").
            comment("Format: <effect name>:<duration>:<amplifier>:<weight>").
            defineList("rewards",
                Arrays.asList(
                    MobEffects.REGENERATION.getRegistryName().toString() + ":30:1:5",
                    MobEffects.REGENERATION.getRegistryName().toString() + ":10:3:1",
                    MobEffects.SATURATION.getRegistryName().toString() + ":120:1:10",
                    MobEffects.SATURATION.getRegistryName().toString() + ":600:1:1",
                    MobEffects.SATURATION.getRegistryName().toString() + ":30:3:1",
                    MobEffects.NIGHT_VISION.getRegistryName().toString() + ":600:1:3",
                    MobEffects.MOVEMENT_SPEED.getRegistryName().toString() + ":120:1:5",
                    MobEffects.DIG_SPEED.getRegistryName().toString() + ":120:1:5",
                    MobEffects.ABSORPTION.getRegistryName().toString() + ":120:10:1"
                ),
                o -> o instanceof String);
        this.rewardEverywhere = builder.
            comment("If true, then rewards will be applied in always when sleeping in velvet beds.").
            comment("If false, then rewards will be applied only in end dimension.").
            define("rewardEverywhere", true);
        builder.pop();

        Configuration.GENERAL_SPEC = builder.build();
    }


    /**
     * Returns punishment effect records.
     * @return punishment effect records
     */
    public WeightedList<EffectRecord> getPunishments()
    {
        if (PUNISHMENT_LIST.isEmpty())
        {
            this.punishments.get().forEach(text ->
            {
                String[] split = text.split(":");

                try
                {
                    if (split.length == 2)
                    {
                        PUNISHMENT_LIST.put(
                            new EffectRecord(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(split[0], split[1])),
                                10,
                                1),
                            1.0);
                    }
                    else if (split.length == 3)
                    {
                        PUNISHMENT_LIST.put(
                            new EffectRecord(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(split[0], split[1])),
                                10,
                                1),
                            Double.parseDouble(split[2]));
                    }
                    else if (split.length == 4)
                    {
                        PUNISHMENT_LIST.put(
                            new EffectRecord(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(split[0], split[1])),
                                Integer.parseInt(split[2]),
                                Integer.parseInt(split[3])),
                            1.0);
                    }
                    else if (split.length == 5)
                    {
                        PUNISHMENT_LIST.put(
                            new EffectRecord(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(split[0], split[1])),
                                Integer.parseInt(split[2]),
                                Integer.parseInt(split[3])),
                            Double.parseDouble(split[4]));
                    }
                }
                catch (Exception e)
                {
                    BetterVelvetBedMod.LOGGER.error("Error parsing punishment: " + text);
                }
            });
        }

        return PUNISHMENT_LIST;
    }


    /**
     * Returns rewards effect records.
     * @return rewards effect records
     */
    public WeightedList<EffectRecord> getRewards()
    {
        if (REWARD_LIST.isEmpty())
        {
            this.rewards.get().forEach(text ->
            {
                String[] split = text.split(":");

                try
                {
                    if (split.length == 2)
                    {
                        REWARD_LIST.put(
                            new EffectRecord(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(split[0], split[1])),
                                10,
                                1),
                            1.0);
                    }
                    else if (split.length == 3)
                    {
                        REWARD_LIST.put(
                            new EffectRecord(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(split[0], split[1])),
                                10,
                                1),
                            Double.parseDouble(split[2]));
                    }
                    else if (split.length == 4)
                    {
                        REWARD_LIST.put(
                            new EffectRecord(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(split[0], split[1])),
                                Integer.parseInt(split[2]),
                                Integer.parseInt(split[3])),
                            1.0);
                    }
                    else if (split.length == 5)
                    {
                        REWARD_LIST.put(
                            new EffectRecord(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(split[0], split[1])),
                                Integer.parseInt(split[2]),
                                Integer.parseInt(split[3])),
                            Double.parseDouble(split[4]));
                    }
                }
                catch (Exception e)
                {
                    BetterVelvetBedMod.LOGGER.error("Error parsing punishment: " + text);
                }
            });
        }

        return REWARD_LIST;
    }



    /**
     * Gets punishment chance.
     * @return the punishment chance
     */
    public double getPunishmentChance()
    {
        return this.punishmentChance.get();
    }


    /**
     * Gets reward chance.
     * @return the reward chance
     */
    public double getRewardChance()
    {
        return this.rewardChance.get();
    }


    /**
     * Gets punish everywhere.
     * @return the punish everywhere
     */
    public boolean isPunishEverywhere()
    {
        return this.punishEverywhere.get();
    }


    /**
     * Gets reward everywhere.
     * @return the reward everywhere
     */
    public boolean isRewardEverywhere()
    {
        return this.rewardEverywhere.get();
    }


    /**
     * Clears punishments.
     */
    public static void resetStorage()
    {
        PUNISHMENT_LIST.clear();
        REWARD_LIST.clear();
    }


// ---------------------------------------------------------------------
// Section: Variables
// ---------------------------------------------------------------------

    /**
     * The list of punishments.
     */
    private static final WeightedList<EffectRecord> PUNISHMENT_LIST = new WeightedList<>();

    /**
     * The list of rewards.
     */
    private static final WeightedList<EffectRecord> REWARD_LIST = new WeightedList<>();

    /**
     * The config value for the list of configs to sync.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> punishments;

    /**
     * The config value for the list of configs to sync.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> rewards;

    /**
     * The config value for the chance of the effect being applied on player for respawning at bed.
     */
    private final ForgeConfigSpec.ConfigValue<Double> punishmentChance;

    /**
     * The config value for the chance of the effect being applied on player for sleeping in bed.
     */
    private final ForgeConfigSpec.ConfigValue<Double> rewardChance;

    /**
     * The config value for effects being applied in all dimensions.
     */
    private final ForgeConfigSpec.ConfigValue<Boolean> punishEverywhere;

    /**
     * The config value for effects being applied in all dimensions.
     */
    private final ForgeConfigSpec.ConfigValue<Boolean> rewardEverywhere;

    /**
     * The general config spec.
     */
    public static ForgeConfigSpec GENERAL_SPEC;
}
