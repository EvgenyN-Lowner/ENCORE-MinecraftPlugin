package com.evgenyn.encore.ecomony;

import com.evgenyn.encore.SQL.SQLGetter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BankSystem {

    public BankSystem(){
    }

    //Обычный банк
    public void depositMoney(Player player, SQLGetter data, float moneyAmmount){
        data.addbank(player.getUniqueId(), moneyAmmount);
    }

    public void withdrawMoney(Player player, SQLGetter data, float moneyAmmount){
        if (!(data.getBank(player.getUniqueId()) < moneyAmmount)){
            data.removeBank(player.getUniqueId(), moneyAmmount);
        } else {
            player.sendMessage( ChatColor.DARK_RED + "У вас недостаточно денег на счете в банке");
        }
    }

    public float getMoneyInBank(Player player, SQLGetter data ){
        float moneyinBank = data.getBank(player.getUniqueId());
        return moneyinBank;
    }

    public void depositInRealMoney(Player player, SQLGetter data, Integer realMoneyAmmount){
        data.removeBank(player.getUniqueId(), realMoneyAmmount);
        ItemStack realMoney = new ItemStack(Material.GOLD_BLOCK, realMoneyAmmount);
        player.getInventory().removeItem(realMoney);
    }

    public void withdrawInRealMoney(Player player, SQLGetter data, Integer realMoneyAmmount){
        data.removeBank(player.getUniqueId(), realMoneyAmmount);
        ItemStack realMoney = new ItemStack(Material.GOLD_BLOCK, realMoneyAmmount);
        if (!(data.getBank(player.getUniqueId()) < realMoneyAmmount)){
            player.getInventory().addItem(realMoney);
        } else {
            player.sendMessage( ChatColor.DARK_RED + "У вас недостаточно денег на счете в банке");
        }
    }

    //Вклады
    public void depositVklad(Player player, SQLGetter data, float moneyAmmount){
        data.addVklad(player.getUniqueId(), moneyAmmount);
    }

    public void withdrawVklad(Player player, SQLGetter data, float moneyAmmount){
        if (!(data.getVklad(player.getUniqueId()) < moneyAmmount)){
            data.removeVklad(player.getUniqueId(), moneyAmmount);
        } else {
            player.sendMessage( ChatColor.DARK_RED + "У вас недостаточно денег на счете в банке");
        }
    }

}
