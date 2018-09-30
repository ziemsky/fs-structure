package com.ziemsky.fsstructure;

import java.util.Collections;
import java.util.List;

public class FsFile extends AbstractFsItem {

    private final byte[] content;

    public FsFile(final String name, final byte[] content) {
        this(name, Collections.emptyList(), content);
    }

    public FsFile(final String name, final List<FsDir> parents, final byte[] content) {
        super(name, parents);
        this.content = content;
    }

    public byte[] content() {
        return content;
    }

    @Override public String toString() {
        return "FsFile{" +
            "name='" + name() + '\'' +
            '}';
    }
}
