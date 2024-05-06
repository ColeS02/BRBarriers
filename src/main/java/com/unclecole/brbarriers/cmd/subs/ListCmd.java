package com.unclecole.brbarriers.cmd.subs;
import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.cmd.AbstractCommand;
import com.unclecole.brbarriers.database.BarrierData;
import com.unclecole.brbarriers.objects.Barrier;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCmd extends AbstractCommand {

    public ListCmd() { super("list", false); }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        BarrierData.barrierList.forEach((s, barrier) -> {

            String status = "&c&lERROR";
            if(BarrierData.barrierKeys.containsKey(s))
                status = "&a&lGOOD";

            TL.BARRIER_LIST.send(sender, new PlaceHolder("%barrier%", s), new PlaceHolder("%status%", status));
        });

        return false;
    }

    @Override
    public String getDescription() {
        return "Used to list all barriers";
    }

    @Override
    public String getPermission() {
        return "barriers.admin.list";
    }
}
