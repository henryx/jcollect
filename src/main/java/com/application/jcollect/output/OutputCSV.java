/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public OutputCSV(Section section) {
        super(section);
    }

    @Override
    public void write() {
        String fileName = this.getSection().get("output", "path") + File.separator + this.getName() + ".csv";
        
        try(FileWriter writer = new FileWriter(fileName)) {
            // TODO: Write code for writing data into CSV file
            
            writer.append(NEW_LINE_SEPARATOR);
            
            
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(OutputCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
