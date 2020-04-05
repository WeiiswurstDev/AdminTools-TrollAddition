package dev.wwst.admintrolladditions;

import dev.wwst.admintools3.modules.ModuleLoader;
import dev.wwst.admintools3.util.MessageTranslator;
import dev.wwst.admintools3.util.Metrics;
import dev.wwst.admintrolladditions.modules.DropItemModule;
import dev.wwst.admintrolladditions.modules.HotbarSwapperModule;
import dev.wwst.admintrolladditions.modules.LagMoveModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;

public final class AdminTroll extends JavaPlugin {

    private static AdminTroll INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        if(!Bukkit.getPluginManager().isPluginEnabled("Admintools3")) {
            getLogger().severe("!!! YOU NEED TO INSTALL ADMINTOOLS3 FOR THIS PLUGIN !!!");
            getLogger().severe("!!! https://www.spigotmc.org/resources/admintools.76747/ !!!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        saveResource("messages.yml",false); // this will not replace any existing message file.
        MessageTranslator.getInstance().loadMessageFile("plugins/AdminTrollAddition/messages.yml");

        ModuleLoader ml = ModuleLoader.getInstance();
        ml.registerModule(new HotbarSwapperModule());
        ml.registerModule(new DropItemModule());
        ml.registerModule(new LagMoveModule());

        Metrics metrics = new Metrics(this, 6979);
        metrics.addCustomChart(new Metrics.SimplePie("admintools_version", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Bukkit.getPluginManager().getPlugin("Admintools3").getDescription().getVersion();
            }
        }));

        getLogger().info("AdminTool3-TrollAddition was loaded and is now working.");
        getLogger().info("Suggest new trolls, report bugs and ask questions about AdminTrolls in our discord: https://discord.gg/YDkQbE7");
        getLogger().info("Have fun!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AdminTroll getInstance() {
        return INSTANCE;
    }
}
