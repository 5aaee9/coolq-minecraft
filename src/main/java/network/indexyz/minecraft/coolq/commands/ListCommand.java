package network.indexyz.minecraft.coolq.commands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import network.indexyz.minecraft.coolq.utils.Req;

import java.util.List;

public class ListCommand implements Command {
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

    @Override
    public String getPrefix() {
        return "list";
    }

    @Override
    public String getName() {
        return "List users in server";
    }
}
