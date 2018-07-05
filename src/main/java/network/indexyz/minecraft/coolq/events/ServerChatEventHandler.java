package network.indexyz.minecraft.coolq.events;

import net.minecraft.entity.player.EntityPlayerMP;
import network.indexyz.minecraft.coolq.utils.Req;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.IOException;

public class ServerChatEventHandler {
    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event) {
        EntityPlayerMP sender = event.getPlayer();
        new Thread(() -> {
            try {
                Req.sendToQQ(sender, event.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
