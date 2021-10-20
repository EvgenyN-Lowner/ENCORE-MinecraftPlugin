package com.evgenyn.encore.events;

import com.evgenyn.encore.SQL.SQLGetter;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BankDepositEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Player p;
    SQLGetter dataSQL;
    float deposit;
    float depositOnePro;

    public BankDepositEvent(Player player, SQLGetter data, float depositAmmount){
        p = player;
        dataSQL = data;
        deposit = depositAmmount;
            depositOnePro = deposit / 100;
    }

    public Player getPlayer() {
        return p;
    }

    public SQLGetter getData() {
        return dataSQL;
    }

    public float getDepositOnePro() {
        return depositOnePro;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
