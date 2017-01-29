package com.ziemsky.fsstructure.test.util

import java.nio.file.Path
import java.nio.file.Paths

import static java.nio.file.Files.createDirectories
import static java.nio.file.Files.createFile

class FileSystemTestUtils {

    static Path createDirectory(Path parentDir, String strokeDelimitedRelativePath) {
        createDirectories(concatenatePaths(parentDir, strokeDelimitedRelativePath))
    }

    static Path createFile(Path parentDir, String relativePath) {
        final Path filePath = concatenatePaths(parentDir, relativePath)

        createDirectories(filePath.getParent())

        return createFile(filePath)
    }

    static Path concatenatePaths(Path parentDir, String strokeDelimitedRelativePath) {
        StringJoiner stringJoiner = new StringJoiner('/', parentDir.toString() + '/', "")
        for (String pathFragment: strokeDelimitedRelativePath.split('/')) {
            stringJoiner.add pathFragment
        }
        return Paths.get(stringJoiner.toString())
    }
}
