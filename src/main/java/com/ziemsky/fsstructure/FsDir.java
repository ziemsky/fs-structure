package com.ziemsky.fsstructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class FsDir implements FsItem {

    private final String name;
    private final List<FsItem> fsItems = new ArrayList<>();

    public FsDir(final String name, final FsItem... fsItems) {
        this.name = name;
        this.fsItems.addAll(Optional.ofNullable(fsItems).map(Arrays::asList).orElse(emptyList()));
    }

    public String getName() {
        return name;
    }

    public List<FsItem> getFsItems() {
        return unmodifiableList(fsItems);
    }

    @Override public String toString() {
        return "FsDir{" +

            "name='" + name + '\'' +
            '}';
    }
}
