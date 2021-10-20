package com.evgenyn.encore.utilities;

import com.evgenyn.encore.SQL.SQLGetter;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OSOPSerializer {
    public OSOPSerializer(){
    }

    public List getOSOPS(String OSOPdata) throws IOException{
        String[] OSOPstring = OSOPdata.split(";");
        List<String> OSOParray = new ArrayList<>();
        for (int i = 0; i < OSOPstring.length; i++){
            if (!OSOPstring[i].equals("")){
                try {
                    OSOParray.add(OSOPstring[i]);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return OSOParray;
    }

}
