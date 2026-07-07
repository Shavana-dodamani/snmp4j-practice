package com.exampl.snmp.config;

public final class SnmpConfig {

    private SnmpConfig() {
    }

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 161;
    public static final String COMMUNITY = "public";

    public static final long TIMEOUT = 5000;
    public static final int RETRIES = 2;

}