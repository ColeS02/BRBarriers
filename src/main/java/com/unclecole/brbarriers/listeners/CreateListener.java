package com.unclecole.brbarriers.listeners;

import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.database.BarrierData;
import com.unclecole.brbarriers.utils.LocationUtility;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class CreateListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(!BRBarriers.getInstance().getEditingPlayers().containsKey(player.getUniqueId())) return;

        if(event.getItem() == null) return;

        if(event.getItem().getType().equals(Material.STICK)) {
            event.setCancelled(true);
            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                BRBarriers.getInstance().getEditingPlayers().get(player.getUniqueId()).setCorner1(BRBarriers.getInstance().getLocationUtility().parseToString(event.getClickedBlock().getLocation()));
                TL.SUCCESSFULLY_SET_CORNER.send(player, new PlaceHolder("%corner%", "One"));
            }
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                BRBarriers.getInstance().getEditingPlayers().get(player.getUniqueId()).setCorner2(BRBarriers.getInstance().getLocationUtility().parseToString(event.getClickedBlock().getLocation()));
                TL.SUCCESSFULLY_SET_CORNER.send(player, new PlaceHolder("%corner%", "Two"));
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(!BRBarriers.getInstance().getEditingPlayers().containsKey(player.getUniqueId())) return;

        if(event.getMessage().equalsIgnoreCase("DONE")) {
            if(BRBarriers.getInstance().getEditingPlayers().get(player.getUniqueId()).getCorner1() != null &&
                    BRBarriers.getInstance().getEditingPlayers().get(player.getUniqueId()).getCorner2() != null) {
                BarrierData.barrierList.put(BRBarriers.getInstance().getEditingPlayers().get(player.getUniqueId()).getName(), BRBarriers.getInstance().getEditingPlayers().get(player.getUniqueId()));
                BRBarriers.getInstance().getEditingPlayers().remove(player.getUniqueId());
                BarrierData.save();
                TL.EDITING_MODE.send(player, new PlaceHolder("%toggle%", "DISABLED"));
                BarrierData.save();
                event.setCancelled(true);
            } else {
                TL.EDITING_ERROR.send(player);
                event.setCancelled(true);
            }
        }
    }
}
