package com.unclecole.brbarriers.cmd.subs;
import com.unclecole.brbarriers.cmd.AbstractCommand;
import com.unclecole.brbarriers.database.BarrierData;
import com.unclecole.brbarriers.objects.Key;
import com.unclecole.brbarriers.utils.C;
import com.unclecole.brbarriers.utils.PlaceHolder;
import com.unclecole.brbarriers.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import redempt.redlib.itemutils.ItemBuilder;

public class GiveKeyCmd extends AbstractCommand {

    public GiveKeyCmd() { super("givekey", false); }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length < 3) {
            TL.INVALID_COMMAND_USAGE.send(sender, new PlaceHolder("<command>", "/barriers givekey <player> <key> <amount>"));
            return false;
        }

        if(Bukkit.getPlayer(args[0]) == null) {
            TL.INVALID_COMMAND_USAGE.send(sender, new PlaceHolder("<command>", "/barriers givekey <player> <key> <amount>"));
            return false;
        }

        if(!BarrierData.barrierKeys.containsKey(args[1])) {
            TL.INVALID_COMMAND_USAGE.send(sender, new PlaceHolder("<command>", "/barriers givekey <key> <amount>"));
            return false;
        }

        Key keyobject = BarrierData.barrierKeys.get(args[1]);

        ItemBuilder key = new ItemBuilder(keyobject.getMaterial()).setName(C.color(keyobject.getName())).addLore(C.color(keyobject.getLore())).addEnchant(Enchantment.DIG_SPEED,1)
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_PLACED_ON);
        key.setAmount(Integer.parseInt(args[2]));
        Bukkit.getPlayer(args[0]).getInventory().addItem(key);

        BarrierData.save();

        return false;
    }

    @Override
    public String getDescription() {
        return "used to delete barriers";
    }

    @Override
    public String getPermission() {
        return "barriers.admin.givekey";
    }
}
