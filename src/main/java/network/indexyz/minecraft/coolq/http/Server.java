package network.indexyz.minecraft.coolq.http;

import fi.iki.elonen.NanoHTTPD;
import network.indexyz.minecraft.coolq.Main;
import network.indexyz.minecraft.coolq.utils.Config;
import network.indexyz.minecraft.coolq.utils.Recv;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
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
        Map<String, String> headers = session.getHeaders();

        if (!Method.POST.equals(method)) {
            return newFixedLengthResponse(Response.Status.METHOD_NOT_ALLOWED, MIME_PLAINTEXT, "POST plz.");
        }
        Map<String, String> files = new HashMap<>();

        try {
            session.parseBody(files);
        } catch (IOException ioe) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT,
                    "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
        } catch (ResponseException re) {
            return newFixedLengthResponse(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
        }

        String postBody = files.get("postData");

        if (!Config.signature.equals("")) {
            // Need verify body
            String signature = HmacUtils.hmacSha1Hex(Config.signature, postBody);

            if (!headers.containsKey("x-signature")) {
                JSONObject obj = new JSONObject();
                obj.put("status", "error");
                obj.put("message", "Not found x-signature");
                return newFixedLengthResponse(Response.Status.FORBIDDEN,
                        "application/json", obj.toString());
            }

            String serverPost = headers.get("x-signature").substring(5);
            if (!serverPost.equals(signature)) {
                Main.logger.warn("signature verify error");
                Main.logger.warn("get: " + serverPost);
                Main.logger.warn("require: " + signature);
                JSONObject obj = new JSONObject();
                obj.put("status", "error");
                obj.put("message", "Signature verify error");
                return newFixedLengthResponse(Response.Status.UNAUTHORIZED,
                        "application/json", obj.toString());
            }
        }

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
