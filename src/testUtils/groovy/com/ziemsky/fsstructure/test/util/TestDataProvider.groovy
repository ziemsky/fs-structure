package com.ziemsky.fsstructure.test.util

import static com.ziemsky.fsstructure.test.util.RandomUtils.randomByteArray
import static com.ziemsky.fsstructure.test.util.RandomUtils.randomString

class TestDataProvider {

    static String newFileName() {
        return randomString() + "." + randomString()
    }

    static String newFileContent() {
        return randomByteArray()
    }
}
