package com.unclecole.brbarriers.cmd.subs;
import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.cmd.AbstractCommand;
import com.unclecole.brbarriers.database.BarrierData;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.command.CommandSender;

public class DeleteCmd extends AbstractCommand {

    public DeleteCmd() { super("delete", false); }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(args.length < 1) {
            TL.INVALID_COMMAND_USAGE.send(sender, new PlaceHolder("<command>", "/barriers delete <name>"));
            return false;
        }

        if(!BarrierData.barrierList.containsKey(args[0])) {
            TL.INVALID_COMMAND_USAGE.send(sender, new PlaceHolder("<command>", "/barriers delete <name>"));
            return false;
        }

        BarrierData.barrierList.remove(args[0]);
        TL.BARRIER_DELETED.send(sender, new PlaceHolder("%barrier%", args[0]));
        BarrierData.save();

        return false;
    }

    @Override
    public String getDescription() {
        return "used to delete barriers";
    }

    @Override
    public String getPermission() {
        return "barriers.admin.delete";
    }
}
