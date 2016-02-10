package apmod00;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import com.google.common.eventbus.Subscribe;

import base.Subscriber;
import configuration.Configuration;
import event.EventGunDetected;
import event.EventScan;

public class ComponentManager extends Subscriber {
    private Class clazz;
    private Object instance;
    private Object port;

    public ComponentManager() {
        if (Configuration.instance.isDebug)
            System.out.println("--- ComponentManager");

        File componentJavaArchive = new File(Configuration.instance.apMod00ComponentJavaArchivePath);
        if (!componentJavaArchive.exists()) {
            System.out.println("Component " + Configuration.instance.apMod00ComponentJavaArchivePath + " not found.");
            System.exit(-1);
        } else {
            System.out.println("Component " + Configuration.instance.apMod00ComponentJavaArchivePath + " found.");
            setClazz();
            setInstance();
            setPort();
        }
    }

    public void setClazz() {
        if (Configuration.instance.isDebug)
            System.out.println("- setClazz");

        try {
            URL[] urls = {new File(Configuration.instance.apMod00ComponentJavaArchivePath).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls,ComponentManager.class.getClassLoader());
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
    public void receive(EventScan eventScan) {
        try {
            if (Configuration.instance.isDebug) {
                System.out.println("ComponentManager.receive()");
                System.out.println("eventScan : " + eventScan);

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
                System.out.println(eventScan.getItem().getClass());
                System.out.println(methodScan);
            }

            Object[] parameterValues = {eventScan.getItem()};
            boolean isDetected = (boolean)methodScan.invoke(port,parameterValues);
            if (isDetected)
                Configuration.instance.eventBus.post(new EventGunDetected(eventScan.getItem()));

        } catch (Exception e) {
            System.out.println("! exception : " + e.getMessage());
        }
    }
}