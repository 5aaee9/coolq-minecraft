package network.indexyz.minecraft.coolq.commands;

import com.google.common.reflect.ClassPath;
import network.indexyz.minecraft.coolq.Main;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class Index {
    private static List<Class<? extends Command>> classList = null;

    public static void invokeCommand(String command, Context ctx) {
        String commandBody = command.substring(2);
        List<String> commandAndArgs = Arrays.stream(commandBody.split(" "))
                .collect(Collectors.toList());

        for (Class c : getCommandClass()) {
            try {
                Command anInstance = (Command) c.newInstance();
                if (anInstance.getPrefix().equals(commandAndArgs.get(0))) {
                    anInstance.process(commandAndArgs.subList(1, commandAndArgs.size()), ctx);
                }
            } catch (NoSuchMethodError |
                     IllegalAccessException |
                     InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Class<? extends Command>> getCommandClass() {
        if (Index.classList == null) {
            List<Class<? extends Command>> returnClassList = new ArrayList<>();
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
                    if (info.getName().startsWith(Command.class.getPackage().getName().replace("/", "."))) {
                        final Class<?> clazz = info.load();
                        if (Command.class.isAssignableFrom(clazz)) {
                            if (!Command.class.equals(clazz)) {
                                returnClassList.add((Class<? extends Command>)clazz);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.logger.info("loaded " + returnClassList.size() + " commands");
            Index.classList = returnClassList;
        }
        return Index.classList;
    }
}