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
 * @author ebianchi
 */
public class Cpu extends GenericInput {
    
    public Cpu(Section section) {
        super(section);
    }

    @Override
    public void run() {
        String hostname = this.getHostname();
        String os = this.getOs();
    }
    
}
