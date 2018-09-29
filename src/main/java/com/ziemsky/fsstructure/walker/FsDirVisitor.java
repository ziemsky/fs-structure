package com.ziemsky.fsstructure.walker;

import com.ziemsky.fsstructure.FsDir;

@FunctionalInterface
public interface FsDirVisitor extends FsItemVisitor<FsDir> {

    void visit(FsDir fsDir, FsDir parent);
}
