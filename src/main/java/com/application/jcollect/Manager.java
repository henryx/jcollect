/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect;

import org.ini4j.Wini;

/**
 *
 * @author enrico
 */
public class Manager {
    
    public static void exec(Wini cfg) {
        for (String section : cfg.keySet()) {
            if (!(section.equals("general") || section.equals("dataset"))) {
                // TODO: launch thread for section
            }
        }
    }
    
}
