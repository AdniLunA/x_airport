package apmod12;

/**
 * When MEDICAL is triggered, Medical Department triggers Medical Event
 *
 * Medical Department contains 3 Emergency Vehicles
 */
public class MedicalDepartment {
    private MedicalComponentManager emergencyVehicle1 = new MedicalComponentManager();
    private MedicalComponentManager emergencyVehicle2 = new MedicalComponentManager();
    private MedicalComponentManager emergencyVehicle3 = new MedicalComponentManager();

    public void receiveEmergencyCall(String location){
        System.out.println("Emergency call received. Location: "+location);

        MedicalComponentManager pickedVehicle;
        int whichVehicle = (int) Math.round(Math.random()*2)+1; //random Number between 1 and 3
        switch (whichVehicle){
            case 1:
                pickedVehicle = emergencyVehicle1;
            case 2:
                pickedVehicle = emergencyVehicle2;
            default:
                pickedVehicle = emergencyVehicle3;
        }

        pickedVehicle.launchEmergencyVehicle(location);
        System.out.println("Vehicle will return in 5 Minutes.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pickedVehicle.returnEmergencyVehicle();
    }
}
