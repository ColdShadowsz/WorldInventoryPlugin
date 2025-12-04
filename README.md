WorldInventoryPlugin
WorldInventoryPlugin was inspired by the BentoBox add‑on InvSwitcher, which attempted to separate inventories across worlds but was broken on older versions. This plugin is a full recreation, fixing those issues. (Built and tested on 1.20.6 — CREATED FOR KAIDMC)

 Features
Per‑world inventory isolation for players.

Automatic saving and loading of inventories when switching worlds.

Inventories are persisted to disk (plugins/WorldInventoryPlugin/data/) so players keep items across server restarts.

Flexible grouping system:

By default, worlds are automatically grouped by suffix (_nether, _end, _the_end).

Optional config mode allows server owners to manually define groups in config.yml.

Each player’s inventories are stored separately by UUID and group key.

 Requirements
Java 17–21

PaperMC / Spigot 1.13+ (built and tested on 1.20.6)

Note: WorldInventoryPlugin is officially built for 1.20.6. Users can manually change the api-version in plugin.yml or recompile the plugin for other Paper/Spigot versions if needed.

 Installation
Place WorldInventoryPlugin.jar in your server’s plugins folder.

Start the server.

A default config.yml will be generated in plugins/WorldInventoryPlugin/.

yaml
# WARNING:
# If you don't know what this does or how to manually add groups, leave this alone.
# Default is false, which means worlds are grouped automatically by suffix (_nether, _end).
# Set to true ONLY if you want to manually define groups below.

Loadconfig-on-start: false

groups:
  skyblock:
    - bskyblock
    - bskyblock_nether
    - bskyblock_the_end
  oneblock:
    - oneblock
    - oneblock_nether
    - oneblock_the_end
If Loadconfig-on-start is left false, grouping is automatic.

If set to true, you must manually define groups under the groups: section.

🛠 Development / Contribution
Fork the repository and submit pull requests for features or bug fixes.

Ensure compatibility with PaperMC 1.13+ and Java 17–21.

Use Maven for building:

bash
mvn clean package
Recompile for other Minecraft versions by adjusting plugin.yml or dependencies.

 Licence
MIT License – free to use and modify.

 Author
Cold – https://github.com/ColdShadowsz
