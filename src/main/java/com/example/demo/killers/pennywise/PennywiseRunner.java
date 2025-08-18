package com.example.demo.killers.pennywise;

import dev.openfeature.sdk.MutableContext;
import jakarta.annotation.PreDestroy;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.FlagEvaluationDetails;

@Component
@ConditionalOnExpression("${killers.enabled:false} and '${killers.profile:}' == 'pennywise'")
public class PennywiseRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PennywiseRunner.class);
    

    private final PennywiseProperties properties;
    private final Client openfeature;

    private volatile boolean running = true;

    @Value("${detective.suspect.name:unknown}")
    private String detectiveSuspectName;

   

    public PennywiseRunner(PennywiseProperties properties,Client client) {
        this.openfeature = client;
        this.properties = properties;

        MutableContext context = new MutableContext();
        context.add("userId", properties.getName());
        client.setEvaluationContext(context);
        running = properties.isEnabled();
    }

    public void run(String... args) throws InterruptedException {
        Thread.currentThread().setName(properties.getName());

        logger.info("- Pennywise is initialized. Waiting for a signal.");
        

        // Simulate Pennywise's main loop
        while (running) {
            

            if (openfeature.getBooleanValue("pennywise.enabled", false)) {
                logger.info("-# Pennywise has started collecting children.");
            } else {
                logger.info("-/ No sight of Pennywise. He is just a regular clown now.");
            }

            if(openfeature.getBooleanValue("killersonly.enabled", false)) {
                logger.info("-## {} is on the run from the coppers. He must be careful not to leave any traces behind.", properties.getName());
            } else {
                logger.info("-// No real killer on the loose. Only {}.", properties.getName());
            }

            FlagEvaluationDetails<Boolean> variant = openfeature.getBooleanDetails("detective.suspect.name", false);
            if (variant.getValue()) {
                // This does not work at the momment, because the Provider uses the wrong version of the Unleash SDK.
                logger.info("#-## The detective suspects {}:{} is the killer.", variant.getVariant(), detectiveSuspectName);
            } else {
                logger.info("#-## The detective has no clue who the killer is.");
            }

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                logger.error("#-## {}'s main loop was interrupted by a nosy detective.", properties.getName(), e);
                break; // Exit the loop to allow the shutdown hook to run
            }
            logger.info("#-## The coppers are still looking for the killer, but they have no clue who it is.");
            logger.info("#-## We know it is {}.", properties.getName());


        }
    }

    @PreDestroy
    public void onShutdown() {
        logger.info("/// Shutdown signal received. Cops stopped the killing spree. ///");
        running = false; // Stop the main loop
    }
}
