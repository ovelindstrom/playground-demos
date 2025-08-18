package com.example.demo.killers.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "killers")
public class KillersProperties {
    private boolean enabled;
    private String profile;

    // getters and setters
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public String getProfile() { return profile; }
    public void setProfile(String profile) { this.profile = profile; }
}