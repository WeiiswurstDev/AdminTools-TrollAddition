package dev.wwst.admintrolladditions.modules;

import com.google.common.collect.Lists;
import dev.wwst.admintools3.modules.Module;
import dev.wwst.admintools3.util.XMaterial;
import dev.wwst.admintrolladditions.AdminTroll;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class DropItemModule extends Module {

    private final List<Player> toPunish;
    private final Random random;

    public DropItemModule() {
        super(false,true,"dropitem", XMaterial.WATER_BUCKET);
        useDefaultMessageKeyFormat = false;
        permissionBase = "trolladditions.dropitem";
        permissionSelf = "trolladditions.dropitem"; // no reason to add a seperate self permission

        random = new Random();

        toPunish = Lists.newArrayList();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(AdminTroll.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player p : toPunish) {
                    if(p.isOnline()) {
                        ItemStack inMainHand = p.getInventory().getItemInMainHand();
                        if(inMainHand == null || inMainHand.getType() == Material.AIR) continue;
                        Item item = p.getWorld().dropItemNaturally(p.getLocation(), inMainHand);
                        item.setVelocity(item.getVelocity().multiply(3));
                        p.getInventory().setItemInMainHand(null);
                    }
                }
            }
        }, 20, 20*8);
    }

    @Override
    public boolean execute(Player player, Player other, World world) {
        if(!super.execute(player, other, world)) {
            return false;
        }
        if(toPunish.contains(other)) {
            toPunish.remove(other);
            player.sendMessage(msg.getMessageAndReplace("module.dropitem.message.toggleOff",true,player,other.getName()));
        } else {
            toPunish.add(other);
            player.sendMessage(msg.getMessageAndReplace("module.dropitem.message.toggleOn",true,player,other.getName()));
        }
        return false;
    }

}
