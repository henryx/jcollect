/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect;

import com.application.jcollect.input.GenericInput;
import com.application.jcollect.output.GenericOutput;
import com.application.jcollect.output.OutputCSV;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import javax.management.AttributeNotFoundException;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;

/**
 *
 * @author enrico
 */
public class Manager {

    private final Wini CFG;
    private final HashMap<String, GenericInput> ENABLEDINPUTS;
    private final HashMap<String, String> INPUTS = new HashMap<String, String>() {
        {
            put("cpu", "com.application.jcollect.input.Cpu");
        }
    };

    public Manager(Wini cfg) throws ReflectiveOperationException, AttributeNotFoundException {
        this.CFG = cfg;
        this.ENABLEDINPUTS = new HashMap<>();

        this.init();
    }

    private void init() throws ReflectiveOperationException, AttributeNotFoundException {
        for (String section : this.CFG.keySet()) {
            if (!(section.equals("general") || section.equals("output"))
                    && this.CFG.get(section, "enabled", boolean.class)) {
                Constructor constructor = Class.forName(this.INPUTS.get(section)).getConstructor(Section.class);

                GenericInput input = (GenericInput) constructor.newInstance(this.CFG.get(section));
                input.setHostname(CFG.get("general", "hostname"));
                input.setOutput(this.computeOutput(this.CFG.get("output")));
                this.ENABLEDINPUTS.put(section, input);
            }
        }
    }

    private GenericOutput computeOutput(Section section) throws AttributeNotFoundException {
        String type;

        type = section.get("type");
        switch (type) {
            case "csv":
                return new OutputCSV(section);
            default:
                throw new AttributeNotFoundException("Type not valid: " + type);
        }
    }

    public void exec() {
        for (String input : this.ENABLEDINPUTS.keySet()) {
            Thread thread = new Thread(this.ENABLEDINPUTS.get(input));
            thread.setName(input + " data collector");
            thread.start();
        }
    }
}
