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

    private double getCpuLoad() {
        double load;

        load = this.cpu.getSystemCpuLoad() * 100;
        return load;
    }

    private double[] getCpuLoads() {
        double[] loads;

        loads = this.cpu.getProcessorCpuLoadBetweenTicks();
        return loads;
    }

    @Override
    public void run() {
        LinkedHashMap<String, String> data;
        double[] loads = new double[4];

        data = new LinkedHashMap<>();
        loads = this.getCpuLoads();

        data.put("hostname", this.getHostname());
        data.put("os", this.getOs());
        for (int i = 0; i < loads.length; i++) {
            data.put("cpu" + (i + 1), Double.toString(loads[i] * 100));
        }
        data.put("load", Double.toString(this.getCpuLoad()));
        data.put("loadavg", Double.toString(this.getCpuLoadAverage()));
        // TODO: getting Cpu metrics

        this.output.setName("Cpu");
        this.output.setData(data);
        this.output.write();
    }

}
