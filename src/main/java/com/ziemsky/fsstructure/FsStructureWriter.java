package com.ziemsky.fsstructure;

import java.nio.file.Path;
import java.util.List;

class FsStructureWriter {

    void write(final List<FsItem> fileSystemItems, final Path dir) {
        fileSystemItems.forEach(fsItem -> getWriterFor(fsItem).saveIn(dir));
    }

    private FsItemWriter getWriterFor(final FsItem fsItem) {

        return fsItem instanceof FsFile
            ? new FileWriter((FsFile) fsItem)
            : new DirWriter((FsDir) fsItem, this);
    }
}
