package com.evgenyn.encore.utilities;

import com.evgenyn.encore.XMaterial;
import com.evgenyn.encore.dataManagers.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.SkullType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class Item {
    public Item(){
    }

    public static ItemStack create(Inventory inv, String material, int amount, int invSlot, String displayName, String... loreString){
        ArrayList lore = new ArrayList();
        ItemStack item = new ItemStack(((XMaterial)XMaterial.matchXMaterial(material).get()).parseMaterial(), amount, (short)((XMaterial)XMaterial.matchXMaterial(material).get()).getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        String[] s = loreString;
        int s2 = loreString.length;

        for (int s3 = 0; s3 < s2; s3++ ){
            String s4 = s[s3];
            lore.add(s4);
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);
        return item;
    }

    public static ItemStack createSkill(Inventory inv, String material, int amount, int invSlot, String displayName, String path, DataManager config){
        ArrayList lore = new ArrayList();
        ItemStack item = new ItemStack(((XMaterial)XMaterial.matchXMaterial(material).get()).parseMaterial(), amount, (short)((XMaterial)XMaterial.matchXMaterial(material).get()).getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);

        for (int a1 = 0; a1 < config.getConfig().getStringList(path).size(); a1++){
            lore.add(ChatColor.COLOR_CHAR + config.getConfig().getStringList(path).get(a1));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);
        return item;
    }

    public static ItemStack createS(String material, int amount, String displayName, String... loreString){
        ArrayList lore = new ArrayList();
        ItemStack item = new ItemStack(((XMaterial)XMaterial.matchXMaterial(material).get()).parseMaterial(), amount, (short)((XMaterial)XMaterial.matchXMaterial(material).get()).getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        String[] s = loreString;
        int s2 = loreString.length;

        for (int s3 = 0; s3 < s2; s3++ ){
            String s4 = s[s3];
            lore.add(s4);
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createPlayerHead(Inventory inv, String player, int amount, int invSlot, String displayName, String... loreString){
        ArrayList lore = new ArrayList();
        ItemStack item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), amount, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta)item.getItemMeta();
        skullMeta.setOwner(player);
        skullMeta.setDisplayName(displayName);
        item.setItemMeta(skullMeta);
        ItemMeta meta = item.getItemMeta();
        String[] var10 = loreString;
        int var11 = loreString.length;

        for(int var12 = 0; var12 < var11; ++var12) {
            String s = var10[var12];
            lore.add(s);
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);
        return item;
    }

    public static ItemStack createByte(Inventory inv, String material, int byteId, int amount, int invSlot, String displayName, String... loreString) {
        ArrayList lore = new ArrayList();
        ItemStack item = new ItemStack(((XMaterial)XMaterial.matchXMaterial(material).get()).parseMaterial(), amount, (short)byteId);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        String[] var10 = loreString;
        int var11 = loreString.length;

        for(int var12 = 0; var12 < var11; ++var12) {
            String s = var10[var12];
            lore.add(s);
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);
        return item;
    }
}
