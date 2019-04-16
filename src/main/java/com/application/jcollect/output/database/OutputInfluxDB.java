/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.output.database;

import com.application.jcollect.output.Output;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.ini4j.Profile.Section;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class OutputInfluxDB extends Output {

    private final InfluxDB influxDB;
    private final String dbName;

    public OutputInfluxDB(Section section) {
        super(section);

        String URL = "http://" + this.section.get("host") + ":" + this.section.get("port");
        
        this.dbName = this.section.get("database");
        this.influxDB = InfluxDBFactory.connect(URL, this.section.getOrDefault("user", ""),
                this.section.getOrDefault("password", ""));
        
        this.influxDB.createDatabase(this.dbName);
    }

    @Override
    public void write() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}