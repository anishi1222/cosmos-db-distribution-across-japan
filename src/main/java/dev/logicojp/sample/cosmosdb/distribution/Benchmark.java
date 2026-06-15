package dev.logicojp.sample.cosmosdb.distribution;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import com.azure.data.cosmos.ConnectionPolicy;
import com.azure.data.cosmos.CosmosClient;
import com.azure.data.cosmos.CosmosContainer;

import reactor.core.scheduler.Schedulers;

public class Benchmark {
    CosmosContainer container;
    CosmosClient client;
    AppConfig appConfig;
    ArrayList<SampleCustomer> sampleCustomerArrayList;
    final int MAXLOOP = 50;

    public Benchmark(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public void initialize() {
        ConnectionPolicy connectionPolicy = new ConnectionPolicy()
                .usingMultipleWriteLocations(appConfig.multiWrite)
                .preferredLocations(Arrays.asList(appConfig.getTargetRegion()));

        client = CosmosClient.builder()
                .endpoint(appConfig.getEndpoint())
                .key(appConfig.getKey())
                .connectionPolicy(connectionPolicy)
                .build();

        // Connect to Database and container
        container = client.getDatabase(appConfig.getDatabase())
                .getContainer(appConfig.getContainer());

        // Create sample data for test
        SampleCustomer sampleCustomer = new SampleCustomer();
        sampleCustomerArrayList = sampleCustomer.createData(appConfig.getPartitionKey());
    }

    public void readTest() {
        ConcurrentHashMap<String, Double> resultMap = new ConcurrentHashMap();

        // Load Data to Cosmos DB
        sampleCustomerArrayList.parallelStream().forEach(f -> this.container.upsertItem(f).block());

        // Get Items via SampleCustomer#id
        System.out.println("(test)" + appConfig.getTestName());
        System.out.println("(desc)" + appConfig.getTestDescription());
        System.out.println("(mode)" + appConfig.getTestMode());
        for(int i=0;i<MAXLOOP;i++) {
            sampleCustomerArrayList.parallelStream().forEach(
                    f -> {
                        long t1 = System.nanoTime();
                        container.getItem(f.getId(), f.getMyPartitionKey())
                                .read()
                                .publishOn(Schedulers.immediate())
                                .block();
                        long t2 = System.nanoTime();
                        double delta = t2 - t1;
                        resultMap.put(f.getId(), Double.valueOf(delta / 1000000));
                    }
            );
        }
        this.calculation(resultMap);
        return;
    }

    public void writeTest() {
        ConcurrentHashMap<String, Double> resultMap = new ConcurrentHashMap();

        // Create each item on Cosmos DB
        System.out.println("(test)" + appConfig.getTestName());
        System.out.println("(desc)" + appConfig.getTestDescription());
        System.out.println("(mode)" + appConfig.getTestMode());

        for(int i=0;i<MAXLOOP;i++) {
            sampleCustomerArrayList.parallelStream().forEach(
                    f -> {
                        long t1 = System.nanoTime();
                        container.upsertItem(f)
                                .publishOn(Schedulers.immediate())
                                .block();
                        long t2 = System.nanoTime();
                        double delta = t2 - t1;
                        resultMap.put(f.getId(), Double.valueOf(delta / 1000000));
                    }
            );
        }
        this.calculation(resultMap);
        return;
    }

    void calculation(ConcurrentHashMap<String, Double> map) {
        Double x_average = map.values().stream().mapToDouble(x->x).summaryStatistics().getAverage();
        Double std_dev = sqrt(map.values().stream().mapToDouble(x->(x-x_average)*(x-x_average)).sum()/map.size());
        System.out.printf("(Trial:%d) [Average] %10f (msec) [Standard Dev] %10f (msec)%n", MAXLOOP * sampleCustomerArrayList.size(), x_average, std_dev);
        return;
    }

    public void cleanup() {
        sampleCustomerArrayList.parallelStream().forEach(f-> {
            container.getItem(f.getId(), f.getMyPartitionKey())
                    .delete()
                    .publishOn(Schedulers.immediate())
                    .block();
        });
        client.close();
    }
}