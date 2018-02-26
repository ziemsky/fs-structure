package com.ziemsky.fsstructure

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.Consumer
import java.util.function.Function

import static com.ziemsky.fsstructure.TestFilesystemItem.Type.DIR
import static com.ziemsky.fsstructure.TestFilesystemItem.Type.FILE

class TestFilesystemItem {

    private final Path rootDir
    private final Path path
    private final Type type

    TestFilesystemItem(final Type type, final Path rootDir, final String... pathComponents) {
        this.rootDir = rootDir
        this.type = type
        this.path = Paths.get(rootDir.toString(), pathComponents)
    }

    boolean exists() {
        return Files.exists(path)
    }

    boolean isOfExpectedType() {
        return type.isPathOfType(path)
    }

    void create() {
        Files.createDirectories(path.getParent())
        type.create(path)
    }

    @Override
    String toString() {
        return "${type}: ${rootDir.relativize(path)}"
    }

    static TestFilesystemItem newFile(final Path contextDir, final String[] pathComponents) {
        return new TestFilesystemItem(FILE, contextDir, pathComponents)
    }

    static TestFilesystemItem newDir(final Path contextDir, final String[] pathComponents) {
        return new TestFilesystemItem(DIR, contextDir, pathComponents)
    }

    private enum Type {

        FILE(
                { Files.isRegularFile it },
                { Files.createFile it }
        ),
        DIR(
                { Files.isDirectory it },
                { Files.createDirectory it }
        )

        private final Function<Path, Boolean> typeCheckAction
        private final Consumer<Path> createAction

        Type(final Function<Path, Boolean>  typeCheckAction,
             final Consumer<Path> createAction
        ) {
            this.typeCheckAction = typeCheckAction
            this.createAction = createAction
        }

        boolean isPathOfType(final Path path) {
            return typeCheckAction.apply(path)
        }

        void create(final Path path) {
            createAction.accept(path)
        }
    }


}
