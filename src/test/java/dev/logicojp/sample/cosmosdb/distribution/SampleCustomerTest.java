package dev.logicojp.sample.cosmosdb.distribution;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SampleCustomerTest {

    @Test
    void createDataCreatesOneHundredCustomersWithExpectedShape() {
        ArrayList<SampleCustomer> customers = new SampleCustomer().createData("/myPartitionKey");

        assertEquals(1000, customers.size());

        Set<String> ids = new HashSet<>();
        customers.forEach(customer -> assertAll(
                () -> assertEquals("/myPartitionKey", customer.getMyPartitionKey()),
                () -> assertNotNull(UUID.fromString(customer.getId())),
                () -> assertTrue(ids.add(customer.getId()), "customer id should be unique"),
                () -> assertFalse(customer.getName().isBlank()),
                () -> assertFalse(customer.getCity().isBlank()),
                () -> assertFalse(customer.getRegion().isBlank()),
                () -> assertTrue(customer.getZipCode().matches("\\d{5}")),
                () -> assertTrue(customer.getUserDefinedId() >= 0),
                () -> assertTrue(customer.getUserDefinedId() <= 1000)
        ));
    }

    @Test
    void gettersReturnConfiguredValues() {
        SampleCustomer customer = new SampleCustomer();

        customer.setId("customer-1");
        customer.setName("Test Customer");
        customer.setCity("Tokyo");
        customer.setZipCode("10000");
        customer.setRegion("Japan");
        customer.setMyPartitionKey("/myPartitionKey");
        customer.setUserDefinedId(42);

        assertAll(
                () -> assertEquals("customer-1", customer.getId()),
                () -> assertEquals("Test Customer", customer.getName()),
                () -> assertEquals("Tokyo", customer.getCity()),
                () -> assertEquals("10000", customer.getZipCode()),
                () -> assertEquals("Japan", customer.getRegion()),
                () -> assertEquals("/myPartitionKey", customer.getMyPartitionKey()),
                () -> assertEquals(42, customer.getUserDefinedId())
        );
    }
}