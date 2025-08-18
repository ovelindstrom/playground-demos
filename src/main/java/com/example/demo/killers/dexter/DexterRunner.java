package com.example.demo.killers.dexter;

import io.getunleash.Unleash;
import io.getunleash.UnleashContext;
import jakarta.annotation.PreDestroy;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;


import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnExpression("${killers.enabled:false} and '${killers.profile:}' == 'dexter'")
public class DexterRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DexterRunner.class);
    
    // Injected values from application properties
    // These can be overridden in application.properties or application.yml
    // Always ensure that properties has a reasonable default value. 
    @Value("${killers.dexter.enabled:true}")
    private volatile boolean running;
    @Value("${killers.dexter.name:Dexter}")
    private String killerName;

    @Value("${detective.suspect.name:unknown}")
    private String detectiveSuspectName;

    // Unleash instance for feature flag management
    private Unleash unleash;
    private UnleashContext context;

    public DexterRunner(Unleash unleash) {

        this.unleash = unleash;
        
    }

    public void run(String... args) throws InterruptedException {
        Thread.currentThread().setName(killerName);
        Logger logger = LoggerFactory.getLogger(DexterRunner.class);

        context = UnleashContext.builder()
                .userId(killerName)
                .build();
        

        
        logger.info("# Dexter is putting up plastic sheets and sharpening his knives.");
        
        // Simulate Dexter's main loop
        while (running) {
            

            if (unleash.isEnabled("dexter.enabled")) {
                logger.info("# Dexter is ready to unleash his full potential!");
            } else {
                logger.info("/ Dexter is not enabled. He is just a regular guy now.");
            }

            if(unleash.isEnabled("killersonly.enabled", context)) {
                logger.info("## {} is on the run from the coppers. He must be careful not to leave any traces behind.", killerName);
            } else {
                logger.info("// No real killer on the loose. Only {}.", killerName);
            }

            var variant = unleash.getVariant("detective.suspect.name", context);
            if (variant.isEnabled()) {
                logger.info("### The detective suspects {}:{} is the killer.", variant.getName(), variant.getPayload());
            } else {
                logger.info("/// The detective has no clue who the killer is.");
            }

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                logger.error("#E# {}'s main loop was interrupted by a nosy detective.", killerName, e);
                break; // Exit the loop to allow the shutdown hook to run
            }
            logger.info("### The coppers are still looking for the killer...");
            logger.info("### We know it is {}.", killerName);
        }
    }

    @PreDestroy
    public void onShutdown() {
        log.info("/// Shutdown signal received. Cops stopped the killing spree. ///");
        running = false; // Stop the main loop
    }
}
