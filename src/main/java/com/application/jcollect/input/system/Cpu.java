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
    private long[][] prevLoadTicks;

    public Cpu(Section section) {
        super(section);

        this.cpu = this.si.getHardware().getProcessor();
        this.prevLoadTicks = this.cpu.getProcessorCpuLoadTicks();
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

        loads = this.cpu.getProcessorCpuLoadBetweenTicks(this.prevLoadTicks);
        this.prevLoadTicks = this.cpu.getProcessorCpuLoadTicks();

        return loads;
    }

    private long getCpuIoWait() {
        long[] iowait;

        iowait = this.cpu.getSystemCpuLoadTicks();
        return iowait[TickType.IOWAIT.getIndex()];
    }

    @Override
    public void run() {
        LinkedHashMap<String, Object> data;
        double[] cpuloads, avgloads;

        data = new LinkedHashMap<>();
        cpuloads = this.getCpuLoads();
        avgloads = this.getCpuLoadAverage();

        data.put("hostname", this.getHostname());
        data.put("os", this.getOs());
        for (int i = 0; i < cpuloads.length; i++) {
            data.put(String.format("cpu%d", (i + 1)), cpuloads[i] * 100);
        }

        if (this.section.get("aggregate", boolean.class)) {
            data.put("cpu", this.getCpuLoad());
        } else {
            data.put("cpu", "");
        }
        data.put("iowait", this.getCpuIoWait());

        data.put("load1", avgloads[0]);
        data.put("load5", avgloads[1]);
        data.put("load15", avgloads[2]);

        this.write(this.metricName, data);
    }
}
