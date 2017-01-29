package com.ziemsky.fsstructure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class FileWriter implements FsItemWriter<FsFile> {

    private final FsFile fsFile;

    FileWriter(final FsFile fsFile) {
        this.fsFile = fsFile;
    }

    public void saveIn(final Path dir) {

        Path filePath = null;
        try {
            filePath = Paths.get(dir.toString(), fsFile.getName());

            Files.createFile(filePath);

            Files.write(filePath, fsFile.getContent());

        } catch (final Exception e) {
            throw new RuntimeException("Failed to create file " + filePath, e);
        }
    }
}
