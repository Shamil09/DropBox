package com.sc.cloud.client;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class Atributes {
    private final String name,type;
    private final long saze;
    private final FileTime date;

    public Atributes(String name, String type, long saze, FileTime date) {
        this.name = name;
        this.type = type;
        this.saze = saze;
        this.date=date;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public long getSaze() {
        return saze;
    }

    public FileTime getDate() {
        return date;
    }
}
