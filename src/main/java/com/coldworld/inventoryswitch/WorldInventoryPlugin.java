package com.coldworld.inventoryswitch;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldInventoryPlugin extends JavaPlugin implements Listener {

    private final Map<UUID, Map<String, ItemStack[]>> inventories = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("WorldInventoryPlugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WorldInventoryPlugin disabled!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String world = player.getWorld().getName();
        loadInventory(player, world);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String fromWorld = event.getFrom().getName();
        String toWorld = player.getWorld().getName();

        saveInventory(player, fromWorld);
        loadInventory(player, toWorld);
    }

    private void saveInventory(Player player, String world) {
        inventories.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                   .put(world, player.getInventory().getContents());
    }

    private void loadInventory(Player player, String world) {
        ItemStack[] inv = inventories
                .computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .get(world);

        if (inv != null) {
            player.getInventory().setContents(inv);
        } else {
            player.getInventory().clear();
        }
    }
}
