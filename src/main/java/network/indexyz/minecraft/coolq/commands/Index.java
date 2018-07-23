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
    private static List<Class> classList = null;

    public static void invokeCommand(String command) {
        String commandBody = command.substring(2);
        List<String> commandAndArgs = Arrays.stream(commandBody.split(" "))
                .collect(Collectors.toList());

        for (Class c : getCommandClass()) {
            try {
                Field field = c.getDeclaredField("prefix");
                field.setAccessible(true);
                String prefix = (String) field.get(c);
                Object anInstance = c.newInstance();
                if (prefix.equals(commandAndArgs.get(0))) {
                    for (Method method : c.getMethods()) {
                        if (method.getName().equals("process")) {
                            method.invoke(anInstance, commandAndArgs.subList(1, commandAndArgs.size()));
                        }
                    }
                }
            } catch (NoSuchFieldException |
                     NoSuchMethodError |
                     IllegalAccessException |
                     InvocationTargetException |
                     InstantiationException e) {
                e.printStackTrace();
            }
        }
    }


    private static List<Class> getCommandClass() {
        List<Class> returnClassList = new ArrayList<>();

        if (Index.classList == null) {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
                    if (info.getName().startsWith(Command.class.getPackage().getName().replace("/", "."))) {
                        final Class<?> clazz = info.load();
                        if (Command.class.isAssignableFrom(clazz)) {
                            if (!Command.class.equals(clazz)) {
                                returnClassList.add(clazz);
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