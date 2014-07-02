/*
 Copyright (C) 2014 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

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
                .isRequired()
                .withArgName("CFGFILE")
                .create("c"));
    }

    public void printHelp(Integer code) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("JCollect", this.opts);
        System.exit(code);
    }

    public void go(String[] args) throws ParseException, FileNotFoundException, IOException, InterruptedException {
        CommandLine cmd;
        CommandLineParser parser;
        Properties cfg;
        int interval;

        parser = new PosixParser();
        cmd = parser.parse(this.opts, args);

        cfg = new Properties();
        cfg.load(new FileInputStream(new File(cmd.getOptionValue("cfg"))));

        interval = Integer.parseInt(cfg.getProperty("interval"));
        while (true) {
            // TODO add code for execute collectors systems
            Thread.sleep(interval * 1000);
        }
    }

    public static void main(String[] args) {
        Main app;

        app = new Main();
        try {
            app.go(args);
        } catch (ParseException ex) {
            app.printHelp(1);
        } catch (FileNotFoundException ex) {
            System.err.println("Configuration file not found");
        } catch (IOException ex) {
            System.err.println("Error when opening configuration file");
        } catch (InterruptedException ex) {
            System.err.println("Interrupted");
        }
    }
}
