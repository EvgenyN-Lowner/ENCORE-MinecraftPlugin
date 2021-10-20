package com.evgenyn.encore.gui;

import com.evgenyn.encore.SQL.SQLGetter;
import com.evgenyn.encore.utilities.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import com.evgenyn.encore.utilities.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerMenuPanelGUI {
    public static HashMap<Player, Player> target_player = new HashMap();

    public PlayerMenuPanelGUI() {
    }

    public Inventory GUI_PlayerMenuMain(Player p, SQLGetter data) {
        Inventory inv_PlayerMenu = Bukkit.createInventory(p, 54, ChatColor.DARK_GREEN + "Меню");

        for (int i = 0; i < 54; i++) {
            Item.create(inv_PlayerMenu, "GRAY_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.createPlayerHead(inv_PlayerMenu, p.getName(), 1, 0, ChatColor.DARK_GREEN + p.getName(),
                ChatColor.LIGHT_PURPLE + "Текущий уровень персонажа: " + ChatColor.WHITE + data.getLevel(p.getUniqueId()),
                ChatColor.WHITE + "Прогресс уровня персонажа: " + ChatColor.GRAY + "null / null EXP",
                " ",
                ChatColor.GREEN + "Вы находитесь в мире: " + ChatColor.GOLD + p.getWorld().getName(),
                ChatColor.GREEN + "Текущая профессия: " + ChatColor.AQUA + "NULL",
                ChatColor.GREEN + "Уровень профессии " + ChatColor.LIGHT_PURPLE + "NULL"
        );

        Item.create(inv_PlayerMenu, "ENCHANTED_BOOK", 1, 11, ChatColor.LIGHT_PURPLE + "Характеристики / Способности / Особенности", new String[0]);

        Item.create(inv_PlayerMenu, "BARRIER", 1, 53, ChatColor.RED + "Закрыть Меню", new String[0]);

        return inv_PlayerMenu;
    }

    public void clicked_playerMenu(Player p, int slot, ItemStack clicked, Inventory inv) {
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Закрыть Меню")) {
            p.closeInventory();
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.LIGHT_PURPLE + "Характеристики / Способности / Особенности")) {
            Bukkit.dispatchCommand(p, "skills");
        }
    }
}
