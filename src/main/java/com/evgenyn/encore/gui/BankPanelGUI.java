package com.evgenyn.encore.gui;

import com.evgenyn.encore.ENCORE;
import com.evgenyn.encore.SQL.SQLGetter;
import com.evgenyn.encore.utilities.InventoriesSerializer;
import com.evgenyn.encore.utilities.InventoryGUI;
import com.evgenyn.encore.utilities.Item;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class BankPanelGUI {
    public static HashMap<Player, Player> target_player = new HashMap();
    private final HashMap<Player, Integer> page = new HashMap();
    private final HashMap<Player, Integer> pages = new HashMap();

    public BankPanelGUI(){
    }

    public Inventory GUI_BankMain (Player p, SQLGetter data, int isVaultBoughtINT){
        Inventory inv_bankmain = Bukkit.createInventory(p, 27, ChatColor.GOLD + "Банк");

        boolean isVaultBought = false;
        if (isVaultBoughtINT == 1){
            isVaultBought = true;
        }
        String chestBought;

        for (int i = 0; i < 26; i++) {
            Item.create(inv_bankmain, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        if (isVaultBought) {
            chestBought = ChatColor.DARK_GREEN + "Куплена";
            Item.createPlayerHead(inv_bankmain, "Torias_Dax", 1, 14, ChatColor.AQUA + "Открыть ячейку", ChatColor.AQUA + "----------",
                    ChatColor.AQUA + "Ячейка: " + chestBought
            );
            Item.createPlayerHead(inv_bankmain, p.getName(), 1, 0, ChatColor.GOLD + "Информация", ChatColor.GOLD + "---------",
                    ChatColor.GOLD + "Баланс на счете: " + ChatColor.GREEN + data.getBank(p.getUniqueId()) + "₡",
                    ChatColor.GOLD + "Баланс на вкладе: " + ChatColor.LIGHT_PURPLE + data.getVklad(p.getUniqueId()) + "₡",
                    ChatColor.GOLD + "Ячейка: " + chestBought
            );
        } else {
            chestBought = ChatColor.RED + "Не куплена";
            Item.createPlayerHead(inv_bankmain, "Torias_Dax", 1, 14, ChatColor.AQUA + "Купить ячейку в банке на 9 предметов", ChatColor.AQUA + "----------",
                    ChatColor.AQUA + "Купить ячейку за: " + ChatColor.DARK_GREEN + "250₡"
            );
            Item.createPlayerHead(inv_bankmain, p.getName(), 1, 0, ChatColor.GOLD + "Информация", ChatColor.GOLD + "---------",
                    ChatColor.GOLD + "Баланс на счете: " + ChatColor.GREEN + data.getBank(p.getUniqueId()) + "₡",
                    ChatColor.GOLD + "Баланс на вкладе: " + ChatColor.LIGHT_PURPLE + data.getVklad(p.getUniqueId()) + "₡",
                    ChatColor.GOLD + "Ячейка: " + chestBought
            );
        }

        Item.createPlayerHead(inv_bankmain, "MrSnowDK", 1, 12, ChatColor.GREEN + "Операции со счетами", ChatColor.GREEN + "----------",
                ChatColor.GREEN + "Текущий баланс: " + ChatColor.GOLD + data.getBank(p.getUniqueId()) + "₡"
                );

        Item.create(inv_bankmain, "BARRIER", 1, 26, ChatColor.RED + "Закрыть Меню Банка", new String[0]);

        return inv_bankmain;
    }

    public Inventory GUI_Banking(Player p, SQLGetter data){
        Inventory inv_deposit = Bukkit.createInventory(p, 27, ChatColor.DARK_GREEN + "Счета");

        for (int i = 0; i < 26; i++) {
            Item.create(inv_deposit, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.createPlayerHead(inv_deposit, "Addelburgh", 1, 0, ChatColor.GOLD + "Информация о счетах", ChatColor.GOLD + "----------",
                ChatColor.GOLD + "Текущий баланс счета: " + ChatColor.AQUA + data.getBank(p.getUniqueId()) + "₡",
                ChatColor.GOLD + "----------",
                ChatColor.LIGHT_PURPLE + "Текущий баланс накопительного вклада: " + ChatColor.AQUA + data.getVklad(p.getUniqueId()) + "₡",
                ChatColor.LIGHT_PURPLE + "----------",
                ChatColor.GREEN + "Каждый " + ChatColor.AQUA + "ЧАС" + ChatColor.GREEN + " игры на сервере",
                ChatColor.GREEN + "ваш накопительный вклад ",
                ChatColor.GREEN + "увеличивается на " + ChatColor.AQUA + "1%"
        );

        Item.create(inv_deposit, "GOLD_BLOCK", 1, 11, ChatColor.GOLD + "Перевести деньги на счет другого игрока", new String[0]);
        Item.create(inv_deposit, "DIAMOND_BLOCK", 1, 13, ChatColor.AQUA + "Положить/Снять деньги с накопительного вклада", new String[0]);
        Item.create(inv_deposit, "NETHERITE_BLOCK", 1, 15, ChatColor.LIGHT_PURPLE + "Положить/Обналичить деньги со счета в купюры", new String[0]);

        Item.create(inv_deposit, "BARRIER", 1, 26, ChatColor.RED + "Назад в Меню Банка", new String[0]);

        return inv_deposit;
    }

    public Inventory GUI_Vault (Player p, SQLGetter data){
        Inventory inv_vault = Bukkit.createInventory(p, 9, ChatColor.AQUA + "Ячейка");

        String dataNBT = data.getVaultNBT(p.getUniqueId());
        InventoriesSerializer vaultSer = new InventoriesSerializer();
        try {
            ItemStack[] vault_items = vaultSer.fromBase64(dataNBT);
            inv_vault.setContents(vault_items);
        } catch (Exception e){
            e.printStackTrace();
        }

        return inv_vault;
    }

    public Inventory GUI_Perevod_Players (Player p, SQLGetter data){
        ArrayList<String> pl = new ArrayList();
        Inventory inv_perevod = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.GOLD + "Выберете Игрока для перевода");
        Iterator var4 = Bukkit.getServer().getOnlinePlayers().iterator();

        while(var4.hasNext()) {
            Player all = (Player)var4.next();
            pl.add(all.getName());
        }
        pl.remove(p.getName());

        Collections.sort(pl);
        int online = pl.size();
        this.pages.put(p, (int)Math.ceil((double)((float)online / 45.0F)));

        int player_slot;
        for(player_slot = 45; player_slot <= 52; ++player_slot) {
            Item.create(inv_perevod, "BLACK_STAINED_GLASS_PANE", 1, player_slot, " ", new String[0]);
        }

        player_slot = ((Integer)this.page.getOrDefault(p, 1) - 1) * 45;

        for(int i = 0; i < 45; ++i) {
            if (player_slot < online ) {
                Item.createPlayerHead(inv_perevod, (String)pl.get(player_slot), 1, i,ChatColor.DARK_GREEN + (String) pl.get(player_slot), ChatColor.DARK_GREEN + "---------",
                        ChatColor.GREEN + "Нажмите для выбора игрока"
                );
                ++player_slot;
            } else {
                Item.create(inv_perevod, "GRAY_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
            }
        }

        if ((Integer)this.page.getOrDefault(p, 1) > 1) {
            Item.create(inv_perevod, "STONE_BUTTON", 1, 48, ChatColor.YELLOW + "Прыдыдущая страница", new String[0]);
        }

        if ((Integer)this.pages.getOrDefault(p, 1) > 1) {
            Item.create(inv_perevod, "BOOK", (Integer)this.page.getOrDefault(p, 1), 49, ChatColor.GOLD + "Текущая страница" + " " + this.page.getOrDefault(p, 1), new String[0]);
        }

        if ((Integer)this.pages.get(p) > (Integer)this.page.getOrDefault(p, 1)) {
            Item.create(inv_perevod, "STONE_BUTTON", 1, 50, ChatColor.YELLOW + "Следующая страница", new String[0]);
        }

        Item.create(inv_perevod, "BARRIER", 1, 53, ChatColor.RED + "Назад к счетам", new String[0]);

        return inv_perevod;
    }

    public AnvilGUI.Builder GUI_Anvil_Perevod (Player p, SQLGetter data, ENCORE plugin, Player targetP){

        AnvilGUI.Builder anvil_float_perevod = new AnvilGUI.Builder()
                .onComplete((player, text ) -> {
                    if (Float.parseFloat(text) != 0 && Float.parseFloat(text) <= data.getBank(p.getUniqueId())){
                        float ammountF = Float.parseFloat(text);
                        data.removeBank(p.getUniqueId(), ammountF);
                        data.addbank(targetP.getUniqueId(), ammountF);
                        p.sendMessage(ChatColor.DARK_GREEN + "Вы перевели " + ChatColor.GOLD + ammountF + "₡" + ChatColor.DARK_GREEN + " игроку " + ChatColor.GOLD + targetP.getName());
                        targetP.sendMessage(ChatColor.DARK_GREEN + "На ваш банковский счет было переведено " + ChatColor.GOLD + ammountF + "₡" + ChatColor.DARK_GREEN + " игроком " + ChatColor.GOLD + p.getName());
                        return AnvilGUI.Response.close();
                    }else{
                        return AnvilGUI.Response.text("Неверная сумма");
                    }
                })
                .itemLeft(Item.createS("PAPER", 1, ChatColor.GOLD  + "Введите Сумму перевода", new String[0]))
                .title(ChatColor.GOLD + "Банк")
                .plugin(plugin);

        return anvil_float_perevod;
    }

    public AnvilGUI.Builder GUI_Anvil_Deposit (Player p, SQLGetter data, ENCORE plugin, int type){
        AnvilGUI.Builder anvil_float_deposit = new AnvilGUI.Builder()
                .onComplete((player, text ) -> {
                    if (Float.parseFloat(text) != 0){
                        float ammountF = Float.parseFloat(text);
                        if (type == 1 && Float.parseFloat(text) <= data.getVklad(p.getUniqueId())){
                            data.removeVklad(p.getUniqueId(), ammountF);
                            data.addbank(p.getUniqueId(), ammountF);
                            p.sendMessage(ChatColor.DARK_GREEN + "Вы сняли " + ChatColor.GOLD + ammountF + "₡" + ChatColor.DARK_GREEN + " с вклада");
                            return AnvilGUI.Response.close();
                        } else if (type == 2 && Float.parseFloat(text) <= data.getBank(p.getUniqueId())){
                            data.removeBank(p.getUniqueId(), ammountF);
                            data.addVklad(p.getUniqueId(), ammountF);
                            p.sendMessage(ChatColor.DARK_GREEN + "Вы положили " + ChatColor.GOLD + ammountF + "₡" + ChatColor.DARK_GREEN + " на вклад");
                            return AnvilGUI.Response.close();
                        } else {
                            return AnvilGUI.Response.text("Неверная сумма");
                        }
                    }else{
                        return AnvilGUI.Response.text("Неверная сумма");
                    }
        })
                .itemLeft(Item.createS("PAPER", 1, ChatColor.AQUA  + "Введите Сумму", new String[0]))
                .title(ChatColor.AQUA + "Банк")
                .plugin(plugin);

        return anvil_float_deposit;
    }

    public AnvilGUI.Builder GUI_Anvil_Money (Player p, SQLGetter data, ENCORE plugin, int type){
        AnvilGUI.Builder anvil_float_money = new AnvilGUI.Builder()
                .onComplete((player, text ) -> {
                    if (Float.parseFloat(text) != 0){
                        float ammountF = Float.parseFloat(text);
                        int ammountINV = 0;

                        for (ItemStack playerINV : p.getInventory().getContents()){
                            if (playerINV != null && playerINV.getType() == Material.GOLD_NUGGET){
                                ammountINV = ammountINV + playerINV.getAmount();
                            }
                        }

                        if (type == 1 && (int) ammountF <= data.getBank(p.getUniqueId())){
                            data.removeBank(p.getUniqueId(), (int) ammountF);
                            p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, (int) ammountF));
                            p.sendMessage(ChatColor.DARK_GREEN + "Вы сняли " + ChatColor.GOLD + (int) ammountF + "₡" + ChatColor.DARK_GREEN + " со счета");
                            return AnvilGUI.Response.close();

                        } else if (type == 2 && ammountINV >= (int) ammountF){
                            p.getInventory().removeItem(new ItemStack(Material.GOLD_NUGGET, (int) ammountF));
                            data.addbank(p.getUniqueId(), (int) ammountF);
                            p.sendMessage(ChatColor.DARK_GREEN + "Вы положили " + ChatColor.GOLD + (int) ammountF + "₡" + ChatColor.DARK_GREEN + " на счет");
                            return AnvilGUI.Response.close();
                        } else {
                            return AnvilGUI.Response.text("Неверная сумма");
                        }
                    }else{
                        return AnvilGUI.Response.text("Неверная сумма");
                    }
                })
                .itemLeft(Item.createS("PAPER", 1, ChatColor.LIGHT_PURPLE  + "Введите Сумму", new String[0]))
                .title(ChatColor.LIGHT_PURPLE + "Банк")
                .plugin(plugin);

        return anvil_float_money;
    }

    public Inventory GUI_TypeDeposit (Player p, SQLGetter data){
        Inventory inv_typeDeposit = Bukkit.createInventory(p, 9, ChatColor.AQUA + "Выберете тип опирации");

        for (int i = 0; i < 9; i++) {
            Item.create(inv_typeDeposit, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.create(inv_typeDeposit, "GREEN_CONCRETE", 1, 3, ChatColor.GREEN + "Пополнить Вклад", new String[0]);
        Item.create(inv_typeDeposit, "RED_CONCRETE", 1, 5, ChatColor.RED + "Снять с Вклада", new String[0]);

        Item.create(inv_typeDeposit, "BARRIER", 1, 8, ChatColor.RED + "Назад к счетам", new String[0]);

        return inv_typeDeposit;
    }

    public Inventory GUI_TypeMoney (Player p, SQLGetter data){
        Inventory inv_typeMoney = Bukkit.createInventory(p, 9, ChatColor.LIGHT_PURPLE + "Выберете тип опирации");

        for (int i = 0; i < 9; i++) {
            Item.create(inv_typeMoney, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.create(inv_typeMoney, "GREEN_CONCRETE", 1, 3, ChatColor.GREEN + "Пополнить Счет", new String[0]);
        Item.create(inv_typeMoney, "RED_CONCRETE", 1, 5, ChatColor.RED + "Снять со Счета", new String[0]);

        Item.create(inv_typeMoney, "BARRIER", 1, 8, ChatColor.RED + "Назад к счетам", new String[0]);

        return inv_typeMoney;
    }

    public void clicked_bankmain(Player p, ItemStack clicked, SQLGetter data, ENCORE encore){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Закрыть Меню Банка")) {
            p.closeInventory();
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "Открыть ячейку")) {
            p.openInventory(this.GUI_Vault(p, data));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "Купить ячейку в банке на 9 предметов")) {
            if (data.getBank(p.getUniqueId()) < 250){
                p.sendMessage(ChatColor.DARK_RED + "У вас не хватает денег для покупки ячейки");
                p.closeInventory();
            }else {
                data.removeBank(p.getUniqueId(), 250);
                data.addhasVault(p.getUniqueId(), 1);
                p.sendMessage(ChatColor.DARK_GREEN + "Вы успешно преобрели ячейку в банке");
                p.openInventory(this.GUI_BankMain(p,data, 1));
            }
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GREEN + "Операции со счетами")) {
            p.openInventory(this.GUI_Banking(p, data));
        }
    }

    public void clicked_banking(Player p, ItemStack clicked, SQLGetter data, int isVaultBoughtINT, ENCORE encore){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад в Меню Банка")) {
            p.openInventory(this.GUI_BankMain(p, data, isVaultBoughtINT));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Перевести деньги со счета другому игроку")) {
            p.openInventory(this.GUI_Perevod_Players(p, data));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "Положить/Снять деньги с накопительного вклада")) {
            p.openInventory(this.GUI_TypeDeposit(p, data));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.LIGHT_PURPLE + "Положить/Обналичить деньги со счета в купюры")) {
            p.openInventory(this.GUI_TypeMoney(p, data));
        }
    }

    public void clicked_perevodP(Player p, ItemStack clicked, SQLGetter data, ENCORE encore){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к счетам")) {
            p.openInventory(this.GUI_Banking(p, data));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.WHITE + "Прыдыдущая страница")) {
            this.page.put(p, (Integer)this.page.get(p) - 1);
            p.openInventory(this.GUI_Perevod_Players(p, data));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.WHITE + "Следующая страница")) {
            this.page.put(p, (Integer)this.page.get(p) + 1);
            p.openInventory(this.GUI_Perevod_Players(p, data));
        } else if (clicked.getItemMeta().getLore() != null) {
            Player target_p = Bukkit.getServer().getPlayer(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
            if (target_p != null) {
                target_player.put(p, target_p);
                this.GUI_Anvil_Perevod(p, data, encore, target_p).open(p);
            }
        }
    }

    public void clicked_typeDeposit(Player p, ItemStack clicked, SQLGetter data, ENCORE encore){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к счетам")) {
            p.openInventory(this.GUI_Banking(p, data));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GREEN + "Пополнить Вклад")) {
            this.GUI_Anvil_Deposit(p, data, encore, 2).open(p);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Снять с Вклада")) {
            this.GUI_Anvil_Deposit(p, data, encore, 1).open(p);
        }
    }

    public void clicked_typeMoney(Player p, ItemStack clicked, SQLGetter data, ENCORE encore){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к счетам")) {
            p.openInventory(this.GUI_Banking(p, data));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GREEN + "Пополнить Счет")) {
            this.GUI_Anvil_Money(p, data, encore, 2).open(p);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Снять со Счета")) {
            this.GUI_Anvil_Money(p, data, encore, 1).open(p);
        }
    }
}
