package com.unclecole.brbarriers.objects;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Key {

    @Getter private Material material;
    private String name;
    private List<String> lore;
    private int amount;
    private Boolean enchanted;

    public Key(Material material, String name, List<String> lore, int amount, Boolean enchanted) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.amount = amount;
        this.enchanted = enchanted;
    }

    public Boolean isKey(ItemStack itemStack) {
        if(itemStack.getType().equals(material) && itemStack.getAmount() >= amount && itemStack.containsEnchantment(Enchantment.DIG_SPEED)) {
            return true;
        }
        return false;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }
}
