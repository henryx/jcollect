/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.input.system;

import com.application.jcollect.input.Input;
import java.util.LinkedHashMap;
import org.ini4j.Profile.Section;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

/**
 *
 * @author enrico
 */
public class Network extends Input {

    private final String metricName = "Network";
    private final SystemInfo si;

    public Network(Section section) {
        super(section);

        this.si = new SystemInfo();
    }

    @Override
    public void run() {
        LinkedHashMap<String, String> data;

        for (NetworkIF netIF : this.si.getHardware().getNetworkIFs()) {
            data = new LinkedHashMap<>();

            data.put("name", netIF.getName());
            data.put("mac", netIF.getMacaddr());
            data.put("bytessent", Long.toString(netIF.getBytesSent()));
            data.put("bytesrecv", Long.toString(netIF.getBytesRecv()));
            data.put("packetssent", Long.toString(netIF.getPacketsSent()));
            data.put("packetsrecv", Long.toString(netIF.getPacketsRecv()));

            this.write(this.metricName, data);
        }
    }
}
