package lv.id.bonne.vaulthunters.bettervelvetbed;


import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import lv.id.bonne.vaulthunters.bettervelvetbed.config.Configuration;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


/**
 * The main class for Vault Hunters Sync Server Config mod.
 */
@Mod(BetterVelvetBedMod.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BetterVelvetBedMod.MOD_ID)
public class BetterVelvetBedMod
{
    /**
     * The main class initialization.
     */
    public BetterVelvetBedMod()
    {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        BetterVelvetBedMod.CONFIGURATION = new Configuration();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,
            Configuration.GENERAL_SPEC,
            "vault_hunters_better_velvet_bed.toml");
    }


    /**
     * Adds config to reload listener.
     * @param reloading the reloading event.
     */
    @SubscribeEvent
    public static void onConfigChanged(ModConfigEvent.Reloading reloading)
    {
        if (reloading.getConfig().getType() == ModConfig.Type.SERVER)
        {
            Configuration.resetStorage();
        }
    }


    /**
     * The main configuration file.
     */
    public static Configuration CONFIGURATION;

    /**
     * The logger for this mod.
     */
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * The MOD_ID
     */
    public static final String MOD_ID = "vault_hunters_better_velvet_bed";
}
