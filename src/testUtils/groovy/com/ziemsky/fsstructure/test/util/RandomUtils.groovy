package com.ziemsky.fsstructure.test.util

import java.nio.charset.StandardCharsets

class RandomUtils {

    static String randomString() {
        return UUID.randomUUID().toString()
    }

    static byte[] randomByteArray() {
        return randomString().getBytes(StandardCharsets.UTF_8) // todo proper, raw, full-value-set byte array
    }
}
