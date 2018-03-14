[![Build Status](https://travis-ci.org/ziemsky/fs-structure.svg?branch=master)](https://travis-ci.org/ziemsky/fs-structure)

# fs-structure
A Java library whose goal is to make it easy to declare, create and retrieve elaborate structures of files and
directories on and from a local file system.

The original purpose of the library was to aid in tests verifying code whose responsibility is to find or
otherwise manipulate file system items but there is nothing preventing from using this library to easily
manipulate such structures in production.
  
## What
Defines a fluent API that exposes convenient factory method allowing to define structures in an intuitive, declarative
way. For example, code:
```$java
import static com.ziemsky.fsstructure.FsStructure.*

...

Path testDir;
...
FsStructure fsStructure =
        create(
                fle('topLevelFile'),

                dir('topLevelDir.empty'),

                dir('topLevelDir.withContent',
                        dir('nestedDir.empty'),
                        dir('nestedDir.withContent',
                                fle('nestedFile.level2'),
                        ),
                        fle('nestedFile.level1'),
                )
        ).saveIn(testDir)
... 

```
...creates the following tree of files and folders under the path given by `testDir`:
```
├── topLevelDir.empty/
├── topLevelDir.withContent/
│   ├── nestedDir.empty/
│   ├── nestedDir.withContent/
│   │   └── nestedFile.level2
│   └── nestedFile.level1
└── topLevelFile

```

## Why
To make interactions with file system structures easier by using more intuitive language than `java.nio.file.Path`
and `java.io.File`.   

## Build
In the main project's directory execute `./gradlew` with no arguments for description of key build tasks.   

## Versioning
This project follows [Semantic Versioning][semver] specification. 

## Status
NOTE: THIS PROJECT IS A WORK IN PROGRESS AND IS IN VERY EARLY STAGE OF DEVELOPMENT - NOT READY FOR ACTUAL USE, YET.

## Development
* [Testing Strategy]
 
## Licence
Entire source code in this repository is licenced to use as specified in [MIT licence][mit licence].

Summary of the intention for allowed use of the code from this repository: 
* Feel free to use it in any form (source code or binary) and for any purpose (personal use or commercial).
* Feel free to use entire files or snippets of the code with or without modifications or simply use it as examples to
  inspire your own solutions.
* You don't have to state my authorship in any way and you don't have to include any specific licence.
* Don't hold me responsible for any results of using this code.

For more details of this licence see:
* The [LICENCE][licence file] file included in this project.
* [Licence][mit licence] section of [opensource.org].
 

[gradle]:                       https://gradle.org/getting-started-gradle/
[gradle wrapper]:               https://docs.gradle.org/current/userguide/gradle_wrapper.html
[gradle multi project builds]:  https://docs.gradle.org/current/userguide/intro_multi_project_builds.html

[mit licence]:                  https://opensource.org/licenses/MIT
[licence file]:                 LICENSE
[opensource.org]:               https://opensource.org

[semver]:                       https://semver.org/spec/v2.0.0.html

[Testing Strategy]:             testing.md