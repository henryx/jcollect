/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.input;

import java.util.LinkedHashMap;
import org.ini4j.Profile.Section;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

/**
 *
 * @author ebianchi
 */
public class Cpu extends GenericInput {
    private final CentralProcessor cpu;

    public Cpu(Section section) {
        super(section);
        
        SystemInfo si;
        
        si = new SystemInfo();
        this.cpu = si.getHardware().getProcessor();
    }

    private double getCpuLoadAverage() {
        double load;
        
        load = this.cpu.getSystemLoadAverage();
        return load;
    }
    
    @Override
    public void run() {
        LinkedHashMap<String, String> data;

        data = new LinkedHashMap<>();

        data.put("hostname", this.getHostname());
        data.put("os", this.getOs());
        data.put("loadavg", Double.toString(this.getCpuLoadAverage()));
        // TODO: getting Cpu metrics

        this.output.setName("Cpu");
        this.output.setData(data);
        this.output.write();
    }

}
