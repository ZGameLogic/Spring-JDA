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

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;

@Slf4j
public abstract class AdvancedListenerAdapter extends ListenerAdapter {

    private final HashMap<Class, LinkedList<Method>> methodMap;

    public AdvancedListenerAdapter(){
        methodMap = new HashMap<>();
        for(Class c: this.getClass().getSuperclass().getDeclaredClasses()){
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

    /**
     * Annotation for CommandAutoCompleteInteractionEvent
     * Here is a code example of a slash command with options
     * <pre>{@code
     * Commands.slash("test", "Test command with fruit")
     *     .addOption(OptionType.STRING, "fruit", "Fruit to pick", true, true);
     * }</pre>
     * and here is an example of the method that will get called
     * <pre>{@code
     * {@literal @}AutoCompleteResponse(slashCommandId = "fruit", focusedOption = "name")
     * private void autocompleteExample(CommandAutoCompleteInteractionEvent event){
     *     String[] words = new String[]{"apple", "apricot", "banana", "cherry", "coconut", "cranberry"};
     *     List$&#123;Command.Choice$&#125; options = Stream.of(words)
     *             .filter(word -$&#125; word.startsWith(event.getFocusedOption().getValue())) // only display words that start with the user's current input
     *             .map(word -$&#125; new Command.Choice(word, word)) // map the words to choices
     *             .collect(Collectors.toList());
     *     event.replyChoices(options).queue();
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode CommandAutoCompleteInteractionEvent in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.CommandAutoCompleteInteractionEvent(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(AutoCompleteResponses.class)
    public @interface AutoCompleteResponse {
        /**
         * Slash command ID
         * @return slashCommandId
         */
        String slashCommandId();

        /**
         * Focused Option
         * @return focusedOption
         */
        String focusedOption();

        String slashSubCommandId() default "";
    }

    /**
     * Annotation to allow for multiple Auto complete responses on one method
     * @see AutoCompleteResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AutoCompleteResponses { AutoCompleteResponse[] value(); }

    /**
     * Annotation for UserContextInteractionEvent
     * Here is a code example of a MessageContextInteractionEvent
     * <pre>{@code
     * Commands.context(Command.Type.USER, "Test"));
     * }</pre>
     * and here is a code example of the method that will get called when a user does this interaction
     * <pre>{@code
     * {@literal @}UserInteractionResponse("test")
     * private void userInteractionExample(UserContextInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onUserContextInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onUserContextInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(UserInteractionResponses.class)
    public @interface UserInteractionResponse {
        /**
         * ID of the interaction
         * @return interactionId
         */
        String value();
    }

