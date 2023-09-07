package com.zgamelogic.test;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class TestListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        log.info("We are ready on discord");
    }
}
