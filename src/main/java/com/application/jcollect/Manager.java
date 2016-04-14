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
    private final HashMap<String, String> INPUTS = new HashMap<String, String>() {
        {
            put("cpu", "com.application.jcollect.input.Cpu");
        }
    };
    
    public Manager(Wini cfg) {
        this.CFG = cfg;
    }

    public void exec() throws ReflectiveOperationException, AttributeNotFoundException {

        for (String section : this.CFG.keySet()) {
            if (!(section.equals("general") || section.equals("output"))) {
                Constructor constructor = Class.forName(this.INPUTS.get(section)).getConstructor(Section.class);

                GenericInput input = (GenericInput) constructor.newInstance(this.CFG.get(section));
                input.setHostname(this.CFG.get("general", "hostname"));
                input.setOutput(this.computeOutput(CFG.get("output")));

                Thread thread = new Thread(input);
                thread.start();
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
}
