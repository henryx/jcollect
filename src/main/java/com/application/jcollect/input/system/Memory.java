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
import oshi.hardware.GlobalMemory;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class Memory extends Input {

    private final GlobalMemory mem;
    private final String metricName = "Memory";

    public Memory(Section section) {
        super(section);

        this.mem = this.si.getHardware().getMemory();
    }

    @Override
    public void run() {
        LinkedHashMap<String, Object> data;

        data = new LinkedHashMap<>();
        data.put("hostname", this.getHostname());
        data.put("os", this.getOs());
        data.put("total", this.mem.getTotal());
        data.put("available", this.mem.getAvailable());
        data.put("swaptotal", this.mem.getVirtualMemory().getSwapTotal());
        data.put("swapused", this.mem.getVirtualMemory().getSwapUsed());

        this.write(this.metricName, data);
    }
}
