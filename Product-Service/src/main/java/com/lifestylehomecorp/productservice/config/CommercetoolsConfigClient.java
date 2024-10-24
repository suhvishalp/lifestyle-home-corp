package com.lifestylehomecorp.productservice.config;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommercetoolsConfigClient {

    private final APIClientProperties apiClientProperties;

    @Autowired
    public CommercetoolsConfigClient(APIClientProperties apiClientProperties) {
        this.apiClientProperties = apiClientProperties;
    }

    @Bean
    public ProjectApiRoot projectApiRoot() {
        String projectKey = apiClientProperties.getProjectKey();
        String clientId = apiClientProperties.getClientId();
        String clientSecret = apiClientProperties.getClientSecret();

        return ApiRootBuilder.of()
                .defaultClient(
                        ClientCredentials.of()
                                .withClientId(clientId)
                                .withClientSecret(clientSecret)
                                .build(),
                        ServiceRegion.GCP_EUROPE_WEST1.getOAuthTokenUrl(),
                        ServiceRegion.GCP_EUROPE_WEST1.getApiUrl()
                )
                .build(projectKey);
    }
}
