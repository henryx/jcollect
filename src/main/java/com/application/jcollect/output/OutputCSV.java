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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Profile.Section;

/**
 *
 * @author enrico.bianchi@gmail.com
 */
public class OutputCSV extends Output {

    private final String COMMA_DELIMITER = ",";
    private final String NEW_LINE_SEPARATOR = "\n";

    public OutputCSV(Section section) {
        super(section);
    }

    @Override
    public void write() {
        String fileName;
        Path path;
        boolean header;
        long now;

        now = System.currentTimeMillis() / 1000L;
        fileName = this.section.get("path") + File.separator + this.name.toLowerCase() + ".csv";
        path = Paths.get(fileName);
        header = !Files.exists(path);
        
        try (FileWriter writer = new FileWriter(fileName, true)) {
            if (header) {
                writer.append("date");
                writer.append(this.COMMA_DELIMITER);
                for (String key : this.data.keySet()) {
                    writer.append(key);
                    writer.append(this.COMMA_DELIMITER);
                }
                writer.append(this.NEW_LINE_SEPARATOR);
            }

            writer.append(Long.toString(now));
            writer.append(this.COMMA_DELIMITER);
            for (String key : this.data.keySet()) {
                writer.append(this.data.get(key));
                writer.append(this.COMMA_DELIMITER);
            }
            writer.append(this.NEW_LINE_SEPARATOR);

            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(OutputCSV.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
