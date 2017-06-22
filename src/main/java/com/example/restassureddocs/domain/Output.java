package com.example.restassureddocs.domain;

import lombok.Data;

@Data
public class Output {
    private final boolean valid;
    private final String message;
}
