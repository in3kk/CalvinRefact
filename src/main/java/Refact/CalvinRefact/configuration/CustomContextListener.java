package Refact.CalvinRefact.configuration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.stereotype.Component;

@Component
public class CustomContextListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Thread.getAllStackTraces().keySet().forEach(thread -> {
            if (thread.getName().contains("SeedGenerator")) {
                thread.interrupt();;
            }
        });
    }
}
