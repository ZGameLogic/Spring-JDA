package com.zgamelogic.tests;

import com.zgamelogic.jda.AdvancedListenerAdapter;
import com.zgamelogic.jda.Annotations;
import com.zgamelogic.testdata.TestListener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Advanced Listener Adapter Test Suite")
class AdvancedListenerAdapterTest {

    private static AdvancedListenerAdapter listener;

    @BeforeAll
    static void setUp() {
        listener = new TestListener();
    }

    @Nested
    @DisplayName("Register methods")
    class registeringMethods {
        @Test
        void registerAutoCompleteResponse(){
            assertEquals(1, listener.getMethodMap().get(Annotations.AutoCompleteResponse.class).size());
        }

        @Test
        void registerUserInteractionResponse(){
            assertEquals(1, listener.getMethodMap().get(Annotations.UserInteractionResponse.class).size());
        }

        @Test
        void registerMessageInteractionResponse(){
            assertEquals(1, listener.getMethodMap().get(Annotations.MessageInteractionResponse.class).size());
        }

        @Test
        void registerButtonResponse(){
            assertEquals(1, listener.getMethodMap().get(Annotations.ButtonResponse.class).size());
        }

        @Test
        void registerModalResponse(){
            assertEquals(1, listener.getMethodMap().get(Annotations.ModalResponse.class).size());
        }

        @Test
        void registerSlashResponse(){
            assertEquals(1, listener.getMethodMap().get(Annotations.SlashResponse.class).size());
        }

        @Test
        void registerEntitySelectionResponse(){
            assertEquals(1, listener.getMethodMap().get(Annotations.EntitySelectionResponse.class).size());
        }

        @Test
        void registerStringSelectionResponse(){
            assertEquals(1, listener.getMethodMap().get(Annotations.StringSelectionResponse.class).size());
        }
    }
}