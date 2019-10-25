package cosmos.global.distributions;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class AppConfig {

    public AppConfig() {
    }

    String partitionKey;
    String partitionKeyValue;
    String database;
    String container;
    String testName;
    String testDescription;
    String testMode;
    String endpoint;
    String key;
    Boolean multiWrite;
    String[] targetRegion;

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public String getPartitionKeyValue() {
        return partitionKeyValue;
    }

    public void setPartitionKeyValue(String partitionKeyValue) {
        this.partitionKeyValue = partitionKeyValue;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getTestMode() {
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getMultiWrite() {
        return multiWrite;
    }

    public void setMultiWrite(Boolean multiWrite) {
        this.multiWrite = multiWrite;
    }

    public String[] getTargetRegion() {
        return targetRegion;
    }

    public void setTargetRegion(String[] targetRegion) {
        this.targetRegion = targetRegion;
    }

    public AppConfig readConfig(String fileName) throws IOException {
        Gson gson = new Gson();
        AppConfig appConfig;
        try (Reader reader = new FileReader(fileName)){
            appConfig = gson.fromJson(reader, AppConfig.class);
        }
        return appConfig;
    }
}
