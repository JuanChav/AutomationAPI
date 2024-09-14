    package com.globant.stepDefinitions;

    import com.globant.models.Resource;
    import com.globant.requests.ResourceRequest;
    import io.cucumber.java.en.Given;
    import io.cucumber.java.en.Then;
    import io.cucumber.java.en.When;
    import io.restassured.response.Response;
    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.Logger;
    import org.junit.Assert;

    import java.util.List;

    public class ResourceSteps {
        private static final Logger logger = LogManager.getLogger(ResourceSteps.class);

        private final ResourceRequest resourceRequest = new ResourceRequest();

        private Response response;
        private Resource resource;
        List<Resource> activeResources;

        @Given("have at least 5 active resources")
        public void haveAtLeast5ActiveResources() {
            response = resourceRequest.getResource();
            Assert.assertEquals(200, response.statusCode());

            List<Resource> resourceList = resourceRequest.getResourcesEntity(response);

            long activeCount = resourceList.stream().filter(resource -> resource.isActive()).count();

            if (activeCount < 5) {
                int resourcesToCreate = 5 - (int) activeCount;
                for (int i = 0; i < resourcesToCreate; i++) {
                    response = resourceRequest.createDefaultResource();
                    Assert.assertEquals(201, response.statusCode());
                }
            }
            logger.info("There is at least 5 active resources");
        }

        @When("find all resources active")
        public void findAllResourcesActive() {
            response = resourceRequest.getResource();
            Assert.assertEquals(200, response.statusCode());

            List<Resource> resourceList = resourceRequest.getResourcesEntity(response);
            activeResources = resourceList.stream()
                    .filter(resource -> resource.isActive())
                    .toList();

            Assert.assertFalse("No active resources found", activeResources.isEmpty());
            logger.info("Resources found");
        }

        @Then("update them as inactive")
        public void updateThemAsInactive() {
            for (Resource resource : activeResources) {
                resource.setActive(false);

                Response response = resourceRequest.updateResource(resource, resource.getId());
                Assert.assertEquals(200, response.statusCode());
            }

            Response updatedResponse = resourceRequest.getResource();
            Assert.assertEquals(200, updatedResponse.statusCode());
            List<Resource> updatedResourceList = resourceRequest.getResourcesEntity(updatedResponse);

            boolean allInactive = updatedResourceList.stream()
                    .allMatch(res -> !res.isActive());

            Assert.assertTrue("Not all resources are inactive", allInactive);
            logger.info("Resources inactive");
        }
    }
