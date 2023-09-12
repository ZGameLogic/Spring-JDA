package com.zgamelogic.jda;

import lombok.Getter;
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
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import static com.zgamelogic.jda.Annotations.*;

/**
 * An advanced version of the ListenerAdapter class.
 * Highly annotative allowing automatic method calling.
 * @see AutoCompleteResponse
 * @see UserInteractionResponse
 * @see MessageInteractionResponse
 * @see ButtonResponse
 * @see ModalResponse
 * @see SlashResponse
 * @see EntitySelectionResponse
 * @see StringSelectionResponse
 * @see OnReady
 * @author Ben Shabowski
 */
@Slf4j
@Getter
public abstract class AdvancedListenerAdapter extends ListenerAdapter {

    private final HashMap<Class, LinkedList<Method>> methodMap;
    private final LinkedList<CommandData> commands;

    public AdvancedListenerAdapter(){
        commands = new LinkedList<>();
        methodMap = new HashMap<>();
        for(Class c: Annotations.class.getDeclaredClasses()){
            methodMap.put(c, new LinkedList<>());
        }
        log.info("Registering annotated methods for class: " + this.getClass().getName());
        mapMethods();
    }

    private void handleEvent(EventVerify verify, Event event, Class annotation){
        if(!methodMap.containsKey(annotation) || methodMap.get(annotation).isEmpty()) return;
        methodMap.get(annotation).forEach(method -> {
            try {
                if(verify.verify(method.getAnnotation(annotation), event)) {
                    method.setAccessible(true);
                    method.invoke(this, event);
                }
            } catch (Exception e) {
                log.error("Unable to auto run method: " + method.getName(), e);
            }
        });
    }

    private void mapMethods(){
        LinkedList<Class> allowedAnnotations = new LinkedList<>(Arrays.asList(Annotations.class.getDeclaredClasses()));
        for(Method method: getClass().getDeclaredMethods()){
            for(Annotation a: method.getAnnotations()){
                if(allowedAnnotations.contains(a.annotationType())){
                    methodMap.get(a.annotationType()).add(method);
                    log.info(method.getName() + ": " + a.annotationType().getSimpleName() + " registered");
                }
            }
        }
    }

    @Override
    public void onReady(ReadyEvent event) {
        EventVerify verify = (givenAnnotation, givenEvent) -> true;
        handleEvent(verify, event, OnReady.class);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EventVerify verify = (givenAnnotation, givenEvent) -> {
            try {
                SlashResponse a = (SlashResponse) givenAnnotation;
                SlashCommandInteractionEvent e = (SlashCommandInteractionEvent) givenEvent;
                if(a.subCommandName().isEmpty()){
                    return a.value().equals(e.getName());
                } else {
                    return a.value().equals(e.getName()) && a.subCommandName().equals(e.getSubcommandName());
                }
            } catch (Exception e) {
                return false;
            }
        };
        handleEvent(verify, event, SlashResponse.class);
    }

    @Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {
        EventVerify verify = (givenAnnotation, givenEvent) -> {
            try {
                UserInteractionResponse a = (UserInteractionResponse) givenAnnotation;
                UserContextInteractionEvent e = (UserContextInteractionEvent) givenEvent;
                return a.value().equals(e.getName());
            } catch (Exception e) {
                return false;
            }
        };
        handleEvent(verify, event, UserInteractionResponse.class);
    }

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        EventVerify verify = (givenAnnotation, givenEvent) -> {
            try {
                MessageInteractionResponse a = (MessageInteractionResponse) givenAnnotation;
                MessageContextInteractionEvent e = (MessageContextInteractionEvent) givenEvent;
                return a.value().equals(e.getName());
            } catch (Exception e) {
                return false;
            }
        };
        handleEvent(verify, event, MessageInteractionResponse.class);
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        EventVerify verify = (givenAnnotation, givenEvent) -> {
            try {
                ButtonResponse a = (ButtonResponse) givenAnnotation;
                ButtonInteractionEvent e = (ButtonInteractionEvent) givenEvent;
                return a.value().equals(e.getButton().getId());
            } catch (Exception e) {
                return false;
            }
        };
        handleEvent(verify, event, ButtonResponse.class);
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        EventVerify verify = (givenAnnotation, givenEvent) -> {
            try {
                AutoCompleteResponse a = (AutoCompleteResponse) givenAnnotation;
                CommandAutoCompleteInteractionEvent e = (CommandAutoCompleteInteractionEvent) givenEvent;
                boolean output =  a.slashCommandId().equals(e.getName()) && a.focusedOption().equals(e.getFocusedOption().getName());
                if(!a.slashSubCommandId().isEmpty()){
                    output &= a.slashSubCommandId().equals(e.getSubcommandName());
                }
                return output;
            } catch (Exception e) {
                return false;
            }
        };
        handleEvent(verify, event, AutoCompleteResponse.class);
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        EventVerify verify = (givenAnnotation, givenEvent) -> {
            try {
                ModalResponse a = (ModalResponse) givenAnnotation;
                ModalInteractionEvent e = (ModalInteractionEvent) givenEvent;
                return a.value().equals(e.getModalId());
            } catch (Exception e) {
                return false;
            }
        };
        handleEvent(verify, event, ModalResponse.class);
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        EventVerify verify = (givenAnnotation, givenEvent) -> {
            try {
                StringSelectionResponse a = (StringSelectionResponse) givenAnnotation;
                StringSelectInteractionEvent e = (StringSelectInteractionEvent) givenEvent;
                if(a.selectedOptionValue().isEmpty()){
                    return a.value().equals(e.getSelectMenu().getId());
                } else {
                    return a.value().equals(e.getSelectMenu().getId()) && a.selectedOptionValue().equals(event.getInteraction().getSelectedOptions().get(0).getValue());
                }
            } catch (Exception e) {
                return false;
            }
        };
        handleEvent(verify, event, StringSelectionResponse.class);
    }

    @Override
    public void onEntitySelectInteraction(EntitySelectInteractionEvent event) {
        EventVerify verify = (givenAnnotation, givenEvent) -> {
            try {
                EntitySelectionResponse a = (EntitySelectionResponse) givenAnnotation;
                EntitySelectInteractionEvent e = (EntitySelectInteractionEvent) givenEvent;
                return a.value().equals(e.getSelectMenu().getId());
            } catch (Exception e) {
                return false;
            }
        };
        handleEvent(verify, event, EntitySelectionResponse.class);
    }
}
