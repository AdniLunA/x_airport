import java.lang.reflect.Method;




public class Component {



    private static Component instance = new Component();
    public Port port;

    private Component() {
        port = new Port();
    }

    public static Component getInstance() {
        return instance;
    }

    public String getVersion() {
        return "Type 2";
    }

    public class Port implements IComponent {
        private Method[] methods = getClass().getMethods();

        public void printComponentVersion() {
            System.out.println(getVersion() + "\n");
        }

        public String launchVehicle(String location) {
            return innerLaunchVehicle(location);
        }
        public String returnToBase () {
            return innerReturnToBase();
        }

        public void listMethods() {
            System.out.println("--- public methods for " + getClass().getName());
            for (int i = 0; i < methods.length; i++)
                if (!methods[i].toString().contains("Object") && !methods[i].toString().contains("list"))
                    System.out.println(methods[i]);
            System.out.println("---");
        }
    }

    private String innerLaunchVehicle(String location) {

        return "Vehicle " + getVersion() + " is launching to " + location +".";
    }
    private String innerReturnToBase(){

        return "Vehicle " + getVersion() + " is returning to Medical Department.";
    }


}