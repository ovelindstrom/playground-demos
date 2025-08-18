package com.example.demo.killers.pennywise;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "killers.pennywise")
public class PennywiseProperties {
    /**
     * Whether Pennywise is enabled.
     */
    private boolean enabled = true;

    /**
     * The name of the killer.
     */
    private String name = "Pennywise";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}