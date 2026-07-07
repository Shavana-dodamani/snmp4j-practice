package com.exampl.snmp.service;

import com.exampl.snmp.dto.SnmpRequest;
import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.dto.TrapMessage;
import com.exampl.snmp.service.impl.SnmpServiceImpl;
import com.exampl.snmp.util.ValidationUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SnmpServiceImplTest {

    private final SnmpServiceImpl service = new SnmpServiceImpl();

    private SnmpRequest request() {
        SnmpRequest req = new SnmpRequest();
        req.setIp("127.0.0.1");
        req.setPort(161);
        req.setCommunity("public");
        req.setOid("1.3.6.1.2.1.1.5.0");
        req.setValue("VCTI_DEVICE");
        return req;
    }

    @Test
    void testGet() {

        try (MockedStatic<ValidationUtil> util = Mockito.mockStatic(ValidationUtil.class)) {

            util.when(() ->
                            ValidationUtil.snmpGet(
                                    "127.0.0.1",
                                    161,
                                    "public",
                                    "1.3.6.1.2.1.1.5.0"))
                    .thenReturn("Device");

            SnmpResponse response = service.get(request());

            assertEquals("SUCCESS", response.getStatus());
            assertEquals("Device", response.getValue());
            assertEquals("SNMP GET successful", response.getMessage());
        }
    }

    @Test
    void testSet() {

        try (MockedStatic<ValidationUtil> util = Mockito.mockStatic(ValidationUtil.class)) {

            util.when(() ->
                            ValidationUtil.snmpSet(
                                    "127.0.0.1",
                                    161,
                                    "public",
                                    "1.3.6.1.2.1.1.5.0",
                                    "VCTI_DEVICE"))
                    .thenReturn("SET OK");

            SnmpResponse response = service.set(request());

            assertEquals("SUCCESS", response.getStatus());
            assertEquals("SET OK", response.getValue());
        }
    }

    @Test
    void testGetNext() {

        try (MockedStatic<ValidationUtil> util = Mockito.mockStatic(ValidationUtil.class)) {

            util.when(() ->
                            ValidationUtil.snmpGetNext(
                                    "127.0.0.1",
                                    161,
                                    "public",
                                    "1.3.6.1.2.1.1.5.0"))
                    .thenReturn("Next OID");

            SnmpResponse response = service.getNext(request());

            assertEquals("SUCCESS", response.getStatus());
            assertEquals("Next OID", response.getValue());
        }
    }

    @Test
    void testWalk() {

        try (MockedStatic<ValidationUtil> util = Mockito.mockStatic(ValidationUtil.class)) {

            util.when(() ->
                            ValidationUtil.snmpWalk(
                                    "127.0.0.1",
                                    161,
                                    "public",
                                    "1.3.6.1.2.1.1"))
                    .thenReturn("Walk Result");

            SnmpRequest req = request();
            req.setOid("1.3.6.1.2.1.1");

            SnmpResponse response = service.walk(req);

            assertEquals("SUCCESS", response.getStatus());
            assertEquals("Walk Result", response.getValue());
        }
    }

    @Test
    void testGetBulk() {

        try (MockedStatic<ValidationUtil> util = Mockito.mockStatic(ValidationUtil.class)) {

            util.when(() ->
                            ValidationUtil.snmpGetBulk(
                                    "127.0.0.1",
                                    161,
                                    "public",
                                    "1.3.6.1.2.1.1"))
                    .thenReturn("Bulk Result");

            SnmpRequest req = request();
            req.setOid("1.3.6.1.2.1.1");

            SnmpResponse response = service.getBulk(req);

            assertEquals("SUCCESS", response.getStatus());
            assertEquals("Bulk Result", response.getValue());
        }
    }

    @Test
    void testTrap() {

        try (MockedStatic<ValidationUtil> util = Mockito.mockStatic(ValidationUtil.class)) {

            util.when(() ->
                            ValidationUtil.snmpTrap(
                                    "127.0.0.1",
                                    161,
                                    "public",
                                    "1.3.6.1.2.1.1.5.0",
                                    "VCTI_DEVICE"))
                    .thenReturn("Trap Sent");

            SnmpResponse response = service.trap(request());

            assertEquals("SUCCESS", response.getStatus());
            assertEquals("Trap Sent", response.getValue());
        }
    }

    @Test
    void testInform() {

        try (MockedStatic<ValidationUtil> util = Mockito.mockStatic(ValidationUtil.class)) {

            util.when(() ->
                            ValidationUtil.snmpInform(
                                    "127.0.0.1",
                                    161,
                                    "public",
                                    "1.3.6.1.2.1.1.5.0",
                                    "VCTI_DEVICE"))
                    .thenReturn("Inform Sent");

            SnmpResponse response = service.inform(request());

            assertEquals("SUCCESS", response.getStatus());
            assertEquals("Inform Sent", response.getValue());
        }
    }

    @Test
    void testReceiveTrap() {

        try (MockedStatic<ValidationUtil> util = Mockito.mockStatic(ValidationUtil.class)) {

            util.when(ValidationUtil::receiveTrap)
                    .thenReturn("Trap Received");

            SnmpResponse response = service.receiveTrap();

            assertEquals("SUCCESS", response.getStatus());
            assertEquals("Trap Received", response.getMessage());
        }
    }

    @Test
    void testGetReceivedTraps() {

        TrapMessage trap = new TrapMessage();

        try (MockedStatic<ValidationUtil> util = Mockito.mockStatic(ValidationUtil.class)) {

            util.when(ValidationUtil::getReceivedTraps)
                    .thenReturn(List.of(trap));

            List<TrapMessage> traps = service.getReceivedTraps();

            assertEquals(1, traps.size());
        }
    }
}