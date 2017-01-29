package com.ziemsky.fsstructure;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.createDirectories;

class DirWriter implements FsItemWriter<FsDir> {

    private final FsDir fsDir;
    private final FsStructureWriter fsStructureWriter;

    DirWriter(final FsDir fsDir, final FsStructureWriter fsStructureWriter) {
        this.fsDir = fsDir;
        this.fsStructureWriter = fsStructureWriter;
    }

    public void saveIn(final Path parentDir) {

        String name = null;
        Path thisDir = null;

        try {
            name = fsDir.getName();
            thisDir = Paths.get(parentDir.toString(), name);
            createDirectories(thisDir);

        } catch (final Exception e) {
            throw new RuntimeException("Failed to create directory " + thisDir, e);
        }

        fsStructureWriter.write(fsDir.getFsItems(), thisDir);
    }
}
