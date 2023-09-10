package com.zgamelogic.tests;

import com.zgamelogic.jda.AdvancedListenerAdapter;
import com.zgamelogic.jda.Annotations;
import com.zgamelogic.testdata.TestListener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdvancedListenerAdapterTest {

    private static AdvancedListenerAdapter listener;

    @BeforeAll
    static void setUp() {
        listener = new TestListener();
    }

    @Test
    void registerTest() {
        assertEquals(1, listener.getMethodMap().get(Annotations.SlashResponse.class).size());
    }
}