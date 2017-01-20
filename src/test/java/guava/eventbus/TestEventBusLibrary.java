package guava.eventbus;

import static org.junit.Assert.assertEquals;
import guava.eventbus.EventBusExample.AnotherAppEventSubscriber;
import guava.eventbus.EventBusExample.AppEventSubscriber;
import guava.eventbus.EventBusExample.Publisher;
import guava.eventbus.EventBusExample.YetAnotherAppEventSubscriber;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

public class TestEventBusLibrary {
  EventBus eventBus;
  
  @Before
  public void setup() {
    // TODO Auto-generated method stub
    eventBus = new EventBus();
  }
  
  @Test
  public void testEventBusPublisher() {    
    Publisher app = new Publisher(eventBus);
    AppEventSubscriber subscriber = new AppEventSubscriber();
    
    AnotherAppEventSubscriber lateSubscriber = new AnotherAppEventSubscriber();
    
    eventBus.register(subscriber);
    
    app.starting();    
    assertEquals(subscriber.appStartingEventCount, 1);
    
    eventBus.register(lateSubscriber);
    assertEquals(lateSubscriber.appEventCount, 0);
    
    app.initialized();
    assertEquals(subscriber.appInitializedEventCount, 1);
    
    app.terminated();
    assertEquals(subscriber.appTerminatedEventCount, 1);
    
    assertEquals(lateSubscriber.appEventCount, 2);
    
  }
  
  @Test
  public void testEventBusException() {    
    Publisher app = new Publisher(eventBus);
    
    YetAnotherAppEventSubscriber exceptionThrowingSubscriber = new YetAnotherAppEventSubscriber();
    AppEventSubscriber subscriber = new AppEventSubscriber();
    
    eventBus.register(subscriber);
    
    app.starting();    
    assertEquals(subscriber.appStartingEventCount, 1);
    
    eventBus.register(exceptionThrowingSubscriber);
    assertEquals(exceptionThrowingSubscriber.appEventCount, 0);
    
    app.initialized();
    assertEquals(subscriber.appInitializedEventCount, 1);
    
    app.terminated();
    assertEquals(subscriber.appTerminatedEventCount, 1);
    
    assertEquals(exceptionThrowingSubscriber.appEventCount, 2);
    
  }
}
