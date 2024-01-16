package com.zgamelogic.boot;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Set;

@Slf4j
public class JDASpringApplication extends SpringApplication {

    private final JDABuilder botBuilder;

    /**
     * Create a new {@link SpringApplication} instance. The application context will load
     * beans from the specified primary sources (see {@link SpringApplication class-level}
     * documentation for details. The instance can be customized before calling
     * {@link #run(String...)}.
     *
     * @param bot bot builder for JDA bot
     * @param primarySources the primary bean sources
     * @see #run(Class, String[])
     * @see #setSources(Set)
     */
    public JDASpringApplication(JDABuilder bot, Class<?>... primarySources) {
        super(primarySources);
        botBuilder = bot;
    }

    /**
     * Run the Spring application, creating and refreshing a new
     * {@link ApplicationContext}.
     *
     * @param args the application arguments (usually passed from a Java main method)
     * @return a running {@link ApplicationContext}
     */
    @Override
    public ConfigurableApplicationContext run(String... args) {
        ConfigurableApplicationContext cac = super.run(args);
        log.info("Initializing JDA Bot");

        try {
            JDA bot = botBuilder.build();
            bot.awaitReady();
        } catch (Exception e) {
            cac.close();
            log.error("Unable to launch bot", e);
        }
        return cac;
    }
}
