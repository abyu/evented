package org.skk.evented;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NoParamHandlerMethod extends HandlerMethod{

    private EventHandler handler;
    private Method handlerMethod;

    public NoParamHandlerMethod(EventHandler handler, Method handlerMethod) {

        this.handler = handler;
        this.handlerMethod = handlerMethod;
    }

    @Override
    public void invoke() throws InvocationTargetException, IllegalAccessException {
        handlerMethod.invoke(handler);
    }

    @Override
    protected void finalize() throws Throwable {
        handler = null;
        super.finalize();
    }
}