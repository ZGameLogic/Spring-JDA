package com.zgamelogic.testdata;

import com.zgamelogic.jda.AdvancedListenerAdapter;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;

import static com.zgamelogic.jda.Annotations.*;

public class TestListener extends AdvancedListenerAdapter {

    @OnReady
    public void onReady(ReadyEvent event) {}

    @AutoCompleteResponse(slashCommandId = "test_slash_command", focusedOption = "test_focused_option")
    private void testAutoCompleteResponse(CommandAutoCompleteInteractionEvent event){}

    @UserInteractionResponse("test_user_interaction_command")
    private void testUserInteractionResponse(UserContextInteractionEvent event){}

    @MessageInteractionResponse("test_message_interaction_command")
    private void testMessageInteractionResponse(MessageContextInteractionEvent event){}

    @ButtonResponse("test_button_command")
    private void testButtonResponse(ButtonInteractionEvent event){}

    @ModalResponse("test_modal_command")
    private void testModalResponse(ModalInteractionEvent event){}

    @SlashResponse("test_slash_command")
    private void testSlashResponse(SlashCommandInteractionEvent event){}

    @EntitySelectionResponse("test_entity_selection_command")
    private void testEntitySelectionResponse(EntitySelectInteractionEvent event){}

    @StringSelectionResponse("test_string_selection_command")
    private void testStringSelectionResponse(StringSelectInteractionEvent event){}

}
