package com.ziemsky.fsstructure.test.unit

import com.ziemsky.fsstructure.FsDir
import com.ziemsky.fsstructure.FsItem
import spock.lang.Specification

import static com.ziemsky.fsstructure.test.util.TestDataProvider.newFsItemName

class FsDirTest extends Specification {

    def "constructor   initialises all fields"() {

        given:

            final String expectedDirName = newFsItemName()
            final FsItem fsItemA = Mock(FsItem.class)
            final FsItem fsItemB = Mock(FsItem.class)

        when:
            final FsDir actualFsDir = new FsDir(expectedDirName, fsItemA, fsItemB)

        then:
            with(actualFsDir) {
                name == expectedDirName
                fsItems == [fsItemA, fsItemB]
            }
    }

    def "toString includes dir name and type"() {

        given:
            final String expectedDirName = newFsItemName()

        when:
            final FsDir actualFsDir = new FsDir(expectedDirName)

        then:
            actualFsDir.toString() == "FsDir{name='${actualFsDir.name}'}"
    }
}
