package com.evgenyn.encore.SQL;

import com.evgenyn.encore.ENCORE;
import org.bukkit.entity.Player;

import javax.print.DocFlavor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {
    private ENCORE plugin;
    public SQLGetter(ENCORE plugin){
        this.plugin = plugin;
    }

    public void createBankTable(){
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playersdbBank "
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),BANK DECIMAL(15,2),VKLAD DECIMAL(15,2),VAULTB INT(10),VAULTNBT LONGTEXT NOT NULL, PRIMARY KEY (NAME))");
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createSkillsTable(){
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playersdbSkills "
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),PlayerLEVEL INT(10),PlayerLEVELexp DECIMAL(15,2) ,CharacteristicUP INT(10),SkillUP INT(10),Characteristic1 INT(10),Characteristic2 INT(10),Characteristic3 INT(10),Characteristic4 INT(10),Characteristic5 INT(10),Characteristic6 INT(10),Characteristic7 INT(10),OSOPDATA LONGTEXT NOT NULL, PRIMARY KEY (NAME))");
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player){
        try {
            UUID uuid = player.getUniqueId();
            if (!exists(uuid)){
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO playersdbBank"
                        + " (NAME,UUID) VALUES (?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, uuid.toString());
                ps2.executeUpdate();

                PreparedStatement ps3 = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO playersdbSkills"
                        + " (NAME,UUID) VALUES (?,?)");
                ps3.setString(1, player.getName());
                ps3.setString(2, uuid.toString());
                ps3.executeUpdate();

                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM playersdbBank WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet result = ps.executeQuery();
            if (result.next()){
                return true;
            }

            PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("SELECT * FROM playersdbSkills WHERE UUID=?");
            ps2.setString(1, uuid.toString());
            ResultSet result2 = ps2.executeQuery();
            if (result2.next()){
                return true;
            }

            return false;
        }catch (SQLException e){
            e.printStackTrace();

        }
        return false;
    }

    //BANK
    public void addbank(UUID uuid, float moneyAmmount){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbBank SET BANK=? WHERE UUID=?");
            ps.setFloat(1, (getBank(uuid) + moneyAmmount));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public float getBank(UUID uuid){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT BANK FROM playersdbBank WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            float bankMoney = 0f;
            if (rs.next()){
                bankMoney = rs.getFloat("BANK");
                return bankMoney;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0f;
    }

    public void removeBank(UUID uuid, float moneyAmmount){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbBank SET BANK=? WHERE UUID=?");
            ps.setFloat(1, (getBank(uuid) - moneyAmmount));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //VKLAD
    public void addVklad(UUID uuid, float vkladAmmount){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbBank SET VKLAD=? WHERE UUID=?");
            ps.setFloat(1, (getVklad(uuid) + vkladAmmount));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public float getVklad(UUID uuid){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT VKLAD FROM playersdbBank WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            float vkladMoney = 0f;
            if (rs.next()){
                vkladMoney = rs.getFloat("VKLAD");
                return vkladMoney;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void removeVklad(UUID uuid, float vkladAmmount){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbBank SET VKLAD=? WHERE UUID=?");
            ps.setFloat(1, (getVklad(uuid) - vkladAmmount));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    //HAS VAULT
    public void addhasVault(UUID uuid, int hasVault){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbBank SET VAULTB=? WHERE UUID=?");
            ps.setInt(1, (gethasVault(uuid) + hasVault));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int gethasVault(UUID uuid){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT VAULTB FROM playersdbBank WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int hasVault = 0;
            if (rs.next()){
                hasVault = rs.getInt("VAULTB");
                return hasVault;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //NBT-VAULT
    public void addVaultNBT(UUID uuid, String VaultNBT){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbBank SET VAULTNBT=? WHERE UUID=?");
            ps.setString(1, VaultNBT);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getVaultNBT(UUID uuid){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT VAULTNBT FROM playersdbBank WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            String vkladMoney = "null";
            if (rs.next()){
                vkladMoney = rs.getString("VAULTNBT");
                return vkladMoney;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //SKILLS
    //Level
    public void addLevel(UUID uuid, int level){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET PlayerLEVEL=? WHERE UUID=?");
            ps.setInt(1, getLevel(uuid) + level);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            if(getLevel(uuid) == 10 || getLevel(uuid) == 20 || getLevel(uuid) == 30 || getLevel(uuid) == 40 || getLevel(uuid) == 50 || getLevel(uuid) == 60 || getLevel(uuid) == 70 || getLevel(uuid) == 80 || getLevel(uuid) == 90 || getLevel(uuid) == 100){
                addCharacteristicUP(uuid, 5);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getLevel(UUID uuid){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PlayerLEVEL FROM playersdbSkills WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int pLevel = 0;
            if (rs.next()){
                pLevel = rs.getInt("PlayerLEVEL");
                return pLevel;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void removeLevel(UUID uuid, int level){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET PlayerLEVEL=? WHERE UUID=?");
            ps.setInt(1, getLevel(uuid) - level);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //LevelUP
    public void addCharacteristicUP(UUID uuid, int characteristicUP){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET CharacteristicUP=? WHERE UUID=?");
            ps.setInt(1, getCharacteristicUP(uuid) + characteristicUP);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getCharacteristicUP(UUID uuid){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT CharacteristicUP FROM playersdbSkills WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int pLevel = 0;
            if (rs.next()){
                pLevel = rs.getInt("CharacteristicUP");
                return pLevel;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void removeCharacteristicUP(UUID uuid, int characteristicUP){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET CharacteristicUP=? WHERE UUID=?");
            ps.setInt(1, getCharacteristicUP(uuid) - characteristicUP);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addSkillUP(UUID uuid, int skillUP){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET SkillUP=? WHERE UUID=?");
            ps.setInt(1, getCharacteristicUP(uuid) + skillUP);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getSkillUP(UUID uuid){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT SkillUP FROM playersdbSkills WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int pLevel = 0;
            if (rs.next()){
                pLevel = rs.getInt("SkillUP");
                return pLevel;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void removeSkillUP(UUID uuid, int skillUP){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET SkillUP=? WHERE UUID=?");
            ps.setInt(1, getCharacteristicUP(uuid) - skillUP);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //EXP-Level

    public void addPlayerExp(UUID uuid, float exp){
        try {

            float levelCheck = getPlayerEXP(uuid) + exp;
            if (levelCheck >= getEXPtoNextLevel(uuid)){
                float expFinal = levelCheck - getEXPtoNextLevel(uuid);
                addLevel(uuid, 1);
                addSkillUP(uuid, 3);
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET PlayerLEVELexp=? WHERE UUID=?");
                ps.setFloat(1, expFinal);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }
            else {
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET PlayerLEVELexp=? WHERE UUID=?");
                ps.setFloat(1, getPlayerEXP(uuid) + exp);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public float getPlayerEXP(UUID uuid){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PlayerLEVELexp FROM playersdbSkills WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int pLevel = 0;
            if (rs.next()){
                pLevel = rs.getInt("PlayerLEVELexp");
                return pLevel;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //LevelEXP Base
    public float getEXPtoNextLevel(UUID uuid){
        int pLevel = getLevel(uuid);
        float lEXP = 1000;
        float expCalc = 0;


        for (int i=0; i < pLevel; i++) {
            expCalc = (lEXP / 100) * 5;
            lEXP = lEXP + expCalc;
        }
        int lEXPfinal = (int) lEXP;

        return lEXPfinal;
    }

    //Characteristic
    public void addCharacteristic(UUID uuid, int characteristicNumber, int characteristicAmmount ){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET Characteristic" + characteristicNumber + "=? WHERE UUID=?");
            ps.setInt(1, getCharacteristic(uuid, characteristicNumber) + characteristicAmmount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getCharacteristic(UUID uuid, int characteristicNumber){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT Characteristic" + characteristicNumber + " FROM playersdbSkills WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int skill = 0;
            if (rs.next()){
                skill = rs.getInt("Characteristic" + characteristicNumber);
                return skill;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void removeCharacteristic(UUID uuid, int characteristicNumber, int characteristicAmmount ){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET Characteristic" + characteristicNumber + "=? WHERE UUID=?");
            ps.setInt(1, getCharacteristic(uuid, characteristicNumber) - characteristicAmmount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Особенности
    public void addOSOP(UUID uuid, String osop, Player p){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playersdbSkills SET OSOPDATA=? WHERE UUID=?");

            String osopData = getOSOP(p.getUniqueId());
            boolean isExist = false;

            if (osopData == ""){
                osopData = osop + ";";
                ps.setString(1, osopData);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }else {
                String[] osopArray = osopData.split(";");
                for (int i = 0; i < osopArray.length; i++){
                    if (osopArray[i].equals(osop)){
                        isExist = true;
                    }
                }
                if (isExist == false) {
                    osopData = osopData + osop + ";";
                    ps.setString(1, osopData);
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getOSOP(UUID uuid){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT OSOPDATA FROM playersdbSkills WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            String osop = "null";
            if (rs.next()){
                osop = rs.getString("OSOPDATA");
                return osop;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
