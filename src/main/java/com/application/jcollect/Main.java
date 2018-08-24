/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.management.AttributeNotFoundException;
import org.ini4j.Wini;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class Main {

    @Parameter(names = {"-h", "--help"}, help = true, description = "Print this help")
    private Boolean help = Boolean.FALSE;

    @Parameter(names = {"-V", "--version"}, help = true, description = "Get version of the software")
    private Boolean version = Boolean.FALSE;
    
    @Parameter(names = {"-c", "--cfg"}, description = "Set the configuration file", required = true)
    private String cfg;
    
    
    public static final String VERSION = "0.3.0";

    public Main() {
    }

    public Boolean getHelp() {
        return this.help;
    }
    
    public Boolean getVersion() {
        return this.version;
    }

    public void go() throws FileNotFoundException, IOException, InterruptedException, ReflectiveOperationException, AttributeNotFoundException {
        Manager manager;
        Wini cfg;

        cfg = new Wini();
        cfg.load(new FileInputStream(new File(this.cfg)));
        manager = new Manager(cfg);

        manager.exec();
    }

    public static void main(String[] args) {
        JCommander cmd;
        Main app;

        app = new Main();
        try {
            cmd = JCommander.newBuilder().addObject(app).build();
            cmd.setProgramName("JCollect");
            cmd.parse(args);

            try {
                if (app.getHelp()) {
                    cmd.usage();
                    System.exit(0);
                }
                
                if (app.getVersion()){
                    System.out.println("JCollect " + Main.VERSION);
                    System.exit(0);
                }
                
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
            } catch (ReflectiveOperationException ex) {
                System.err.println("Problem when loading class: " + ex.getMessage());
                System.exit(5);
            } catch (AttributeNotFoundException ex) {
                System.err.println("Problem when processing data: " + ex.getMessage());
                System.exit(6);
            }
        } catch (ParameterException ex) {
            System.err.println(ex.getMessage());
            System.exit(4);
        }
    }
}
