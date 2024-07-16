package tk.bridgersilk.worldhostsecurity;

import org.bukkit.plugin.java.JavaPlugin;

public final class WorldHostSecurity extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("worldhostsecurity").setExecutor(new WorldHostSecurityCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
