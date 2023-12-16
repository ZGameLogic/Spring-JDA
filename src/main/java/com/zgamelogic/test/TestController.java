package com.zgamelogic.test;

import com.zgamelogic.jda.AdvancedListenerAdapter;
import com.zgamelogic.jda.Annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController extends AdvancedListenerAdapter {

    private final JDA bot;

    @Autowired
    private TestController(JDA bot){
        this.bot = bot;
    }

    @GetMapping("/")
    private String test(){
        bot.getGuildById(738850921706029168L)
                .getTextChannelById(1150451554797760593L)
                .sendMessage("bep").queue();
        return "hell0!";
    }

    @SlashResponse("bug")
    private void bug(SlashCommandInteractionEvent event){
        event.reply("no").queue();
    }
}
