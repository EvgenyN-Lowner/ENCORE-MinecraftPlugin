package com.evgenyn.encore.gui;

import com.evgenyn.encore.ENCORE;
import com.evgenyn.encore.SQL.SQLGetter;
import com.evgenyn.encore.utilities.Fireworks;
import com.evgenyn.encore.utilities.InventoriesSerializer;
import com.evgenyn.encore.utilities.InventoryGUI;
import com.evgenyn.encore.utilities.Item;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


public class AdminPanelGUI {
    public static HashMap<Player, Player> target_player = new HashMap();
    private final HashMap<Player, Integer> ban_months = new HashMap();
    private final HashMap<Player, Integer> ban_days = new HashMap();
    private final HashMap<Player, Integer> ban_hours = new HashMap();
    private final HashMap<Player, Integer> ban_minutes = new HashMap();
    private final HashMap<Player, Integer> page = new HashMap();
    private final HashMap<Player, Integer> pages = new HashMap();

    private final long tickInterval = 50;
    private transient long lastPoll = System.nanoTime();


    public AdminPanelGUI() {
    }

    public Inventory AdminGUI_Main(Player p) {
        Inventory adminGUI_main = Bukkit.createInventory(p, 36, ChatColor.RED + "Админ Панель");
        Player randomPlayer = (Player) Bukkit.getOnlinePlayers().stream().findAny().get();

        for (int i = 0; i < 36; i++) {
            Item.create(adminGUI_main, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.createPlayerHead(adminGUI_main, randomPlayer.getName(), 1, 10, ChatColor.DARK_GREEN + "Онлайн Игроки", new String[0]);
        Item.create(adminGUI_main, "TARGET", 1, 20, ChatColor.DARK_RED + "Анти-Чит", new String[0]);
        Item.createPlayerHead(adminGUI_main, "BlockminersTV", 1, 12, ChatColor.GREEN + "Управление Миром", new String[0]);
        Item.createPlayerHead(adminGUI_main, "God", 1, 22, ChatColor.GRAY + "Управление Сервером", new String[0]);

        Item.create(adminGUI_main, "BARRIER", 1, 35, ChatColor.RED + "Закрыть Админ Панель", new String[0]);
        return adminGUI_main;
    }

    private Inventory GUI_Players(Player p) {
        ArrayList<String> pl = new ArrayList();
        Inventory inv_players = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.DARK_GREEN + "Панель Онлайн Игроков");
        Iterator var4 = Bukkit.getServer().getOnlinePlayers().iterator();

        while(var4.hasNext()) {
            Player all = (Player)var4.next();
            pl.add(all.getName());
        }

        Collections.sort(pl);
        int online = pl.size();
        this.pages.put(p, (int)Math.ceil((double)((float)online / 45.0F)));

        int player_slot;
        for(player_slot = 45; player_slot <= 52; ++player_slot) {
            Item.create(inv_players, "BLACK_STAINED_GLASS_PANE", 1, player_slot, " ", new String[0]);
        }

        player_slot = ((Integer)this.page.getOrDefault(p, 1) - 1) * 45;

        for(int i = 0; i < 45; ++i) {
            if (player_slot < online ) {
                Player targerPL = p.getServer().getPlayer(pl.get(player_slot));
                String Admin = "";
                if(targerPL.isOp()){
                    Admin = "АДМИНИСТРАТОР";
                }

                String hp = new DecimalFormat("#0.0").format(targerPL.getHealth());
                String eat = new DecimalFormat("#0.0").format(targerPL.getFoodLevel());
                String exp = new DecimalFormat("#0.0").format(targerPL.getExp());

                Item.createPlayerHead(inv_players, (String)pl.get(player_slot), 1, i,ChatColor.YELLOW + (String) pl.get(player_slot), ChatColor.GOLD + "---------",
                        ChatColor.GOLD + "Здоровье: " + ChatColor.RED + hp,
                        ChatColor.GOLD + "Еда: " + ChatColor.GREEN + eat,
                        ChatColor.GOLD + "EXP: " + ChatColor.AQUA + exp,
                        ChatColor.YELLOW + "Текущий Мир: " + ChatColor.DARK_GREEN + targerPL.getWorld().getName(),
                        ChatColor.YELLOW + "Гейм мод: " + ChatColor.LIGHT_PURPLE + targerPL.getGameMode(),
                        ChatColor.DARK_RED + Admin);
                ++player_slot;
            } else {
                Item.create(inv_players, "GRAY_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
            }
        }

        if ((Integer)this.page.getOrDefault(p, 1) > 1) {
            Item.create(inv_players, "STONE_BUTTON", 1, 48, ChatColor.YELLOW + "Прыдыдущая страница", new String[0]);
        }

        if ((Integer)this.pages.getOrDefault(p, 1) > 1) {
            Item.create(inv_players, "BOOK", (Integer)this.page.getOrDefault(p, 1), 49, ChatColor.GOLD + "Текущая страница" + " " + this.page.getOrDefault(p, 1), new String[0]);
        }

        if ((Integer)this.pages.get(p) > (Integer)this.page.getOrDefault(p, 1)) {
            Item.create(inv_players, "STONE_BUTTON", 1, 50, ChatColor.YELLOW + "Следующая страница", new String[0]);
        }

        Item.create(inv_players, "BARRIER", 1, 53, ChatColor.RED + "Назад в Меню", new String[0]);
        return inv_players;
    }

    public Inventory GUI_AntiCheat(Player p) {
        p.sendMessage(ChatColor.DARK_RED + "В разработке");
        return null;
    }

    public Inventory GUI_World(Player p) {
        Inventory inv_world = Bukkit.createInventory((InventoryHolder)null, 27, ChatColor.AQUA + "Управление Миром");

        String weather = "Неизветсно";
        String time = "Неизветсно";

        for(int i = 1; i < 26; ++i) {
            Item.create(inv_world, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        if(p.getPlayer().getWorld().isThundering()){
            weather = ChatColor.DARK_AQUA + "Шторм";
            Item.create(inv_world, "LIGHT_BLUE_CONCRETE", 1, 14, ChatColor.AQUA + "Переключить на Дождь", new String[0]);
        }else if (p.getPlayer().getWorld().hasStorm()){
            weather = ChatColor.AQUA + "Дождь";
            Item.create(inv_world, "WHITE_CONCRETE", 1, 14, ChatColor.GOLD + "Переключить на Ясно", new String[0]);
        }else {
            weather = ChatColor.GOLD + "Ясно";
            Item.create(inv_world, "BLUE_CONCRETE", 1, 14, ChatColor.DARK_AQUA + "Переключить на Шторм", new String[0]);
        }
        if (p.getPlayer().getWorld().getTime() < 13000L) {
            time = ChatColor.DARK_AQUA + "День";
            Item.create(inv_world, "BLACK_CONCRETE", 1, 12, ChatColor.AQUA + "Переключить на Ночь", new String[0]);
        } else {
            time = ChatColor.AQUA + "Ночь";
            Item.create(inv_world, "YELLOW_CONCRETE", 1, 12, ChatColor.GOLD + "Переключить на День", new String[0]);
        }

        Item.createPlayerHead(inv_world, "BlockminersTV", 1, 0, ChatColor.DARK_GREEN + "Информация о мире", ChatColor.GREEN + "---------",
                ChatColor.GREEN + "Текущий Мир: " + ChatColor.DARK_GREEN + p.getPlayer().getWorld().getName(),
                ChatColor.GREEN + "Время в мире: " + ChatColor.DARK_GREEN + time,
                ChatColor.GREEN + "Погода в мире: " + weather,
                ChatColor.GREEN + "Игроков в мире: " + ChatColor.LIGHT_PURPLE + p.getPlayer().getWorld().getPlayers().size()
                );

        Item.create(inv_world, "BARRIER", 1, 26, ChatColor.RED + "Назад в Меню", new String[0]);
        return inv_world;
    }

    public Inventory GUI_ServerSettings(Player p) {
        Inventory inv_serverSettings = Bukkit.createInventory(p, 27, ChatColor.BLACK + "Управление Сервером");

        for (int i = 1; i < 26; i++) {
            Item.create(inv_serverSettings, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        int mb = 1024 * 1024;
        long totalMemory = Runtime.getRuntime().totalMemory() / mb;
        long freeMemory = Runtime.getRuntime().freeMemory() / mb;
        long usedMemory = totalMemory - freeMemory;
        String loadedWords = "";

        final long startTime = System.nanoTime();
        long timeSpent = (startTime - lastPoll) / 1000;
        if (timeSpent == 0)
        {
            timeSpent = 1;
        }
        double tps = tickInterval * 1000000.0 / timeSpent;

        for (int i = 0; i < p.getServer().getWorlds().size(); i++){
            loadedWords = loadedWords + p.getServer().getWorlds().get(i).getName() + " | ";
        }

        Item.createPlayerHead(inv_serverSettings, "God", 1, 0, ChatColor.GRAY + "Информация о сервере",ChatColor.WHITE + "---------",
                ChatColor.WHITE + "Память: " + ChatColor.LIGHT_PURPLE + usedMemory + " / " + totalMemory,
                ChatColor.WHITE + "TPS: " + ChatColor.AQUA + "/tps",
                ChatColor.WHITE + "Загруженные миры: " + ChatColor.DARK_GREEN + loadedWords
                );

        Item.createPlayerHead(inv_serverSettings, "GOOFYGIANT", 1, 12, ChatColor.GREEN + "Сохранить миры", new String[0]);
        Item.createPlayerHead(inv_serverSettings, "luece", 1, 14, ChatColor.DARK_RED + "Рестарт Сервера", new String[0]);


        Item.create(inv_serverSettings, "BARRIER", 1, 26, ChatColor.RED + "Назад в Меню", new String[0]);
        return inv_serverSettings;
    }

    public Inventory GUI_Players_Settings(Player p, Player target_player) {
        String inventory_players_settings_name = ChatColor.DARK_GREEN + "Игрок: " + ChatColor.GOLD + target_player.getName();
        Inventory inv_players_settings = Bukkit.createInventory((InventoryHolder)null, 45, inventory_players_settings_name);
        String Admin = "";

        String hp = new DecimalFormat("#0.0").format(target_player.getHealth());
        String eat = new DecimalFormat("#0.0").format(target_player.getFoodLevel());
        String exp = new DecimalFormat("#0.0").format(target_player.getExp());

        if(target_player.isOp()){
            Admin = "АДМИНИСТРАТОР";
        }

        for(int i = 0; i < 44; ++i) {
            Item.create(inv_players_settings, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.createPlayerHead(inv_players_settings, target_player.getName(), 1, 0 , ChatColor.DARK_GREEN + "Информация об игроке", ChatColor.GOLD + "---------",
                ChatColor.GOLD + "Здоровье: " + ChatColor.RED + hp,
                ChatColor.GOLD + "Еда: " + ChatColor.GREEN + eat,
                ChatColor.GOLD + "EXP: " + ChatColor.AQUA + exp,
                ChatColor.YELLOW + "Текущий Мир: " + ChatColor.DARK_GREEN + target_player.getWorld().getName(),
                ChatColor.YELLOW + "Гейм мод: " + ChatColor.LIGHT_PURPLE + target_player.getGameMode(),
                ChatColor.DARK_RED + Admin);

        Item.create(inv_players_settings, "DIAMOND_SWORD", 1, 19, ChatColor.BLUE + "Действия с игроком", new String[0]);
        Item.create(inv_players_settings, "ENDER_EYE", 1, 21, ChatColor.DARK_RED + "Бан Меню", new String[0]);

        Item.create(inv_players_settings, "CHEST", 1, 7, ChatColor.GOLD + "Посмотреть Инвентарь Игрока", new String[0]);
        Item.create(inv_players_settings, "ENDER_CHEST", 1, 8, ChatColor.LIGHT_PURPLE + "Посмотреть Эндер Сундук Игрока", new String[0]);
        Item.createPlayerHead(inv_players_settings, "MrSnowDK", 1, 6, ChatColor.DARK_GREEN + "Банк Игрока", new String[0]);

        Item.create(inv_players_settings, "REDSTONE", 1, 42, ChatColor.GOLD + "IP / UUID", ChatColor.GOLD + "----------",
                ChatColor.GOLD + "IP: " + ChatColor.LIGHT_PURPLE + target_player.getAddress().getAddress().getHostAddress(),
                ChatColor.GOLD + "UUID: " + ChatColor.AQUA + target_player.getUniqueId());
        Item.create(inv_players_settings, "NETHER_STAR", 1, 36, ChatColor.AQUA + "Телепортироваться к Игроку", new String[0]);
        Item.create(inv_players_settings, "BARRIER", 1, 44, ChatColor.RED + "Назад к Игрокам", new String[0]);

        return inv_players_settings;
    }

    public Inventory GUI_BankingAP(Player p, SQLGetter data, Player target_player, int isVaultBought){
        Inventory inv_bankingAP = Bukkit.createInventory(p, 9, ChatColor.DARK_GREEN + "Управление Банком Игрока");

        for (int i = 1; i < 9; i++) {
            Item.create(inv_bankingAP, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }
        Item.createPlayerHead(inv_bankingAP, target_player.getName(), 1, 0, ChatColor.DARK_GREEN + "Банк Игрока", ChatColor.DARK_GREEN + "---------",
                ChatColor.GREEN + "Основной счет: " + ChatColor.GOLD + data.getBank(target_player.getUniqueId()) + "₡",
                ChatColor.GREEN + "Вклад: " + ChatColor.GOLD + data.getVklad(target_player.getUniqueId()) + "₡"
                );

        Item.create(inv_bankingAP, "GOLD_BLOCK", 1, 2, ChatColor.GOLD + "Основной Счет", new String[0]);
        Item.create(inv_bankingAP, "DIAMOND_BLOCK", 1, 4, ChatColor.AQUA + "Вклад", new String[0]);
        if (isVaultBought == 1) {
            Item.create(inv_bankingAP, "ENDER_CHEST", 1, 6, ChatColor.LIGHT_PURPLE + "Ячейка", new String[0]);
        }

        Item.create(inv_bankingAP, "BARRIER", 1, 8, ChatColor.RED + "Назад к Игроку", new String[0]);

        return inv_bankingAP;
    }

    public Inventory GUI_BankingAPBank(Player p, SQLGetter data, Player target_player){
        Inventory inv_bankingAPBank = Bukkit.createInventory(p, 9, ChatColor.GOLD + "Основной Счет");

        for (int i = 1; i < 9; i++) {
            Item.create(inv_bankingAPBank, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }
        Item.createPlayerHead(inv_bankingAPBank, target_player.getName(), 1, 0, ChatColor.DARK_GREEN + "Банк Игрока", ChatColor.DARK_GREEN + "---------",
                ChatColor.GREEN + "Основной счет: " + ChatColor.GOLD + data.getBank(target_player.getUniqueId()) + "₡"
        );

        Item.create(inv_bankingAPBank, "GREEN_CONCRETE", 1, 3, ChatColor.GREEN + "Увеличить", new String[0]);
        Item.create(inv_bankingAPBank, "RED_CONCRETE", 1, 5, ChatColor.RED + "Уменьшить", new String[0]);

        Item.create(inv_bankingAPBank, "BARRIER", 1, 8, ChatColor.RED + "Назад к Банку Игрока", new String[0]);

        return inv_bankingAPBank;
    }

    public Inventory GUI_BankingAPDeposit(Player p, SQLGetter data, Player target_player){
        Inventory inv_bankingAPDeposit = Bukkit.createInventory(p, 9, ChatColor.AQUA + "Вклад");

        for (int i = 1; i < 9; i++) {
            Item.create(inv_bankingAPDeposit, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }
        Item.createPlayerHead(inv_bankingAPDeposit, target_player.getName(), 1, 0, ChatColor.DARK_GREEN + "Банк Игрока", ChatColor.DARK_GREEN + "---------",
                ChatColor.GREEN + "Вклад: " + ChatColor.GOLD + data.getVklad(target_player.getUniqueId()) + "₡"
        );

        Item.create(inv_bankingAPDeposit, "GREEN_CONCRETE", 1, 3, ChatColor.GREEN + "Увеличить", new String[0]);
        Item.create(inv_bankingAPDeposit, "RED_CONCRETE", 1, 5, ChatColor.RED + "Уменьшить", new String[0]);

        Item.create(inv_bankingAPDeposit, "BARRIER", 1, 8, ChatColor.RED + "Назад к Банку Игрока", new String[0]);

        return inv_bankingAPDeposit;
    }

    public AnvilGUI.Builder GUI_AnvilAP_Bank (Player p, SQLGetter data, ENCORE plugin, int type, Player target_player) {
        AnvilGUI.Builder anvil_float_depositAP = new AnvilGUI.Builder()
                .onComplete((player, text) -> {
                    if (Float.parseFloat(text) != 0) {
                        float ammountF = Float.parseFloat(text);
                        if (type == 1) {
                            data.addbank(target_player.getUniqueId(), ammountF);
                            p.sendMessage(ChatColor.DARK_GREEN + "Вы добавили " + ChatColor.GOLD + ammountF + "₡" + ChatColor.DARK_GREEN + " на счет игрока " + ChatColor.GOLD + target_player.getName());
                            return AnvilGUI.Response.close();
                        } else if (type == 2 && Float.parseFloat(text) <= data.getBank(target_player.getUniqueId())) {
                            data.removeBank(target_player.getUniqueId(), ammountF);
                            p.sendMessage(ChatColor.DARK_GREEN + "Вы сняли " + ChatColor.GOLD + ammountF + "₡" + ChatColor.DARK_GREEN + " со счета игрока " + ChatColor.GOLD + target_player.getName());
                            return AnvilGUI.Response.close();
                        } else {
                            return AnvilGUI.Response.text("Неверная сумма");
                        }
                    } else {
                        return AnvilGUI.Response.text("Неверная сумма");
                    }
                })
                .itemLeft(Item.createS("PAPER", 1, ChatColor.GOLD + "Введите Сумму", new String[0]))
                .title(ChatColor.GOLD + "Банк")
                .plugin(plugin);

        return anvil_float_depositAP;
    }

    public AnvilGUI.Builder GUI_AnvilAP_Deposit (Player p, SQLGetter data, ENCORE plugin, int type, Player target_player) {
        AnvilGUI.Builder anvil_float_depositAP = new AnvilGUI.Builder()
                .onComplete((player, text) -> {
                    if (Float.parseFloat(text) != 0) {
                        float ammountF = Float.parseFloat(text);
                        if (type == 1 ) {
                            data.addVklad(target_player.getUniqueId(), ammountF);
                            p.sendMessage(ChatColor.DARK_GREEN + "Вы добавили " + ChatColor.GOLD + ammountF + "₡" + ChatColor.DARK_GREEN + " на вклад игрока " + ChatColor.GOLD + target_player.getName());
                            return AnvilGUI.Response.close();
                        } else if (type == 2 && Float.parseFloat(text) <= data.getVklad(target_player.getUniqueId())) {
                            data.removeVklad(target_player.getUniqueId(), ammountF);
                            p.sendMessage(ChatColor.DARK_GREEN + "Вы сняли " + ChatColor.GOLD + ammountF + "₡" + ChatColor.DARK_GREEN + " с вклада игрока " + ChatColor.GOLD + target_player.getName());
                            return AnvilGUI.Response.close();
                        } else {
                            return AnvilGUI.Response.text("Неверная сумма");
                        }
                    } else {
                        return AnvilGUI.Response.text("Неверная сумма");
                    }
                })
                .itemLeft(Item.createS("PAPER", 1, ChatColor.AQUA + "Введите Сумму", new String[0]))
                .title(ChatColor.AQUA + "Банк")
                .plugin(plugin);

        return anvil_float_depositAP;
    }

    public Inventory GUI_chest(Player p, Player target_player) {
        String inventory_players_chest_name = ChatColor.GOLD + "Инвентарь Игрока: " + ChatColor.GREEN + target_player.getName();
        Inventory inv_chest = Bukkit.createInventory((InventoryHolder)null, 54, inventory_players_chest_name);

        inv_chest.setItem(0, target_player.getInventory().getHelmet());
        inv_chest.setItem(1, target_player.getInventory().getChestplate());
        inv_chest.setItem(2, target_player.getInventory().getLeggings());
        inv_chest.setItem(3, target_player.getInventory().getBoots());

        inv_chest.setItem(8, target_player.getInventory().getItemInOffHand());

        for (int i = 0; i < 9; ++i){
            inv_chest.setItem(i+36, target_player.getInventory().getItem(i));
        }

        for (int i = 9; i < 36; ++i){
            inv_chest.setItem(i, target_player.getInventory().getItem(i));
        }

        for(int i = 4; i < 8; ++i) {
            Item.create(inv_chest, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        for(int i = 45; i < 53; ++i) {
            Item.create(inv_chest, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.create(inv_chest, "NETHER_STAR", 1, 45, ChatColor.AQUA + "Изменить Инвентарь Игрока", new String[0]);
        Item.create(inv_chest, "BARRIER", 1, 53, ChatColor.RED + "Назад к Информации о Игроке", new String[0]);

        return inv_chest;
    }

    public Inventory GUI_ENDchest(Player p, Player target_player) {
        String inventory_players_ENDchest_name = ChatColor.LIGHT_PURPLE + "Эндер Сундук Игрока: " + ChatColor.GREEN + target_player.getName();
        Inventory inv_ENDchest = Bukkit.createInventory((InventoryHolder)null, 36, inventory_players_ENDchest_name);

        for (int i = 0; i < 27; ++i){
            inv_ENDchest.setItem(i, target_player.getEnderChest().getItem(i));
        }

        for(int i = 27; i < 35; ++i) {
            Item.create(inv_ENDchest, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.create(inv_ENDchest, "NETHER_STAR", 1, 27, ChatColor.LIGHT_PURPLE + "Изменить Эндер Сундук Игрока", new String[0]);
        Item.create(inv_ENDchest, "BARRIER", 1, 35, ChatColor.RED + "Назад к Информации о Игроке", new String[0]);

        return inv_ENDchest;
    }

    public Inventory GUI_Vault_ADMIN (Player p, SQLGetter data, Player target_player){
        Inventory inv_vault = Bukkit.createInventory(p, 9, ChatColor.LIGHT_PURPLE + "Ячейка");

        String dataNBT = data.getVaultNBT(target_player.getUniqueId());
        InventoriesSerializer vaultSer = new InventoriesSerializer();
        try {
            ItemStack[] vault_items = vaultSer.fromBase64(dataNBT);
            inv_vault.setContents(vault_items);
        } catch (Exception e){
            e.printStackTrace();
        }

        return inv_vault;
    }

    public Inventory GUI_Ban(Player p, Player target) {
        String inventory_ban_name = ChatColor.DARK_RED + "Бан Меню Игрока: " + ChatColor.GOLD + target.getName();
        target_player.put(p, target);
        Inventory inv_ban = Bukkit.createInventory((InventoryHolder)null, 36, inventory_ban_name);

        for(int i = 0; i < 35; ++i) {
            Item.create(inv_ban, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        if ((Integer)this.ban_months.getOrDefault(p, 0) == 0) {
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 12, ChatColor.GOLD + "Месяцев", new String[0]);
        } else {
            Item.create(inv_ban, "CLOCK", (Integer)this.ban_months.getOrDefault(p, 0), 12, ChatColor.GOLD + "Месяцев", new String[0]);
        }

        if ((Integer)this.ban_days.getOrDefault(p, 0) == 0) {
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 13, ChatColor.GOLD + "Дней", new String[0]);
        } else {
            Item.create(inv_ban, "CLOCK", (Integer)this.ban_days.getOrDefault(p, 0), 13, ChatColor.GOLD + "Дней", new String[0]);
        }

        if ((Integer)this.ban_hours.getOrDefault(p, 0) == 0) {
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 14, ChatColor.GOLD + "Часов", new String[0]);
        } else {
            Item.create(inv_ban, "CLOCK", (Integer)this.ban_hours.getOrDefault(p, 0), 14, ChatColor.GOLD + "Часов", new String[0]);
        }

        if ((Integer)this.ban_minutes.getOrDefault(p, 0) == 0) {
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 15, ChatColor.GOLD + "Минут", new String[0]);
        } else {
            Item.create(inv_ban, "CLOCK", (Integer)this.ban_minutes.getOrDefault(p, 0), 15, ChatColor.GOLD + "Минут", new String[0]);
        }

        Item.create(inv_ban, "GRAY_TERRACOTTA", 1, 8, ChatColor.AQUA + "Кикнуть игрока", new String[0]);
        Item.create(inv_ban, "GREEN_TERRACOTTA", 1, 27, ChatColor.GREEN + "Информация о предыдущих нарушиниях", new String[0]);

        Item.create(inv_ban, "WHITE_TERRACOTTA", 1, 29, ChatColor.GOLD + "Временный Мут", new String[0]);
        Item.create(inv_ban, "ORANGE_TERRACOTTA", 1, 30, ChatColor.GOLD + "Временная Блокировка", new String[0]);
        Item.create(inv_ban, "MAGENTA_TERRACOTTA", 1, 31, ChatColor.GOLD + "Мут - Перманентно", new String[0]);
        Item.create(inv_ban, "LIGHT_BLUE_TERRACOTTA", 1, 32, ChatColor.GOLD + "Бан - Перманентно", new String[0]);
        Item.create(inv_ban, "YELLOW_TERRACOTTA", 1, 33, ChatColor.GOLD + "Блокировка по IP - Перманентно", new String[0]);
        Item.create(inv_ban, "BARRIER", 1, 35, ChatColor.RED + "Назад к Информации о Игроке", new String[0]);
        return inv_ban;
    }

    public Inventory GUI_Actions(Player p, Player target) {
        String inventory_action_name = ChatColor.AQUA + "Действия с  Игроком: " + ChatColor.GOLD + target.getName();
        target_player.put(p, target);
        Inventory inv_actions = Bukkit.createInventory((InventoryHolder)null, 36, inventory_action_name);

        for(int i = 0; i < 35; ++i) {
            Item.create(inv_actions, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.createPlayerHead(inv_actions, "Omenoctoa", 1, 0, ChatColor.GOLD + "Максимальное ХП", new String[0]);
        Item.createPlayerHead(inv_actions, "simbasbestbud", 1, 1, ChatColor.GOLD + "Максимальная ЕДА", new String[0]);

        Item.create(inv_actions, "WHITE_TERRACOTTA", 1, 5, ChatColor.GREEN + "ГеймМод - Сурвайвал", new String[0]);
        Item.create(inv_actions, "ORANGE_TERRACOTTA", 1, 6, ChatColor.AQUA + "ГеймМод - Креатив", new String[0]);
        Item.create(inv_actions, "MAGENTA_TERRACOTTA", 1, 7, ChatColor.DARK_RED + "ГеймМод - Адвенчер", new String[0]);
        Item.create(inv_actions, "LIGHT_BLUE_TERRACOTTA", 1, 8, ChatColor.LIGHT_PURPLE + "ГеймМод - Спектатор", new String[0]);

        Item.create(inv_actions, "NETHERITE_SWORD", 1, 10, ChatColor.AQUA + "Убить Игрока", new String[0]);
        Item.create(inv_actions, "CAMPFIRE", 1, 12, ChatColor.RED + "Поджечь", new String[0]);
        Item.create(inv_actions, "END_CRYSTAL", 1, 18, ChatColor.GOLD + "Ударить Молнией", new String[0]);
        Item.create(inv_actions, "FIREWORK_ROCKET", 1, 20, ChatColor.GRAY + "Случайный Фейерверк", new String[0]);

        Item.create(inv_actions, "BARRIER", 1, 35, ChatColor.RED + "Назад к Информации о Игроке", new String[0]);
        return inv_actions;
    }

    public void clicked_main(Player p, int slot, ItemStack clicked, Inventory inv) {
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Закрыть Админ Панель")) {
            p.closeInventory();
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.DARK_GREEN + "Онлайн Игроки")) {
            p.openInventory(this.GUI_Players(p));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.DARK_RED + "Анти-Чит")) {
            p.openInventory(this.GUI_AntiCheat(p));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GREEN + "Управление Миром")) {
            p.openInventory(this.GUI_World(p));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GRAY + "Управление Сервером")) {
            p.openInventory(this.GUI_ServerSettings(p));
        }

    }

    public void clicked_actions(Player p, int slot, ItemStack clicked, Inventory inv, Player target_player) {
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Максимальное ХП")) {
            target_player.setHealth(20.0D);
            target_player.setFireTicks(0);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Максимальная ЕДА")) {
            target_player.setFoodLevel(20);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GREEN + "ГеймМод - Сурвайвал")) {
            target_player.setGameMode(GameMode.SURVIVAL);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "ГеймМод - Креатив")) {
            target_player.setGameMode(GameMode.CREATIVE);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.DARK_RED + "ГеймМод - Адвенчер")) {
            target_player.setGameMode(GameMode.ADVENTURE);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.LIGHT_PURPLE + "ГеймМод - Спектатор")) {
            target_player.setGameMode(GameMode.SPECTATOR);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "Убить Игрока")) {
            target_player.setHealth(0.0D);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Поджечь")) {
            target_player.setFireTicks(500);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Ударить Молнией")) {
            target_player.getWorld().strikeLightning(target_player.getLocation());
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GRAY + "Случайный Фейерверк")) {
            Fireworks.createRandom(target_player);
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к Информации о Игроке")) {
            p.openInventory(this.GUI_Players_Settings(p, target_player));
        }

    }

    public void clicked_players(Player p, int slot, ItemStack clicked, Inventory inv) {
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED +"Назад в Меню")) {
            p.openInventory(this.AdminGUI_Main(p));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.WHITE + "Прыдыдущая страница")) {
            this.page.put(p, (Integer)this.page.get(p) - 1);
            p.openInventory(this.GUI_Players(p));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.WHITE + "Следующая страница")) {
            this.page.put(p, (Integer)this.page.get(p) + 1);
            p.openInventory(this.GUI_Players(p));
        } else if (clicked.getItemMeta().getLore() != null) {
            Player target_p = Bukkit.getServer().getPlayer(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
            if (target_p != null) {
                target_player.put(p, target_p);
                p.openInventory(this.GUI_Players_Settings(p, target_p));
            }
        }
    }

    public void clicked_world(Player p, int slot, ItemStack clicked, Inventory inv){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад в Меню")) {
            p.openInventory(this.AdminGUI_Main(p));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "Переключить на Дождь")) {
            Bukkit.dispatchCommand(p, "weather rain" );
            p.openInventory(this.GUI_World(p));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Переключить на Ясно")) {
            Bukkit.dispatchCommand(p, "weather clear");
            p.openInventory(this.GUI_World(p));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.DARK_AQUA + "Переключить на Шторм")) {
            Bukkit.dispatchCommand(p, "weather thunder");
            p.openInventory(this.GUI_World(p));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "Переключить на Ночь")) {
            Bukkit.dispatchCommand(p, "time set night");
            p.openInventory(this.GUI_World(p));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Переключить на День")) {
            Bukkit.dispatchCommand(p, "time set day");
            p.openInventory(this.GUI_World(p));
        }
    }
    public void clicked_serverSettings(Player p, int slot, ItemStack clicked, Inventory inv){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад в Меню")) {
            p.openInventory(this.AdminGUI_Main(p));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GREEN + "Сохранить миры")) {
            Bukkit.dispatchCommand(p, "save-all");
            p.openInventory(this.GUI_ServerSettings(p));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.DARK_RED + "Рестарт Сервера")) {
            Bukkit.dispatchCommand(p, "restart");
            p.closeInventory();
        }
    }

    public void clicked_playersSettings(Player p, int slot, ItemStack clicked, Inventory inv, SQLGetter data, Player target_player){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.BLUE + "Действия с игроком")) {
            p.openInventory(this.GUI_Actions(p, target_player));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.DARK_RED + "Бан Меню")) {
            p.openInventory(this.GUI_Ban(p, target_player));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Посмотреть Инвентарь Игрока")) {
            p.openInventory(this.GUI_chest(p, target_player));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.LIGHT_PURPLE + "Посмотреть Эндер Сундук Игрока")) {
            p.openInventory(this.GUI_ENDchest(p, target_player));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.DARK_GREEN + "Банк Игрока")) {
            int isVaultBought = data.gethasVault(target_player.getUniqueId());
            p.openInventory(this.GUI_BankingAP(p, data, target_player, isVaultBought));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "Телепортироваться к Игроку")) {
            Bukkit.dispatchCommand(p, "tp " + p.getName() + " " + target_player.getName());
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к Игрокам")) {
            p.openInventory(this.GUI_Players(p));
        }
    }

    public void clicked_bankingAP(Player p, int slot, ItemStack clicked, Inventory inv, SQLGetter data, Player target_player){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к Игроку")) {
                p.openInventory(this.GUI_Players_Settings(p, target_player));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Основной Счет")) {
                p.openInventory(this.GUI_BankingAPBank(p, data, target_player));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "Вклад")) {
                p.openInventory(this.GUI_BankingAPDeposit(p, data, target_player));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.LIGHT_PURPLE + "Ячейка")) {
            p.openInventory(this.GUI_Vault_ADMIN(p, data, target_player));
        }
    }

    public void clicked_bankingAPBank(Player p, int slot, ItemStack clicked, Inventory inv, SQLGetter data, ENCORE encore, Player target_player){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к Банку Игрока")) {
            int isVaultBought = data.gethasVault(target_player.getUniqueId());
                p.openInventory(this.GUI_BankingAP(p, data, target_player, isVaultBought));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GREEN + "Увеличить")) {
                this.GUI_AnvilAP_Bank(p, data, encore, 1, target_player).open(p);
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Уменьшить")) {
                this.GUI_AnvilAP_Bank(p, data, encore, 2, target_player).open(p);
        }
    }

    public void clicked_bankingAPDeposit(Player p, int slot, ItemStack clicked, Inventory inv, SQLGetter data, ENCORE encore, Player target_player){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к Банку Игрока")) {
            int isVaultBought = data.gethasVault(target_player.getUniqueId());
                p.openInventory(this.GUI_BankingAP(p, data, target_player, isVaultBought));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GREEN + "Увеличить")) {
                this.GUI_AnvilAP_Deposit(p, data, encore, 1, target_player).open(p);
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Уменьшить")) {
                this.GUI_AnvilAP_Deposit(p, data, encore, 2, target_player).open(p);
        }
    }


    public void clicked_chest(Player p, int slot, ItemStack clicked, Inventory inv, Player target_player){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к Информации о Игроке")) {
            p.openInventory(this.GUI_Players_Settings(p, target_player));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "Изменить Инвентарь Игрока")) {
            p.closeInventory();
            Bukkit.dispatchCommand(p, "openinv " + target_player.getName());
        }
    }

    public void clicked_ENDchest(Player p, int slot, ItemStack clicked, Inventory inv, Player target_player){
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к Информации о Игроке")) {
            p.openInventory(this.GUI_Players_Settings(p, target_player));
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.LIGHT_PURPLE + "Изменить Эндер Сундук Игрока")) {
            p.closeInventory();
            Bukkit.dispatchCommand(p, "openender " + target_player.getName());
        }
    }

    public void clicked_anticheat(Player p, int slot, ItemStack clicked, Inventory inv){

    }

    public void clicked_ban(Player p, int slot, ItemStack clicked, Inventory inv, Player target_player) {
        if (InventoryGUI.getClickedItem(clicked, ChatColor.RED + "Назад к Информации о Игроке")) {
            p.openInventory(this.GUI_Players_Settings(p, target_player));
        }

        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Временный Мут")) {
                        p.closeInventory();
                        long timeCalc = 43200 * (long)(Integer)this.ban_months.getOrDefault(p, 0) + 1440 * (long)(Integer)this.ban_days.getOrDefault(p, 0) + 60 * (long)(Integer)this.ban_hours.getOrDefault(p, 0) + (long)(Integer)this.ban_minutes.getOrDefault(p, 0);
            Bukkit.dispatchCommand(p, "tempmute " + target_player.getName() + " " + timeCalc + "m" + " Подробности в Discord канале #bans");
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Временная Блокировка")) {
                        p.closeInventory();
            long timeCalc = 43200 * (long)(Integer)this.ban_months.getOrDefault(p, 0) + 1440 * (long)(Integer)this.ban_days.getOrDefault(p, 0) + 60 * (long)(Integer)this.ban_hours.getOrDefault(p, 0) + (long)(Integer)this.ban_minutes.getOrDefault(p, 0);
            Bukkit.dispatchCommand(p, "tempban " + target_player.getName() + " " + timeCalc + "m" + " Подробности в Discord канале #bans");
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Мут - Перманентно")) {
                        p.closeInventory();
            Bukkit.dispatchCommand(p, "mute " + target_player.getName() + " Подробности в Discord канале #bans");
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Бан - Перманентно")) {
                        p.closeInventory();
            Bukkit.dispatchCommand(p, "ban " + target_player.getName() + " Подробности в Discord канале #bans");
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Блокировка по IP - Перманентно")) {
                        p.closeInventory();
            Bukkit.dispatchCommand(p, "banip " + target_player.getName() + " Подробности в Discord канале #bans");
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.AQUA + "Кикнуть игрока")) {
                        p.closeInventory();
            Bukkit.dispatchCommand(p, "kick " + target_player.getName() + " Подробности в Discord канале #bans");
        }
        if (InventoryGUI.getClickedItem(clicked, ChatColor.GREEN + "Информация о предыдущих нарушиниях")) {
            p.closeInventory();
            Bukkit.dispatchCommand(p, "history " + target_player.getName());
        }

        if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Месяцев")) {
            switch((Integer)this.ban_months.getOrDefault(p, 0)) {
                case 0:
                    this.ban_months.put(p, 1);
                    break; case 1: this.ban_months.put(p, 2);
                    break; case 2: this.ban_months.put(p, 3);
                    break; case 3: this.ban_months.put(p, 4);
                    break; case 4: this.ban_months.put(p, 5);
                    break; case 5: this.ban_months.put(p, 6);
                    break; case 6: this.ban_months.put(p, 7);
                    break; case 7: this.ban_months.put(p, 8);
                    break; case 8: this.ban_months.put(p, 9);
                    break; case 9: this.ban_months.put(p, 10);
                    break; case 10: this.ban_months.put(p, 11);
                    break; case 11: this.ban_months.put(p, 12);
                    break; case 12: this.ban_months.put(p, 0);
            }
            p.openInventory(this.GUI_Ban(p, target_player));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Дней")) {
            switch((Integer)this.ban_days.getOrDefault(p, 0)) {
                case 0:
                    this.ban_days.put(p, 1);
                    break; case 1: this.ban_days.put(p, 2);
                    break; case 2: this.ban_days.put(p, 3);
                    break; case 3: this.ban_days.put(p, 4);
                    break; case 4: this.ban_days.put(p, 5);
                    break; case 5: this.ban_days.put(p, 6);
                    break; case 6: this.ban_days.put(p, 7);
                    break; case 7: this.ban_days.put(p, 8);
                    break; case 8: this.ban_days.put(p, 9);
                    break; case 9: this.ban_days.put(p, 10);
                    break; case 10: this.ban_days.put(p, 15); case 11: case 12: case 13: case 14: case 16: case 17: case 18: case 19: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29: default: break; case 15: this.ban_days.put(p, 20);
                    break; case 20: this.ban_days.put(p, 30);
                    break; case 30: this.ban_days.put(p, 0);
            }
            p.openInventory(this.GUI_Ban(p, target_player));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Часов")) {
            switch((Integer)this.ban_hours.getOrDefault(p, 0)) {
                case 0:
                    this.ban_hours.put(p, 1);
                    break; case 1: this.ban_hours.put(p, 2);
                    break; case 2: this.ban_hours.put(p, 3);
                    break; case 3: this.ban_hours.put(p, 4);
                    break; case 4: this.ban_hours.put(p, 5);
                    break; case 5: this.ban_hours.put(p, 6);
                    break; case 6: this.ban_hours.put(p, 7);
                    break; case 7: this.ban_hours.put(p, 8);
                    break; case 8: this.ban_hours.put(p, 9);
                    break; case 9: this.ban_hours.put(p, 10);
                    break; case 10: this.ban_hours.put(p, 15); case 11: case 12: case 13: case 14: case 16: case 17: case 18: case 19: default: break; case 15: this.ban_hours.put(p, 20);
                    break; case 20: this.ban_hours.put(p, 0);
            }
            p.openInventory(this.GUI_Ban(p, target_player));
        } else if (InventoryGUI.getClickedItem(clicked, ChatColor.GOLD + "Минут")) {
            switch((Integer)this.ban_minutes.getOrDefault(p, 0)) {
                case 0:
                    this.ban_minutes.put(p, 5);
                    break; case 5: this.ban_minutes.put(p, 10);
                    break; case 10: this.ban_minutes.put(p, 15);
                    break; case 15: this.ban_minutes.put(p, 20);
                    break; case 20: this.ban_minutes.put(p, 25);
                    break; case 25: this.ban_minutes.put(p, 30);
                    break; case 30: this.ban_minutes.put(p, 35);
                    break; case 35: this.ban_minutes.put(p, 40);
                    break; case 40: this.ban_minutes.put(p, 45);
                    break; case 45: this.ban_minutes.put(p, 50);
                    break; case 50: this.ban_minutes.put(p, 55);
                    break; case 55: this.ban_minutes.put(p, 0);
            }
            p.openInventory(this.GUI_Ban(p, target_player));
        }
    }
}