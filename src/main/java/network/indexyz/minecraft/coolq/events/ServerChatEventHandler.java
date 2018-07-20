package network.indexyz.minecraft.coolq.events;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import network.indexyz.minecraft.coolq.utils.Req;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ServerChatEventHandler {
    @SubscribeEvent
    public static void onServerChatEvent(ServerChatEvent event) {
        EntityPlayerMP sender = event.getPlayer();
        new Thread(() -> {
            Req.sendToQQ(sender, event.getMessage());
        }).start();
    }
}
