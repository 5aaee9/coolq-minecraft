package network.indexyz.minecraft.coolq.commands;

import net.minecraftforge.fml.common.FMLCommonHandler;
import network.indexyz.minecraft.coolq.utils.Req;

import java.util.Arrays;
import java.util.List;

public class TpsCommand implements Command {
    private static long mean(long[] values) {
         Long sum = Arrays.stream(values)
                .reduce(0L, (total, item) -> total + item);

        return sum / values.length;
    }

    @Override
    public void process(List<String> args, Context ctx) {
        double meanTickTime = mean(FMLCommonHandler.instance().getMinecraftServerInstance().tickTimeArray) * 1.0E-6D;
        double meanTPS = Math.min(1000.0/meanTickTime, 20);

        String outPut = String.format("Overall TPS: %.2f", meanTPS);
        Req.sendToQQ(outPut);
    }

    @Override
    public String getPrefix() {
        return "tps";
    }

    @Override
    public String getName() {
        return "Get server tick-pre-second";
    }
}
