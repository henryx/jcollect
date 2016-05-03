/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.input.system;

import com.application.jcollect.input.Input;
import java.util.LinkedHashMap;
import org.ini4j.Profile.Section;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class Disk extends Input {

    private final String metricName = "Disk";
    private SystemInfo si;

    public Disk(Section section) {
        super(section);

        si = new SystemInfo();
    }

    @Override
    public void run() {
        LinkedHashMap<String, String> data;

        for (HWDiskStore store : this.si.getHardware().getDiskStores()) {
            data = new LinkedHashMap<>();
            data.put("name", store.getName());
            data.put("reads", Long.toString(store.getReads()));
            data.put("writes", Long.toString(store.getWrites()));

            this.write(this.metricName, data);
        }
    }
}
