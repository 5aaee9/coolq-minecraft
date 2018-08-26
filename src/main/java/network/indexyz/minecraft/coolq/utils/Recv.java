package network.indexyz.minecraft.coolq.utils;

import net.minecraft.util.text.TextComponentString;
import network.indexyz.minecraft.coolq.Main;
import network.indexyz.minecraft.coolq.commands.Context;
import network.indexyz.minecraft.coolq.commands.Index;
import org.json.JSONObject;


public class Recv {
    public static void parseRequestBody(JSONObject jsonObject) {
        String type = jsonObject.getString("post_type");
        switch (type) {
            case "message": {
                if (jsonObject.getString("message_type").equals("group")) {
                    if (!(jsonObject.getInt("group_id") == Config.groupId)) {
                        return;
                    }
                    long userId = jsonObject.getLong("user_id");
                    String username = Req.getUsernameFromInfo(Req.getProfile(Config.groupId, userId));
                    if (username.equals("")) {
                        Main.logger.warn("get user info error, user: " + userId);
                        return;
                    }

                    String message = jsonObject.getString("raw_message");
                    message = CoolQ.replaceAt(message);
                    message = CoolQ.clearImage(message);
                    message = CoolQ.replaceCharset(message);

                    // Image only message
                    if (message.length() == 0) {
                        return;
                    }

                    for (long black: Config.blacklist) {
                        if (black == userId) {
                            return;
                        }
                    }

                    if (Config.messageLimit != -1 && Config.messageLimit < message.length()) {
                        return;
                    }


                    if (message.startsWith("!!")) {
                        Context ctx = new Context();
                        ctx.sendFrom = userId;
                        Index.invokeCommand(message, ctx);
                        return;
                    }

                    Chat.addMessageToChat(new TextComponentString("[QQ][" + username + "]: " + message));
                }
                break;
            }
            default: {

            }
        }
    }
}
