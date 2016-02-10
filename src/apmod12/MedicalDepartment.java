package apmod12;

/**
 * When MEDICAL is triggered, Medical Department triggers Medical Event
 *
 * Medical Department contains 3 Emergency Vehicles
 */
public class MedicalDepartment {
    private MedicalComponentManager medicalComponentManager = new MedicalComponentManager();

    public void receiveEmergencyCall(String location){
        medicalComponentManager.launchEmergencyVehicle(location);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //medicalComponentManager.returnEmergencyVehicle();
    }
}
