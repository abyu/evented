package org.skk.tide.intnl;

import org.skk.tide.Event;
import org.skk.tide.EventHandler;
import org.skk.tide.HandlerMethodNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class EventHandlerWrappers extends ArrayList<EventHandlerWrapper> {

    public boolean contains(EventHandler handler) {
        for (int index = 0; index < size(); index++) {
            if (get(index).containsHandler(handler))
                return true;
        }

        return false;
    }

    public void invokeHandlerMethod(final Event event) throws IllegalAccessException, HandlerMethodNotFoundException, InvocationTargetException {

        for (int i = 0; i < size(); i++) {
            get(i).invokeHandlerMethod(event);
        }

    }
}
