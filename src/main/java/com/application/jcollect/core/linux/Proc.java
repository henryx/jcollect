/*
 Copyright (C) 2014 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.core.linux;

import com.application.jcollect.core.linux.Cpu;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author enrico
 */
public class Proc {

    public ArrayList<HashMap<String, HashMap<String, String>>> get() {
        ArrayList<HashMap<String, HashMap<String, String>>> result;
        Cpu cpu;

        result = new ArrayList<>();
        cpu = new Cpu();

        result.add(cpu.get());

        return result;
    }
}
