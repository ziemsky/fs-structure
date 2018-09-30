package com.ziemsky.fsstructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public class FsDir extends AbstractFsItem {

    private final List<FsItem> children = new ArrayList<>();

    public FsDir(final String name, final FsItem... children) {
        this(name, emptyList(), children);
    }

    public FsDir(final String name, final List<FsDir> parents, final FsItem... children) {
        super(name, parents);

        this.children.addAll(
            Optional.ofNullable(children).map(Arrays::asList).orElse(emptyList())
                .stream()
                .peek(fsItem -> fsItem.addParent(this))
                .collect(toList())
        );
    }

    public List<FsItem> children() {
        return unmodifiableList(children);
    }

    @Override public String toString() {
        return "FsDir{" +
            "name='" + name() + '\'' +
            '}';
    }
}
