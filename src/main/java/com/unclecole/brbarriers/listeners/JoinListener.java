package com.unclecole.brbarriers.listeners;

import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.database.PlayerData;
import com.unclecole.brbarriers.objects.BarrierList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(!BRBarriers.getInstance().getUnlockedBarriersList().containsKey(event.getPlayer().getUniqueId())) {
            BRBarriers.getInstance().getUnlockedBarriersList().put(event.getPlayer().getUniqueId(), new BarrierList(event.getPlayer().getUniqueId().toString()));
        }
        PlayerData.load(event.getPlayer().getUniqueId().toString());
    }
}
