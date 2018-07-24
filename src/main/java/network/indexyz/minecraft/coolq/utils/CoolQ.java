package network.indexyz.minecraft.coolq.utils;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CoolQ {
    static String clearImage(String str) {
        String regex = "\\[CQ:image,[(\\s\\S)]*\\]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    static String replaceAt(String origin) {
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

    static String replaceCharset(String origin) {
        return origin.replace("&#91;", "[").replace("&#93;", "]");
    }
}
