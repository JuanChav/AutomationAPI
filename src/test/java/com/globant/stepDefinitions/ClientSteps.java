package com.globant.stepDefinitions;

import com.globant.models.Client;
import com.globant.requests.ClientRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;

public class ClientSteps {
    private static final Logger logger = LogManager.getLogger(ClientSteps.class);

    private final ClientRequest clientRequest = new ClientRequest();

    private Response response;
    private Client   client;
    private String savedPhoneNumber;
    private String createdClientId;

    @Given("there is at least 10 registered clients")
    public void thereIsAtLeast10RegisteredClients() {
        response = clientRequest.getClients();
        logger.info(response.jsonPath().prettify());
        Assert.assertEquals(200, response.statusCode());

        List<Client> clientList = clientRequest.getClientsEntity(response);

        if (clientList.size() < 10) {
            int clientsToCreate = 10 - clientList.size();
            for (int i = 0; i < clientsToCreate; i++) {
                response = clientRequest.createDefaultClient();
                logger.info("Created client with status code: " + response.statusCode());
                Assert.assertEquals(201, response.statusCode());
            }
        }
    }

    @When("find the first client named Laura")
    public void findTheFirstClientNamedLaura() {
        response = clientRequest.getClients();
        logger.info(response.jsonPath().prettify());
        Assert.assertEquals(200, response.statusCode());

        List<Client> clientList = clientRequest.getClientsEntity(response);

        client = clientList.stream()
                .filter(c -> "Laura".equals(c.getName()))
                .findFirst()
                .orElse(null);

        if (client == null) {
            Assert.fail("No client named Laura found.");
        }

        logger.info("Found client: " + client);
    }

    @When("save her current phone number")
    public void saveHerCurrentPhoneNumber() {
        if (client == null) {
            Assert.fail("No client named Laura found to save her phone number.");
        }

        savedPhoneNumber = client.getPhone();
        logger.info("Saved phone number: " + savedPhoneNumber);
    }

    @When("update her phone number")
    public void updateHerPhoneNumber() {
        if (client == null) {
            Assert.fail("No client named Laura found to update her phone number.");
        }

        String newPhoneNumber = "123-456-7890";

        client.setPhone(newPhoneNumber);

        response = clientRequest.updateClient(client, client.getId());
        logger.info("Updated phone number to: " + newPhoneNumber);
    }

    @When("validate her new phone number is different")
    public void validateHerNewPhoneNumberIsDifferent() {
        if (client == null) {
            Assert.fail("No client named Laura found to validate her phone number.");
        }

        String updatedPhoneNumber = client.getPhone();

        Assert.assertNotEquals("The phone number has not been updated.", savedPhoneNumber, updatedPhoneNumber);
        logger.info("Successfully validated that the new phone number is different from the saved phone number.");
    }

    @Then("delete all the registered clients")
    public void deleteAllTheRegisteredClients() {
        clientRequest.deleteAllClients();

        logger.info("Successfully deleted all registered clients.");
    }

    @When("create a new client")
    public void createANewClient() {
        Response response = clientRequest.createDefaultClient();

        Assert.assertEquals(201, response.getStatusCode());
        this.createdClientId = response.jsonPath().getString("id");
        logger.info("Successfully created a new client");
    }

    @When("find the new client")
    public void findTheNewClient() {
        Response response = clientRequest.getClient(createdClientId);

        Assert.assertEquals(200, response.getStatusCode());
        Client foundClient = clientRequest.getClientEntity(response);

        Assert.assertNotNull("Client not found!", foundClient);
        Assert.assertEquals("Client ID doesn't match!", createdClientId, foundClient.getId());
        logger.info("Successfully found the client with ID: " + foundClient.getId());
    }

    @When("update any parameter of the new client")
    public void updateAnyParameterOfTheNewClient() {
        Response response = clientRequest.getClient(createdClientId);
        Assert.assertEquals(200, response.getStatusCode());

        Client clientToUpdate = clientRequest.getClientEntity(response);

        clientToUpdate.setName("Pedro");

        Response updateResponse = clientRequest.updateClient(clientToUpdate, createdClientId);

        Assert.assertEquals(200, updateResponse.getStatusCode());
        logger.info("Successfully updated the client with ID: " + createdClientId);
    }

    @When("delete the new client")
    public void deleteTheNewClient() {
        clientRequest.deleteClient(createdClientId);
        logger.info("Successfully deleted the client with ID: " + createdClientId);
    }
}
