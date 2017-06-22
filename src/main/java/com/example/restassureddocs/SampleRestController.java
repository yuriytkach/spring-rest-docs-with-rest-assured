package com.example.restassureddocs;

import com.example.restassureddocs.domain.Input;
import com.example.restassureddocs.domain.Output;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SampleRestController {

    @PostMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
    public Output process(@RequestBody final Input input) {
        if (Optional.ofNullable(input.getId()).orElse(0L) > 0) {
            return new Output(true, "Received: " + input);
        } else {
            return new Output(false, "Id should be > 0. Received: " + input);
        }
    }
}
