package network.indexyz.minecraft.coolq.commands;

import network.indexyz.minecraft.coolq.utils.Req;

import java.lang.reflect.Field;
import java.util.List;

public class HelpCommand implements Command {
    public static String prefix = "help";
    public static String name = "Display help for commands";

    @Override
    public void process(List<String> args, Context ctx) {
        List<Class<? extends Command>> classes = Index.getCommandClass();
        StringBuilder result = new StringBuilder();

        for (Class<? extends Command> clazz : classes) {
            try {
                Field prefixField = clazz.getDeclaredField("prefix");
                Field nameField = clazz.getDeclaredField("name");
                prefixField.setAccessible(true);
                nameField.setAccessible(true);
                String commandPrefix = (String) prefixField.get(clazz);
                String commandName = (String) nameField.get(clazz);
                result.append("!!").append(commandPrefix).append(": ").append(commandName).append("\n");
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // remove last \n char
        result.deleteCharAt(result.length() - 1);

        Req.sendToQQ(result.toString());
    }
}
