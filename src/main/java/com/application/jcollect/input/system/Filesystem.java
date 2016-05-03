/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.input.system;

import com.application.jcollect.input.Input;
import org.ini4j.Profile.Section;
import oshi.SystemInfo;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class Filesystem extends Input {

    private final String metricName = "Filesystem";
    private SystemInfo si;

    public Filesystem(Section section) {
        super(section);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
