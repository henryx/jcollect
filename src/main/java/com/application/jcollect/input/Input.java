/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.input;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Profile.Section;
import oshi.SystemInfo;

/**
 *
 * @author enrico
 */
public abstract class Input implements Runnable {

    private String hostname;
    private final SystemInfo si;
    private final Section section;

    public Input(Section section) {
        this.section = section;
        this.si = new SystemInfo();
    }

    /**
     * @return the operating system
     */
    public String getOs() {
        return this.si.getOperatingSystem().getManufacturer();
    }
    
    public Section getSection() {
        return this.section;
    }

    public String getHostname() {
        try {
            if (this.hostname.equals("")) {
                return InetAddress.getLocalHost().getHostName();
            } else {
                return this.hostname;
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
            return "unknown";
        }
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
