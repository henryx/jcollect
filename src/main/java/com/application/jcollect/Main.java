/*
 Copyright (C) 2014 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 *
 * @author enrico
 */
public class Main {
    private Options opts;
    
    public Main() {
        this.opts = new Options();

        this.opts.addOption("h", "help", false, "Print this help");
        this.opts.addOption(OptionBuilder
                .withLongOpt("cfg")
                .withDescription("Set the configuration file")
                .hasArg()
                .withArgName("CFGFILE")
                .create("c"));
    }
    
        public void printHelp(Integer code) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Memento", this.opts);
        System.exit(code);
    }

    public static void main(String[] args) {
        // TODO: add code
    }
}
