package configuration;

import com.google.common.eventbus.EventBus;

public enum Configuration {
    instance;

    public boolean isDebug = true;

    public String userDirectory = System.getProperty("user.dir");
    public String fileSeparator = System.getProperty("file.separator");

    public String apMod00ComponentPackage = "apmod00";
    public String apMod00ComponentType = "02";
    public String apMod00ComponentName = "Component.jar";
    public String apMod00ComponentJavaArchivePath = userDirectory + fileSeparator + apMod00ComponentPackage + fileSeparator + "exchangeComponent" + apMod00ComponentType + fileSeparator + apMod00ComponentName;

    public EventBus eventBus = new EventBus("Airport");
}