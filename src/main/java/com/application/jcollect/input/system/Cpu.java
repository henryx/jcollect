/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.input.system;

import com.application.jcollect.input.Input;
import com.sun.management.OperatingSystemMXBean;
import org.ini4j.Profile.Section;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;

import java.lang.management.ManagementFactory;
import java.util.LinkedHashMap;

/**
 * @author enrico.bianchi@gmail.com
 */
public class Cpu extends Input {

    private final CentralProcessor cpu;
    private final String metricName = "Cpu";

    public Cpu(Section section) {
        super(section);

        this.cpu = this.si.getHardware().getProcessor();
    }

    private double[] getCpuLoadAverage() {
        double[] load;

        load = this.cpu.getSystemLoadAverage(3);
        return load;
    }

    private double getCpuLoad() {
        double load;

        OperatingSystemMXBean beam = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        load = beam.getSystemCpuLoad() * 100;
        return load;
    }

    private double[] getCpuLoads() {
        double[] loads;

        loads = this.cpu.getProcessorCpuLoadBetweenTicks();
        return loads;
    }

    private long getCpuIoWait() {
        long[] iowait;

        iowait = this.cpu.getSystemCpuLoadTicks();
        return iowait[TickType.IOWAIT.getIndex()];
    }

    @Override
    public void run() {
        LinkedHashMap<String, String> data;
        double[] cpuloads, avgloads;

        data = new LinkedHashMap<>();
        cpuloads = this.getCpuLoads();
        avgloads = this.getCpuLoadAverage();

        data.put("hostname", this.getHostname());
        data.put("os", this.getOs());
        for (int i = 0; i < cpuloads.length; i++) {
            data.put("cpu" + (i + 1), Double.toString(cpuloads[i] * 100));
        }

        if (this.section.get("aggregate", boolean.class)) {
            data.put("cpu", Double.toString(this.getCpuLoad()));
        } else {
            data.put("cpu", "");
        }
        data.put("iowait", Long.toString(this.getCpuIoWait()));

        data.put("load1", Double.toString(avgloads[0]));
        data.put("load5", Double.toString(avgloads[1]));
        data.put("load15", Double.toString(avgloads[2]));

        this.write(this.metricName, data);
    }
}
