package com.ziemsky.fsstructure;

import java.nio.file.Path;

import static com.ziemsky.fsstructure.FsTools.getPath;

public class FsFileWriter implements FsItemWriter<FsFile> {

    private final FsFile fsFile;

    private FsTools fsTools;

    public FsFileWriter(final FsFile fsFile, final FsTools fsTools) {
        this.fsFile = fsFile;
        this.fsTools = fsTools;
    }

    public void saveIn(final Path parentDir) {

        final Path targetFilePath = getPath(parentDir, fsFile.name());

        try {
            fsTools.createFile(targetFilePath);
        } catch (final Exception e) {
            throw new RuntimeException("Failed to create file " + targetFilePath, e);
        }

        try {
            fsTools.write(targetFilePath, fsFile.content());
        } catch (final Exception e) {
            throw new RuntimeException("Failed to write to file " + targetFilePath, e);
        }

    }
}
