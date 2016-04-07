/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.output;

import java.util.HashMap;
import org.ini4j.Profile.Section;

/**
 *
 * @author ebianchi
 */
public abstract class GenericOutput {

    private final Section section;
    private HashMap<String, String> data;
    private String name;

    public GenericOutput(Section section) {
        this.section = section;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Section getSection() {
        return this.section;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public abstract void write();
}
