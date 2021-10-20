package com.evgenyn.encore.listeners;

import com.evgenyn.encore.ENCORE;
import com.evgenyn.encore.SQL.SQLGetter;
import com.evgenyn.encore.ecomony.BankSystem;
import com.evgenyn.encore.events.BankDepositEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinListener implements Listener {
    private ENCORE encore;
    public SQLGetter data;
    private final BankSystem bankSystem = new BankSystem();

    public PlayerJoinListener(ENCORE plugin) {
        this.encore = plugin;
        this.data = new SQLGetter(encore);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        data.createPlayer(p);
        if (!p.hasPlayedBefore()) {
            bankSystem.depositMoney(p, data, 25);
            data.addhasVault(p.getUniqueId(), 0);
        }

        //Система вкладов
            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!p.isOnline()){
                        this.cancel();
                    }else if (data.getVklad(p.getUniqueId()) > 0) {
                        BankDepositEvent event = new BankDepositEvent(p, data, data.getVklad(p.getUniqueId()));
                        Bukkit.getServer().getPluginManager().callEvent(event);
                    }
                }
            };
            task.runTaskTimer(encore, 72000, 72000); //1200 = 1 минута | 72000 = 1 час

    }

    @EventHandler
    public void bankDepositCalculator(BankDepositEvent e){
        Player p = e.getPlayer();
        data.addVklad(p.getUniqueId(), e.getDepositOnePro());
    }
}
