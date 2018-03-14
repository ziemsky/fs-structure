package com.ziemsky.fsstructure.test.integration

import com.ziemsky.fsstructure.FsTools
import com.ziemsky.fsstructure.test.util.FsTestTools
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.nio.file.*

import static com.ziemsky.fsstructure.test.util.TestDataProvider.newFileContent
import static com.ziemsky.fsstructure.test.util.TestDataProvider.newFsItemName
import static java.nio.file.Files.readAllBytes

class FsToolsTest extends Specification {

    @Rule
    final TemporaryFolder tempDir = new TemporaryFolder()

    private Path testDir
    private FsTestTools fsTestTools

    private FsTools fsTools

    void setup() {
        testDir = tempDir.getRoot().toPath()
        fsTestTools = new FsTestTools(testDir)

        fsTools = new FsTools()
    }

    def "verifies that given path represents a regular file"() {

        given:
            final Path filePath = fsTestTools.newFile()

        when:
            final boolean isRegularFile = fsTools.isRegularFile(filePath)

        then:
            isRegularFile
    }

    def "verifies that given path does not represent a regular file"() {

        given:
            final Path filePath = fsTestTools.newDirectory()

        when:
            final boolean isRegularFile = fsTools.isRegularFile(filePath)

        then:
            !isRegularFile
    }

    def "reads in content of a given file"() {

        given:
            final byte[] expectedFileContent = newFileContent()
            final Path filePath = fsTestTools.newFile(expectedFileContent)

        when:
            final byte[] actualFileContent = fsTools.readAllBytes(filePath)

        then:
            actualFileContent == expectedFileContent
    }

    def "reports failure when cannot read content of a given file"() {

        given:
            final Path nonExistentFilePath = fsTestTools.newPathInMemory()

        when:
            fsTools.readAllBytes(nonExistentFilePath)

        then:
            final UncheckedIOException exception = thrown(UncheckedIOException)
            exception.message == "Failed to read content of ${nonExistentFilePath}"
            exception.cause
            exception.cause.class == NoSuchFileException.class
            exception.cause.message == "${nonExistentFilePath}"
    }

    def "creates given file"() {

        given:
            final Path fileToBeCreated = fsTestTools.newPathInMemory()

        when:
            fsTools.createFile(fileToBeCreated)

        then:
            Files.isRegularFile(fileToBeCreated)
    }

    def "reports failure when can not create given file"() {

        given:
            final Path existingFile = fsTestTools.newFile()

        when:
            fsTools.createFile(existingFile)

        then:
            final UncheckedIOException exception = thrown(UncheckedIOException)
            exception.message == "Failed to create file ${existingFile}"
            exception.cause
            exception.cause.class == FileAlreadyExistsException.class
            exception.cause.message == "${existingFile}"
    }

    def "writes given content to given file"() {

        given:
            final Path expectedFile = fsTestTools.newFileEmpty()
            final expectedFileContent = newFileContent()

        when:
            fsTools.write(expectedFile, expectedFileContent)

        then:
            readAllBytes(expectedFile) == expectedFileContent
    }

    def "reports failure when can not write to given file"() {

        given:
            final Path existingUnwritableFile = fsTestTools.newFile()
            existingUnwritableFile.toFile().setReadOnly()

            final byte[] fileContentToWrite = newFileContent()

        when:
            fsTools.write(existingUnwritableFile, fileContentToWrite)

        then:
            final UncheckedIOException exception = thrown(UncheckedIOException)
            exception.message == "Failed to write to file ${existingUnwritableFile}"
            exception.cause
            exception.cause.class == AccessDeniedException.class
            exception.cause.message == "${existingUnwritableFile}"
    }

    def "creates given directory together with its missing parents"() {
        given:
            final String nonExistingParentDirName = newFsItemName()
            final String expectedDirName = newFsItemName()
            final Path expectedDir = Paths.get(testDir.toString(), nonExistingParentDirName, expectedDirName)

        when:
            fsTools.createDirectories(expectedDir)

        then:
            Files.isDirectory(expectedDir)
    }

    def "reports failure when can not create given directory"() {

        given:
            final Path existingConflictingFilePath = fsTestTools.newFile()

        when:
            fsTools.createDirectories(existingConflictingFilePath)

        then:
            final UncheckedIOException exception = thrown(UncheckedIOException)
            exception.message == "Failed to create directory ${existingConflictingFilePath}"
            exception.cause
            exception.cause.class == FileAlreadyExistsException.class
            exception.cause.message == "${existingConflictingFilePath}"
    }
}
