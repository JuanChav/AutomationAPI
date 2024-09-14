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
        Resource latestResource;
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

        @Given("have at least 15 resources")
        public void haveAtLeast15Resources() {
            response = resourceRequest.getResource();
            Assert.assertEquals(200, response.statusCode());

            List<Resource> resourceList = resourceRequest.getResourcesEntity(response);

            int currentCount = resourceList.size();

            if (currentCount < 15) {
                int resourcesToCreate = 15 - currentCount;
                for (int i = 0; i < resourcesToCreate; i++) {
                    response = resourceRequest.createDefaultResource();
                    Assert.assertEquals(201, response.statusCode());
                }
            }
            logger.info("There is at least 15 resources");
        }

        @When("find the latest resource")
        public void findTheLatestResource() {
            response = resourceRequest.getResource();
            Assert.assertEquals(200, response.statusCode());

            List<Resource> resourceList = resourceRequest.getResourcesEntity(response);

            latestResource = resourceList.stream()
                    .max((r1, r2) -> Integer.compare(Integer.parseInt(r1.getId()), Integer.parseInt(r2.getId())))
                    .orElse(null);

            Assert.assertNotNull("No resources found", latestResource);
            logger.info("Latest resource found: " + latestResource.getName());
        }

        @When("update all the parameters of this resource")
        public void updateAllTheParametersOfThisResource() {
            Assert.assertNotNull("Latest resource is not found", latestResource);
            latestResource.setPrice(999.99f); // Nuevo precio
            Response updateResponse = resourceRequest.updateResource(latestResource, latestResource.getId());
            Assert.assertEquals(200, updateResponse.statusCode());

            logger.info("Resource updated successfully: " + latestResource.getName());
        }
    }
