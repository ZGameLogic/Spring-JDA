package com.zgamelogic.test;

import com.zgamelogic.testdata.Config;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@Slf4j
public class TestListener extends ListenerAdapter {

    private Config config;

    @Autowired
    public TestListener(Config config){
        log.info("We are in the constructor");
        this.config = config;
        log.info(config.getName());
    }

    @PostConstruct
    public void postConst(){
        log.info("We are post construct");
        log.info(config.getName());
    }

    @Override
    public void onReady(ReadyEvent event) {
        log.info("We are ready on discord");
        log.info(config.getName());
    }
}
