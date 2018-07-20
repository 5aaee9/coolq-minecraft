package network.indexyz.minecraft.coolq.utils;

import net.minecraft.entity.player.EntityPlayerMP;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    public static void sendToQQ(EntityPlayerMP player, String message) {
        sendToQQ("[" + player.getName() + "]: " + message);
    }

    public static void sendToQQ(String message) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = getRequest()
                    .url(Config.sendHost + "/send_group_msg?group_id=" + Config.groupId + "&message=" + message)
                    .build();

            client.newCall(request).execute().close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
