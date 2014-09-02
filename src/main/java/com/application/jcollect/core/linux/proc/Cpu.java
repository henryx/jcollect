/*
 Copyright (C) 2014 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.core.linux.proc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        String[] data;
        String line;

        result = new HashMap<>();
        store = new HashMap<String, String>();

        try (BufferedReader in = new BufferedReader(new FileReader(this.stat));) {
            while ((line = in.readLine()) != null) {
                data = line.replace("  ", " ").split(" ");
                if (data[0].startsWith("cpu")) {
                    store.put(data[0] + ".name", data[0]);
                    store.put(data[0] + ".user", data[1]);
                    store.put(data[0] + ".system", data[3]);
                    store.put(data[0] + ".idle", data[4]);
                } else if (data[0].equals("procs_running")) {
                    store.put("procs.running", data[1]);
                } else if (data[0].equals("procs_blocked")) {
                    store.put("procs.blocked", data[1]);
                }
            }

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
