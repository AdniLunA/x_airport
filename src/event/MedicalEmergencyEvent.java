package event;


import base.Item;

public class MedicalEmergencyEvent {
    private Item item;

    public MedicalEmergencyEvent(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ EventScan : ");
        stringBuilder.append("item = ").append(item).append(" }");
        return stringBuilder.toString();
    }
}
