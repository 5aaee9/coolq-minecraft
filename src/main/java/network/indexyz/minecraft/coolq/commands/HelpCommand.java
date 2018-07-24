package network.indexyz.minecraft.coolq.commands;

import network.indexyz.minecraft.coolq.utils.Req;

import java.util.List;

public class HelpCommand implements Command {
    @Override
    public void process(List<String> args, Context ctx) {
        List<Class<? extends Command>> classes = Index.getCommandClass();
        StringBuilder result = new StringBuilder();

        for (Class<? extends Command> clazz : classes) {
            try {
                Command instance = (Command) clazz.newInstance();
                result.append("!!").append(instance.getPrefix()).append(": ").append(instance.getName()).append("\n");
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // remove last \n char
        result.deleteCharAt(result.length() - 1);

        Req.sendToQQ(result.toString());
    }

    @Override
    public String getPrefix() {
        return "help";
    }

    @Override
    public String getName() {
        return "Display help for commands";
    }
}
