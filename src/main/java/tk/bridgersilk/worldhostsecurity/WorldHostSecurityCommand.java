package tk.bridgersilk.worldhostsecurity;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.DecimalFormat;

public class WorldHostSecurityCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("worldhostsecurity")) {
            sender.sendMessage(ChatColor.GOLD + "WorldHostSecurity Data ->");

            for (World world : Bukkit.getWorlds()) {
                int playerCount = world.getPlayers().size();
                int loadedChunks = world.getLoadedChunks().length;
                int entityCount = world.getEntities().size();
                double fileSizeGB = calculateWorldFolderSize(world) / (1024.0 * 1024.0 * 1024.0); // Convert bytes to GB

                DecimalFormat df = new DecimalFormat("0.00");
                String formattedFileSize = df.format(fileSizeGB);

                String playerMessage = (playerCount <= 10) ? ChatColor.GREEN + String.valueOf(playerCount) + " P" : ChatColor.RED + String.valueOf(playerCount) + " P";
                String chunkMessage = (loadedChunks <= 10000) ? ChatColor.GREEN + String.valueOf(loadedChunks) + " C" : ChatColor.RED + String.valueOf(loadedChunks) + " C";
                String entityMessage = (entityCount <= 500) ? ChatColor.GREEN + String.valueOf(entityCount) + " E" : ChatColor.RED + String.valueOf(entityCount) + " E";
                String fileSizeMessage = (fileSizeGB <= 1.0) ? ChatColor.GREEN + formattedFileSize + "GB F" : ChatColor.RED + formattedFileSize + "GB F";

                sender.sendMessage(ChatColor.LIGHT_PURPLE + "-> " +
                        ChatColor.GRAY + world.getName() + ChatColor.LIGHT_PURPLE + ": " +
                        playerMessage + ", " +
                        chunkMessage + ", " +
                        entityMessage + ", " +
                        fileSizeMessage);
            }
            return true;
        }
        return false;
    }

    private long calculateWorldFolderSize(World world) {
        File worldFolder = world.getWorldFolder();
        return getFolderSize(worldFolder);
    }

    private long getFolderSize(File folder) {
        long length = 0;
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += getFolderSize(file);
                }
            }
        }

        return length;
    }
}