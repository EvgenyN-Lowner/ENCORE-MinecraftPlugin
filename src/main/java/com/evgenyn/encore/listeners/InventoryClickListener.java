package com.evgenyn.encore.listeners;

import com.evgenyn.encore.ENCORE;
import com.evgenyn.encore.SQL.SQLGetter;
import com.evgenyn.encore.dataManagers.DataManager;
import com.evgenyn.encore.gui.AdminPanelGUI;
import com.evgenyn.encore.gui.BankPanelGUI;
import com.evgenyn.encore.gui.PlayerMenuPanelGUI;
import com.evgenyn.encore.gui.SkillsPanelGUI;
import com.evgenyn.encore.utilities.InventoriesSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClickListener implements Listener {
    private final ENCORE encore;
    public SQLGetter data;
    public DataManager conf;
    private final AdminPanelGUI adminPanelGUI = new AdminPanelGUI();
    private final PlayerMenuPanelGUI playerMenuPanelGUI = new PlayerMenuPanelGUI();
    private final BankPanelGUI bankPanelGUI = new BankPanelGUI();
    private final SkillsPanelGUI skillsPanelGUI = new SkillsPanelGUI();

    public InventoryClickListener(ENCORE plugin, DataManager config) {
        this.encore = plugin;
        this.data = new SQLGetter(encore);
        this.conf = config;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        String title = e.getView().getTitle();
        Player p = (Player) e.getWhoClicked();

        try {
            //Admin GUI
            if (title.equals(ChatColor.RED + "Админ Панель")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_main(p, e.getSlot(), e.getCurrentItem(), e.getInventory());
                }
            }else if (title.equals(ChatColor.DARK_GREEN + "Панель Онлайн Игроков")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_players(p, e.getSlot(), e.getCurrentItem(), e.getInventory());
                }
            }else if (title.equals(ChatColor.AQUA + "Управление Миром")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_world(p, e.getSlot(), e.getCurrentItem(), e.getInventory());
                }
            }else if (title.equals(ChatColor.BLACK + "Управление Сервером")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_serverSettings(p, e.getSlot(), e.getCurrentItem(), e.getInventory());
                }
            }else if (title.equals(ChatColor.DARK_GREEN + "Игрок: " + ChatColor.GOLD + ((Player)AdminPanelGUI.target_player.get(p)).getName())) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_playersSettings(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), data,  (Player)AdminPanelGUI.target_player.get(p));
                }
            }else if (title.equals(ChatColor.GOLD + "Инвентарь Игрока: " + ChatColor.GREEN + ((Player)AdminPanelGUI.target_player.get(p)).getName())) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_chest(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), (Player)AdminPanelGUI.target_player.get(p));
                }
            }else if (title.equals(ChatColor.LIGHT_PURPLE + "Эндер Сундук Игрока: " + ChatColor.GREEN + ((Player)AdminPanelGUI.target_player.get(p)).getName())) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_ENDchest(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), (Player)AdminPanelGUI.target_player.get(p));
                }
            }else if (title.equals(ChatColor.DARK_RED + "Бан Меню Игрока: " + ChatColor.GOLD + ((Player)AdminPanelGUI.target_player.get(p)).getName())) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_ban(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), (Player)AdminPanelGUI.target_player.get(p));
                }
            }else if (title.equals(ChatColor.AQUA + "Действия с  Игроком: " + ChatColor.GOLD + ((Player)AdminPanelGUI.target_player.get(p)).getName())) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_actions(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), (Player)AdminPanelGUI.target_player.get(p));
                }
            }else if (title.equals(ChatColor.DARK_GREEN + "Управление Банком Игрока")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_bankingAP(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), data, (Player)AdminPanelGUI.target_player.get(p));
                }
            }else if (title.equals(ChatColor.GOLD + "Основной Счет")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_bankingAPBank(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), data ,encore, (Player)AdminPanelGUI.target_player.get(p));
                }
            }else if (title.equals(ChatColor.AQUA + "Вклад")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.adminPanelGUI.clicked_bankingAPDeposit(p, e.getSlot(), e.getCurrentItem(), e.getInventory(),data ,encore, (Player)AdminPanelGUI.target_player.get(p));
                }
            }
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }

        try{
            //Players Menu
            if (title.equals(ChatColor.DARK_GREEN + "Меню")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.playerMenuPanelGUI.clicked_playerMenu(p, e.getSlot(), e.getCurrentItem(), e.getInventory());
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }

        try {
            //Bank Menu
            if (title.equals(ChatColor.GOLD + "Банк")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.bankPanelGUI.clicked_bankmain(p, e.getCurrentItem(), data, encore);
                }
            } else if (title.equals(ChatColor.DARK_GREEN + "Счета")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.bankPanelGUI.clicked_banking(p, e.getCurrentItem(), data, data.gethasVault(p.getUniqueId()), encore);
                }
            } else if (title.equals(ChatColor.GOLD + "Выберете Игрока для перевода")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.bankPanelGUI.clicked_perevodP(p, e.getCurrentItem(), data, encore);
                }
            } else if (title.equals(ChatColor.AQUA + "Выберете тип опирации")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.bankPanelGUI.clicked_typeDeposit(p, e.getCurrentItem(), data, encore);
                }
            } else if (title.equals(ChatColor.LIGHT_PURPLE + "Выберете тип опирации")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.bankPanelGUI.clicked_typeMoney(p, e.getCurrentItem(), data, encore);
                }
            }

        } catch (Exception ex3) {
        }

        try {
            //SkillsMenus
            if (title.equals("§5§lНавыки")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.skillsPanelGUI.clicked_skillsmain(p, e.getCurrentItem(), data, conf);
                }
            } else if (title.equals("§6§lХарактеристики")) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    this.skillsPanelGUI.clicked_characteristics(p, e.getCurrentItem(), data);
                }
            }
        } catch (Exception ex3) {
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        String title = e.getView().getTitle();
        Player p = (Player) e.getPlayer();
        try {
            //Bank Vault
            if (title.equals(ChatColor.AQUA + "Ячейка")) {
                InventoriesSerializer inventSer = new InventoriesSerializer();
                String itemsNBT = inventSer.toBase64(e.getInventory().getContents());
                data.addVaultNBT(p.getUniqueId(), itemsNBT);
            }

            if (title.equals(ChatColor.LIGHT_PURPLE + "Ячейка")) {
                InventoriesSerializer inventSer = new InventoriesSerializer();
                String itemsNBT = inventSer.toBase64(e.getInventory().getContents());
                Player target_player = (Player)AdminPanelGUI.target_player.get(p);
                data.addVaultNBT(target_player.getUniqueId(), itemsNBT);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
