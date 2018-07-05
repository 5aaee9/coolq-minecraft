package network.indexyz.minecraft.coolq.utils;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.json.JSONObject;

import java.io.IOException;

public class Recv {
    public static void parseRequestBody(JSONObject jsonObject) throws IOException {
        String type = jsonObject.getString("post_type");
        switch (type) {
            case "message": {
                if (jsonObject.getString("sub_type").equals("group")) {
                    if (jsonObject.getInt("group_id") == Config.groupId) {
                        return;
                    }
                    JSONObject userInfo = new JSONObject(Req.getUserNameById(Config.groupId, jsonObject.getInt("user_id")));
                    String username = userInfo.getString("card");
                    if (username == null) {
                        username = userInfo.getString("nickname");
                    }

                    // Get Real Chat Message
                    FMLCommonHandler.instance().getMinecraftServerInstance().sendMessage(
                            new TextComponentString("[" + username + "]: " + jsonObject.getString("raw_message"))
                    );
                }
                break;
            }
            default: {

            }
        }
    }
}
