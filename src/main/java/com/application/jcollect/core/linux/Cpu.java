/*
 Copyright (C) 2014 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.core.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author enrico
 */
public class Cpu {

    private static final String STAT = "/proc/stat";
    private File stat;

    public Cpu() {
        super();
        this.stat = new File(this.STAT);
    }

    public HashMap<String, HashMap<String, String>> get() {
        HashMap<String, HashMap<String, String>> result;
        HashMap<String, String> store;
        String[] line;

        result = new HashMap<>();
        store = new HashMap<String, String>();
        
        try (BufferedReader in = new BufferedReader(new FileReader(this.stat));) {

            line = in.readLine().replace("  ", " ").split(" ");
            store.put("cpu.name", line[0]);
            store.put("cpu.user", line[1]);
            store.put("cpu.system", line[3]);
            store.put("cpu.idle", line[4]);

            line = in.readLine().replace("  ", " ").split(" ");
            store.put("cpu.0.name", line[0]);
            store.put("cpu.0.user", line[1]);
            store.put("cpu.0.system", line[3]);
            store.put("cpu.0.idle", line[4]);
            
            result.put("cpu", store);
            
        } catch (FileNotFoundException ex) {
            // Useless
            ex.printStackTrace();
        } catch (IOException ex) {
            // Useless
            ex.printStackTrace();
        }

        return result;
    }
}
