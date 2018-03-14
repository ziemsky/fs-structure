package com.ziemsky.fsstructure;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public class FsStructureReader {

    private static final String ROOT_DIR_PROTOTYPE_NAME = "/";
    private final List<Path> paths = new ArrayList<>();

    private FsTools fsTools;

    FsStructureReader(final List<Path> paths, final FsTools fsTools) {
        this.paths.addAll(Optional.ofNullable(paths).orElse(emptyList()));
        this.fsTools = fsTools;
    }

    public FsStructure inContextOf(final Path contextDir) {


        // assume all paths with contextDir as ancestor
        // iterate over those paths, creating corresponding FsItems
        //   for each create parent FsDirs up to, but excluding, contextDir
        //     for files create FsFile with appropriate parent
        //     for dirs create FsDir with appropriate parent

        final Stream<Stream<FsItemPrototype>> linesStream = paths.stream()
            .map(path -> {

                final String fileName = path.getFileName().toString();

                FsItemPrototype lastFsItem;
                    lastFsItem = fsTools.isRegularFile(path)
                        ? new FsFilePrototype(fileName, fsTools.readAllBytes(path))
                        : new FsDirPrototype(fileName);

                Stream<FsItemPrototype> fsItemsStream = Stream.of(lastFsItem);

                for (Path curr = path.getParent();
                     !contextDir.equals(curr);
                     curr = curr.getParent()) {

                    lastFsItem = new FsDirPrototype(curr.getFileName().toString());

                    fsItemsStream = Stream.concat(
                        Stream.of(lastFsItem),
                        fsItemsStream
                    );
                }

                return fsItemsStream;
            });

        // Paths in 'paths' actually share the same contextDir, so when the above produces
        // list of paths built out of FsItems, where each FsDir only ever contains single
        // FsItem, there will be duplicates among the directories, for example:

        // [0]: FsDirA / FsDirB / FsFileA
        // [1]: FsDirA / FsDirB / FsFileB
        // [2]: FsDirC / FsDirD / FsDirE
        // [3]: FsDirC / FsDirD / FsDirF

        // We now need to consolidate them, so that we end up with a tree-like FsStructure,
        // without duplicates:

        // FsDirA
        //   FsDirB
        //     FsFileA
        //     FsFileB
        // FsDirC
        //   FsDirD
        //     FsDirE
        //     FsDirF



//        fsItemStream.collect(
//            FsStructurePrototype::new,
//            FsStructurePrototype::add,
//            (left, right) -> { /* no-op - no combiner required in a serial stream */ }
//        );

        final FsDirPrototype rootDirPrototype = new FsDirPrototype(ROOT_DIR_PROTOTYPE_NAME);

        linesStream.forEach(fsItemPrototypeStream -> {

            final AtomicReference<FsDirPrototype> currentDirPrototypeRef = new AtomicReference<>(rootDirPrototype);

            fsItemPrototypeStream.forEach(fsItemPrototype -> {
                final FsDirPrototype currentDirPrototype = currentDirPrototypeRef.get();

                currentDirPrototypeRef.set(currentDirPrototype.addAndGet(fsItemPrototype));
            });
        });

        return FsStructure.create(toFsItems(rootDirPrototype.getContent()));
    }

    private FsItem[] toFsItems(final List<FsItemPrototype> content) {
        return content.stream().map(fsItemPrototype -> {
            final String name = fsItemPrototype.getName();

            if (fsItemPrototype instanceof FsFilePrototype) {
                return FsStructure.fle(name, ((FsFilePrototype)fsItemPrototype).getContent());
            } else {
                return FsStructure.dir(name, toFsItems(((FsDirPrototype)fsItemPrototype).getContent()));
            }
        }).toArray(FsItem[]::new);
    }

    interface FsItemPrototype<CONTENT_TYPE> {
        String getName();

        CONTENT_TYPE getContent();
    }

    class FsFilePrototype implements FsItemPrototype<byte[]> {

        private final String name;
        private final byte[] content;

        FsFilePrototype(final String name, final byte[] content) {
            this.name = name;
            this.content = content;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public byte[] getContent() {
            return content;
        }
    }

    class FsDirPrototype implements FsItemPrototype<List<FsItemPrototype>> {

        private final String name;

        private final Map<String, FsItemPrototype> content = new HashMap<>();

        FsDirPrototype(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<FsItemPrototype> getContent() {
            return new ArrayList<>(content.values());
        }

        private FsDirPrototype addAndGet(final FsItemPrototype fsItemPrototype) {

            if (!content.containsKey(fsItemPrototype.getName())) {
                content.put(fsItemPrototype.getName(), fsItemPrototype);
            }

            final FsItemPrototype prototype = content.get(fsItemPrototype.getName());

            return prototype instanceof FsDirPrototype ? (FsDirPrototype) prototype : null;
        }
    }

}
