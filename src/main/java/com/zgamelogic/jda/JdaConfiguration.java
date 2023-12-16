package com.zgamelogic.jda;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JdaConfiguration {
    @Bean
    public JDA buildBot(ConfigurableApplicationContext cac, JDABuilder botBuilder){
        log.info("Initializing JDA Bot");
        cac.getBeansOfType(ListenerAdapter.class).values().forEach(listener -> {
            log.info(listener.getClass().getName());
            botBuilder.addEventListeners(listener);
        });
        try {
            JDA bot = botBuilder.build();
            bot.awaitReady();
            return bot;
        } catch (Exception e) {
            cac.close();
            log.error("Unable to launch bot", e);
            return null;
        }
    }
}
