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
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

/**
 * @author enrico.bianchi@gmail.com
 */
public class Filesystem extends Input {

    private FileSystem fs;
    private final String metricName = "Filesystem";

    public Filesystem(Section section) {
        super(section);
        this.fs = this.si.getOperatingSystem().getFileSystem();
    }

    @Override
    public void run() {
        LinkedHashMap<String, Object> data;

        for (OSFileStore store : this.si.getOperatingSystem().getFileSystem().getFileStores()) {
            if (!store.getType().contains("nfs")) {
                data = new LinkedHashMap<>();
                data.put("hostname", this.getHostname());
                data.put("os", this.getOs());
                data.put("volume", store.getVolume());
                data.put("mount", store.getMount());
                data.put("type", store.getType());
                data.put("size", store.getTotalSpace());
                data.put("used", store.getUsableSpace());
                data.put("free", (store.getTotalSpace() - store.getUsableSpace()));

                this.write(this.metricName, data);
            }
        }
    }
}
