package network.indexyz.minecraft.coolq;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Main.MOD_ID, name = Main.NAME, version = Main.VERSION,
        acceptedMinecraftVersions = "1.12.2", serverSideOnly = true)
public class Main {
    public static final String MOD_ID = "coolq-minecraft";
    public static final String NAME = "Coolq Minecraft";
    public static final String VERSION = "0.0.0";

    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Coolq Minecraft Loaded");
    }
}
