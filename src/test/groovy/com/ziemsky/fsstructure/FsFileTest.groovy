package com.ziemsky.fsstructure

import spock.lang.Specification

import static com.ziemsky.fsstructure.test.util.TestDataProvider.newFileContent
import static com.ziemsky.fsstructure.test.util.TestDataProvider.newFsItemName

class FsFileTest extends Specification {

    def "constructor initialises all fields"() {

        given:
            final String expectedFileName = newFsItemName()
            final byte[] expectedFileContent = newFileContent()

        when:
            final FsFile actualFsFile = new FsFile(expectedFileName, expectedFileContent)

        then:
            with(actualFsFile) {
                name == expectedFileName
                content == expectedFileContent
            }
    }

    def "toString includes file name and type"() {

        given:
            final String expectedFileName = newFsItemName()
            final byte[] expectedFileContent = newFileContent()

        when:
            final FsFile actualFsFile = new FsFile(expectedFileName, expectedFileContent)

        then:
            actualFsFile.toString() == "FsFile{name='${actualFsFile.name}'}"
    }
}
