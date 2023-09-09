package com.zgamelogic.testdata;

import com.zgamelogic.jda.AdvancedListenerAdapter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import static com.zgamelogic.jda.Annotations.*;

public class TestListener extends AdvancedListenerAdapter {

    @SlashResponse("test_slash_command")
    private void testSlashResponse(SlashCommandInteractionEvent event){

    }

}
