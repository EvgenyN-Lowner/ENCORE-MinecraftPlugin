package com.evgenyn.encore.gui;

import com.evgenyn.encore.SQL.SQLGetter;
import com.evgenyn.encore.dataManagers.DataManager;
import com.evgenyn.encore.utilities.InventoryGUI;
import com.evgenyn.encore.utilities.Item;
import com.evgenyn.encore.utilities.OSOPSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.List;

public class SkillsPanelGUI {

    public SkillsPanelGUI(){
    }

    public Inventory GUI_SkillsMain(Player p, SQLGetter data){
        Inventory inv_skillsMain = Bukkit.createInventory(p, 27, "§5§lНавыки");

        for (int i = 0; i < 26; i++) {
            Item.create(inv_skillsMain, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.createPlayerHead(inv_skillsMain, p.getName(), 1, 0, "§5§l" + p.getName(),
                "§6Текущий уровень персонажа: §9§l" + data.getLevel(p.getUniqueId()),
                "§7§oПрогресс уровня персонажа: §f" + (int) data.getPlayerEXP(p.getUniqueId()) + " / " + (int) data.getEXPtoNextLevel(p.getUniqueId()) + " EXP",
                " ",
                "§6§lХарактеристики:",
                "§4Сила: §f§n" + data.getCharacteristic(p.getUniqueId(), 1),
                "§3Восприятие: §f§n" + data.getCharacteristic(p.getUniqueId(), 2),
                "§2Выносливость: §f§n" + data.getCharacteristic(p.getUniqueId(), 3),
                "§6Харизма: §f§n" + data.getCharacteristic(p.getUniqueId(), 4),
                "§9Интелект: §f§n" + data.getCharacteristic(p.getUniqueId(), 5),
                "§aЛовкость: §f§n" + data.getCharacteristic(p.getUniqueId(), 6),
                "§5Удача: §f§n" + data.getCharacteristic(p.getUniqueId(), 7),
                " ",
                "§e§oДоступно очков Характеристик: §2§n" + data.getCharacteristicUP(p.getUniqueId()),
                "§7§oДоступно очков Способностей: §b§n" + data.getSkillUP(p.getUniqueId())
        );

        Item.create(inv_skillsMain, "EXPERIENCE_BOTTLE", 1, 11, "§6§lХарактеристики",
                "§4Сила: §f§n" + data.getCharacteristic(p.getUniqueId(), 1),
                "§3Восприятие: §f§n" + data.getCharacteristic(p.getUniqueId(), 2),
                "§2Выносливость: §f§n" + data.getCharacteristic(p.getUniqueId(), 3),
                "§6Харизма: §f§n" + data.getCharacteristic(p.getUniqueId(), 4),
                "§9Интелект: §f§n" + data.getCharacteristic(p.getUniqueId(), 5),
                "§aЛовкость: §f§n" + data.getCharacteristic(p.getUniqueId(), 6),
                "§5Удача: §f§n" + data.getCharacteristic(p.getUniqueId(), 7),
                " ",
                "§e§oДоступно очков Характеристик: §2§n" + data.getCharacteristicUP(p.getUniqueId())
                );
        Item.create(inv_skillsMain, "ENCHANTED_BOOK", 1, 13, "§9§lСпособности",
                "§7§oДоступно очков Способностей: §b§n" + data.getSkillUP(p.getUniqueId())
                );
        Item.create(inv_skillsMain, "NAME_TAG", 1, 15, "§d§lИзъяны",
                "§7§oКоличество Изъянов: §5§n" + data.getOSOP(p.getUniqueId()).length()/2
                );

        Item.create(inv_skillsMain, "BARRIER", 1, 26, "§c§lМеню Игрока", new String[0]);

        return inv_skillsMain;
    }

    public Inventory GUI_Characteristics(Player p, SQLGetter data){
        Inventory inv_characteristics = Bukkit.createInventory(p, 27, "§6§lХарактеристики");

        for (int i = 0; i < 26; i++) {
            Item.create(inv_characteristics, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

        Item.createPlayerHead(inv_characteristics, p.getName(), 1, 0, "§6§l" + p.getName(),
                "§4Сила: §f§n" + data.getCharacteristic(p.getUniqueId(), 1),
                "§3Восприятие: §f§n" + data.getCharacteristic(p.getUniqueId(), 2),
                "§2Выносливость: §f§n" + data.getCharacteristic(p.getUniqueId(), 3),
                "§6Харизма: §f§n" + data.getCharacteristic(p.getUniqueId(), 4),
                "§9Интелект: §f§n" + data.getCharacteristic(p.getUniqueId(), 5),
                "§aЛовкость: §f§n" + data.getCharacteristic(p.getUniqueId(), 6),
                "§5Удача: §f§n" + data.getCharacteristic(p.getUniqueId(), 7),
                " ",
                "§e§oДоступно очков Характеристик: §2§n" + data.getCharacteristicUP(p.getUniqueId()),
                " ",
                "§7§oПри наличии очков Характеристик, вы можете бесплатно увеличить",
                "§7§oодну характеристику на 1"
        );

        String CharUP = "";
        if (data.getCharacteristicUP(p.getUniqueId()) > 0){
            CharUP = "§2§oНажмите, чтобы увеличить характеристику на 1";
        }else {
            CharUP = "";
        }

        Item.create(inv_characteristics, "RED_CONCRETE", 1, 10, "§4Сила | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),1), CharUP);
        Item.create(inv_characteristics, "BLUE_CONCRETE", 1, 11, "§3Восприятие | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),2), CharUP);
        Item.create(inv_characteristics, "GREEN_CONCRETE", 1, 12, "§2Выносливость | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),3), CharUP);
        Item.create(inv_characteristics, "ORANGE_CONCRETE", 1, 13, "§6Харизма | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),4), CharUP);
        Item.create(inv_characteristics, "LIGHT_BLUE_CONCRETE", 1, 14,  "§9Интелект | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),5), CharUP);
        Item.create(inv_characteristics, "YELLOW_CONCRETE", 1, 15, "§aЛовкость | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),6), CharUP);
        Item.create(inv_characteristics, "PURPLE_CONCRETE", 1, 16, "§5Удача | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),7), CharUP);


        Item.create(inv_characteristics, "BARRIER", 1, 26, "§c§lНазад", new String[0]);

        return inv_characteristics;
    }

    public Inventory GUI_Skills(Player p, SQLGetter data){
        Inventory inv_skills = Bukkit.createInventory(p, 27, "§9§lСпособности");

        return inv_skills;
    }

    public Inventory GUI_SkillsStrength(Player p, SQLGetter data, DataManager config){
        Inventory inv_skillsStrength = Bukkit.createInventory(p, 54, ChatColor.AQUA + "Сопосбности: " + ChatColor.RED + "Сила");

        for (int i=0; i < 45; i++){
            Item.createSkill(inv_skillsStrength, config.getConfig().getString("strength.skills_" + i + ".block"), 1, i, ChatColor.COLOR_CHAR + config.getConfig().getString("strength.skills_" + i + ".name"), "strength.skills_" + i + ".description", config);
        }

        return inv_skillsStrength;
    }

    public Inventory GUI_Peculiarities(Player p, SQLGetter data, DataManager config){
        Inventory inv_peculiarities = Bukkit.createInventory(p, 54, "§d§lИзъяны");

        for (int i = 0; i < 45; i++) {
            Item.create(inv_peculiarities, "GRAY_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }

            String osopD = data.getOSOP(p.getUniqueId());
            OSOPSerializer osopData = new OSOPSerializer();
            try {
                List osopArray = osopData.getOSOPS(osopD);
                for (int a = 0; a < osopArray.size(); a++){
                    Item.createSkill(inv_peculiarities, config.getConfig().getString("peculiarities.peculiarity_" + osopArray.get(a) + ".block"), 1, a, ChatColor.COLOR_CHAR + config.getConfig().getString("peculiarities.peculiarity_" + osopArray.get(a) + ".name"),"peculiarities.peculiarity_" + osopArray.get(a) + ".description" ,config);
                }
            } catch (IOException e){
                e.printStackTrace();
            }

        for (int i = 45; i < 53; i++) {
            Item.create(inv_peculiarities, "BLACK_STAINED_GLASS_PANE", 1, i, " ", new String[0]);
        }


        Item.create(inv_peculiarities, "BARRIER", 1, 53, ChatColor.RED + "Назад", new String[0]);

        return inv_peculiarities;
    }

    public void clicked_skillsmain(Player p, ItemStack clicked, SQLGetter data,  DataManager config){
        if (InventoryGUI.getClickedItem(clicked, "§c§lМеню Игрока")) {
            Bukkit.dispatchCommand(p, "menu");
        } else if (InventoryGUI.getClickedItem(clicked, "§6§lХарактеристики")) {
            p.openInventory(this.GUI_Characteristics(p, data));
        } else if (InventoryGUI.getClickedItem(clicked, "§9§lСпособности")) {
            p.openInventory(this.GUI_Skills(p, data));
        } else if (InventoryGUI.getClickedItem(clicked, "§d§lИзъяны")) {
            p.openInventory(this.GUI_Peculiarities(p, data, config));
        }
    }

    public void clicked_characteristics(Player p, ItemStack clicked, SQLGetter data){
        if (InventoryGUI.getClickedItem(clicked, "§c§lНазад")) {
            p.openInventory(this.GUI_SkillsMain(p, data));
        } else if (InventoryGUI.getClickedItem(clicked, "§4Сила | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),1))) {
            if (data.getCharacteristicUP(p.getUniqueId()) > 0){
                data.removeCharacteristicUP(p.getUniqueId(), 1);
                data.addCharacteristic(p.getUniqueId(), 1, 1);
                p.openInventory(this.GUI_Characteristics(p, data));
            } else {
                p.sendMessage("§4У вас нет очков Характеристик");
            }
        } else if (InventoryGUI.getClickedItem(clicked, "§3Восприятие | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),2))) {
            if (data.getCharacteristicUP(p.getUniqueId()) > 0){
                data.removeCharacteristicUP(p.getUniqueId(), 1);
                data.addCharacteristic(p.getUniqueId(), 2, 1);
                p.openInventory(this.GUI_Characteristics(p, data));
            } else {
                p.sendMessage("§4У вас нет очков Характеристик");
            }
        } else if (InventoryGUI.getClickedItem(clicked, "§2Выносливость | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),3))) {
            if (data.getCharacteristicUP(p.getUniqueId()) > 0){
                data.removeCharacteristicUP(p.getUniqueId(), 1);
                data.addCharacteristic(p.getUniqueId(), 3, 1);
                p.openInventory(this.GUI_Characteristics(p, data));
            } else {
                p.sendMessage("§4У вас нет очков Характеристик");
            }
        } else if (InventoryGUI.getClickedItem(clicked, "§6Харизма | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),4))) {
            if (data.getCharacteristicUP(p.getUniqueId()) > 0){
                data.removeCharacteristicUP(p.getUniqueId(), 1);
                data.addCharacteristic(p.getUniqueId(), 4, 1);
                p.openInventory(this.GUI_Characteristics(p, data));
            } else {
                p.sendMessage("§4У вас нет очков Характеристик");
            }
        } else if (InventoryGUI.getClickedItem(clicked, "§9Интелект | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),5))) {
            if (data.getCharacteristicUP(p.getUniqueId()) > 0){
                data.removeCharacteristicUP(p.getUniqueId(), 1);
                data.addCharacteristic(p.getUniqueId(), 5, 1);
                p.openInventory(this.GUI_Characteristics(p, data));
            } else {
                p.sendMessage("§4У вас нет очков Характеристик");
            }
        } else if (InventoryGUI.getClickedItem(clicked, "§aЛовкость | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),6))) {
            if (data.getCharacteristicUP(p.getUniqueId()) > 0){
                data.removeCharacteristicUP(p.getUniqueId(), 1);
                data.addCharacteristic(p.getUniqueId(), 6, 1);
                p.openInventory(this.GUI_Characteristics(p, data));
            } else {
                p.sendMessage("§4У вас нет очков Характеристик");
            }
        } else if (InventoryGUI.getClickedItem(clicked, "§5Удача | §7§oУровень: §9§l" + data.getCharacteristic(p.getUniqueId(),7))) {
            if (data.getCharacteristicUP(p.getUniqueId()) > 0){
                data.removeCharacteristicUP(p.getUniqueId(), 1);
                data.addCharacteristic(p.getUniqueId(), 7, 1);
                p.openInventory(this.GUI_Characteristics(p, data));
            } else {
                p.sendMessage("§4У вас нет очков Характеристик");
            }
        }
    }

}
