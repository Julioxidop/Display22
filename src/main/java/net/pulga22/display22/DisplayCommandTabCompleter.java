package net.pulga22.display22;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DisplayCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        if (args.length == 1){
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
            Bukkit.getOnlinePlayers().toArray(players);
            for (Player p: players) {
                playerNames.add(p.getName());
            }
            playerNames.add("@a");
            playerNames.add("@s");
            return playerNames;
        } else if (args.length == 2) {
            return Display22.animationNames;
        }
        return null;
    }

}
