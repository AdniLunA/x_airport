package apmod12;

import base.Item;

/**
 * Medical Event is triggered by the Medical Department
 *
 * When Medical Event is triggered, one of the 3 existing
 * EmergencyVehicles is sent to the location of the emergency
 */
public class MedicalEvent {
    private Item item;

    public MedicalEvent(Item item) {
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
