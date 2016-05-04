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
        LinkedHashMap<String, String> data;

        data = new LinkedHashMap<>();
        data.put("total", Long.toString(this.mem.getTotal()));
        data.put("available", Long.toString(this.mem.getAvailable()));
        data.put("swaptotal", Long.toString(this.mem.getSwapTotal()));
        data.put("swapused", Long.toString(this.mem.getSwapUsed()));

        this.write(this.metricName, data);
    }
}
