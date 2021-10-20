package com.evgenyn.encore.dataManagers;

import com.evgenyn.encore.ENCORE;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataManager {

    private ENCORE encore;
    private FileConfiguration encoreConfig = null;
    private File configFile = null;

    public DataManager(ENCORE encore){
        this.encore = encore;
        saveDefaultConfig();
    }

    public void reloadConfig(){
        if(this.configFile == null)
            this.configFile = new File(this.encore.getDataFolder(), "skills.yml");

        this.encoreConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.encore.getResource("skills.yml");
        if (defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.encoreConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if (this.encoreConfig == null)
            reloadConfig();

        return this.encoreConfig;
    }

    public void saveConfig(){
        if (this.encoreConfig == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e){
            encore.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    public void saveDefaultConfig(){
        if (this.configFile == null)
            this.configFile = new File(this.encore.getDataFolder(), "skills.yml");

        if (!this.configFile.exists()){
            this.encore.saveResource("skills.yml", false);
        }
    }
}
