package network.indexyz.minecraft.coolq.utils;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import network.indexyz.minecraft.coolq.Main;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class Recv {
    public static void parseRequestBody(JSONObject jsonObject) throws IOException {
        String type = jsonObject.getString("post_type");
        switch (type) {
            case "message": {
                if (jsonObject.getString("message_type").equals("group")) {
                    if (!(jsonObject.getInt("group_id") == Config.groupId)) {
                        return;
                    }
                    int userId = jsonObject.getInt("user_id");
                    JSONObject userInfo = new JSONObject(Req.getUserNameById(Config.groupId, userId));
                    if (userInfo.getInt("retcode") != 0) {
                        Main.logger.warn("get user info error, user: " + userId);
                        return;
                    }

                    String username = userInfo.getJSONObject("data").getString("card");
                    if (username == null) {
                        username = userInfo.getJSONObject("data").getString("nickname");
                    }

                    ITextComponent message = new TextComponentString("[QQ][" + username + "]: " + jsonObject.getString("raw_message"));

                    Arrays.stream(DimensionManager.getWorlds())
                            .forEach(
                                    w -> w.addScheduledTask(() -> w.playerEntities
                                            .forEach(p -> p.sendMessage(message)))
                            );
                }
                break;
            }
            default: {

            }
        }
    }
}
