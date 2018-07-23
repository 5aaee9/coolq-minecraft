package network.indexyz.minecraft.coolq.utils;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import network.indexyz.minecraft.coolq.Main;
import network.indexyz.minecraft.coolq.commands.Context;
import network.indexyz.minecraft.coolq.commands.Index;
import org.json.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recv {
    private static String clearImage(String str) {
        String regex = "\\[CQ:image,[(\\s\\S)]*\\]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String replaceAt(String origin) throws NotImplementedException {
        String regex = "\\[CQ:at,qq=[(\\d)]{6,11}\\]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(origin);
        if (m.find()) {
            Main.logger.info(m.groupCount());
        } else {
            Main.logger.info("not found");
        }
        throw new NotImplementedException();
    }

    public static void parseRequestBody(JSONObject jsonObject) throws IOException {
        String type = jsonObject.getString("post_type");
        switch (type) {
            case "message": {
                if (jsonObject.getString("message_type").equals("group")) {
                    if (!(jsonObject.getInt("group_id") == Config.groupId)) {
                        return;
                    }
                    long userId = jsonObject.getLong("user_id");
                    JSONObject userInfo = new JSONObject(Req.getUserNameById(Config.groupId, userId));
                    if (userInfo.getInt("retcode") != 0) {
                        Main.logger.warn("get user info error, user: " + userId);
                        return;
                    }

                    String message = jsonObject.getString("raw_message");
                    message = Recv.clearImage(message);

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

                    String username = userInfo.getJSONObject("data").getString("card");
                    if (username.equals("")) {
                        username = userInfo.getJSONObject("data").getString("nickname");
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
