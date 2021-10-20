package com.evgenyn.encore;

import com.evgenyn.encore.SQL.MySQL;
import com.evgenyn.encore.SQL.SQLGetter;
import com.evgenyn.encore.commands.*;
import com.evgenyn.encore.dataManagers.DataManager;
import com.evgenyn.encore.listeners.InventoryClickListener;
import com.evgenyn.encore.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class ENCORE extends JavaPlugin {
    private static ENCORE instance;
    public MySQL SQL;
    public SQLGetter data;
    public DataManager config;

    public ENCORE(){
    }

    public void onEnable() {
        instance = this;

        this.SQL = new MySQL();
        this.data = new SQLGetter(this);
        this.config = new DataManager(this);

        new InventoryClickListener(this, config);
        new PlayerJoinListener(this);

        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info( "MySQL database not connected");
        }
        if (SQL.isConnected()){
            Bukkit.getLogger().info("MySQL database is connected");
            data.createBankTable();
            data.createSkillsTable();
        }

        this.getCommand("ap").setExecutor(new AdminPanel());
        this.getCommand("menu").setExecutor(new PlayerMenuPanel(data));
        this.getCommand("bank").setExecutor(new BankPanel(data));
        this.getCommand("skills").setExecutor(new SkillsPanel(data));
        //this.getCommand("job").setExecutor(new JobsPanel());

    }

    public void onDisable() {
        SQL.disconnect();
        config.getConfig().getBoolean("");
    }

    public static ENCORE getInstance() {
        return instance;
    }
}

