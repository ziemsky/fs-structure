package com.ziemsky.fsstructure

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.nio.file.Path

import static com.ziemsky.fsstructure.FsStructure.*
import static TestFilesystemItem.newDir
import static TestFilesystemItem.newFile

class FsStructureSpec extends Specification {

    @Rule
    final TemporaryFolder tempDir = new TemporaryFolder()

    private Path testDir

    def setup() {
        testDir = tempDir.getRoot().toPath()
    }

    def "writes the structure"() {

        given: "a structure of directories and files to in memory"
            final FsStructure structureToWrite = newReferenceFsStructure()

        when: "it saves the structure"
            structureToWrite.saveIn(testDir)

        then: "the structure on the disk matches that in memory"
            final List<TestFilesystemItem> expectedFileSystemItems = newReferenceTestFileSystemItems()
        
            int actualFileSystemItemsCount = 0
            testDir.traverse { actualFileSystemItemsCount++ }

            actualFileSystemItemsCount == expectedFileSystemItems.size()

            expectedFileSystemItems.forEach { testFilesystemItem ->
                assert testFilesystemItem.exists()
                assert testFilesystemItem.isOfExpectedType()
            }
    }

    def "reads the structure"() {

        given: "a structure of directories and files on the disk"
            newReferenceTestFileSystemItems().forEach({ it.create() })

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

    def "reads the structure it wrote previously"() {

        given:
            final FsStructure structureToWrite = newReferenceFsStructure()

        when:
            structureToWrite.saveIn(testDir)

        then:
            final FsStructure actualFsStructure = readFrom(testDir)

            actualFsStructure == structureToWrite
    }

    private FsStructure newReferenceFsStructure() {

        create(
                fle('fleLevel1_noExtension'),
                fle('fleLevel1.extension'),

                dir('dirLevel1_noContent_noExtension'),
                dir('dirLevel1_noContent.extension'),

                dir('dirLevel1_content_noExtension',
                        fle('fleLevel2_noExtension'),
                        fle('fleLevel2.extension'),
                        dir('dirLevel2_noContent_noExtension'),
                        dir('dirLevel2_noContent.extension'),
                        dir('dirLevel2_content_noExtension',
                                fle('fleLevel3_noExtension'),
                                fle('fleLevel3.extension'),
                                dir('dirLevel3_noContent_noExtension'),
                                dir('dirLevel3_noContent.extension'),
                        ),
                        dir('dirLevel2_content.extension',
                                fle('fleLevel3_noExtension'),
                                fle('fleLevel3.extension'),
                                dir('dirLevel3_noContent_noExtension'),
                                dir('dirLevel3_noContent.extension'),
                        ),
                ),
                dir('dirLevel1_content.extension',
                        fle('fleLevel2_noExtension'),
                        fle('fleLevel2.extension'),
                        dir('dirLevel2_noContent_noExtension'),
                        dir('dirLevel2_noContent.extension'),
                        dir('dirLevel2_content_noExtension',
                                fle('fleLevel3_noExtension'),
                                fle('fleLevel3.extension'),
                                dir('dirLevel3_noContent_noExtension'),
                                dir('dirLevel3_noContent.extension'),
                        ),
                        dir('dirLevel2_content.extension',
                                fle('fleLevel3_noExtension'),
                                fle('fleLevel3.extension'),
                                dir('dirLevel3_noContent_noExtension'),
                                dir('dirLevel3_noContent.extension'),
                        ),
                ),
        )
    }

    private List<TestFilesystemItem> newReferenceTestFileSystemItems() {
        [
                newF('fleLevel1_noExtension'),
                newF('fleLevel1.extension'),

                newD('dirLevel1_noContent_noExtension'),
                newD('dirLevel1_noContent.extension'),

                newD('dirLevel1_content_noExtension'),
                newF('dirLevel1_content_noExtension', 'fleLevel2_noExtension'),
                newF('dirLevel1_content_noExtension', 'fleLevel2.extension'),
                newD('dirLevel1_content_noExtension', 'dirLevel2_noContent_noExtension'),
                newD('dirLevel1_content_noExtension', 'dirLevel2_noContent.extension'),

                newD('dirLevel1_content_noExtension', 'dirLevel2_content_noExtension'),
                newF('dirLevel1_content_noExtension', 'dirLevel2_content_noExtension', 'fleLevel3_noExtension'),
                newF('dirLevel1_content_noExtension', 'dirLevel2_content_noExtension', 'fleLevel3.extension'),
                newD('dirLevel1_content_noExtension', 'dirLevel2_content_noExtension', 'dirLevel3_noContent_noExtension'),
                newD('dirLevel1_content_noExtension', 'dirLevel2_content_noExtension', 'dirLevel3_noContent.extension'),

                newD('dirLevel1_content_noExtension', 'dirLevel2_content.extension'),
                newF('dirLevel1_content_noExtension', 'dirLevel2_content.extension', 'fleLevel3_noExtension'),
                newF('dirLevel1_content_noExtension', 'dirLevel2_content.extension', 'fleLevel3.extension'),
                newD('dirLevel1_content_noExtension', 'dirLevel2_content.extension', 'dirLevel3_noContent_noExtension'),
                newD('dirLevel1_content_noExtension', 'dirLevel2_content.extension', 'dirLevel3_noContent.extension'),

                newD('dirLevel1_content.extension'),
                newF('dirLevel1_content.extension', 'fleLevel2_noExtension'),
                newF('dirLevel1_content.extension', 'fleLevel2.extension'),
                newD('dirLevel1_content.extension', 'dirLevel2_noContent_noExtension'),
                newD('dirLevel1_content.extension', 'dirLevel2_noContent.extension'),

                newD('dirLevel1_content.extension', 'dirLevel2_content_noExtension'),
                newF('dirLevel1_content.extension', 'dirLevel2_content_noExtension', 'fleLevel3_noExtension'),
                newF('dirLevel1_content.extension', 'dirLevel2_content_noExtension', 'fleLevel3.extension'),
                newD('dirLevel1_content.extension', 'dirLevel2_content_noExtension', 'dirLevel3_noContent_noExtension'),
                newD('dirLevel1_content.extension', 'dirLevel2_content_noExtension', 'dirLevel3_noContent.extension'),

                newD('dirLevel1_content.extension', 'dirLevel2_content.extension'),
                newF('dirLevel1_content.extension', 'dirLevel2_content.extension', 'fleLevel3_noExtension'),
                newF('dirLevel1_content.extension', 'dirLevel2_content.extension', 'fleLevel3.extension'),
                newD('dirLevel1_content.extension', 'dirLevel2_content.extension', 'dirLevel3_noContent_noExtension'),
                newD('dirLevel1_content.extension', 'dirLevel2_content.extension', 'dirLevel3_noContent.extension')
        ]
    }

    private TestFilesystemItem newF(final String[] pathComponents) {
        return newFile(testDir, pathComponents)
    }

    private TestFilesystemItem newD(final String[] pathComponents) {
        return newDir(testDir, pathComponents)
    }
}
