/*
 Copyright (C) 2014 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect;

import com.application.jcollect.core.linux.Proc;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author enrico
 */
public class Main {

    @Parameter(names = {"-h", "--help"}, help = true, description = "Print this help")
    private Boolean help;

    @Parameter(names = {"-c", "--cfg"}, description = "Set the configuration file", required = true)
    private String cfg;

    public Main() {
    }

    public Boolean getHelp() {
        return this.help;
    }

    public void go() throws FileNotFoundException, IOException, InterruptedException {
        Properties cfg;
        int interval;

        cfg = new Properties();
        cfg.load(new FileInputStream(new File(this.cfg)));

        interval = Integer.parseInt(cfg.getProperty("interval"));
        while (true) {
            switch (System.getProperty("os.name").split(" ")[0]) {
                case "Windows":
                    // TODO: write code for getting data on windows systems
                    break;
                case "Linux":
                    Proc proc = new Proc();
                    proc.get();
                    break;
            }
            Thread.sleep(interval * 1000);
        }
    }

    public static void main(String[] args) {
        JCommander cmd;
        Main app;

        app = new Main();
        try {
            cmd = new JCommander(app, args);
            cmd.setProgramName("JCollect");

            try {
                app.go();
            } catch (FileNotFoundException ex) {
                System.err.println("Configuration file not found");
                cmd.usage();
                System.exit(1);
            } catch (IOException ex) {
                System.err.println("Error when opening configuration file");
                System.exit(2);
            } catch (InterruptedException ex) {
                System.err.println("Interrupted");
                System.exit(3);
            }
        } catch (ParameterException ex) {
            System.err.println(ex.getMessage());
            System.exit(4);
        }
    }
}
