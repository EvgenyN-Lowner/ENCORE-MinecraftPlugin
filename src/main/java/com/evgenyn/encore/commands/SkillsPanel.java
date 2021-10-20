package com.evgenyn.encore.commands;

import com.evgenyn.encore.ENCORE;
import com.evgenyn.encore.SQL.SQLGetter;
import com.evgenyn.encore.dataManagers.DataManager;
import com.evgenyn.encore.gui.SkillsPanelGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SkillsPanel implements CommandExecutor {
    private SkillsPanelGUI adminGUI = new SkillsPanelGUI();
    private ENCORE encore;
    public SQLGetter data;

    public SkillsPanel(SQLGetter data){
        this.data = data;
    }

    public boolean onCommand(CommandSender sender, Command command, String displayName, String[] args){
        if(!(sender instanceof Player)){
            return true;
        }else {
            Player p = (Player) sender;
            p.openInventory(this.adminGUI.GUI_SkillsMain(p, data));
            return true;
        }
    }
}
