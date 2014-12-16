package com.skk.evented;

import com.skk.evented.events.EventOne;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.mockito.MockitoAnnotations.initMocks;

public class EventRepositoryTest {

    @Mock
    private EventHandler eventHandler1;

    @Mock
    private EventHandler eventHandler2;

    private TestHandler testHandler = new TestHandler();

    private EventRepository eventRepository;

    @Before
    public void setUp(){
        initMocks(this);
        eventRepository = new EventRepository();

    }

    @Test
    public void aHandlerForAnEventIsRegistered() throws InvocationTargetException, IllegalAccessException {

        eventRepository.register(eventHandler1, EventOne.class);

        ArrayList<EventHandler> handlers = eventRepository.getHandlers(EventOne.class);

        Assert.assertThat(handlers.size(), Is.is(1));
    }

    @Test
    public void multipleHandlersForAnEventAreRegistered(){
        eventRepository.register(eventHandler1, EventOne.class);
        eventRepository.register(eventHandler2, EventOne.class);

        ArrayList<EventHandler> handlers = eventRepository.getHandlers(EventOne.class);

        Assert.assertThat(handlers.size(), Is.is(2));
    }
//
//    @Test
//    public void onlyOneInstanceOfAHandlerIsRegisteredForAnEvent(){
//        eventRepository.register(eventHandler1, Event.SMSReceived);
//        eventRepository.register(eventHandler1, Event.SMSReceived);
//
//        ArrayList<EventHandler> handlers = eventRepository.getHandlers(Event.SMSReceived);
//
//        Assert.assertThat(handlers.size(), Is.is(1));
//    }
//
//    @Test
//    public void emptyHandlerListIsReturnedWhenNoneRegistered(){
//
//        ArrayList<EventHandler> handlers = eventRepository.getHandlers(Event.SMSReceived);
//
//        Assert.assertTrue(handlers.isEmpty());
//    }
//
//    @Test
//    public void methodsAnnotatedWithHandleEventInHandlersAreInvokedOnRaiseAnEvent() throws IllegalAccessException {
//        eventRepository.register(testHandler, Event.SMSReceived);
//
//        IncomingSmsData data = new IncomingSmsData();
//        try {
//            eventRepository.raiseEvent(Event.SMSReceived, data);
//            Assert.fail("Expected InvocationTargetException to be thrown");
//        } catch (InvocationTargetException e) {
//            Throwable targetException = e.getTargetException();
//            Assert.assertTrue("Expected MethodInvoked exception, but was "+targetException.toString(), targetException instanceof MethodInvoked);
//            Assert.assertThat(((MethodInvoked) targetException).getData(), Is.<EventData>is(data));
//        }
//    }
//
//    @Test
//    public void handlersCanRegisterToMultipleEventsCorrespondingMethodIsOnlyCalled() throws IllegalAccessException {
//        eventRepository.register(testHandler, Event.SMSReceived);
//        eventRepository.register(testHandler, Event.SMSReplied);
//
//        RepliedSms data = new RepliedSms();
//        try {
//            eventRepository.raiseEvent(Event.SMSReplied, data);
//            Assert.fail("Expected InvocationTargetException to be thrown");
//        } catch (InvocationTargetException e) {
//            Throwable targetException = e.getTargetException();
//            Assert.assertTrue("Expected MethodInvoked exception, but was "+targetException.toString(), targetException instanceof MethodInvoked);
//            Assert.assertThat(((MethodInvoked) targetException).getData(), Is.<EventData>is(data));
//        }
//    }


    private class TestHandler implements EventHandler{

        @HandleEvent(eventType = EventOne.class)
        public void handleEvent() throws MethodInvoked {
         //Mockito does not support spying on annotated methods(the annotations are lost on the spy object), hence an exception is used assert that the method was called.
            throw new MethodInvoked();
        }

    }

    private class MethodInvoked extends Exception {

        private EventData data;

        public MethodInvoked(EventData data) {

            this.data = data;
        }

        public MethodInvoked() {

        }

        public EventData getData() {
            return data;
        }
    }
}


