package com.zgamelogic.jda;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

@Slf4j
public abstract class AdvancedListenerAdapter extends ListenerAdapter {

    HashMap<Class, LinkedList<Method>> methodMap;

    public AdvancedListenerAdapter(){
        log.info("Registering annotated methods for class: " + this.getClass().getName());
        for(Method m : getAnnotatedMethods()){
            log.info("\t\t" + m.getName());
        }
    }

    private void registerMethod(Class annotation, Method method){
        if(methodMap.containsKey(annotation)){
            methodMap.get(annotation).add(method);
        } else {
            methodMap.put(annotation, new LinkedList(Collections.singleton(method)));
        }
    }

    private LinkedList<Method> getAnnotatedMethods(Class...classes){
        LinkedList<Method> methods = new LinkedList<>();
        for(Method m : this.getClass().getDeclaredMethods()){
            if(classes.length == 0 && m.getAnnotations().length > 0) {
                for (Class current : this.getClass().getSuperclass().getDeclaredClasses())
                    if (m.isAnnotationPresent(current)) {
                        methods.add(m);
                        break;
                    }
            } else {
                for (Class current : classes)
                    if (m.isAnnotationPresent(current)) {
                        methods.add(m);
                        break;
                    }
            }
        }
        return methods;
    }
}
