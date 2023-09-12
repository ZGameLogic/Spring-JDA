package com.zgamelogic.jda;

import java.lang.annotation.*;

public abstract class Annotations {
    /**
     * Annotation for CommandAutoCompleteInteractionEvent
     * Here is a code example of a slash command with options
     * <pre>{@code
     * Commands.slash("test", "Test command with fruit")
     *     .addOption(OptionType.STRING, "fruit", "Fruit to pick", true, true);
     * }</pre>
     * and here is an example of the method that will get called
     * <pre>{@code
     * @AutoCompleteResponse(slashCommandId = "fruit", focusedOption = "name")
     * private void autocompleteExample(CommandAutoCompleteInteractionEvent event){
     *     String[] words = new String[]{"apple", "apricot", "banana", "cherry", "coconut", "cranberry"};
     *     List<Command.Choice> options = Stream.of(words)
     *             .filter(word -> word.startsWith(event.getFocusedOption().getValue())) // only display words that start with the user's current input
     *             .map(word -> new Command.Choice(word, word)) // map the words to choices
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
     * Annotation for UserContextInteractionEvent
     * Here is a code example of a MessageContextInteractionEvent
     * <pre>{@code
     * Commands.context(Command.Type.USER, "Test"));
     * }</pre>
     * and here is a code example of the method that will get called when a user does this interaction
     * <pre>{@code
     * @UserInteractionResponse("test")
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
    public @interface UserInteractionResponse {
        /**
         * ID of the interaction
         * @return interactionId
         */
        String value();
    }

    /**
     * Annotation for MessageContextInteractionEvent
     * Here is a code example of a MessageContextInteractionEvent
     * <pre>{@code
     * Commands.context(Command.Type.MESSAGE, "Test"));
     * }</pre>
     * and here is a code example of the method that will get called when a user does this interaction
     * <pre>{@code
     * @MessageInteractionResponse("test")
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
    public @interface MessageInteractionResponse {

        /**
         * ID of the interaction
         * @return interactionId
         */
        String value();
    }

    /**
     * Annotation for ButtonInteractionEvent
     * Here is a code example of a button
     * <pre>{@code
     * Button.success("example_button", "Example");
     * }</pre>
     * and here is a code example of the method that will get called when a user hits that button
     * <pre>{@code
     * @ButtonResponse("example_button")
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
    public @interface ButtonResponse {
        /**
         * ID of the button that was pressed
         * @return button Id
         */
        String value();
    }

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
     * @ModalResponse("example_modal")
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
    public @interface ModalResponse {
        /**
         * Name of the modal
         * @return name of the modal
         */
        String value();
    }

    /**
     * Annotation for SlashCommandInteractionEvent.
     * Here is a code example of a slash command
     * <pre>{@code
     *  Commands.slash("test", "This is a description");
     * }</pre>
     * and here is a code example of the method that will get called when a user triggers that command
     * <pre>{@code
     * @SlashResponse("test")
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
     * @SlashResponse(value = "test", subCommandName = "sub")
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
     * Annotation for EntitySelectInteractionEvent
     * Here is a code example of an entity selection
     * <pre>{@code
     *   EntitySelectMenu.create("test_selection", EntitySelectMenu.SelectTarget.USER).setMinValues(1).setMaxValues(25).build();
     * }</pre>
     * and here is a code example of the method that will get called when a user triggers that entity selection
     * @EntitySelectionResponse("test_selection")
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
    public @interface EntitySelectionResponse {
        /**
         * Menu ID for the menu
         * @return menu ID
         */
        String value();
    }

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
     * @StringSelectionResponse("food_selection")
     * private void foodSelection(StringSelectInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * You can also annotate your methods with the exact option that got picked by doing something like this
     * <pre>{@code
     * @StringSelectionResponse(value = "food_selection", selectedOptionValue = "banana")
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
     * Annotate any method you want this to be called when the onReady event is generated
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface OnReady {}
}
