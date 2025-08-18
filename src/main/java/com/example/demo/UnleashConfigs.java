package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.openfeature.contrib.providers.unleash.UnleashProvider;
import dev.openfeature.contrib.providers.unleash.UnleashProviderConfig;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.OpenFeatureAPI;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;

@Configuration
public class UnleashConfigs {
    
    // Make this into a constructor
    @Bean
    public UnleashConfig.Builder unleashConfigBuilder(
            @Value("${unleash.appName}") String appName,
            @Value("${unleash.instanceId}") String instanceId,
            @Value("${unleash.apiUrl}") String apiUrl,
            @Value("${unleash.apiKey}") String apiKey
    ) {
        return UnleashConfig.builder()
                .appName(appName)
                .instanceId(instanceId)
                .unleashAPI(apiUrl)
                .fetchTogglesInterval(2)
                .apiKey(apiKey);
    }

    @Bean
    public Unleash directUnleash(UnleashConfig.Builder configBuilder) {
        return new io.getunleash.DefaultUnleash(configBuilder.build());
    }

    @Bean
    public Client throughOpenFeatureClient(UnleashConfig.Builder configBuilder) {
        // Initialize Unleash with the provided configuration
         dev.openfeature.contrib.providers.unleash.UnleashProviderConfig unleashProviderConfig = UnleashProviderConfig.builder()
                .unleashConfigBuilder(configBuilder)
                .build();

        
        UnleashProvider unleashProvider = new UnleashProvider(unleashProviderConfig);
    
        OpenFeatureAPI.getInstance().setProviderAndWait(unleashProvider);

        return OpenFeatureAPI.getInstance().getClient();
    }

}
