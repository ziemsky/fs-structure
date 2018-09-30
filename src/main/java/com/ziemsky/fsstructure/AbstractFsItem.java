package com.ziemsky.fsstructure;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class AbstractFsItem implements FsItem {

    private final String name;
    private final List<FsDir> parents = new ArrayList<>();

    protected AbstractFsItem(final String name, final List<FsDir> parents) {
        this.name = name;
        this.parents.addAll(parents);
    }

    @Override
    public String name() {
        return name;
    }

    @Override public List<FsDir> parents() {
        return unmodifiableList(parents);
    }

    @Override public FsDir parent() {
        return parents.get(0); // todo null
    }

    @Override public void addParent(final FsDir parent) {
        parents.add(parent);
    }

    @Override public boolean isNested() {
        return !parents().isEmpty();
    }
}
