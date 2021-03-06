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
import oshi.hardware.NetworkIF;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class Network extends Input {

    private final String metricName = "Network";

    public Network(Section section) {
        super(section);
    }

    @Override
    public void run() {
        LinkedHashMap<String, Object> data;

        for (NetworkIF netIF : this.si.getHardware().getNetworkIFs()) {
            data = new LinkedHashMap<>();

            data.put("hostname", this.getHostname());
            data.put("os", this.getOs());
            data.put("name", netIF.getName());
            data.put("mac", netIF.getMacaddr());
            data.put("bytessent", netIF.getBytesSent());
            data.put("bytesrecv", netIF.getBytesRecv());
            data.put("packetssent", netIF.getPacketsSent());
            data.put("packetsrecv", netIF.getPacketsRecv());

            this.write(this.metricName, data);
        }
    }
}
