package apmod12;

import base.Subscriber;
import com.google.common.eventbus.Subscribe;
import configuration.Configuration;
import event.EventGunDetected;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * When Medical Event is triggered, one of the 3 existing
 * EmergencyVehicles is sent to the location of the emergency
 * by the Component Manager
 */

public class MedicalComponentManager extends Subscriber {
    private Class clazz;
    private Object instance;
    private Object port;

    public MedicalComponentManager() {
        if (Configuration.instance.isDebug)
            System.out.println("--- MedicalComponentManager");

        File componentJavaArchive = new File(Configuration.instance.apMod12ComponentJavaArchivePath);
        if (!componentJavaArchive.exists()) {
            System.out.println("Component " + Configuration.instance.apMod12ComponentJavaArchivePath + " not found.");
            System.exit(-1);
        } else {
            System.out.println("Component " + Configuration.instance.apMod12ComponentJavaArchivePath + " found.");
            setClazz();
            setInstance();
            setPort();
        }
    }

    public void setClazz() {
        if (Configuration.instance.isDebug)
            System.out.println("- setClazz");

        try {
            URL[] urls = {new File(Configuration.instance.apMod12ComponentJavaArchivePath).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, MedicalComponentManager.class.getClassLoader());
            clazz = Class.forName("Component",true,urlClassLoader);
            if (Configuration.instance.isDebug)
                System.out.println("clazz : " + clazz.toString() + " - " + clazz.hashCode());
        } catch (Exception e) {
            System.out.println("! exception : " + e.getMessage());
        }
    }

    public void setInstance() {
        try {
            instance = clazz.getMethod("getInstance", new Class[0]).invoke(null,new Object[0]);
            if (Configuration.instance.isDebug)
                System.out.println("object instance - " + instance.hashCode());
        } catch (Exception e) {
            System.out.println("! exception : " + e.getMessage());
        }
    }

    public void setPort() {
        try {
            port = clazz.getDeclaredField("port").get(instance);
            if (Configuration.instance.isDebug)
                System.out.println("port instance - " + port.hashCode());
        } catch (Exception e) {
            System.out.println("! exception : " + e.getMessage());
        }
    }

    @Subscribe
    public void receive(MedicalEvent medicalEvent) {
        try {
            if (Configuration.instance.isDebug) {
                System.out.println("MedicalComponentManager.receive()");
                System.out.println("medicalEmergencyEvent : " + medicalEvent);

                Method methodGetVersion = clazz.getDeclaredMethod("getVersion");
                System.out.println(methodGetVersion);
                String version = (String)methodGetVersion.invoke(instance);
                System.out.println("version : " + version);

                System.out.println();

                Method methodListMethods = port.getClass().getMethod("listMethods");
                System.out.println(methodListMethods);
                methodListMethods.invoke(port);
            }

            Class[] parameterTypes = {base.Item.class};
            Method methodScan = port.getClass().getMethod("scan",parameterTypes);

            if (Configuration.instance.isDebug) {
                System.out.println(medicalEvent.getItem().getClass());
                System.out.println(methodScan);
            }

            Object[] parameterValues = {medicalEvent.getItem()};
            boolean isDetected = (boolean)methodScan.invoke(port,parameterValues);
            if (isDetected)
                Configuration.instance.eventBus.post(new EventGunDetected(medicalEvent.getItem()));

        } catch (Exception e) {
            System.out.println("! exception : " + e.getMessage());
        }
    }
}