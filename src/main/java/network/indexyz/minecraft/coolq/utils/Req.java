package network.indexyz.minecraft.coolq.utils;

import net.minecraft.entity.player.EntityPlayerMP;
import network.indexyz.minecraft.coolq.Main;
import okhttp3.*;
import org.json.JSONException;
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

    private static final JSONObject errorObject = new JSONObject("{\"retcode\": 1}");

    public static JSONObject getUserNameById(long group, long userId) {
        OkHttpClient client = new OkHttpClient();
        Request request = getRequest()
                .url(Config.sendHost + "/get_group_member_info?group_id=" + group + "&user_id=" + userId)
                .build();


        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException err) {
            err.printStackTrace();
            return errorObject;
        }

        if (response.body() == null) {
            return errorObject;
        }


        try {
            return new JSONObject(response.body().string());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return errorObject;
        } finally {
            response.close();
        }
    }

    static String getUsernameFromInfo(JSONObject userInfo) {
        if (userInfo.getNumber("retcode").intValue() != 0) {
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
