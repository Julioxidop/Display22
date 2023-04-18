package net.pulga22.display22;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class Display22 extends JavaPlugin {

    public static String PATH;

    public static List<File> animations = new ArrayList<>();
    public static List<String> animationNames = new ArrayList<>();
    private static TaskChainFactory taskChainFactory;

    @Override
    public void onEnable() {
        // Get animations
        taskChainFactory = BukkitTaskChainFactory.create(this);
        PATH = this.getDataFolder().getAbsolutePath();
        final File dataFolder = new File(PATH);
        getAnimations(dataFolder);

        getCommand("display").setExecutor(new DisplayCommand(this));
        getCommand("display").setTabCompleter(new DisplayCommandTabCompleter());

    }

    public void getAnimations(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                animations.add(fileEntry);
                animationNames.add(fileEntry.getName().replace(".json",""));
                System.out.println("Animation Founded: " + fileEntry.getName());
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static TaskChain newChain() {
        return taskChainFactory.newChain();
    }


}
