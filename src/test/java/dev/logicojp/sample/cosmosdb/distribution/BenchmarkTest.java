package dev.logicojp.sample.cosmosdb.distribution;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BenchmarkTest {

    @Test
    void calculationPrintsTrialCountAverageAndStandardDeviation() {
        PrintStream originalOut = System.out;
        Locale originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

        try {
            Benchmark benchmark = new Benchmark(new AppConfig());
            benchmark.sampleCustomerArrayList = new ArrayList<>();
            benchmark.sampleCustomerArrayList.add(new SampleCustomer());
            benchmark.sampleCustomerArrayList.add(new SampleCustomer());
            benchmark.sampleCustomerArrayList.add(new SampleCustomer());

            ConcurrentHashMap<String, Double> resultMap = new ConcurrentHashMap<>();
            resultMap.put("customer-1", 10.0);
            resultMap.put("customer-2", 20.0);
            resultMap.put("customer-3", 30.0);

            benchmark.calculation(resultMap);
        } finally {
            System.setOut(originalOut);
            Locale.setDefault(originalLocale);
        }

        String report = output.toString(StandardCharsets.UTF_8);
        assertTrue(report.contains("(Trial:150)"));
        assertTrue(report.contains("[Average]"));
        assertTrue(report.contains("20.000000"));
        assertTrue(report.contains("[Standard Dev]"));
        assertTrue(report.contains("8.164966"));
    }
}