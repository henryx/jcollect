/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.input;

import com.application.jcollect.output.GenericOutput;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Profile.Section;

/**
 *
 * @author enrico
 */
public abstract class GenericInput implements Runnable {

    private String hostname;
    protected final Section section;
    protected GenericOutput output;

    public GenericInput(Section section) {
        this.section = section;
    }

    public void write(String section, LinkedHashMap<String, String> data) {
        this.output.setName(section);
        this.output.setData(data);
        this.output.write();
    }

    public String getOs() {
        return System.getProperty("os.name");
    }

    public void setOutput(GenericOutput output) {
        this.output = output;
    }

    public void setHostname(String hostname) {
        try {
            if (hostname.equals("")) {
                this.hostname = InetAddress.getLocalHost().getHostName();
            } else {
                this.hostname = hostname;
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(GenericInput.class.getName()).log(Level.SEVERE, null, ex);
            this.hostname = "unknown";
        }
    }

    public String getHostname() {
        return this.hostname;
    }
}
