package com.lifestylehomecorp.config;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.project.Project;
import io.vrap.rmf.base.client.ApiHttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommercetoolsApiIntegrationTest {

    @Autowired
    private ProjectApiRoot projectApiRoot;

    @Autowired
    private APIClientProperties apiClientProperties;

    @Test
    public void testFetchingProjectDetails() {
        ApiHttpResponse<Project> projectResponse = projectApiRoot
                .get()
                .executeBlocking();

        Project project = projectResponse.getBody();
        assertThat(project).isNotNull();
        assertThat(project.getKey()).isEqualTo("happy-garden-1021-mixed-dev");
        // Add more assertions based on your requirements
    }
}
