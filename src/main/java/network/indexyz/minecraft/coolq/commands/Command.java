package network.indexyz.minecraft.coolq.commands;

import java.util.List;

public interface Command {
    public static String prefix = "";
    public static String name = "";
    public static void process(List<String> args) {};
}
