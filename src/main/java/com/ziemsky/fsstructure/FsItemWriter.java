package com.ziemsky.fsstructure;

import java.nio.file.Path;

interface FsItemWriter<T extends FsItem> {
    void saveIn(Path dir);
}
