package com.evgenyn.encore.utilities;

import org.bukkit.inventory.ItemStack;

public class InventoryGUI {
    public InventoryGUI() {
    }

    public static boolean getClickedItem(ItemStack clicked, String message) {
        return clicked.getItemMeta().getDisplayName().equalsIgnoreCase(message);
    }
}