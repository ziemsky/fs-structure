package com.ziemsky.fsstructure;

import java.nio.file.Path;
import java.util.List;

public class FsStructureWriter {

    private final FsTools fsTools;

    FsStructureWriter(final FsTools fsTools) {
        this.fsTools = fsTools;
    }

    public void write(final List<FsItem> fileSystemItems, final Path dir) {
        fileSystemItems.forEach(fsItem -> getWriterFor(fsItem).saveIn(dir));
    }

    private FsItemWriter getWriterFor(final FsItem fsItem) {

        return fsItem instanceof FsFile
            ? new FsFileWriter((FsFile) fsItem, fsTools)
            : new FsDirWriter((FsDir) fsItem, this, fsTools);
    }
}
