package dev.wwst.admintrolladditions.modules;

import com.google.common.collect.Lists;
import dev.wwst.admintools3.modules.Module;
import dev.wwst.admintools3.util.XMaterial;
import dev.wwst.admintrolladditions.AdminTroll;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.Random;

public class LagMoveModule extends Module implements Listener {

    private final List<Player> toLagBack;
    private final Random random;

    public LagMoveModule() {
        super(false,true,"lagmove", XMaterial.RED_STAINED_GLASS);

        useDefaultMessageKeyFormat = false;
        permissionBase = "trolladditions.lagmove";
        permissionSelf = "trolladditions.lagmove"; // no reason to add a seperate self permission

        random = new Random();

        toLagBack = Lists.newArrayList();

        Bukkit.getPluginManager().registerEvents(this,AdminTroll.getInstance());
    }

    @Override
    public boolean execute(Player player, Player other, World world) {
        if(!super.execute(player, other, world)) {
            return false;
        }
        if(toLagBack.contains(other)) {
            toLagBack.remove(other);
            player.sendMessage(msg.getMessageAndReplace("module.lagmove.message.toggleOff",true,player,other.getName()));
        } else {
            toLagBack.add(other);
            player.sendMessage(msg.getMessageAndReplace("module.lagmove.message.toggleOn",true,player,other.getName()));
        }
        return false;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(toLagBack.contains(event.getPlayer())) {
            if(random.nextInt(100) > 90) {
                event.setCancelled(true);
            }
        }
    }
}
