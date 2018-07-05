package network.indexyz.minecraft.coolq.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

public class Req {
    static OkHttpClient client = new OkHttpClient();

    public static String getUserNameById(int group, int userId) throws IOException {

        Request request = new Request.Builder()
                .url(Config.sendHost + "/get_group_member_info?group_id=" + group + "&user_id=" + userId)
                .build();

        return client.newCall(request).execute().body().string();
    }
}