    /**
     * Annotation to allow for multiple User interaction responses to be put on one method
     * @see UserInteractionResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface UserInteractionResponses { UserInteractionResponse[] value(); }

    /**
     * Annotation for MessageContextInteractionEvent
     * Here is a code example of a MessageContextInteractionEvent
     * <pre>{@code
     * Commands.context(Command.Type.MESSAGE, "Test"));
     * }</pre>
     * and here is a code example of the method that will get called when a user does this interaction
     * <pre>{@code
     * {@literal @}MessageInteractionResponse("test")
     * private void messageInteractionExample(MessageContextInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onMessageContextInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onMessageContextInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(MessageInteractionResponses.class)
    public @interface MessageInteractionResponse {

        /**
         * ID of the interaction
         * @return interactionId
         */
        String value();
    }

    /**
     * Annotation to allow for multiple Message interaction responses on one method
     * @see MessageInteractionResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MessageInteractionResponses{ MessageInteractionResponse[] value(); }

    /**
     * Annotation for ButtonInteractionEvent
     * Here is a code example of a button
     * <pre>{@code
     * Button.success("example_button", "Example");
     * }</pre>
     * and here is a code example of the method that will get called when a user hits that button
     * <pre>{@code
     * {@literal @}ButtonResponse("example_button")
     * private void exampleButtonResponse(ButtonInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onButtonInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onButtonInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(ButtonResponses.class)
    public @interface ButtonResponse {
        /**
         * ID of the button that was pressed
         * @return button Id
         */
        String value();
    }

    /**
     * Annotation to allow for multiple Button responses on one method
     * @see ButtonResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    protected @interface ButtonResponses{ ButtonResponse[] value(); }

    /**
     * Annotation for MessageReceivedEvent
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(MessageResponses.class)
    public @interface MessageResponse {
        /**
         * @return A trigger to call this method if the message starts with this string.
         */
        String trigger() default "";

        /**
         * Default value is false.
         * @return false if the message is allowed from a guild or from a private message
         */
        boolean onlyFromGuild() default false;
    }

    /**
     * Annotation to allow for multiple message responses on one method
     * @see MessageResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MessageResponses {
        MessageResponse[] value();
    }

    /**
     * Annotation for onGenericMessageReaction
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(EmoteResponses.class)
    public @interface EmoteResponse {
        /**
         * Name of the reaction emoji
         * @return Name of the reaction emoji
         */
        String value();

        /**
         * Boolean to trigger method if the reaction is being added.
         * True if method should trigger on reaction add.
         * False if the method should trigger on reaction remove.
         * @return if this method should be called if the reaction is added
         */
        boolean isAdding();
    }

    /**
     * Annotation to allow for multiple Button responses on one method
     * @see EmoteResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface EmoteResponses{ EmoteResponse[] value(); }

    /**
     * Annotation for ModalInteractionEvent
     * Here is a code example of a modal response
     * <pre>{@code
     * Modal.create("example_modal", "Example Modal Title")
     *         .addActionRow(TextInput.create("example_input", "Input", TextInputStyle.SHORT).build())
     * .build();
     * }</pre>
     * and here is a code example of the method that will get called when a user submits that modal
     * <pre>{@code
     * {@literal @}ModalResponse("example_modal")
     * private void exampleModalResponse(ModalInteractionEvent event){
     *    // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onModalInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onModalInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(ModalResponses.class)
    public @interface ModalResponse {
        /**
         * Name of the modal
         * @return name of the modal
         */
        String value();
    }

    /**
     * Annotation to allow for multiple modal responses on one method
     * @see ModalResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ModalResponses{ ModalResponse[] value(); }

    /**
     * Annotation for SlashCommandInteractionEvent.
     * Here is a code example of a slash command
     * <pre>{@code
     *  Commands.slash("test", "This is a description");
     * }</pre>
     * and here is a code example of the method that will get called when a user triggers that command
     * <pre>{@code
     * {@literal @}SlashResponse("test")
     * private void testMethod(SlashCommandInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * Here is a code example of a slash command with a sub command
     * <pre>{@code
     * Commands.slash("test", "This is a description")
     *    .addSubcommands(new SubcommandData("sub","This is a subcommand"));
     * }</pre>
     * and here is a code example of the method that will get called when a user triggers that command
     * <pre>{@code
     * {@literal @}SlashResponse(value = "test", subCommandName = "sub")
     * private void testMethodSub(SlashCommandInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onSlashCommandInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onSlashCommandInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(SlashResponses.class)
    public @interface SlashResponse {
        /**
         * Command name
         * @return Command name
         */
        String value();

        /**
         * Optional, use only if there is a subcommand
         * @return Subcommand name
         */
        String subCommandName() default "";
    }

    /**
     * Annotation to allow for multiple Slash responses on one method
     * @see SlashResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface SlashResponses { SlashResponse[] value(); }

    /**
     * Annotation for EntitySelectInteractionEvent
     * Here is a code example of an entity selection
     * <pre>{@code
     *   EntitySelectMenu.create("test_selection", EntitySelectMenu.SelectTarget.USER).setMinValues(1).setMaxValues(25).build();
     * }</pre>
     * and here is a code example of the method that will get called when a user triggers that entity selection
     * {@literal @}EntitySelectionResponse("test_selection")
     * private void entitySelRes(EntitySelectInteractionEvent event){
     *     // TODO respond to event
     * }
     * If your annotated methods are not getting called, perhaps you overrode onEntitySelectInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onEntitySelectInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(EntitySelectionResponses.class)
    public @interface EntitySelectionResponse {
        /**
         * Menu ID for the menu
         * @return menu ID
         */
        String value();
    }

    /**
     * Annotation to allow for multiple Entity selection responses on one method
     * @see EntitySelectionResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface EntitySelectionResponses{ EntitySelectionResponse[] value(); }

    /**
     * Annotation for StringSelectInteractionEvent
     * Here is a code example of a string selection
     * <pre>{@code
     * StringSelectMenu.create("food_selection")
     *         .addOption("Strawberry", "strawberry")
     *         .addOption("Banana", "banana")
     *         .addOption("Pear", "pear")
     * .build();
     * }</pre>
     * and here is a code example of the methods that will get called when a user triggers that string selection
     * <pre>{@code
     * {@literal @}StringSelectionResponse("food_selection")
     * private void foodSelection(StringSelectInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * You can also annotate your methods with the exact option that got picked by doing something like this
     * <pre>{@code
     * {@literal @}StringSelectionResponse(value = "food_selection", selectedOptionValue = "banana")
     * private void foodSelectionBanana(StringSelectInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onStringSelectInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onStringSelectInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(StringSelectionResponses.class)
    public @interface StringSelectionResponse {
        /**
         * Menu id for the menu
         * @return menu Id
         */
        String value();

        /**
         * Optional, selected value of the menu
         * @return selected value
         */
        String selectedOptionValue() default "";
    }

    /**
     * Annotation to allow for multiple Entity selection responses on one method
     * @see StringSelectionResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface StringSelectionResponses{ StringSelectionResponse[] value(); }

    /**
     * Annotate any method you want to get skipped if the event is from a bot
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface NoBot{}
}
