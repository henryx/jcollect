/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.input;

import org.ini4j.Profile.Section;

/**
 *
 * @author enrico
 */
public class Network extends GenericInput {

    private final String metricName = "Network";

    public Network(Section section) {
        super(section);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
