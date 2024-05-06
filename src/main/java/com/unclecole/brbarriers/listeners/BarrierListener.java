package com.unclecole.brbarriers.listeners;

import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.database.BarrierData;
import com.unclecole.brbarriers.database.PlayerData;
import com.unclecole.brbarriers.objects.BarrierList;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BarrierListener implements Listener {

    BRBarriers plugin = BRBarriers.getInstance();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        if(event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
        if(!plugin.getWhitelistedWorlds().contains(event.getTo().getWorld().getName())) return;

        BarrierData.barrierList.forEach((s, barrier) -> {

            if(BRBarriers.getInstance().getUnlockedBarriersList().get(event.getPlayer().getUniqueId()).contains(s)) {
                return;
            }

            if(BarrierData.barrierKeys.get(s) == null) {
                return;
            }

            if(BRBarriers.getInstance().getLocationUtility().isInRegion(BRBarriers.getInstance().getLocationUtility().parseToString(event.getTo()), barrier.getCorner1(), barrier.getCorner2())) {
                if(event.getPlayer().getInventory().contains(BarrierData.barrierKeys.get(s).getMaterial())) {
                    if(!checkInv(event.getPlayer(),s).get()) {
                        TL.NOT_PERMITTED_TO_ENTER.send(event.getPlayer(), new PlaceHolder("%barrier%", s),
                                new PlaceHolder("%amount%", BarrierData.barrierKeys.get(s).getAmount()),
                                new PlaceHolder("%key%", BarrierData.barrierKeys.get(s).getName()));
                        event.setCancelled(true);
                    }
                } else {
                    TL.NOT_PERMITTED_TO_ENTER.send(event.getPlayer(), new PlaceHolder("%barrier%", s),
                            new PlaceHolder("%amount%", BarrierData.barrierKeys.get(s).getAmount()),
                            new PlaceHolder("%key%", BarrierData.barrierKeys.get(s).getName()));
                    event.setCancelled(true);
                }
            }
        });
    }

    public AtomicBoolean checkInv(Player player, String s) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        AtomicInteger amount = new AtomicInteger(BarrierData.barrierKeys.get(s).getAmount());
        player.getInventory().forEach(itemStack -> {
            if(itemStack != null) {
                if(amount.get() <= 0) return;
                if (BarrierData.barrierKeys.get(s).isKey(itemStack)) {
                    BRBarriers.getInstance().getUnlockedBarriersList().get(player.getUniqueId()).add(s);
                    if(itemStack.getAmount() < amount.get()) {
                        itemStack.setAmount(0);
                        amount.addAndGet(-itemStack.getAmount());
                        PlayerData.save(player.getUniqueId().toString());
                        return;
                    }
                    itemStack.setAmount(itemStack.getAmount() - amount.get());
                    amount.set(0);

                    hasItem.set(true);
                    PlayerData.save(player.getUniqueId().toString());
                }
            }
        });
        return hasItem;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        BarrierData.barrierList.forEach((s, barrier) -> {
            if(!BRBarriers.getInstance().getUnlockedBarriersList().containsKey(event.getPlayer().getUniqueId())) {
                BRBarriers.getInstance().getUnlockedBarriersList().put(event.getPlayer().getUniqueId(), new BarrierList(event.getPlayer().getUniqueId().toString()));
                PlayerData.save(event.getPlayer().getUniqueId().toString());
            }
            if(BRBarriers.getInstance().getUnlockedBarriersList().get(event.getPlayer().getUniqueId()).contains(s)) {
                return;
            }


            if(BRBarriers.getInstance().getLocationUtility().isInRegion(BRBarriers.getInstance().getLocationUtility().parseToString(event.getTo()), barrier.getCorner1(), barrier.getCorner2())) {
                if(event.getPlayer().getInventory().contains(BarrierData.barrierKeys.get(s).getMaterial())) {
                    event.getPlayer().getInventory().forEach(itemStack -> {
                        if(itemStack != null) {
                            if (BarrierData.barrierKeys.get(s).isKey(itemStack)) {
                                BRBarriers.getInstance().getUnlockedBarriersList().get(event.getPlayer().getUniqueId()).add(s);
                                itemStack.setAmount(itemStack.getAmount() - BarrierData.barrierKeys.get(s).getAmount());
                                PlayerData.save(event.getPlayer().getUniqueId().toString());
                                return;
                            }
                        }
                    });
                }
                TL.NOT_PERMITTED_TO_ENTER.send(event.getPlayer(), new PlaceHolder("%barrier%", s),
                        new PlaceHolder("%amount%", BarrierData.barrierKeys.get(s).getAmount()),
                        new PlaceHolder("%key%", BarrierData.barrierKeys.get(s).getName()));
                event.setCancelled(true);
            }
        });
    }

}
