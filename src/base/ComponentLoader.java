package base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ComponentLoader extends ClassLoader {
    public ComponentLoader(ClassLoader parent) {
        super(parent);
    }

    @SuppressWarnings("resource")
    private static HashMap<String,ByteArrayOutputStream> unzipJar(String jarPath)
            throws IOException {
        File file = new File(jarPath);
        JarFile jar = new JarFile(file);
        HashMap<String,ByteArrayOutputStream> hashMap = new HashMap<>();
        for (Enumeration<JarEntry> jarEntryEnumeration = jar.entries();jarEntryEnumeration.hasMoreElements();) {
            JarEntry jarEntry = jarEntryEnumeration.nextElement();
            if (jarEntry.toString().endsWith(".class")) {
                InputStream inputStream = jar.getInputStream(jarEntry);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (inputStream.available() > 0)
                    byteArrayOutputStream.write(inputStream.read());
                hashMap.put(jarEntry.toString(),byteArrayOutputStream);
            }
        }
        return hashMap;
    }

    @SuppressWarnings("rawtypes")
    private Class loadClass(String className,HashMap<String,ByteArrayOutputStream> hashMapBuffer)
            throws ClassNotFoundException {
        try {
            byte[] classData = null;
            for (Map.Entry<String,ByteArrayOutputStream> e : hashMapBuffer.entrySet())
                if (e.getKey().equals(className + ".class"))
                    classData = e.getValue().toByteArray();
            return defineClass(className,classData,0,classData.length);
        } catch (ClassFormatError cfe) {
            cfe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        throw new ClassNotFoundException();
    }

    @SuppressWarnings("rawtypes")
    public static Class loadClassFromJar(String path,String className)
            throws ClassNotFoundException {
        Class clazz;
        HashMap<String,ByteArrayOutputStream> hashMap = null;

        try {
            hashMap = ComponentLoader.unzipJar(path);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        ClassLoader parentClassLoader = ComponentLoader.class.getClassLoader();
        ComponentLoader componentLoader = new ComponentLoader(parentClassLoader);
        clazz = componentLoader.loadClass(className,hashMap);
        return clazz;
    }
}