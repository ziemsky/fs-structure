package com.ziemsky.fsstructure.walker;

import com.ziemsky.fsstructure.FsDir;
import com.ziemsky.fsstructure.FsFile;
import com.ziemsky.fsstructure.FsItem;

import java.util.List;

public class FsStructureWalker {
    private final List<FsItem> fsItems;
    private final FsDirVisitor fsDirVisitor;
    private final FsFileVisitor fsFileVisitor;

    public FsStructureWalker(final List<FsItem> fsItems,
                             final FsDirVisitor fsDirVisitor,
                             final FsFileVisitor fsFileVisitor
    ) {
        this.fsItems = fsItems;
        this.fsDirVisitor = fsDirVisitor;
        this.fsFileVisitor = fsFileVisitor;
    }

    public void walk() {
        walk(fsItems, null);
    }

    private void walk(final List<FsItem> fsItems, final FsDir parent) {
        fsItems.forEach(fsItem -> {
            visitor(fsItem).visit(fsItem, parent);

            if (fsItem instanceof FsDir) {
                final FsDir fsDir = (FsDir) fsItem;

                walk(fsDir.getFsItems(), fsDir);
            }
        });
    }

    private FsItemVisitor visitor(FsItem fsItem) {
        if (fsItem instanceof FsDir) {
            return fsDirVisitor;
        } else if (fsItem instanceof FsFile) {
            return fsFileVisitor;
        } else {
            throw new IllegalArgumentException("Unsupported subtype type of " + FsItem.class.getName());
        }
    }
}
