package com.zgamelogic.boot;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * Custom spring launch class to also start up a JDA bot
 * @author Ben Shabowski
 */
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
        log.info("Adding manual advance listener adapters...");
        // TODO create new advanced listener adapter to hold and call methods based off of the object and the methods
//        HashMap<Annotation, LinkedList<HashMap<Object, Method>>> yup;
        cac.getBeansOfType(ListenerAdapter.class).values().forEach(ala -> {
            botBuilder.addEventListeners(ala);
            log.info(ala.getClass().getName() + " added.");
        });
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
