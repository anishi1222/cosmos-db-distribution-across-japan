package dev.logicojp.sample.cosmosdb.distribution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppConfigTest {

    @TempDir
    Path tempDir;

    @Test
    void readConfigParsesJsonConfiguration() throws IOException {
        Path configFile = tempDir.resolve("config.json");
        Files.writeString(configFile, """
                {
                  "partitionKey": "/myPartitionKey",
                  "partitionKeyValue": "1001",
                  "database": "demo",
                  "container": "customers",
                  "testName": "Japan East read test",
                  "testDescription": "Read sample customers from Japan East",
                  "testMode": "READ",
                  "endpoint": "https://example.documents.azure.com:443/",
                  "key": "fake-key",
                  "multiWrite": false,
                  "targetRegion": ["Japan East", "Japan West"]
                }
                """);

        AppConfig appConfig = new AppConfig().readConfig(configFile.toString());

        assertEquals("/myPartitionKey", appConfig.getPartitionKey());
        assertEquals("1001", appConfig.getPartitionKeyValue());
        assertEquals("demo", appConfig.getDatabase());
        assertEquals("customers", appConfig.getContainer());
        assertEquals("Japan East read test", appConfig.getTestName());
        assertEquals("Read sample customers from Japan East", appConfig.getTestDescription());
        assertEquals("READ", appConfig.getTestMode());
        assertEquals("https://example.documents.azure.com:443/", appConfig.getEndpoint());
        assertEquals("fake-key", appConfig.getKey());
        assertFalse(appConfig.getMultiWrite());
        assertArrayEquals(new String[]{"Japan East", "Japan West"}, appConfig.getTargetRegion());
    }

    @Test
    void readConfigThrowsWhenFileDoesNotExist() {
        Path missingFile = tempDir.resolve("missing.json");

        IOException exception = assertThrows(IOException.class, () -> new AppConfig().readConfig(missingFile.toString()));
        assertNotNull(exception);
    }
}