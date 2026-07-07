package com.exampl.snmp.util;


import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.*;

import static org.junit.jupiter.api.Assertions.*;


class SnmpV1UtilTest {



    @Test
    void testSnmpGetSuccess() throws Exception {


        Snmp mockSnmp =
                Mockito.mock(Snmp.class);


        ResponseEvent responseEvent =
                Mockito.mock(ResponseEvent.class);


        PDU responsePdu =
                new PDU();


        responsePdu.add(
                new VariableBinding(
                        new OID("1.3.6.1.2.1.1.5.0"),
                        new OctetString("Device")
                )
        );


        Mockito.when(
                responseEvent.getResponse()
        ).thenReturn(responsePdu);



        Mockito.when(
                mockSnmp.send(
                        Mockito.any(PDU.class),
                        Mockito.any(Target.class)
                )
        ).thenReturn(responseEvent);



        try(MockedConstruction<Snmp> mocked =
                    Mockito.mockConstruction(
                            Snmp.class,
                            (mock,context)->{

                                Mockito.when(
                                        mock.send(
                                                Mockito.any(),
                                                Mockito.any()
                                        )
                                ).thenReturn(responseEvent);

                            })) {



            String result =
                    SnmpV1Util.snmpGet(
                            "127.0.0.1",
                            161,
                            "public",
                            "1.3.6.1.2.1.1.5.0"
                    );


            assertTrue(
                    result.contains("Device")
            );

        }

    }



    @Test
    void testSnmpGetNoResponse(){


        String result =
                SnmpV1Util.snmpGet(
                        "127.0.0.1",
                        161,
                        "public",
                        "1.3.6.1.2.1.1.5.0"
                );


        assertNotNull(result);

    }





    @Test
    void testSnmpSetNoResponse(){


        String result =
                SnmpV1Util.snmpSet(
                        "127.0.0.1",
                        161,
                        "public",
                        "1.3.6.1.2.1.1.5.0",
                        "Test"
                );


        assertNotNull(result);

    }





    @Test
    void testSnmpGetNextNoResponse(){


        String result =
                SnmpV1Util.snmpGetNext(
                        "127.0.0.1",
                        161,
                        "public",
                        "1.3.6.1.2.1.1.5.0"
                );


        assertNotNull(result);

    }





    @Test
    void testSnmpWalkNoResponse(){


        String result =
                SnmpV1Util.snmpWalk(
                        "127.0.0.1",
                        161,
                        "public",
                        "1.3.6.1.2.1.1"
                );


        assertNotNull(result);

    }





    @Test
    void testSnmpTrap(){


        String result =
                SnmpV1Util.snmpTrap(
                        "127.0.0.1",
                        162,
                        "public",
                        "1.3.6.1.6.3.1.1.5.3",
                        "Interface Down"
                );


        assertNotNull(result);

    }

}