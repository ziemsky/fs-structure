package com.ziemsky.fsstructure;

public class FsFile implements FsItem {

    private final String name;
    private final byte[] content;

    public FsFile(final String name, final byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public byte[] getContent() {
        return content;
    }

    @Override public String toString() {
        return "FsFile{" +
            "name='" + name + '\'' +
            '}';
    }
}
