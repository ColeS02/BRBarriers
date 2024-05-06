package com.unclecole.brbarriers.cmd.subs;
import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.cmd.AbstractCommand;
import com.unclecole.brbarriers.database.BarrierData;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewCmd extends AbstractCommand {

    public ViewCmd() { super("view", true); }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if(args.length < 1) {
            if(BRBarriers.getInstance().getViewBarrierList().containsKey(player.getUniqueId())) {
                BRBarriers.getInstance().getViewBarrierList().remove(player.getUniqueId());
                return false;
            }
            TL.INVALID_COMMAND_USAGE.send(sender, new PlaceHolder("<command>", "/barriers view <name>"));
            return false;
        }

        if(!BarrierData.barrierList.containsKey(args[0])) {
            TL.INVALID_COMMAND_USAGE.send(sender, new PlaceHolder("<command>", "/barriers view <name>"));
            return false;
        }

        if(BRBarriers.getInstance().getViewBarrierList().containsKey(player.getUniqueId()) && BRBarriers.getInstance().getViewBarrierList().get(player.getUniqueId()).equals(args[0])) {
            BRBarriers.getInstance().getViewBarrierList().remove(player.getUniqueId());
            return false;
        }
        BRBarriers.getInstance().getViewBarrierList().put(player.getUniqueId(), args[0]);
        return false;
    }

    @Override
    public String getDescription() {
        return "Used to view certain barriers";
    }

    @Override
    public String getPermission() {
        return "barriers.admin.view";
    }
}
