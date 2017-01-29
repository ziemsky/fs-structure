package com.ziemsky.fsstructure

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.nio.file.Path

import static com.ziemsky.fsstructure.FsStructure.*

class FsStructureSpec extends Specification {

    @Rule
    final TemporaryFolder tempDir = new TemporaryFolder()

    private Path testDir

    def setup() {
        testDir = tempDir.getRoot().toPath()
    }

    def "writes correct structure"() {

        given:
            final FsStructure expectedFsStructure = create(
                    file('topLevelFile.withMatchingExtension'),
                    file('topLevelFile.withMismatchingExtension'),
                    file('topLevelFile_withNoExtension'),

                    dir('topLevelDir_empty.withMatchingExtension'),
                    dir('topLevelDir_empty.withMismatchingExtension'),
                    dir('topLevelDir_empty_withNoExtension'),

                    dir('topLevelDir_withContent.withMatchingExtension',
                            dir('nestedDir.withMatchingExtension'),
                            dir('nestedDir.withMismatchingExtension'),
                            dir('nestedDir_withNoExtension'),
                            file('nestedFile.withMatchingExtension'),
                            file('nestedFile.withMismatchingExtension'),
                            file('nestedFile_withNoExtension'),
                    ),

                    dir('topLevelDir_withContent.withMismatchingExtension',
                            dir('nestedDir.withMatchingExtension'),
                            dir('nestedDir.withMismatchingExtension'),
                            dir('nestedDir_withNoExtension'),
                            file('nestedFile.withMatchingExtension'),
                            file('nestedFile.withMismatchingExtension'),
                            file('nestedFile_withNoExtension'),
                    ),

                    dir('topLevelDir_withContent_withNoExtension',
                            dir('nestedDir.withMatchingExtension',
                                dir('nestedDirLevel2'),
                                file('nestedFileLevel2')
                            ),
                            dir('nestedDir.withMismatchingExtension'),
                            dir('nestedDir_withNoExtension'),
                            file('nestedFile.withMatchingExtension'),
                            file('nestedFile.withMismatchingExtension'),
                            file('nestedFile_withNoExtension'),
                    )
            )

        when:
            expectedFsStructure.saveIn(testDir)

        then:
            final FsStructure actualFsStructure = readFrom(testDir)

            actualFsStructure == expectedFsStructure
    }
}
