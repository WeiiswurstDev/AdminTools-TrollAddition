package dev.wwst.admintrolladditions.modules;

import com.google.common.collect.Lists;
import dev.wwst.admintools3.modules.Module;
import dev.wwst.admintools3.util.XMaterial;
import dev.wwst.admintrolladditions.AdminTroll;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class HotbarSwapperModule extends Module {

    private final List<Player> toSwap;
    private final Random random;

    public HotbarSwapperModule() {
        super(false,true,"hotbarswap", XMaterial.TNT);
        useDefaultMessageKeyFormat = false;
        permissionBase = "trolladditions.hotbarswap";
        permissionSelf = "trolladditions.hotbarswap"; // no reason to add a seperate self permission

        random = new Random();

        toSwap = Lists.newArrayList();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(AdminTroll.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player p : toSwap) {
                    if(p.isOnline()) {
                        p.getInventory().setHeldItemSlot(random.nextInt(9));
                    }
                }
            }
        }, 60, 20*8);
    }

    @Override
    public boolean execute(Player player, Player other, World world) {
        if(!super.execute(player, other, world)) {
            return false;
        }
        if(toSwap.contains(other)) {
            toSwap.remove(other);
            player.sendMessage(msg.getMessageAndReplace("module.hotbarswap.message.toggleOff",true,player,other.getName()));
        } else {
            toSwap.add(other);
            player.sendMessage(msg.getMessageAndReplace("module.hotbarswap.message.toggleOn",true,player,other.getName()));
        }
        return false;
    }
}
