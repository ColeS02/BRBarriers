package com.unclecole.brbarriers.cmd.subs;

import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.cmd.AbstractCommand;
import com.unclecole.brbarriers.cmd.BaseCommand;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BarriersCmd extends AbstractCommand {


    public BarriersCmd() {
        super("barriers", false);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        TL.BARRIER_HELP_CMD.send(sender, new PlaceHolder("%command%", "/barriers create <name>"),
                new PlaceHolder("%description%", "Barrier's Create Command"),
                new PlaceHolder("%permission%", "barriers.admin.create"));
        TL.BARRIER_HELP_CMD.send(sender, new PlaceHolder("%command%", "/barriers delete <name>"),
                new PlaceHolder("%description%", "Barrier's Delete Command"),
                new PlaceHolder("%permission%", "barriers.admin.delete"));
        TL.BARRIER_HELP_CMD.send(sender, new PlaceHolder("%command%", "/barriers list"),
                new PlaceHolder("%description%", "List all barriers and if they're functional"),
                new PlaceHolder("%permission%", "barriers.admin.list"));
        TL.BARRIER_HELP_CMD.send(sender, new PlaceHolder("%command%", "/barriers view <barrier>"),
                new PlaceHolder("%description%", "View outline of barrier"),
                new PlaceHolder("%permission%", "barriers.admin.view"));
        TL.BARRIER_HELP_CMD.send(sender, new PlaceHolder("%command%", "/barriers createkey <name> <amount> <enchanted>"),
                new PlaceHolder("%description%", "Create a new key!"),
                new PlaceHolder("%permission%", "barriers.admin.createkey"));
        TL.BARRIER_HELP_CMD.send(sender, new PlaceHolder("%command%", "/barriers deletekey <name>"),
                new PlaceHolder("%description%", "Used to delete a key!"),
                new PlaceHolder("%permission%", "barriers.admin.deletekey"));
        TL.BARRIER_HELP_CMD.send(sender, new PlaceHolder("%command%", "/barriers listkeys"),
                new PlaceHolder("%description%", "Used to list all keys!"),
                new PlaceHolder("%permission%", "barriers.admin.listkeys"));
        TL.BARRIER_HELP_CMD.send(sender, new PlaceHolder("%command%", "/barriers givekey <player> <key> <amount>"),
                new PlaceHolder("%description%", "Used to give keys!"),
                new PlaceHolder("%permission%", "barriers.admin.givekey"));

        return false;
    }

    @Override
    public String getDescription() {
        return "Barriers Help Command";
    }

    @Override
    public String getPermission() {
        return "barriers";
    }
}
