package com.ziemsky.fsstructure;

import java.util.List;

public interface FsItem {

    String name();

    List<FsDir> parents();

    FsDir parent();

    boolean isNested();

    void addParent(FsDir parent);

    static int compare(FsItem left, FsItem right) {
        if (left instanceof FsDir && !(right instanceof FsDir)) {
            return -1;
        }

        if (!(left instanceof FsDir) && (right instanceof FsDir)) {
            return 1;
        }

        return left.name().compareTo(right.name());
    }
}
