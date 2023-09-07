package com.zgamelogic.test;

import com.zgamelogic.jda.AdvancedListenerAdapter;
import com.zgamelogic.testdata.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestListener extends AdvancedListenerAdapter {

    private Config config;

    @Autowired
    public TestListener(Config config){
        log.info("We are in the constructor");
        this.config = config;
        log.info(config.getName());
    }
}
