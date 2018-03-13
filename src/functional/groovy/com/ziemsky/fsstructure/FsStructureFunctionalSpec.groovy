package com.ziemsky.fsstructure

import com.ziemsky.fsstructure.test.util.TestDataProvider
import com.ziemsky.fsstructure.test.util.TestFilesystemItem
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.nio.file.Path

import static com.ziemsky.fsstructure.FsStructure.readFrom

class FsStructureFunctionalSpec extends Specification {

    @Rule
    final TemporaryFolder tempDir = new TemporaryFolder()

    private Path testDir
    private TestDataProvider testDataProvider

    def setup() {
        testDir = tempDir.getRoot().toPath()
        testDataProvider = new TestDataProvider(testDir)
    }

    def "reads the structure it wrote previously itself"() {
        // We're eating our own dog food here - the library is used both to write the structure to disk
        // and to read it to compare results.

        given:
            final FsStructure structureToWrite = testDataProvider.newReferenceFsStructure()

        when:
            structureToWrite.saveIn(testDir)

        then:
            final FsStructure actualFsStructure = readFrom(testDir)

            actualFsStructure == structureToWrite
    }

    def "writes the structure"() {
        // We use the library to write the structure to disk but use other methods to verify what has been written.

        given: "a structure of directories and files to in memory"
            final FsStructure structureToWrite = testDataProvider.newReferenceFsStructure()

        when: "it saves the structure"
            structureToWrite.saveIn(testDir)

        then: "the structure on the disk matches that in memory"
            final List<TestFilesystemItem> expectedFileSystemItems = testDataProvider.newReferenceTestFileSystemItems()

            int actualFileSystemItemsCount = 0
            testDir.traverse { actualFileSystemItemsCount++ }

            actualFileSystemItemsCount == expectedFileSystemItems.size()

            expectedFileSystemItems.forEach { testFilesystemItem ->
                assert testFilesystemItem.exists()
                assert testFilesystemItem.isOfExpectedType()
            }
    }

    def "reads the structure"() {
        // We use the library to read the structure to disk but use other methods to generate it on disk.

        given: "a structure of directories and files on the disk"
            testDataProvider.newReferenceTestFileSystemItems().forEach({ it.create() })

        when: "it reads the structure from disk into memory"
            final FsStructure actualFsStructureReadFromDisk = readFrom(testDir)

        then: "the items read from disk have expected structure"
            actualFsStructureReadFromDisk.toString() ==
                    '''dirLevel1_content.extension/
                      |  dirLevel2_content.extension/
                      |    dirLevel3_noContent.extension/
                      |    dirLevel3_noContent_noExtension/
                      |    fleLevel3.extension: 
                      |    fleLevel3_noExtension: 
                      |  dirLevel2_content_noExtension/
                      |    dirLevel3_noContent.extension/
                      |    dirLevel3_noContent_noExtension/
                      |    fleLevel3.extension: 
                      |    fleLevel3_noExtension: 
                      |  dirLevel2_noContent.extension/
                      |  dirLevel2_noContent_noExtension/
                      |  fleLevel2.extension: 
                      |  fleLevel2_noExtension: 
                      |dirLevel1_content_noExtension/
                      |  dirLevel2_content.extension/
                      |    dirLevel3_noContent.extension/
                      |    dirLevel3_noContent_noExtension/
                      |    fleLevel3.extension: 
                      |    fleLevel3_noExtension: 
                      |  dirLevel2_content_noExtension/
                      |    dirLevel3_noContent.extension/
                      |    dirLevel3_noContent_noExtension/
                      |    fleLevel3.extension: 
                      |    fleLevel3_noExtension: 
                      |  dirLevel2_noContent.extension/
                      |  dirLevel2_noContent_noExtension/
                      |  fleLevel2.extension: 
                      |  fleLevel2_noExtension: 
                      |dirLevel1_noContent.extension/
                      |dirLevel1_noContent_noExtension/
                      |fleLevel1.extension: 
                      |fleLevel1_noExtension: '''
                            .stripMargin()
    }

    def "saves nothing when empty structure"() {
        // todo
    }

}
