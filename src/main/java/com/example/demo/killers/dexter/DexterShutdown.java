package com.example.demo.killers.dexter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DexterShutdown {
    private static final Logger logger = LoggerFactory.getLogger(DexterShutdown.class);



    @EventListener
    public void onShutdown(ContextClosedEvent event) {
        // Add a shutdown hook to clean up resources if needed
        // This is the equivalent of doing Java:
        // Runtime.getRuntime().addShutdownHook(new Thread(() -> {};
        // but using Spring's event system instead.
            logger.info("### Dexter got the signal to shut down. Starting the cleanup process.");
            // Perform any necessary cleanup here
            logger.info("Dexter is closing all the database traces behind him.");
            logger.info("Dexter is closing all the file handles behind him.");
            logger.info("Dexter is terminating all the leftover network connections.");
            logger.info("The Message consumers and producers was stopped from telling the coppers anything.");
            logger.info("Dexter is cleaning up the memory and resources.");
            logger.info("Dexter is shutting down the application gracefully.");
            logger.info("Flushing all the logs to ensure no traces are left behind.");


            try {
                TimeUnit.SECONDS.sleep(2);// Simulate some cleanup time
                logger.info("Dexter is done. Good luck finding any traces. God bye!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                logger.error("Dexter's cleanup was interrupted by a nosy detective.", e);
            }
    }

}
