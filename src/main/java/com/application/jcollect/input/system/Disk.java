/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.input.system;

import com.application.jcollect.input.Input;
import org.ini4j.Profile.Section;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class Disk extends Input {
    private final String metricName = "Disk";

    public Disk(Section section) {
        super(section);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
