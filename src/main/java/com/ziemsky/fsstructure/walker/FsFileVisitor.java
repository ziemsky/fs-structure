package com.ziemsky.fsstructure.walker;

import com.ziemsky.fsstructure.FsDir;
import com.ziemsky.fsstructure.FsFile;

@FunctionalInterface
public interface FsFileVisitor extends FsItemVisitor<FsFile> {

    void visit(FsFile fsFile, FsDir parent);
}
