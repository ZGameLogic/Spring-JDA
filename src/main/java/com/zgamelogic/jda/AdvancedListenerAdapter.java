package com.zgamelogic.jda;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;

import static com.zgamelogic.jda.Annotations.*;

@Slf4j
public abstract class AdvancedListenerAdapter extends ListenerAdapter {

    private final HashMap<Class, LinkedList<Method>> methodMap;

    public AdvancedListenerAdapter(){
        methodMap = new HashMap<>();
        for(Class c: Annotations.class.getDeclaredClasses()){
            methodMap.put(c, new LinkedList<>());
        }

        log.info("Registering annotated methods for class: " + this.getClass().getName());
        for(Method method: this.getClass().getDeclaredMethods()){
            log.info("\t\t" + method.getName());
        }
    }

    private void handleEvent(EventVerify verify, Event event, Object annotation){
        methodMap.get(annotation.getClass()).forEach(method -> {
            if(verify.verify(annotation, event)){
                try {
                    // TODO call method with event
                } catch(Exception e) {
                    log.error("Unable to auto run method: " + method.getName(), e);
                }
            }
        });
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EventVerify verify = (givenAnnotation, givenEvent) -> {
            try {
                SlashResponse a = (SlashResponse) givenAnnotation;
                SlashCommandInteractionEvent e = event;
                // TODO finish verify
                return true;
            } catch (Exception e) {
                return false;
            }
        };
        handleEvent(verify, event, SlashResponse.class);
    }

    @Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {

    }

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {

    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {

    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {

    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {

    }

    @Override
    public void onEntitySelectInteraction(EntitySelectInteractionEvent event) {

    }
}
