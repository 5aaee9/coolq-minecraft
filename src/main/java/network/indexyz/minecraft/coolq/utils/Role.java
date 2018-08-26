package network.indexyz.minecraft.coolq.utils;

import org.json.JSONObject;

public class Role {
    public enum UserRole {
        OWNER,
        ADMIN,
        USER,
        UNKNOWN
    }

    private static UserRole getUserRole(JSONObject userInfo) {
        if (userInfo.getInt("retcode") != 0) {
            return UserRole.UNKNOWN;
        }

        String role = userInfo.getJSONObject("data").getString("role");
        switch (role) {
            case "owner": {
                return UserRole.OWNER;
            }
            case "admin": {
                return UserRole.ADMIN;
            }
            default: {
                return UserRole.USER;
            }
        }
    }


    public static boolean checkRole(long userId) {
        for (long qq : Config.adminList) {
            if (qq == userId) {
                return true;
            }
        }

        if (Config.groupAdminAsGameAdmin) {
            UserRole role = getUserRole(Req.getProfile(Config.groupId, userId));
            return role == UserRole.ADMIN || role == UserRole.OWNER;
        }

        return false;
    }
}
