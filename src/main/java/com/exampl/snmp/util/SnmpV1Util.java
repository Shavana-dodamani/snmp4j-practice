package com.exampl.snmp.util;

import com.exampl.snmp.config.SnmpConfig;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpV1Util {
    public static String snmpGet(
            String ip,
            int port,
            String community,
            String oidValue) {


        try {

            TransportMapping<UdpAddress> transport =
                    new DefaultUdpTransportMapping();


            Snmp snmp = new Snmp(transport);

            transport.listen();


            CommunityTarget target =
                    new CommunityTarget();


            target.setCommunity(
                    new OctetString(community)
            );


            target.setAddress(
                    new UdpAddress(ip + "/" + port)
            );


            target.setVersion(
                    SnmpConstants.version1
            );


            target.setRetries(SnmpConfig.RETRIES);
            target.setTimeout(SnmpConfig.TIMEOUT);



            PDU pdu = new PDU();


            pdu.add(
                    new VariableBinding(
                            new OID(oidValue)
                    )
            );


            pdu.setType(PDU.GET);



            ResponseEvent response =
                    snmp.send(pdu, target);



            snmp.close();



            if(response != null &&
                    response.getResponse() != null){


                return response
                        .getResponse()
                        .getVariableBindings()
                        .get(0)
                        .toString();

            }


        } catch(Exception e){

            e.printStackTrace();

        }


        return "No Response";
    }
    public static String snmpSet(
            String ip,
            int port,
            String community,
            String oid,
            String value) {


        try {


            TransportMapping<UdpAddress> transport =
                    new DefaultUdpTransportMapping();


            Snmp snmp = new Snmp(transport);

            transport.listen();



            CommunityTarget target =
                    new CommunityTarget();


            target.setCommunity(
                    new OctetString(community)
            );


            target.setVersion(
                    SnmpConstants.version1
            );


            target.setAddress(
                    GenericAddress.parse(
                            "udp:" + ip + "/" + port
                    )
            );


            target.setRetries(SnmpConfig.RETRIES);
            target.setTimeout(SnmpConfig.TIMEOUT);



            PDU pdu = new PDU();


            pdu.add(
                    new VariableBinding(
                            new OID(oid),
                            new OctetString(value)
                    )
            );


            pdu.setType(PDU.SET);



            ResponseEvent response =
                    snmp.send(pdu,target);


            snmp.close();



            if(response != null &&
                    response.getResponse()!=null){

                return response.getResponse()
                        .getVariableBindings()
                        .get(0)
                        .toString();
            }


        }catch(Exception e){

            e.printStackTrace();

        }


        return "SET Failed";
    }
    public static String snmpGetNext(
            String ip,
            int port,
            String community,
            String oidValue){


        try {


            TransportMapping<UdpAddress> transport =
                    new DefaultUdpTransportMapping();


            Snmp snmp =
                    new Snmp(transport);


            transport.listen();



            CommunityTarget<UdpAddress> target =
                    new CommunityTarget<>();


            target.setCommunity(
                    new OctetString(community)
            );


            target.setVersion(
                    SnmpConstants.version1
            );


            target.setAddress(
                    new UdpAddress(ip+"/"+port)
            );


            target.setRetries(2);
            target.setTimeout(5000);



            PDU pdu=new PDU();


            pdu.add(
                    new VariableBinding(
                            new OID(oidValue)
                    )
            );


            pdu.setType(PDU.GETNEXT);



            ResponseEvent<?> response =
                    snmp.send(pdu,target);



            snmp.close();



            if(response!=null &&
                    response.getResponse()!=null){


                return response.getResponse()
                        .getVariableBindings()
                        .get(0)
                        .toString();

            }



        }catch(Exception e){

            e.printStackTrace();

        }


        return "No Response";
    }
    /*
    snmpWalk for v1
     */
    public static String snmpWalk(
            String ip,
            int port,
            String community,
            String rootOid){


        StringBuilder result =
                new StringBuilder();



        try {


            TransportMapping<UdpAddress> transport =
                    new DefaultUdpTransportMapping();


            Snmp snmp =
                    new Snmp(transport);


            transport.listen();



            CommunityTarget<UdpAddress> target =
                    new CommunityTarget<>();


            target.setCommunity(
                    new OctetString(community)
            );


            target.setVersion(
                    SnmpConstants.version1
            );


            target.setAddress(
                    new UdpAddress(ip+"/"+port)
            );


            OID currentOid =
                    new OID(rootOid);



            while(true){


                PDU pdu =
                        new PDU();


                pdu.add(
                        new VariableBinding(currentOid)
                );


                pdu.setType(PDU.GETNEXT);



                ResponseEvent<?> response =
                        snmp.send(pdu,target);



                if(response==null ||
                        response.getResponse()==null){

                    break;
                }



                VariableBinding vb =
                        response.getResponse()
                                .getVariableBindings()
                                .get(0);



                OID nextOid =
                        vb.getOid();



                if(!nextOid.startsWith(
                        new OID(rootOid))){

                    break;
                }



                result.append(vb)
                        .append("\n");



                currentOid=nextOid;

            }



            snmp.close();


        }catch(Exception e){

            e.printStackTrace();

        }


        return result.toString();

    }


    /*
    snp trap for v1
     */
    public static String snmpTrap(
            String ip,
            int port,
            String community,
            String oid,
            String value){


        try {


            TransportMapping<UdpAddress> transport =
                    new DefaultUdpTransportMapping();


            Snmp snmp =
                    new Snmp(transport);


            transport.listen();



            CommunityTarget<UdpAddress> target =
                    new CommunityTarget<>();


            target.setCommunity(
                    new OctetString(community)
            );


            target.setVersion(
                    SnmpConstants.version1
            );


            target.setAddress(
                    new UdpAddress(ip+"/"+port)
            );



            PDUv1 pdu =
                    new PDUv1();



            pdu.setType(PDU.V1TRAP);



            pdu.setEnterprise(
                    new OID(oid)
            );


            pdu.add(
                    new VariableBinding(
                            new OID(oid),
                            new OctetString(value)
                    )
            );



            snmp.send(
                    pdu,
                    target
            );



            snmp.close();



            return "SNMP V1 Trap Sent Successfully";



        }catch(Exception e){

            e.printStackTrace();

            return e.getMessage();

        }

    }


}
