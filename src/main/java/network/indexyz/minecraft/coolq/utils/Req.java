package network.indexyz.minecraft.coolq.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

class Req {
    private static OkHttpClient client = new OkHttpClient();

    static String getUserNameById(int group, int userId) throws IOException {

        Request request = new Request.Builder()
                .url(Config.sendHost + "/get_group_member_info?group_id=" + group + "&user_id=" + userId)
                .build();

        return client.newCall(request).execute().body().string();
    }
}
