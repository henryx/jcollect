/*
 Copyright (C) 2016 Enrico Bianchi (enrico.bianchi@gmail.com)
 Project       JCollect
 Description   A collectd system replacement
 License       GPL version 2 (see GPL.txt for details)
 */
package com.application.jcollect.output;

import java.util.LinkedHashMap;
import org.ini4j.Profile.Section;

/**
 *
 * @author ebianchi
 */
public abstract class Output {

    protected LinkedHashMap<String, String> data;
    protected String name;
    protected final Section section;

    public Output(Section section) {
        this.section = section;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(LinkedHashMap<String, String> data) {
        this.data = data;
    }

    public abstract void write();
}
