package com.evgenyn.encore.commands;

import com.evgenyn.encore.ENCORE;
import com.evgenyn.encore.SQL.SQLGetter;
import com.evgenyn.encore.gui.BankPanelGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankPanel implements CommandExecutor {
    private BankPanelGUI bankPanelGUI = new BankPanelGUI();
    private ENCORE encore;
    public SQLGetter data;

    public BankPanel(SQLGetter data){
        this.data = data;

    }

    public boolean onCommand(CommandSender sender, Command command, String displayName, String[] args){
        if(!(sender instanceof Player)){
            return true;
        }else {
            Player p = (Player) sender;
            p.openInventory(this.bankPanelGUI.GUI_BankMain(p, data, data.gethasVault(p.getUniqueId())));
            return true;
        }
    }
}
