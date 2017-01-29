package com.ziemsky.fsstructure;

public interface FsItem {

    String getName();

    static int compare(FsItem left, FsItem right) {
        if (left instanceof FsDir && !(right instanceof FsDir)) {
            return -1;
        }

        if (!(left instanceof FsDir) && (right instanceof FsDir)) {
            return 1;
        }

        return left.getName().compareTo(right.getName());
    }
}
