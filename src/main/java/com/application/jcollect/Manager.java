/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect;

import com.application.jcollect.input.Input;
import com.application.jcollect.output.Output;
import com.application.jcollect.output.OutputCSV;
import java.lang.reflect.Constructor;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.management.AttributeNotFoundException;

import com.application.jcollect.output.database.OutputInfluxDB;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class Manager {

    private final Wini CFG;
    private final HashMap<String, Input> ENABLEDINPUTS;
    private final HashMap<String, String> INPUTS = new HashMap<String, String>() {
        {
            put("cpu", "com.application.jcollect.input.system.Cpu");
            put("memory", "com.application.jcollect.input.system.Memory");
            put("network", "com.application.jcollect.input.system.Network");
            put("disk", "com.application.jcollect.input.system.Disk");
            put("filesystem", "com.application.jcollect.input.system.Filesystem");
        }
    };

    public Manager(Wini cfg) throws ReflectiveOperationException, AttributeNotFoundException, UnknownHostException, NoRouteToHostException {
        this.CFG = cfg;
        this.ENABLEDINPUTS = new HashMap<>();

        this.init();
    }

    private void init() throws ReflectiveOperationException, AttributeNotFoundException, UnknownHostException, NoRouteToHostException {
        for (String section : this.CFG.keySet()) {
            if (!(section.equals("general") || section.equals("output"))
                    && this.CFG.get(section, "enabled", boolean.class)) {
                Constructor constructor = Class.forName(this.INPUTS.get(section)).getConstructor(Section.class);

                Input input = (Input) constructor.newInstance(this.CFG.get(section));
                input.setHostname(CFG.get("general", "hostname"));
                input.setOutput(this.computeOutput());
                this.ENABLEDINPUTS.put(section, input);
            }
        }
    }

    private Output computeOutput() throws AttributeNotFoundException, UnknownHostException, NoRouteToHostException {
        String type;

        type = this.CFG.get("output", "type");
        switch (type) {
            case "csv":
                return new OutputCSV(this.CFG.get("output"));
            case "influxdb":
                return new OutputInfluxDB(this.CFG.get("output"));
            default:
                throw new AttributeNotFoundException("Type not valid: " + type);
        }
    }

    public void exec() {
        ScheduledExecutorService exec;

        exec = Executors.newSingleThreadScheduledExecutor();
        for (String input : this.ENABLEDINPUTS.keySet()) {
            Thread thread = new Thread(this.ENABLEDINPUTS.get(input));
            thread.setName(input + " data collector");

            exec.scheduleAtFixedRate(thread,
                    0,
                    Integer.parseInt(this.CFG.get("general", "interval")),
                    TimeUnit.SECONDS);
        }
    }
}
