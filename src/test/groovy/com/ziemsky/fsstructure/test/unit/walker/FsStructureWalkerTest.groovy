package com.ziemsky.fsstructure.test.unit.walker

import com.ziemsky.fsstructure.FsDir
import com.ziemsky.fsstructure.FsFile
import com.ziemsky.fsstructure.FsItem
import com.ziemsky.fsstructure.walker.FsDirVisitor
import com.ziemsky.fsstructure.walker.FsFileVisitor
import com.ziemsky.fsstructure.walker.FsStructureWalker
import spock.lang.Specification

class FsStructureWalkerTest extends Specification {

    def "executes visitors when walking the structure"() {

        given:
            // @formatter:off
            def file_0_0
            def file_0_1
            def dir__0_0
            def     dir__1_0
            def         file_1_0
            def         file_1_1
            def     dir__1_1
            def         dir__2_0

            final List<FsItem> fsItems = [
                file_0_0 = new FsFile("file 0-0", "file 0-0 content".bytes),
                file_0_1 = new FsFile("file 0-1", "file 0-1 content".bytes),
                dir__0_0 = new FsDir("dir 0-0 with content",
                        dir__1_0 = new FsDir("dir 1-0 with files",
                                file_1_0 = new FsFile("file 1-0", "file 1-0 content".bytes),
                                file_1_1 = new FsFile("file 1-1", "file 1-1 content".bytes),
                                dir__2_0 = new FsDir("dir 2-0 empty")
                        ),
                        dir__1_1 = new FsDir("dir 1-1 empty"),
                )
            ]
            // @formatter:on

            final List<FsItem> actualItemsVisited = []

            final FsDirVisitor fsDirVisitor = { println("Visiting $it"); actualItemsVisited << it }

            final FsFileVisitor fsFileVisitor = { println("Visiting $it"); actualItemsVisited << it }

            final FsStructureWalker fsStructureWalker = new FsStructureWalker(fsItems, fsDirVisitor, fsFileVisitor)

        when:
            fsStructureWalker.walk()

        then:
            // @formatter:off
            final List<FsItem> expectedItemsVisited = [
                    file_0_0,
                    file_0_1,
                    dir__0_0,
                        dir__1_0,
                            file_1_0,
                            file_1_1,
                            dir__2_0,
                        dir__1_1
            ]
            // @formatter:on

        actualItemsVisited == expectedItemsVisited
    }
}
