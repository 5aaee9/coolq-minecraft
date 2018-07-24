package network.indexyz.minecraft.coolq.commands;

import java.util.List;

public interface Command {
    public void process(List<String> args, Context ctx);
    public String getPrefix();
    public String getName();
}
