package event;

import base.Item;

public class EventGunDetected {
    private Item item;

    public EventGunDetected(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ EventGunDetected : ");
        stringBuilder.append("item = ").append(item).append(" }");
        return stringBuilder.toString();
    }
}