package com.ziemsky.fsstructure.walker;

import com.ziemsky.fsstructure.FsDir;
import com.ziemsky.fsstructure.FsItem;

@FunctionalInterface
public interface FsItemVisitor<T extends FsItem> {

    void visit(T fsItem, FsDir parent);
}
