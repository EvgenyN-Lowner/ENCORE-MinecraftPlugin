package com.evgenyn.encore.commands;

import com.evgenyn.encore.gui.AdminPanelGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminPanel implements CommandExecutor {
    private AdminPanelGUI adminGUI = new AdminPanelGUI();

    public AdminPanel(){
    }

    public boolean onCommand(CommandSender sender, Command command, String displayName, String[] args){
        if(!(sender instanceof Player)){
            return true;
        }else {
            Player p = (Player) sender;
            if(p.hasPermission("encore.admin") && p.isOp()){
                p.openInventory(this.adminGUI.AdminGUI_Main(p));
            }else{
                p.sendMessage( ChatColor.DARK_RED + "Вы не являетесь членом Администрации");
            }
            return true;
        }
    }
}
