package network.indexyz.minecraft.coolq.http;

import fi.iki.elonen.NanoHTTPD;
import network.indexyz.minecraft.coolq.Main;
import network.indexyz.minecraft.coolq.utils.Config;
import network.indexyz.minecraft.coolq.utils.Recv;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Server extends NanoHTTPD {
    public Server() {
        super(Config.httpStartAt);
    }

    public void startService() {
        new Thread(() -> {
            try {
                Main.logger.info("Callback server start in :" + Config.httpStartAt);
                Server.super.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();

        if (!Method.POST.equals(method)) {
            return newFixedLengthResponse(Response.Status.METHOD_NOT_ALLOWED, MIME_PLAINTEXT, "POST plz.");
        }
        Map<String, String> files = new HashMap<String, String>();

        try {
            session.parseBody(files);
        } catch (IOException ioe) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT,
                    "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
        } catch (ResponseException re) {
            return newFixedLengthResponse(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
        }


        String postBody = files.get("postData");
        Main.logger.info(postBody);
        JSONObject obj = new JSONObject(postBody);
        try {
            Recv.parseRequestBody(obj);
        } catch (IOException e) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT,
                    "SERVER INTERNAL ERROR: IOException: " + e.getMessage());
        }
        return newFixedLengthResponse("ojbk");
    }
}
