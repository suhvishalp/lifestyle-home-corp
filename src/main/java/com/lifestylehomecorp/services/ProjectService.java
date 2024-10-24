package com.lifestylehomecorp.services;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.project.Project;
import com.lifestylehomecorp.exceptions.ProjectNotFoundException;
import io.vrap.rmf.base.client.ApiHttpResponse;
import io.vrap.rmf.base.client.error.ApiClientException;
import io.vrap.rmf.base.client.error.ApiServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectApiRoot projectApiRoot;

    @Autowired
    public ProjectService(ProjectApiRoot projectApiRoot) {
        this.projectApiRoot = projectApiRoot;
    }

    public ResponseEntity<Project> getProjectDetails() {
        try {
            ApiHttpResponse<Project> projectResponse = projectApiRoot
                    .get()
                    .executeBlocking();



            if (projectResponse.getStatusCode() == 200) {
                logger.info("Project details fetched successfully");
                return ResponseEntity.ok(projectResponse.getBody());
            } else {
                logger.error("Failed to fetch project details: Status Code {}", projectResponse.getStatusCode());
                throw new ProjectNotFoundException("Project not found or API call failed with status code: "
                        + projectResponse.getStatusCode());
            }

        } catch (ApiClientException apiClientException) {
            logger.error("Client Exception while fetching project details: {}", apiClientException.getMessage());
            throw new ProjectNotFoundException("Client error: " + apiClientException.getMessage());
        } catch (ApiServerException apiServerException) {
            logger.error("Server Exception while fetching project details: {}", apiServerException.getMessage());
            throw new ProjectNotFoundException("Server error: " + apiServerException.getMessage());
        } catch (Exception exception) {
            logger.error("Unexpected error while fetching project details: {}", exception.getMessage());
            throw new ProjectNotFoundException("Unexpected error: " + exception.getMessage());
        }
    }
}
