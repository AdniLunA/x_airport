import base.Item;

import com.google.common.eventbus.Subscribe;

import apmod00.ComponentManager;
import base.Subscriber;
import configuration.Configuration;
import event.EventGunDetected;
import event.EventScan;

public class Airport extends Subscriber {
    private ComponentManager componentManager;

    public Airport() {
        componentManager = new ComponentManager();
    }

    public void initSubscriber() {
        addSubscriber(this);
        addSubscriber(componentManager);
    }

    public void addSubscriber(Subscriber subscriber) {
        Configuration.instance.eventBus.register(subscriber);
    }

    public void scan(Item item) {
        Configuration.instance.eventBus.post(new EventScan(item));
    }

    @Subscribe
    public void receive(EventGunDetected eventGunDetected) {
        if (Configuration.instance.isDebug) {
            System.out.println("Airport.receive()");
            System.out.println("eventGunDetected : " + eventGunDetected);
        }
    }

    public static void main(String... args) {
        Airport airport = new Airport();
        airport.initSubscriber();

        Item item = new Item(1,"ikelooeellgunopwlwkklwwknem");
        airport.scan(item);
    }
}