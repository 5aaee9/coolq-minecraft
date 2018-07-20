package network.indexyz.minecraft.coolq.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import network.indexyz.minecraft.coolq.utils.Req;

@Mod.EventBusSubscriber
public class PlayerEvents {
    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        new Thread(() -> {
            Req.sendToQQ(event.player.getDisplayNameString() + " joined server");
        }).start();
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        new Thread(() -> {
            Req.sendToQQ(event.player.getDisplayNameString() + " left server");
        }).start();
    }

    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            String message = event.getSource().getDeathMessage(event.getEntityLiving()).getUnformattedText();
            new Thread(() -> {
                Req.sendToQQ(String.format(message, ((EntityPlayer) event.getEntity()).getDisplayNameString()));
            }).start();
        }
    }
}
