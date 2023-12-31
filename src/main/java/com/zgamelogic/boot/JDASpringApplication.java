package com.zgamelogic.boot;

import com.zgamelogic.jda.AdvancedListenerAdapter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;

import java.util.LinkedList;
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

        LinkedList<CommandData> globalCommands = new LinkedList<>();
        cac.getBeansOfType(ListenerAdapter.class).values().forEach(listener -> {
            botBuilder.addEventListeners(listener);
            if(listener.getClass().getSuperclass() == AdvancedListenerAdapter.class) {
                globalCommands.addAll(((AdvancedListenerAdapter)listener).getCommands());
            }
        });

        try {
            JDA bot = botBuilder.build();
            bot.awaitReady();
            try {
                bot.updateCommands().addCommands(globalCommands).queue();
                log.info("Updated " + globalCommands.size() + " global commands");
            } catch (Exception e){
                log.error("Unable to update global slash commands", e);
            }
        } catch (Exception e) {
            cac.close();
            log.error("Unable to launch bot", e);
        }
        return cac;
    }
}
