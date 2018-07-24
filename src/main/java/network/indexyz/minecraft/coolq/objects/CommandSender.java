package network.indexyz.minecraft.coolq.objects;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import network.indexyz.minecraft.coolq.utils.Req;

import javax.annotation.Nullable;

public class CommandSender implements ICommandSender {
    @Override
    public String getName() {
        return "CoolQ-Minecraft-Bot";
    }

    @Override
    public boolean canUseCommand(int permLevel, String commandName) {
        return true;
    }

    @Override
    public World getEntityWorld() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    @Override
    public void sendMessage(ITextComponent component) {
        Req.sendToQQ(component.getUnformattedText());
    }
}
