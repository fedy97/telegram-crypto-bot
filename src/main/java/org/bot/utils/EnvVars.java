package org.bot.utils;

public class EnvVars {

    private EnvVars() {}
    public static String getEnvVar(String name, String defaultValue) {
        String envVar = System.getenv(name);
        if (envVar != null) return envVar;
        return defaultValue;
    }

}
