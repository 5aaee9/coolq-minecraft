package network.indexyz.minecraft.coolq.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import network.indexyz.minecraft.coolq.Main;

public class Chat {
    public static void addMessageToChat(ITextComponent chatComponent) {
        try {
            MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance().getServer();
            if (minecraftServer != null) {
                if (!minecraftServer.isSinglePlayer()) {
                    minecraftServer.getPlayerList().sendMessage(chatComponent);
                } else {
                    if (Minecraft.getMinecraft().player != null) {
                        Minecraft.getMinecraft().player.sendMessage(chatComponent);
                    }
                }
            }
        } catch (NullPointerException npe) {
            Main.logger.error("Utils...getMinecraft() threw NullPointerException", npe);
        }
    }
}
