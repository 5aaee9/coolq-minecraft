package network.indexyz.minecraft.coolq;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import network.indexyz.minecraft.coolq.http.Server;
import network.indexyz.minecraft.coolq.utils.Config;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


@Mod(modid = Main.MOD_ID, name = Main.NAME, version = Main.VERSION,
        acceptedMinecraftVersions = "1.12.2", serverSideOnly = true)
public class Main {
    public static final String MOD_ID = "coolq-minecraft";
    public static final String NAME = "Coolq Minecraft";
    public static final String VERSION = "0.0.0";

    public static Configuration configuration;
    public static Logger logger;

    private Server server;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        Main.configuration = new Configuration(event.getSuggestedConfigurationFile());
        Config.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        server = new Server();
        server.startService();
    }
}
