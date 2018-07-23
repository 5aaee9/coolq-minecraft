package network.indexyz.minecraft.coolq.commands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import network.indexyz.minecraft.coolq.Main;
import network.indexyz.minecraft.coolq.utils.Req;

import java.util.List;

public class ListCommand implements Command {
    public static String prefix = "list";
    public static String name = "List users in server";

    @Override
    public void process(List<String> args, Context ctx) {

        List<EntityPlayerMP> users = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
        String userList = users.stream()
            .map(EntityPlayer::getDisplayNameString)
            .reduce("", (listString, user) -> {
                if (listString.length() == 0) {
                    listString += user;
                } else {
                    listString += (", " + user);
                }
                return listString;
            });

        Req.sendToQQ("Online user count: " + users.size());
        if (users.size() > 0) {
            Req.sendToQQ("User list: " + userList);
        }
    }
}
