package com.zgamelogic.testdata;

import com.zgamelogic.jda.AdvancedListenerAdapter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;

import static com.zgamelogic.jda.Annotations.*;

public class TestListener extends AdvancedListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("On ready event");
    }

    @SlashResponse("test_slash_command")
    private void testSlashResponse(SlashCommandInteractionEvent event){

    }

}
