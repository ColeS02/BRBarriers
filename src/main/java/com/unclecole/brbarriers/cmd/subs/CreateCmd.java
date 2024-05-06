package com.unclecole.brbarriers.cmd.subs;
import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.cmd.AbstractCommand;
import com.unclecole.brbarriers.objects.Barrier;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCmd extends AbstractCommand {

    public CreateCmd() { super("create", true); }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if(args.length < 1) {
            TL.INVALID_COMMAND_USAGE.send(player, new PlaceHolder("<command>", "/barriers create <name>"));
            return false;
        }

        TL.EDITING_MODE.send(player, new PlaceHolder("%toggle%", "ENABLED"));
        BRBarriers.getInstance().getEditingPlayers().put(player.getUniqueId(), new Barrier(args[0]));

        return false;
    }

    @Override
    public String getDescription() {
        return "Used to create barriers";
    }

    @Override
    public String getPermission() {
        return "barriers.admin.create";
    }
}
