package org.bot.utils;

public class EnvVars {

    private EnvVars() {}
    public static String getEnvVar(String name) {
        return System.getenv(name);
    }

}
