package com.globant.stepDefinitions;

import com.globant.models.Client;
import com.globant.requests.ClientRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ClientSteps {
    private static final Logger logger = LogManager.getLogger(ClientSteps.class);

    private final ClientRequest clientRequest = new ClientRequest();

    private Response response;
    private Client   client;
    private String savedPhoneNumber;

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

    @When("I find the first client named Laura")
    public void iFindTheFirstClientNamedLaura() {
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

    @When("I save her current phone number")
    public void iSaveHerCurrentPhoneNumber() {
        if (client == null) {
            Assert.fail("No client named Laura found to save her phone number.");
        }

        savedPhoneNumber = client.getPhone();
        logger.info("Saved phone number: " + savedPhoneNumber);
    }

    @When("I update her phone number")
    public void iUpdateHerPhoneNumber() {
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

    @Then("I delete all the registered clients")
    public void iDeleteAllTheRegisteredClients() {
        // Enviar la solicitud para eliminar todos los clientes
        Response deleteResponse = clientRequest.deleteAllClients();

        // Validar que la respuesta sea exitosa
        Assert.assertEquals(204, deleteResponse.statusCode());
        logger.info("Successfully deleted all registered clients.");
    }
}
