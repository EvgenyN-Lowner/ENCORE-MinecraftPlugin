package com.evgenyn.encore.commands;

import com.evgenyn.encore.ENCORE;
import com.evgenyn.encore.SQL.SQLGetter;
import com.evgenyn.encore.gui.PlayerMenuPanelGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerMenuPanel implements CommandExecutor {
    private PlayerMenuPanelGUI playerMenuPanelGUI = new PlayerMenuPanelGUI();
    private ENCORE encore;
    public SQLGetter data;

    public PlayerMenuPanel(SQLGetter data){
        this.data = data;
    }

    public boolean onCommand(CommandSender sender, Command command, String displayName, String[] args){
        if (!(sender instanceof Player)){
            return true;
        }else {
            Player p = (Player) sender;
            p.openInventory(this.playerMenuPanelGUI.GUI_PlayerMenuMain(p, data));
            return true;
        }

    }
}
