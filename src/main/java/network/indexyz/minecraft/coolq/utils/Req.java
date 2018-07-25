package network.indexyz.minecraft.coolq.utils;

import net.minecraft.entity.player.EntityPlayerMP;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class Req {
    private static Request.Builder getRequest() {
        Request.Builder builder = new Request.Builder();

        if (!(Config.accessToken.equals(""))) {
            builder = builder.addHeader("Authorization", "Token " + Config.accessToken);
        }

        return builder;
    }


    static String getUserNameById(long group, long userId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = getRequest()
                .url(Config.sendHost + "/get_group_member_info?group_id=" + group + "&user_id=" + userId)
                .build();
        Response response = client.newCall(request).execute();
        String ret = response.body().string();
        response.close();

        return ret;
    }

    static String getUsernameFromInfo(String infoBody) {
        JSONObject userInfo = new JSONObject(infoBody);

        if (userInfo.getInt("retcode") != 0) {
            return "";
        }

        String username = userInfo.getJSONObject("data").getString("card");
        if (username.equals("")) {
            username = userInfo.getJSONObject("data").getString("nickname");
        }

        return username;
    }

    public static void sendToQQ(EntityPlayerMP player, String message) {
        sendToQQ("[" + Chat.stripColor(player.getName()) + "]: " + message);
    }

    public static void sendToQQ(String message) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();

                JSONObject object = new JSONObject();
                object.put("group_id", Config.groupId);
                object.put("message", message);

                RequestBody requestBody = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"), object.toString());


                Request request = getRequest()
                        .url(Config.sendHost + "/send_group_msg")
                        .post(requestBody)
                        .build();

                client.newCall(request).execute().close();
            } catch (IOException error) {
                error.printStackTrace();
            }
        }).start();
    }
}
