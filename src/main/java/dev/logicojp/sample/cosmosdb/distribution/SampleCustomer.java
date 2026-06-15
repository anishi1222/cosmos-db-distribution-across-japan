package dev.logicojp.sample.cosmosdb.distribution;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import com.github.javafaker.Faker;

public class SampleCustomer {
    private static final Locale EN_US_LOCALE = Locale.of("en", "US");

    String id;
    String name;
    String city;
    String zipCode;
    String region;
    String myPartitionKey;
    Integer userDefinedId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMyPartitionKey() {
        return myPartitionKey;
    }

    public void setMyPartitionKey(String myPartitionKey) {
        this.myPartitionKey = myPartitionKey;
    }

    public Integer getUserDefinedId() {
        return userDefinedId;
    }

    public void setUserDefinedId(Integer userDefinedId) {
        this.userDefinedId = userDefinedId;
    }

    public ArrayList<SampleCustomer> createData(String partitionKey) {

        Faker faker = new Faker(EN_US_LOCALE);
        ArrayList<SampleCustomer> sampleCustomerList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            SampleCustomer sampleCustomer = new SampleCustomer();
            sampleCustomer.setCity(faker.country().capital());
            sampleCustomer.setMyPartitionKey(partitionKey);
            sampleCustomer.setUserDefinedId(faker.random().nextInt(0, 1000));
            sampleCustomer.setZipCode(faker.number().digits(5));
            sampleCustomer.setName(faker.name().name());
            sampleCustomer.setId(UUID.randomUUID().toString());
            sampleCustomer.setRegion(faker.country().name());
            sampleCustomerList.add(sampleCustomer);
        }
        return sampleCustomerList;
    }
}