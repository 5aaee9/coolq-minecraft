package network.indexyz.minecraft.coolq.utils;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import network.indexyz.minecraft.coolq.Main;

public class Config {
    public static String sendHost = "http://127.0.0.1:5700";
    public static int httpStartAt = 1080;
    public static int groupId = 0;

    public static void init() {
        try {
            Main.configuration.load();

            Property sendHostProp = Main.configuration.get(Configuration.CATEGORY_GENERAL, "sendHost",
                    "http://127.0.0.1:5700", "Coolq Host with port");

            Property httpStartAtProp = Main.configuration.get(Configuration.CATEGORY_GENERAL, "httpStartAt",
                    1080, "Coolq Callback server start at this port");
            Property groupIdProp = Main.configuration.get(Configuration.CATEGORY_GENERAL, "groupId",
                    0, "QQ Group id");
            Config.sendHost = sendHostProp.getString();
            Config.httpStartAt = httpStartAtProp.getInt();
            Config.groupId = groupIdProp.getInt();
        } catch (Exception e) {
            // Not need handle here
        } finally {
            if (Main.configuration.hasChanged()) {
                Main.configuration.save();
            }
        }
    }
}
