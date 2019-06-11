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
import oshi.hardware.HWDiskStore;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class Disk extends Input {

    private final String metricName = "Disk";

    public Disk(Section section) {
        super(section);
    }

    @Override
    public void run() {
        LinkedHashMap<String, String> data;

        for (HWDiskStore store : this.si.getHardware().getDiskStores()) {
            data = new LinkedHashMap<>();
            data.put("hostname", this.getHostname());
            data.put("os", this.getOs());

            data.put("name", store.getName());
            data.put("reads", Long.toString(store.getReads()));
            data.put("writes", Long.toString(store.getWrites()));
            data.put("ioqueue", Long.toString(store.getCurrentQueueLength()));

            this.write(this.metricName, data);
        }
    }
}
