package com.zgamelogic.jda;

/**
 * Interface for verify events
 * @author Ben Shabowski
 */
public interface EventVerify {

    /**
     * Used to verify if an event should trigger the calling of a method
     * @param annotation The annotation object
     * @param event The event object
     * @return True if we should run the method or not
     */
    boolean verify(Object annotation, Object event);
}
