package com.evgenyn.encore.utilities;

import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class InventoriesSerializer {
    public InventoriesSerializer(){
    }

    public String toBase64(ItemStack[] itemStack) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < itemStack.length; i++){
            if (i > 0){
                stringBuilder.append(";");
            }
            if ((itemStack[i] != null) && (itemStack[i].getType() != Material.AIR)) {
                stringBuilder.append(StreamSerializer.getDefault().serializeItemStack(itemStack[i]));
            }
        }
        return stringBuilder.toString();
    }

    public ItemStack[] fromBase64(String nbtdata) throws IOException{
        String[] strings = nbtdata.split(";");
        ItemStack[] itemStack = new ItemStack[strings.length];
        for (int i = 0; i < strings.length; i++){
            if (!strings[i].equals("")){
                try {
                    itemStack[i] = StreamSerializer.getDefault().deserializeItemStack(strings[i]);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return itemStack;
    }
}
