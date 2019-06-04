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
import org.influxdb.InfluxDBIOException;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.ini4j.Profile.Section;

import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author enrico.bianchi@gmail.com
 */
public class OutputInfluxDB extends Output {

    private final InfluxDB influxDB;
    private final String dbName;

    public OutputInfluxDB(Section section) throws UnknownHostException, NoRouteToHostException {
        super(section);

        String url;
        String user;
        String password;

        url = "http://" + this.section.get("host") + ":" + this.section.get("port");
        user = this.section.getOrDefault("user", "");
        password = this.section.getOrDefault("password", "");

        this.dbName = this.section.get("database");

        try {
            if (user.equals("")) {
                this.influxDB = InfluxDBFactory.connect(url);
            } else {
                this.influxDB = InfluxDBFactory.connect(url, user, password);
            }

            Pong pong = this.influxDB.ping();
            if (!pong.isGood()) {
                throw new UnknownHostException("Cannot connect to " + url);
            }
        } catch (InfluxDBIOException ex) {
            throw new NoRouteToHostException(ex.getMessage());
        }

        if (!this.isDatabaseExists()) {
            this.createDatabase();
        }
    }

    private Boolean isDatabaseExists() {
        QueryResult results;
        Query query;

        query = new Query("SHOW DATABASES");
        results = this.influxDB.query(query);

        for (QueryResult.Result res : results.getResults()) {
            for (QueryResult.Series series: res.getSeries()) {
                for (List<Object> values: series.getValues()) {
                    for (Object value : values) {
                        if (value.equals(this.dbName)) {
                            return Boolean.TRUE;
                        }
                    }
                }
            }
        }

        return Boolean.FALSE;
    }

    private void createDatabase() {
        Query query;

        query = new Query("CREATE DATABASE " + this.dbName);
        this.influxDB.query(query);
    }

    @Override
    public void write() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
