package guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusExample {
  interface AppEvent {
  }

  private static class AppStartingEvent implements AppEvent {

  }

  private static class AppInitializedEvent implements AppEvent {

  }

  private static class AppTerminatedEvent implements AppEvent {

  }

  public static class AppEventSubscriber {
    
    int appStartingEventCount;
    int appInitializedEventCount;
    int appTerminatedEventCount;
    
    @Subscribe
    public void subscribe(AppStartingEvent event) {
      appStartingEventCount++;
    }

    @Subscribe
    public void subscribe(AppInitializedEvent event) {
      appInitializedEventCount++;
    }

    @Subscribe
    public void subscribe(AppTerminatedEvent event) {
      appTerminatedEventCount++;
    }
  }

  public static class AnotherAppEventSubscriber {
    int appEventCount;
    
    @Subscribe
    public void subscribe(AppEvent event) {
      appEventCount++;
    }
  }
  
  public static class YetAnotherAppEventSubscriber {
    int appEventCount;
    
    @Subscribe
    public void subscribe(AppEvent event) {
      appEventCount++;
      throw new RuntimeException();
    }
  }
  
  public static class Publisher {
    private EventBus eventBus;

    public Publisher(EventBus eventbus) {
      this.eventBus = eventbus;
    }

    public void starting() {
      eventBus.post(new AppStartingEvent());
    }

    public void initialized() {
      eventBus.post(new AppInitializedEvent());
    }

    public void terminated() {
      eventBus.post(new AppTerminatedEvent());
    }

  }
}
