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
import oshi.software.os.OSFileStore;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class Filesystem extends Input {

    private final String metricName = "Filesystem";

    public Filesystem(Section section) {
        super(section);
    }

    @Override
    public void run() {
        LinkedHashMap<String, String> data;

        for (OSFileStore store : this.si.getHardware().getFileStores()) {
            if (!store.getType().contains("nfs")) {
                data = new LinkedHashMap<>();
                data.put("name", store.getName());
                data.put("type", store.getType());
                data.put("size", Long.toString(store.getTotalSpace()));
                data.put("used", Long.toString(store.getUsableSpace()));
                data.put("free", Long.toString(store.getTotalSpace() - store.getUsableSpace()));

                this.write(this.metricName, data);
            }
        }
    }
}
