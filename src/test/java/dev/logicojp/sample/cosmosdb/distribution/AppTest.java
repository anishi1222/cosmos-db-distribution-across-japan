package dev.logicojp.sample.cosmosdb.distribution;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    void mainPrintsErrorWhenConfigArgumentIsMissing() {
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorOutput, true, StandardCharsets.UTF_8));

        try {
            App.main();
        } finally {
            System.setErr(originalErr);
        }

        assertEquals("Test configuration file is mandatory." + System.lineSeparator(), errorOutput.toString(StandardCharsets.UTF_8));
    }
}