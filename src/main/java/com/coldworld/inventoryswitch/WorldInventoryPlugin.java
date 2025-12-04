package com.coldworld.inventoryswitch;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WorldInventoryPlugin extends JavaPlugin implements Listener {

    private final Map<UUID, Map<String, ItemStack[]>> inventories = new HashMap<>();
    private Map<String, String> worldToGroup = new HashMap<>();
    private boolean useConfigGroups = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        useConfigGroups = config.getBoolean("Loadconfig-on-start", false);

        if (useConfigGroups) {
            loadGroupsFromConfig();
            getLogger().info("WorldInventoryPlugin using manual groups from config.yml");
        } else {
            getLogger().info("WorldInventoryPlugin using automatic grouping by world name suffixes");
        }

        loadAllInventories();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("WorldInventoryPlugin enabled!");
    }

    @Override
    public void onDisable() {
        saveAllInventories();
        getLogger().info("WorldInventoryPlugin disabled!");
    }

    private void loadGroupsFromConfig() {
        worldToGroup.clear();
        FileConfiguration config = getConfig();
        if (config.isConfigurationSection("groups")) {
            for (String group : config.getConfigurationSection("groups").getKeys(false)) {
                List<String> worlds = config.getStringList("groups." + group);
                for (String world : worlds) {
                    worldToGroup.put(world.toLowerCase(), group.toLowerCase());
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String group = getGroupKey(player.getWorld().getName());
        loadInventory(player, group);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String fromGroup = getGroupKey(event.getFrom().getName());
        String toGroup = getGroupKey(player.getWorld().getName());

        saveInventory(player, fromGroup);
        loadInventory(player, toGroup);
    }

    private void saveInventory(Player player, String group) {
        inventories.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                   .put(group, player.getInventory().getContents());
        savePlayerInventories(player.getUniqueId());
    }

    private void loadInventory(Player player, String group) {
        ItemStack[] inv = inventories
                .computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .get(group);

        if (inv != null) {
            player.getInventory().setContents(inv);
        } else {
            player.getInventory().clear();
        }
    }

    private String getGroupKey(String worldName) {
        worldName = worldName.toLowerCase();

        if (useConfigGroups) {
            return worldToGroup.getOrDefault(worldName, worldName);
        }

        // Automatic grouping by suffix
        if (worldName.endsWith("_nether")) {
            return worldName.replace("_nether", "");
        }
        if (worldName.endsWith("_the_end")) {
            return worldName.replace("_the_end", "");
        }
        if (worldName.endsWith("_end")) {
            return worldName.replace("_end", "");
        }

        return worldName;
    }

    // ------------------ Persistence ------------------

    private void saveAllInventories() {
        for (UUID uuid : inventories.keySet()) {
            savePlayerInventories(uuid);
        }
    }

    private void savePlayerInventories(UUID uuid) {
        File file = new File(getDataFolder(), "data/" + uuid + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        Map<String, ItemStack[]> playerInvs = inventories.get(uuid);
        if (playerInvs != null) {
            for (Map.Entry<String, ItemStack[]> entry : playerInvs.entrySet()) {
                config.set(entry.getKey(), entry.getValue());
            }
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAllInventories() {
        File dataFolder = new File(getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
            return;
        }
        for (File file : Objects.requireNonNull(dataFolder.listFiles())) {
            if (file.getName().endsWith(".yml")) {
                UUID uuid = UUID.fromString(file.getName().replace(".yml", ""));
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                Map<String, ItemStack[]> playerInvs = new HashMap<>();
                for (String key : config.getKeys(false)) {
                    List<?> list = config.getList(key);
                    if (list != null) {
                        ItemStack[] items = list.toArray(new ItemStack[0]);
                        playerInvs.put(key, items);
                    }
                }
                inventories.put(uuid, playerInvs);
            }
        }
    }
}
