package com.ziemsky.fsstructure;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FsTools {

    public boolean isRegularFile(final Path path) {
        return Files.isRegularFile(path);
    }

    public byte[] readAllBytes(final Path file) {
        try {
            return Files.readAllBytes(file);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read content of " + file, e);
        }
    }

    public void createFile(final Path filePath) {
        try {
            Files.createFile(filePath);
        } catch (final IOException e) {
            throw new UncheckedIOException("Failed to create file " + filePath, e);
        }
    }

    public void write(final Path filePath, final byte[] content) {
        try {
            Files.write(filePath, content);
        } catch (final IOException e) {
            throw new UncheckedIOException("Failed to write to file " + filePath, e);
        }
    }

    public static Path getPath(final Path parentDir, final String childName) {
        return Paths.get(parentDir.toString(), childName);
    }

    public void createDirectories(final Path targetDir) {
        try {
            Files.createDirectories(targetDir);
        } catch (final IOException e) {
            throw new UncheckedIOException("Failed to create directory " + targetDir, e);
        }
    }
}
