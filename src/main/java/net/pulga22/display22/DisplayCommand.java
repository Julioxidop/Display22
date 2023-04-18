package net.pulga22.display22;

import co.aikar.taskchain.TaskChain;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class DisplayCommand implements CommandExecutor {

    Display22 plugin;

    public DisplayCommand(Display22 plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length != 2){
            sender.sendMessage("Invalid arguments.");
            sender.sendMessage("Usage: /display <TARGET> <ANIMATION>");
            return false;
        }

        //Get players to target
        List<Player> targets = new ArrayList<>();

        if (args[0].equals("@a")){
            for (Player p: Bukkit.getOnlinePlayers()) {
                targets.add(p);
            }
        } else if (args[0].equals("@s")) {
            targets.add((Player) sender);
        } else {
            targets.add((Player) Bukkit.getPlayer(args[0]));
        }

        //Get animation data
        Animation animation = null;
        Gson gson = new Gson();
        try (Reader reader = new FileReader(Display22.PATH + "\\" + args[1] + ".json")) {
            animation = gson.fromJson(reader, Animation.class);
        } catch (IOException e){
            e.printStackTrace();
        }

        if (animation != null){
            TaskChain taskChain = Display22.newChain();
            animation.chars.forEach(cchar -> {
                targets.forEach(player -> {
                    taskChain.delay(1).sync(() -> {
                        player.sendTitle(cchar, "", 0, 10, 0);
                    });
                });
            });
            taskChain.sync(TaskChain::abort).execute();
        }




        return true;
    }
}
