package com.unclecole.brbarriers.cmd.subs;
import com.unclecole.brbarriers.cmd.AbstractCommand;
import com.unclecole.brbarriers.database.BarrierData;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.command.CommandSender;

public class ListKeysCmd extends AbstractCommand {

    public ListKeysCmd() { super("listkeys", false); }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        BarrierData.barrierKeys.forEach((s, key) -> {

            String status = "&c&lERROR";
            if(BarrierData.barrierList.containsKey(s))
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
        return "barriers.admin.listkeys";
    }
}
