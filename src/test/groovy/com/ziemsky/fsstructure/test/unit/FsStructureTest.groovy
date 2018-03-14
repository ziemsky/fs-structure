package com.ziemsky.fsstructure.test.unit

import com.ziemsky.fsstructure.FsStructure
import spock.lang.Specification

import static com.ziemsky.fsstructure.FsStructure.create

class FsStructureTest extends Specification {

    // Primary version of FsStructure.create is being tested in functional tests
    // what about overloads, though?

    def "creates new file item with default content"() {}
    def "creates new file item with provided content"() {}
    def "fails to create new file item with no file name provided"() {}

    def "creates new empty directory item"() {}
    def "creates empty directory item with content"() {}
    def "fails to create new directory item with no directory name provided"() {}

    def "creates empty structure when no items given"() {
        when:
            final FsStructure actualFsStructure = FsStructure.create()

        then:
            actualFsStructure.toString() == "\n\n"
    }

}
