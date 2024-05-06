package com.unclecole.brbarriers;

import com.unclecole.brbarriers.cmd.BaseCommand;
import com.unclecole.brbarriers.database.BarrierData;
import com.unclecole.brbarriers.database.PlayerData;
import com.unclecole.brbarriers.database.serializer.Persist;
import com.unclecole.brbarriers.listeners.BarrierListener;
import com.unclecole.brbarriers.listeners.CreateListener;
import com.unclecole.brbarriers.listeners.JoinListener;
import com.unclecole.brbarriers.objects.Barrier;
import com.unclecole.brbarriers.objects.BarrierList;
import com.unclecole.brbarriers.objects.Key;
import com.unclecole.brbarriers.tasks.ParticleTask;
import com.unclecole.brbarriers.utils.ConfigFile;
import com.unclecole.brbarriers.utils.LocationUtility;
import com.unclecole.brbarriers.utils.TL;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.particle.ParticleEffect;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class BRBarriers extends JavaPlugin {

    private static BRBarriers instance;
    @Getter private static Persist persist;
    @Getter private HashMap<UUID, Barrier> editingPlayers;
    @Getter LocationUtility locationUtility;
    @Getter HashMap<UUID, BarrierList> unlockedBarriersList;
    @Getter HashMap<UUID, String> viewBarrierList;
    @Getter ArrayList<String> whitelistedWorlds;


    @Override
    public void onEnable() {

        saveDefaultConfig();

        instance = this;
        locationUtility = new LocationUtility();
        persist = new Persist();
        editingPlayers = new HashMap<>();
        unlockedBarriersList = new HashMap<>();
        viewBarrierList = new HashMap<>();

        File playerDataFolder = new File(this.getDataFolder(), "playerdata");
        if(!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }

        BarrierData.load();
        loadConfig();
        Objects.requireNonNull(getCommand("barriers")).setExecutor(new BaseCommand());

        TL.loadMessages(new ConfigFile("messages.yml", this));

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new ParticleTask(), 10, 10);

        Bukkit.getPluginManager().registerEvents(new CreateListener(), this);
        Bukkit.getPluginManager().registerEvents(new BarrierListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);

        if (Bukkit.getOnlinePlayers().size() != 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerData.load(player.getUniqueId().toString());
                if(!unlockedBarriersList.containsKey(player.getUniqueId())) {
                    unlockedBarriersList.put(player.getUniqueId(), new BarrierList(player.getUniqueId().toString()));
                }
            }
        }


        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(Bukkit.getOnlinePlayers().size() != 0) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                PlayerData.save(player.getUniqueId().toString());
            }
        }
        BarrierData.save();
    }

    public static BRBarriers getInstance() {
        return instance;
    }

    public void loadConfig() {
        for(String key : getConfig().getConfigurationSection("Keys").getKeys(false)) {
            BarrierData.barrierKeys.put(key, new Key(Material.getMaterial(getConfig().getString("Keys." + key + ".Item")),
                    getConfig().getString("Keys." + key + ".Name"),
                    getConfig().getStringList("Keys." + key + ".Lore"),
                    getConfig().getInt("Keys." + key + ".Amount"),
                    getConfig().getBoolean("Keys." + key + ".Enchanted")));
        }
        whitelistedWorlds = (ArrayList<String>) getConfig().getStringList("WhiteListed-Worlds");
    }
}
