package com.zgamelogic.boot;

import com.zgamelogic.jda.JdaConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

import java.util.Collections;
import java.util.Set;

@Slf4j
public class JDASpringApplication extends SpringApplication {

    /**
     * Create a new {@link SpringApplication} instance. The application context will load
     * beans from the specified primary sources (see {@link SpringApplication class-level}
     * documentation for details. The instance can be customized before calling
     * {@link #run(String...)}.
     *
     * @param primarySources the primary bean sources
     * @see #run(Class, String[])
     * @see #setSources(Set)
     */
    public JDASpringApplication(Class<?>... primarySources) {
        super(primarySources);
        this.addPrimarySources(Collections.singleton(JdaConfiguration.class));
    }
}
