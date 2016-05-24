/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.output.database;

import com.application.jcollect.output.Output;
import org.ini4j.Profile.Section;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class OutputInfluxDB extends Output {

    public OutputInfluxDB(Section section) {
        super(section);
    }

    @Override
    public void write() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
