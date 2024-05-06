package com.unclecole.brbarriers.cmd.subs;
import com.unclecole.brbarriers.BRBarriers;
import com.unclecole.brbarriers.cmd.AbstractCommand;
import com.unclecole.brbarriers.database.BarrierData;
import com.unclecole.brbarriers.objects.Barrier;
import com.unclecole.brbarriers.objects.Key;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.itemutils.ItemBuilder;

public class CreateKeyCmd extends AbstractCommand {

    public CreateKeyCmd() { super("createkey", true); }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if(args.length < 2) {
            TL.INVALID_COMMAND_USAGE.send(player, new PlaceHolder("<command>", "/barriers createkey <name> <amount> <enchanted>"));
            return false;
        }

        if(player.getPlayer().getInventory().getItemInMainHand() == null) {
            TL.MUST_HOLD_ITEM_TO_CREATE_KEY.send(player);
            return false;
        }

        ItemStack key = player.getPlayer().getInventory().getItemInMainHand();
        BarrierData.barrierKeys.put(args[0], new Key(key.getType(),key.getItemMeta().getDisplayName(),key.getItemMeta().getLore(),Integer.parseInt(args[1]), Boolean.parseBoolean(args[2])));
        BarrierData.save();
        TL.SUCCESSFULLY_MADE_KEY.send(player, new PlaceHolder("%key%", args[0]));
        return false;
    }

    @Override
    public String getDescription() {
        return "Used to create barriers";
    }

    @Override
    public String getPermission() {
        return "barriers.admin.createkey";
    }
}
