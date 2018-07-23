package network.indexyz.minecraft.coolq.commands;

import java.util.List;

public interface Command {
    public static String prefix = "";
    public static String name = "";
    public void process(List<String> args, Context ctx);
}
