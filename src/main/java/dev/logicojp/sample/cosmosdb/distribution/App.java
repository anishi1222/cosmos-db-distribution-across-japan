package dev.logicojp.sample.cosmosdb.distribution;

import java.io.IOException;


class App
{
    void main( String... args )
    {
        if(args.length != 1) {
            System.err.println("Test configuration file is mandatory.");
            return;
        }

        // Read test configuration
        AppConfig appConfig;
        try {
            appConfig = new AppConfig().readConfig(args[0]);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Test
        Benchmark benchmark = new Benchmark(appConfig);
        benchmark.initialize();
        switch(appConfig.getTestMode()) {
            case "READ": // Read test
                benchmark.readTest();
                break;
            case "WRITE": // Write test
                benchmark.writeTest();
                break;
            default: // Other mode
                break;
        }
        benchmark.cleanup();
    }
}