package com.exampl.snmp.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Test
    void testSnmpGet() {

        String response = ValidationUtil.snmpGet(
                "127.0.0.1",
                161,
                "public",
                "1.3.6.1.2.1.1.5.0");

        assertNotNull(response);
        assertTrue(response.contains("1.3.6.1.2.1.1.5.0"));
    }

    @Test
    void testSnmpSet() {

        String response = ValidationUtil.snmpSet(
                "127.0.0.1",
                161,
                "public",
                "1.3.6.1.2.1.1.5.0",
                "VCTI");

        assertNotNull(response);
    }

    @Test
    void testSnmpGetNext() {

        String response = ValidationUtil.snmpGetNext(
                "127.0.0.1",
                161,
                "public",
                "1.3.6.1.2.1.1.1");

        assertNotNull(response);
    }

    @Test
    void testWalk() {

        String response = ValidationUtil.snmpWalk(
                "127.0.0.1",
                161,
                "public",
                "1.3.6.1.2.1.1");

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    void testBulk() {

        String response = ValidationUtil.snmpGetBulk(
                "127.0.0.1",
                161,
                "public",
                "1.3.6.1.2.1.1");

        assertNotNull(response);
    }

    @Test
    void testTrap() {

        String response = ValidationUtil.snmpTrap(
                "127.0.0.1",
                162,
                "public",
                "1.3.6.1.2.1.1.5.0",
                "Trap Test");

        assertEquals("Trap Sent Successfully", response);
    }

    @Test
    void testInform() {

        String response = ValidationUtil.snmpInform(
                "127.0.0.1",
                161,
                "public",
                "1.3.6.1.2.1.1.5.0",
                "Inform Test");

        assertNotNull(response);
    }

    @Test
    void testReceiveTrap() {

        String response = ValidationUtil.receiveTrap();

        assertTrue(response.contains("Trap Listener Started"));
    }

    @Test
    void testReceivedTraps() {

        assertNotNull(ValidationUtil.getReceivedTraps());
    }
}