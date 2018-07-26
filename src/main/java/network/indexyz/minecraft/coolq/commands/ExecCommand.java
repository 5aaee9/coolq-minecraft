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
        for (long qq : Config.adminList) {
            if (qq == ctx.sendFrom) {
                String commandBody = args.stream().reduce("", (all, item) -> all + " " + item).trim();

                if (ExecCommand.commandSender == null) {
                    ExecCommand.commandSender = new CommandSender();
                }
                // Yes It's able to exec command
                MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

                server.getWorld(0).addScheduledTask(() ->
                    server.getCommandManager().executeCommand(
                        ExecCommand.commandSender, commandBody
                ));
                return;
            }
        }
        Req.sendToQQ("Permission denied.");
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
