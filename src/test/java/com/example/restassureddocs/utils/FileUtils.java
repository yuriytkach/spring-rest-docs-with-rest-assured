package com.example.restassureddocs.utils;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtils {

    private FileUtils() {
        // empty constructor for utility class
    }

    @SneakyThrows
    public static String readSystemResource(final String location) {
        return readResource(Paths.get(ClassLoader.getSystemResource(location).toURI()));
    }

    public static String readResource(final String location) {
        return readResource(Paths.get(location));
    }

    @SneakyThrows
    public static String readResource(final Path path) {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
