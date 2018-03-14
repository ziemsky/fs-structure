package com.ziemsky.fsstructure;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class FsStructure {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String INDENT = "  ";
    private static final Charset CHARSET = Charset.forName("UTF-8");

    private final FsTools fsTools = new FsTools();
    private final FsStructureWriter fsStructureWriter = new FsStructureWriter(fsTools);

    private final List<FsItem> fsItems = new ArrayList<>();

    private FsStructure(final FsItem... fsItems) {
        this.fsItems.addAll(Optional.ofNullable(fsItems).map(Arrays::asList).orElse(emptyList()));
    }

    // STRUCTURE CREATION INTERFACE

    public static FsStructure create(final List<? extends FsItem> fileSystemItems) {
        return new FsStructure(fileSystemItems.toArray(new FsItem[0]));
    }

    public static FsStructure create(final List<? extends FsItem> fileSystemItems,
                                     final FsItem... additionalFileSystemItems
    ) {
        final ArrayList<FsItem> items = new ArrayList<>(fileSystemItems);

        items.addAll(asList(Optional.of(additionalFileSystemItems).orElse(new FsItem[0])));

        return new FsStructure(items.toArray(new FsItem[0]));
    }

    public static FsStructure create(final FsItem... fileSystemItems) {
        return new FsStructure(fileSystemItems);
    }

    // STRUCTURE SAVING INTERFACE

    public FsStructure saveIn(final Path dir) {
        fsStructureWriter.write(getFsItems(), dir);
        return this;
    }

    // STRUCTURE READ INTERFACE

    public static FsStructureReader read(final List<Path> paths) {
        return new FsStructureReader(paths, new FsTools());
    }

    public static FsStructure readFrom(final Path contextDir) {

        List<Path> paths;
        try {
            paths = Files.walk(contextDir, Integer.MAX_VALUE)
                // contextDir gets added by the walker automatically but we're only interested in its content so we need
                // to exclude it
                .filter(path -> !path.equals(contextDir))
                .collect(toList());
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }

        return new FsStructureReader(paths, new FsTools()).inContextOf(contextDir);
    }

    // ITEMS FACTORY METHODS

    public static FsFile fle(final String name) {
        return fle(name, ("default content of file " + name).getBytes());
    }

    public static FsFile fle(final String name, final String content) {
        return new FsFile(name, toByteArray(content));
    }

    public static FsFile fle(final String name, final byte[] content) {
        return new FsFile(name, content);
    }

    public static FsDir dir(final String name, final FsItem... fsItems) {
        return new FsDir(name, fsItems);
    }

    public boolean isEmpty() {
        return fsItems.isEmpty();
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof FsStructure && this.toString().equals(other.toString());
    }

    @Override public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return buildString(new StringBuilder("\n"), fsItems, 0).append("\n").toString();
    }

    private List<FsItem> getFsItems() {
        return unmodifiableList(fsItems);
    }

    private StringBuilder buildString(final StringBuilder sb, final List<FsItem> fsItems, final int level) {

        final ArrayList<FsItem> items = new ArrayList<>(fsItems);

        items.sort(FsItem::compare);

        final AtomicBoolean subsequentItem = new AtomicBoolean(level > 0);

        items.forEach(fsItem -> {

            if (subsequentItem.get()) { // prevent first row being prepended by blank line
                sb.append(NEW_LINE);
            } else {
                subsequentItem.getAndSet(true);
            }

            sb
                .append(IntStream.range(0, level).mapToObj(value -> INDENT).collect(joining("")))
                .append(fsItem.getName());

            if (fsItem instanceof FsFile) {
                sb
                    .append(": ")
                    .append(toString(((FsFile) fsItem).getContent()));

            } else if (fsItem instanceof FsDir) {
                sb.append("/");

                buildString(sb, ((FsDir)fsItem).getFsItems(), level + 1);
            }
        });

        return sb;
    }

    private static byte[] toByteArray(final String text) {
        return text.getBytes(CHARSET);
    }

    private static String toString(final byte[] array) {
        return new String(array, CHARSET);
    }
}
