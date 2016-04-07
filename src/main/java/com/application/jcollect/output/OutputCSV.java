/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Profile.Section;

/**
 *
 * @author ebianchi
 */
public class OutputCSV extends GenericOutput {

    private final String COMMA_DELIMITER = ",";
    private final String NEW_LINE_SEPARATOR = "\n";

    public OutputCSV(Section section) {
        super(section);
    }

    @Override
    public void write() {
        String fileName = this.section.get("path") + File.separator + this.name.toLowerCase() + ".csv";
        
        try(FileWriter writer = new FileWriter(fileName)) {
            // TODO: Write code for writing data into CSV file
            
            writer.append(this.NEW_LINE_SEPARATOR);
            
            
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(OutputCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
