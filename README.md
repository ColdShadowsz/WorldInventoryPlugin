# **WorldInventoryPlugin**

WorldInventoryPlugin was inspired by the BentoBox add‑on *InvSwitcher*, which attempted to separate inventories across worlds but was broken on older versions. This plugin is a full recreation, fixing those issues.  
**Built and tested on 1.20.6 — CREATED FOR KAIDMC**

---

## ** Features**

- **Per‑world isolation:** Player inventories are separated per world or group.
- **Automatic switching:** Inventories save and load seamlessly on world change.
- **Persistent storage:** Inventories are saved to disk at `plugins/WorldInventoryPlugin/data/`
- **Flexible grouping:**
  - Automatic grouping by world suffixes (`_nether`, `_end`, `_the_end`)
  - Optional config mode allows server owners to manually define groups in `config.yml`
- **Per‑player safety:** Inventories are stored per UUID and per group key

---

## ** Requirements**

- **Java:** 17–21  
- **Server:** PaperMC / Spigot 1.13+ (built and tested on 1.20.6)

> **Note:** WorldInventoryPlugin is officially built for 1.20.6.  
> Users can manually change the `api-version` in `plugin.yml` or recompile the plugin for other Paper/Spigot versions if needed.

---

## ** Installation**

1. **Place JAR:** Put `WorldInventoryPlugin.jar` in your server’s `plugins` folder  
2. **Start server:** The plugin initializes and creates its folder  
3. **Edit config:** A default `config.yml` will be generated at `plugins/WorldInventoryPlugin/config.yml`

---

## ** Configuration**

- **Loadconfig-on-start:** Controls grouping mode
  - `false` (default): Automatic grouping by suffix (`_nether`, `_end`, `_the_end`)
  - `true`: Manual grouping loaded from `config.yml`

> ⚠️ **WARNING:**  
> If you don’t know what this does or how to manually add groups, leave this alone.  
> Default is `false`, which means worlds are grouped automatically.  
> Set to `true` ONLY if you want to manually define groups below.

### Example `config.yml`

```yaml
# WARNING:
# If you don't know what this does or how to manually add groups, leave this alone.
# Default is false, which means worlds are grouped automatically by suffix (_nether, _end, _the_end).
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
