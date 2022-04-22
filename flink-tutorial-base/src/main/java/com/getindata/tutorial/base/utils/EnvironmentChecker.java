package com.getindata.tutorial.base.utils;

import java.util.Objects;

public class EnvironmentChecker {

    public static boolean isRunningFromIntellij() {
        final String protocol = EnvironmentChecker.class.getResource("EnvironmentChecker.class").getProtocol();
        return Objects.equals(protocol, "file");
    }

}
