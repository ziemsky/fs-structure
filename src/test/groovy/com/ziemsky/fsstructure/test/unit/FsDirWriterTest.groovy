package com.ziemsky.fsstructure.test.unit

import com.ziemsky.fsstructure.FsDirWriter
import com.ziemsky.fsstructure.FsDir
import com.ziemsky.fsstructure.FsItem
import com.ziemsky.fsstructure.FsStructureWriter
import com.ziemsky.fsstructure.FsTools
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

import static com.ziemsky.fsstructure.test.util.TestDataProvider.newFsItemName
import static com.ziemsky.fsstructure.test.util.TestDataProvider.newRuntimeException

class FsDirWriterTest extends Specification {

    private Path parentDirectory

    private FsItem[] expectedFsDirContent
    private String expectedDirName
    private FsDir fsDirToWrite

    private Path expectedDirPath

    private FsStructureWriter fsStructureWriter
    private FsTools fsTools

    private FsDirWriter fsDirWriter


    void setup() {
        parentDirectory = Paths.get(newFsItemName())

        expectedFsDirContent = [Mock(FsItem), Mock(FsItem)]
        expectedDirName = newFsItemName()
        fsDirToWrite = new FsDir(expectedDirName, expectedFsDirContent)

        expectedDirPath = Paths.get(parentDirectory.toString(), expectedDirName)

        fsStructureWriter = Mock(FsStructureWriter)
        fsTools = Mock(FsTools)

        fsDirWriter = new FsDirWriter(fsDirToWrite, fsStructureWriter, fsTools)
    }

    def "saves given FsDir and its content in given directory"() {

        when:
            fsDirWriter.saveIn(parentDirectory)

        then:
            1 * fsTools.createDirectories(expectedDirPath)
            1 * fsStructureWriter.write(expectedFsDirContent as List<FsItem>, expectedDirPath)
    }

    def "reports failure when can not create directory for given FsDir"() {

        given:
            fsTools.createDirectories(expectedDirPath) >> { throw newRuntimeException() }

        when:
            fsDirWriter.saveIn(parentDirectory)

        then:
            final RuntimeException actualException = thrown(RuntimeException)
            actualException.message == "Failed to create directory ${expectedDirPath}"
    }

    def "reports failure when can not write content of given FsDir"() {

        given:
            fsStructureWriter.write(expectedFsDirContent as List<FsItem>, expectedDirPath) >> {
                throw newRuntimeException()
            }

        when:
            fsDirWriter.saveIn(parentDirectory)

        then:
            final RuntimeException actualException = thrown(RuntimeException)
            actualException.message == "Failed to write content of ${expectedDirPath}"
    }

}
