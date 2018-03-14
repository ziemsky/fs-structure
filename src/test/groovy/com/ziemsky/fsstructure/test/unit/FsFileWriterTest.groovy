package com.ziemsky.fsstructure.test.unit

import com.ziemsky.fsstructure.FsFile
import com.ziemsky.fsstructure.FsTools
import com.ziemsky.fsstructure.FsFileWriter
import com.ziemsky.fsstructure.test.util.TestDataProvider
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

import static com.ziemsky.fsstructure.test.util.TestDataProvider.newFileContent
import static com.ziemsky.fsstructure.test.util.TestDataProvider.newFsItemName

class FsFileWriterTest extends Specification {

    private Path parentDirectory

    private byte[] expectedFileContent
    private String expectedFileName
    private FsFile fsFileToWrite

    private Path expectedFilePath

    private FsTools fsTools

    private FsFileWriter fsFileWriter


    void setup() {
        parentDirectory = Paths.get(newFsItemName())

        expectedFileContent = newFileContent()
        expectedFileName = newFsItemName()
        fsFileToWrite = new FsFile(expectedFileName, expectedFileContent)

        expectedFilePath = Paths.get(parentDirectory.toString(), expectedFileName)

        fsTools = Mock(FsTools)

        fsFileWriter = new FsFileWriter(fsFileToWrite, fsTools)
    }

    def "saves given FsFile in given directory"() {

        when:
            fsFileWriter.saveIn(parentDirectory)

        then:
            1 * fsTools.createFile(expectedFilePath)
            1 * fsTools.write(expectedFilePath, expectedFileContent)
    }

    def "reports failure when can not create file for given FsFile"() {
        given:
            fsTools.createFile(expectedFilePath) >> { throw TestDataProvider.newRuntimeException() }

        when:
            fsFileWriter.saveIn(parentDirectory)

        then:
            final RuntimeException actualException = thrown(RuntimeException)
            actualException.message == "Failed to create file ${expectedFilePath}"

    }

    def "reports failure when can not write content of given FsFile"() {
        given:
            fsTools.write(expectedFilePath, expectedFileContent) >> { throw TestDataProvider.newRuntimeException() }

        when:
            fsFileWriter.saveIn(parentDirectory)

        then:
            final RuntimeException actualException = thrown(RuntimeException)
            actualException.message == "Failed to write to file ${expectedFilePath}"
    }


}
