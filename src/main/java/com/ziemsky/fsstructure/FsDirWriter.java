package com.ziemsky.fsstructure;

import java.nio.file.Path;

import static com.ziemsky.fsstructure.FsTools.getPath;

public class FsDirWriter implements FsItemWriter<FsDir> {

    private final FsDir fsDir;
    private final FsStructureWriter fsStructureWriter;

    private FsTools fsTools;

    public FsDirWriter(final FsDir fsDir, final FsStructureWriter fsStructureWriter,
                       final FsTools fsTools) {
        this.fsDir = fsDir;
        this.fsStructureWriter = fsStructureWriter;
        this.fsTools = fsTools;
    }

    public void saveIn(final Path parentDirectory) {

        final Path thisDir = getPath(parentDirectory, fsDir.getName());

        try {
            fsTools.createDirectories(thisDir);

        } catch (final Exception e) {
            throw new RuntimeException("Failed to create directory " + thisDir, e);
        }

        try {
            fsStructureWriter.write(fsDir.getFsItems(), thisDir);
        } catch (final Exception e) {
            throw new RuntimeException("Failed to write content of " + thisDir, e);
        }
    }
}
