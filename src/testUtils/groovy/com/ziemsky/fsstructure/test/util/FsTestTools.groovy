package com.ziemsky.fsstructure.test.util

import java.nio.file.Path
import java.nio.file.Paths

import static com.ziemsky.fsstructure.test.util.TestDataProvider.newFileContent
import static com.ziemsky.fsstructure.test.util.TestDataProvider.newFsItemName
import static java.nio.file.Files.*

class FsTestTools {

    final private Path rootDir

    FsTestTools(final Path rootDir) {
        this.rootDir = rootDir
    }

    Path newDirectory() {
        newDirectory(newFsItemName())
    }

    Path newDirectory(String strokeDelimitedRelativePath) {
        createDirectories(concatenatePaths(rootDir, strokeDelimitedRelativePath))
    }

    Path newPathInMemory() {
        return Paths.get(rootDir.toString(), newFsItemName())
    }

    Path newFileEmpty() {
        return newFile(null)
    }

    Path newFile() {
        return newFile(newFileContent())
    }

    Path newFile(final byte[] content) {
        return newFile(newFsItemName(), content)
    }

    Path newFile(final String relativePath, final byte[] content) {
        final Path filePath = concatenatePaths(rootDir, relativePath)

        createDirectories(filePath.getParent())

        final Path createdFile = createFile(filePath)

        if (content != null) {
            write(createdFile, content)
        }

        return createdFile
    }

    static Path concatenatePaths(final Path parentDir, final String strokeDelimitedRelativePath) {
        final StringJoiner stringJoiner = new StringJoiner('/', parentDir.toString() + '/', "")
        for (String pathFragment: strokeDelimitedRelativePath.split('/')) {
            stringJoiner.add pathFragment
        }
        return Paths.get(stringJoiner.toString())
    }
}
