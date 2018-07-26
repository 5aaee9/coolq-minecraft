package network.indexyz.minecraft.coolq.utils;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import network.indexyz.minecraft.coolq.Main;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Config {
    public static String sendHost = "http://127.0.0.1:5700";
    public static int httpStartAt = 1080;
    public static long groupId = 0;
    public static String accessToken = "";
    public static String signature = "";
    public static long blacklist[] = null;
    public static long adminList[] = null;
    public static int messageLimit = -1;

    private static long[] convStringListToLong(String list[]) {
        return Arrays.stream(list)
                .filter(i -> i.length() > 0)
                .mapToLong(Long::valueOf)
                .toArray();
    }

    public static void init() {
        try {
            Main.configuration.load();

            Main.configuration.getCategory("coolq");

            Property sendHostProp = Main.configuration.get("coolq", "sendHost",
                    "http://127.0.0.1:5700", "Coolq Host with port");

            Property httpStartAtProp = Main.configuration.get("coolq", "httpStartAt",
                    1080, "Coolq Callback server start at this port");

            Property groupIdProp = Main.configuration.get("coolq", "groupId",
                    0, "QQ Group id");

            Property accessTokenProp = Main.configuration.get("coolq", "accessToken",
                    "", "Coolq HTTP Access Token");

            Property signatureProp = Main.configuration.get("coolq", "signature",
                    "", "Coolq HTTP Signature");

            Property blacklistProp = Main.configuration.get(Configuration.CATEGORY_GENERAL, "blacklist",
                    (new String[]{ }), "Will ignore message from this id");

            Property adminListProp = Main.configuration.get(Configuration.CATEGORY_GENERAL, "adminList",
                    (new String[]{ }), "Bot admin QQ list, allow exec command as server");

            Property messageLimitProp = Main.configuration.get(Configuration.CATEGORY_GENERAL, "messageLimit",
                    -1, "Will ignore message if longer than this value (-1 for unlimited)");


            Config.sendHost = sendHostProp.getString();
            Config.httpStartAt = httpStartAtProp.getInt();
            Config.groupId = groupIdProp.getLong();
            Config.accessToken = accessTokenProp.getString();
            Config.signature = signatureProp.getString();
            Config.blacklist = convStringListToLong(blacklistProp.getStringList());
            Config.adminList = convStringListToLong(adminListProp.getStringList());
            Config.messageLimit = messageLimitProp.getInt();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Main.configuration.hasChanged()) {
                Main.configuration.save();
            }
        }
    }
}
