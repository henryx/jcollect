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

    private static final HashMap<String, String> INPUTS = new HashMap<String, String>() {
        {
            put("cpu", "com.application.jcollect.input.Cpu");
        }
    };

    public static void exec(Wini cfg) throws ReflectiveOperationException, AttributeNotFoundException {

        for (String section : cfg.keySet()) {
            if (!(section.equals("general") || section.equals("output"))) {
                Constructor constructor = Class.forName(Manager.INPUTS.get(section)).getConstructor(Section.class);

                GenericInput input = (GenericInput) constructor.newInstance(cfg.get(section));
                input.setHostname(cfg.get("general", "hostname"));
                input.setOutput(Manager.computeOutput(cfg.get("output")));

                Thread thread = new Thread(input);
                thread.start();
            }
        }
    }

    private static GenericOutput computeOutput(Section section) throws AttributeNotFoundException {
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
