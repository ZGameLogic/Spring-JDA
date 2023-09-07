package com.zgamelogic.boot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;

import java.util.Set;

@Getter
@Slf4j
public class JDASpringApplication extends SpringApplication {

    private JDABuilder botBuilder;
    private JDA bot;

    /**
     * Create a new {@link SpringApplication} instance. The application context will load
     * beans from the specified primary sources (see {@link SpringApplication class-level}
     * documentation for details. The instance can be customized before calling
     * {@link #run(String...)}.
     *
     * @param primarySources the primary bean sources
     * @see #run(Class, String[])
     * @see #SpringApplication(ResourceLoader, Class...)
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
        botBuilder.addEventListeners(cac.getBeansOfType(ListenerAdapter.class).values().iterator().next());
//        LinkedList<ListenerAdapter> listeners = new LinkedList<>(cac.getBeansOfType(ListenerAdapter.class).values());
//
//        botBuilder.addEventListeners(listeners);
        bot = botBuilder.build();

        try {
            bot.awaitReady();
        } catch (InterruptedException e) {
            log.error("Unable to launch bot");
            cac.close();
        }
        return cac;
    }
}
