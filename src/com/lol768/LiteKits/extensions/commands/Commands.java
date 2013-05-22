package com.lol768.LiteKits.extensions.commands;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.lol768.LiteKits.LiteKits;
import com.lol768.LiteKits.utility.Messaging;

public class Commands extends JavaPlugin implements Listener {
    private LiteKits lk;
    
    public void onEnable() {
        Object obj = Bukkit.getServer().getPluginManager().getPlugin("LiteKits");
        if (obj != null) {
            lk = (LiteKits) obj;
            if (lk.getDescription().getVersion().equals("1.0")) {
                getLogger().severe("LiteKits version is too old to use this extension. Disabling self...");
                setEnabled(false);
            } else {
                Bukkit.getPluginManager().registerEvents(this, this);
            }
            
        } else {
            getLogger().severe("Couldn't find LiteKits. Disabling self...");
            setEnabled(false);
        }
        
        
    }
    
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String kit = e.getMessage().substring(1);
        getLogger().info("Got a command, it's " + kit);
        if (lk.kitExists(kit)) {
            if (!e.getPlayer().hasPermission("LiteKits.kit")) {
                Messaging.sendPermissionsError(e.getPlayer(), lk.prefix);
                getLogger().info("User doesn't have permission for LiteKits.kit");
                e.setCancelled(true);
            } else {
                if (!e.getPlayer().hasPermission("LiteKits.use." + kit)) {
                    Messaging.sendPermissionsError(e.getPlayer(), lk.prefix);
                    getLogger().info("User doesn't have permission for LiteKits.use." + kit);
                    e.setCancelled(true);
                } else {
                    lk.supplyKitToPlayer(kit, e.getPlayer());
                    getLogger().info("Giving the kit to the user!");
                    e.setCancelled(true);
                }
            } 
        } else {
            getLogger().info("No such kit :(");
        }
    }

}
