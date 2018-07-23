package network.indexyz.minecraft.coolq.utils;

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

    private static String replaceAt(String origin) throws NotImplementedException {
        String regex = "\\[CQ:at,qq=(\\d*)\\]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(origin);

        try {
            while (m.find()) {
                String username = Req.getUsernameFromInfo(
                    Req.getUserNameById(Config.groupId, Long.valueOf(m.group(1)))
                );

                origin = m.replaceFirst("@" + username);
                m = p.matcher(origin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return origin;
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
                    String username = Req.getUsernameFromInfo(Req.getUserNameById(Config.groupId, userId));
                    if (username.equals("")) {
                        Main.logger.warn("get user info error, user: " + userId);
                        return;
                    }

                    String message = jsonObject.getString("raw_message");
                    message = Recv.replaceAt(message);
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
