package com.ziemsky.fsstructure.test.util

import com.ziemsky.fsstructure.FsStructure

import java.nio.file.Path

import static com.ziemsky.fsstructure.FsStructure.*
import static com.ziemsky.fsstructure.test.util.RandomUtils.randomByteArray
import static com.ziemsky.fsstructure.test.util.RandomUtils.randomString
import static com.ziemsky.fsstructure.test.util.TestFilesystemItem.*

class TestDataProvider {

    private final Path testDir

    TestDataProvider(final Path testDir) {
        this.testDir = testDir
    }

    static String newFsItemName() {
        return randomString() + "." + randomString()
    }

    static String newFileContent() {
        return randomByteArray()
    }

    List<TestFilesystemItem> newReferenceTestFileSystemItems() {
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

    FsStructure newReferenceFsStructure() {

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

    TestFilesystemItem newF(final String[] pathComponents) {
        return newFile(testDir, pathComponents)
    }

    TestFilesystemItem newD(final String[] pathComponents) {
        return newDir(testDir, pathComponents)
    }
}
