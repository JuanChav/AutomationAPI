package com.globant.requests;

import com.globant.models.Client;
import com.globant.models.Resource;
import com.globant.utils.Constants;
import com.google.gson.Gson;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResourceRequest extends BaseRequest{
    private String endpoint;

    /**
     * Get Resource list
     * @return rest-assured response
     */
    public Response getResource() {
        endpoint = String.format(Constants.URL, Constants.RESOURCES_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }

    /**
     * Get Resource by id
     * @param resourceId string
     * @return rest-assured response
     */
    public Response getResource(String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestGet(endpoint, createBaseHeaders());
    }

    /**
     * Create Resource
     * @param resource model
     * @return rest-assured response
     */
    public Response createResource(Client resource) {
        endpoint = String.format(Constants.URL, Constants.RESOURCES_PATH);
        return requestPost(endpoint, createBaseHeaders(), resource);
    }

    /**
     * Update Resource by id
     * @param resource model
     * @param resourceId string
     * @return rest-assured response
     */
    public Response updateResource(Client resource, String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestPut(endpoint, createBaseHeaders(), resource);
    }

    /**
     * Delete Resource by id
     * @param resourceId string
     * @return rest-assured response
     */
    public Response deleteResource(String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestDelete(endpoint, createBaseHeaders());
    }

    public Resource getResourceEntity(@NotNull Response response) {
        return response.as(Resource.class);
    }

    public List<Resource> getResourcesEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }

    /*public Response createDefaultResource() {
        JsonFileReader jsonFile = new JsonFileReader();
        return this.createResource(jsonFile.getClientByJson(Constants.DEFAULT_CLIENT_FILE_PATH));
    }*/

    public Resource getResourceEntity(String clientJson) {
        Gson gson = new Gson();
        return gson.fromJson(clientJson, Resource.class);
    }

    public boolean validateSchema(Response response, String schemaPath) {
        try {
            response.then()
                    .assertThat()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
            return true; // Return true if the assertion passes
        } catch (AssertionError e) {
            // Assertion failed, return false
            return false;
        }
    }
}
