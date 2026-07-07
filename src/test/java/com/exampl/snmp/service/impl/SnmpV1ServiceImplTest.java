package com.exampl.snmp.service.impl;


import com.exampl.snmp.dto.SnmpRequest;
import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.util.SnmpV1Util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.*;


class SnmpV1ServiceImplTest {


    private SnmpV1ServiceImpl service;


    private SnmpRequest request;



    @BeforeEach
    void setup(){


        service = new SnmpV1ServiceImpl();


        request = new SnmpRequest();

        request.setIp("127.0.0.1");
        request.setPort(161);
        request.setCommunity("public");
        request.setOid("1.3.6.1.2.1.1.5.0");
        request.setValue("TestDevice");

    }



    @Test
    void testGet() {


        try(MockedStatic<SnmpV1Util> mocked =
                    Mockito.mockStatic(SnmpV1Util.class)){


            mocked.when(() ->
                    SnmpV1Util.snmpGet(
                            "127.0.0.1",
                            161,
                            "public",
                            "1.3.6.1.2.1.1.5.0"
                    )
            ).thenReturn("Device");


            SnmpResponse response =
                    service.get(request);



            assertEquals(
                    "SUCCESS",
                    response.getStatus()
            );


            assertEquals(
                    "Device",
                    response.getValue()
            );


            assertEquals(
                    "SNMP V1 GET successful",
                    response.getMessage()
            );

        }

    }




    @Test
    void testGetNext(){


        try(MockedStatic<SnmpV1Util> mocked =
                    Mockito.mockStatic(SnmpV1Util.class)){



            mocked.when(() ->
                    SnmpV1Util.snmpGetNext(
                            "127.0.0.1",
                            161,
                            "public",
                            "1.3.6.1.2.1.1.5.0"
                    )
            ).thenReturn("Next Value");



            SnmpResponse response =
                    service.getNext(request);



            assertEquals(
                    "Next Value",
                    response.getValue()
            );


            assertEquals(
                    "SNMP V1 GETNEXT successful",
                    response.getMessage()
            );

        }

    }





    @Test
    void testSet(){


        try(MockedStatic<SnmpV1Util> mocked =
                    Mockito.mockStatic(SnmpV1Util.class)){



            mocked.when(() ->
                    SnmpV1Util.snmpSet(
                            "127.0.0.1",
                            161,
                            "public",
                            "1.3.6.1.2.1.1.5.0",
                            "TestDevice"
                    )
            ).thenReturn("Updated");



            SnmpResponse response =
                    service.set(request);



            assertEquals(
                    "Updated",
                    response.getValue()
            );


            assertEquals(
                    "SNMP V1 SET successful",
                    response.getMessage()
            );

        }

    }






    @Test
    void testTrap(){


        try(MockedStatic<SnmpV1Util> mocked =
                    Mockito.mockStatic(SnmpV1Util.class)){


            mocked.when(() ->
                    SnmpV1Util.snmpTrap(
                            "127.0.0.1",
                            161,
                            "public",
                            "1.3.6.1.2.1.1.5.0",
                            "TestDevice"
                    )
            ).thenReturn("Trap Sent");



            SnmpResponse response =
                    service.trap(request);



            assertEquals(
                    "Trap Sent",
                    response.getValue()
            );


            assertEquals(
                    "SNMP V1 TRAP sent successfully",
                    response.getMessage()
            );


        }

    }







    @Test
    void testWalk(){


        try(MockedStatic<SnmpV1Util> mocked =
                    Mockito.mockStatic(SnmpV1Util.class)){



            mocked.when(() ->
                    SnmpV1Util.snmpWalk(
                            "127.0.0.1",
                            161,
                            "public",
                            "1.3.6.1.2.1.1.5.0"
                    )
            ).thenReturn("OID values");



            SnmpResponse response =
                    service.walk(request);



            assertEquals(
                    "OID values",
                    response.getValue()
            );


            assertEquals(
                    "SNMP V1 WALK successful",
                    response.getMessage()
            );


        }

    }

}