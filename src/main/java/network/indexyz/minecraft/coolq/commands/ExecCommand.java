package network.indexyz.minecraft.coolq.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import network.indexyz.minecraft.coolq.objects.CommandSender;
import network.indexyz.minecraft.coolq.utils.Config;
import network.indexyz.minecraft.coolq.utils.Req;

import java.util.List;

public class ExecCommand implements Command {
    private static CommandSender commandSender = null;

    @Override
    public void process(List<String> args, Context ctx) {
        String commandBody = args.stream().reduce("", (all, item) -> all + " " + item);

        for (long qq : Config.adminList) {
            if (qq == ctx.sendFrom) {
                if (ExecCommand.commandSender == null) {
                    ExecCommand.commandSender = new CommandSender();
                }
                // Yes It's able to exec command
                MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                server.getCommandManager().executeCommand(
                    ExecCommand.commandSender, commandBody
                );
                Req.sendToQQ("Command Exceed");
            }
        }
    }

    @Override
    public String getPrefix() {
        return "exec";
    }

    @Override
    public String getName() {
        return "Exec server command, admin only";
    }
}
