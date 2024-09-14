package com.globant.utils;
import com.globant.models.Client;
import com.globant.models.Resource;
import net.datafaker.Faker;

public class FakeDataGenerator {
    private static final Faker faker = new Faker();

    public static Client createRandomClient() {
        Client client = new Client();
        client.setName(faker.name().firstName());
        client.setLastName(faker.name().lastName());
        client.setCountry(faker.address().country());
        client.setCity(faker.address().city());
        client.setEmail(faker.internet().emailAddress());
        client.setPhone(faker.phoneNumber().phoneNumber());
        client.setId(faker.idNumber().valid());

        return client;
    }

    public static Resource createRandomResource() {
        Resource resource = new Resource();
        resource.setName(faker.commerce().productName());
        resource.setTrademark(faker.company().name());
        resource.setStock(faker.number().numberBetween(1, 1000));
        resource.setPrice(Float.parseFloat(faker.commerce().price(10.0f, 500.0f)));
        resource.setDescription(faker.lorem().sentence(10));
        resource.setTags(faker.lorem().words(3).toString());
        resource.setActive(true);
        resource.setId(faker.idNumber().valid());

        return resource;
    }

    public FakeDataGenerator() {
    }
}
