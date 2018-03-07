package com.ziemsky.fsstructure.test.util

import java.nio.charset.StandardCharsets

class RandomUtils {

    static String randomString() {
        return UUID.randomUUID().toString()
    }

    static String randomByteArray() {
        return randomString().getBytes(StandardCharsets.UTF_8)
    }
}
