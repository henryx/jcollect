/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.input;

import java.util.LinkedHashMap;
import org.ini4j.Profile.Section;

/**
 *
 * @author ebianchi
 */
public class Cpu extends GenericInput {

    public Cpu(Section section) {
        super(section);
    }

    @Override
    public void run() {
        LinkedHashMap<String, String> data;

        data = new LinkedHashMap<>();

        data.put("hostname", this.getHostname());
        data.put("os", this.getOs());
        // TODO: getting Cpu metrics

        this.output.setName("Cpu");
        this.output.setData(data);
        this.output.write();
    }

}
