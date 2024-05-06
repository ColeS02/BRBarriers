package com.unclecole.brbarriers.cmd.subs;
import com.unclecole.brbarriers.cmd.AbstractCommand;
import com.unclecole.brbarriers.database.BarrierData;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.command.CommandSender;

public class DeleteKeyCmd extends AbstractCommand {

    public DeleteKeyCmd() { super("delete", false); }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(args.length < 1) {
            TL.INVALID_COMMAND_USAGE.send(sender, new PlaceHolder("<command>", "/barriers deletekey <name>"));
            return false;
        }

        if(!BarrierData.barrierKeys.containsKey(args[0])) {
            TL.INVALID_COMMAND_USAGE.send(sender, new PlaceHolder("<command>", "/barriers deletekey <name>"));
            return false;
        }

        BarrierData.barrierKeys.remove(args[0]);
        TL.KEY_DELETED.send(sender, new PlaceHolder("%barrier%", args[0]));
        BarrierData.save();

        return false;
    }

    @Override
    public String getDescription() {
        return "used to delete barriers";
    }

    @Override
    public String getPermission() {
        return "barriers.admin.deletekey";
    }
}
