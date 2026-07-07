package com.exampl.snmp.util;

import com.exampl.snmp.dto.TrapMessage;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationUtil {
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
                    SnmpConstants.version2c
            );


            target.setTimeout(3000);
            target.setRetries(1);



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
                    SnmpConstants.version2c
            );


            target.setAddress(
                    GenericAddress.parse(
                            "udp:" + ip + "/" + port
                    )
            );


            target.setTimeout(5000);
            target.setRetries(2);



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
                    SnmpConstants.version2c
            );


            target.setTimeout(3000);
            target.setRetries(1);



            PDU pdu = new PDU();


            pdu.add(
                    new VariableBinding(
                            new OID(oidValue)
                    )
            );


            pdu.setType(PDU.GETNEXT);
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
    public static String snmpWalk(String ip,
                            int port,
                            String community,
                            String rootOid) {

        StringBuilder builder = new StringBuilder();

        try {

            TransportMapping<UdpAddress> transport =
                    new DefaultUdpTransportMapping();

            Snmp snmp = new Snmp(transport);
            transport.listen();

            CommunityTarget target = new CommunityTarget();

            target.setCommunity(new OctetString(community));
            target.setVersion(SnmpConstants.version2c);
            target.setAddress(GenericAddress.parse("udp:" + ip + "/" + port));
            target.setRetries(2);
            target.setTimeout(5000);

            OID currentOid = new OID(rootOid);

            while (true) {

                PDU pdu = new PDU();
                pdu.add(new VariableBinding(currentOid));
                pdu.setType(PDU.GETNEXT);

                ResponseEvent response = snmp.send(pdu, target);

                if (response == null || response.getResponse() == null) {
                    break;
                }

                VariableBinding vb =
                        response.getResponse()
                                .getVariableBindings()
                                .get(0);

                OID nextOid = vb.getOid();

                if (!nextOid.startsWith(new OID(rootOid))) {
                    break;
                }

                builder.append(vb.toString())
                        .append("\n");

                currentOid = nextOid;
            }

            snmp.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
    public static String snmpGetBulk(String ip,
                               int port,
                               String community,
                               String oid) {

        StringBuilder result = new StringBuilder();

        try {

            TransportMapping<UdpAddress> transport =
                    new DefaultUdpTransportMapping();

            Snmp snmp = new Snmp(transport);
            transport.listen();

            CommunityTarget<UdpAddress> target = new CommunityTarget<>();

            target.setCommunity(new OctetString(community));
            target.setVersion(SnmpConstants.version2c);
            target.setAddress(new UdpAddress(ip + "/" + port));
            target.setRetries(2);
            target.setTimeout(5000);

            PDU pdu = new PDU();

            pdu.add(new VariableBinding(new OID(oid)));

            // GETBULK
            pdu.setType(PDU.GETBULK);

            // Number of scalar OIDs
            pdu.setNonRepeaters(0);

            // Number of rows/entries to fetch
            pdu.setMaxRepetitions(10);

            ResponseEvent<?> response = snmp.send(pdu, target);

            if (response != null && response.getResponse() != null) {

                PDU responsePdu = response.getResponse();

                for (VariableBinding vb : responsePdu.getVariableBindings()) {

                    result.append(vb.toString())
                            .append("\n");
                }

            } else {

                result.append("No Response");

            }

            snmp.close();

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return result.toString();
    }
    private static final List<TrapMessage> receivedTraps = new ArrayList<>();
    public static String snmpTrap(
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

            CommunityTarget<UdpAddress> target =
                    new CommunityTarget<>();

            target.setCommunity(new OctetString(community));
            target.setVersion(SnmpConstants.version2c);
            target.setAddress(new UdpAddress(ip + "/" + port));
            target.setRetries(2);
            target.setTimeout(5000);

            PDU pdu = new PDU();

            pdu.setType(PDU.TRAP);

            pdu.add(new VariableBinding(
                    new OID(oid),
                    new OctetString(value)));

            snmp.send(pdu, target);

            snmp.close();

            return "Trap Sent Successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public static String snmpInform(
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

            CommunityTarget<UdpAddress> target =
                    new CommunityTarget<>();

            target.setCommunity(new OctetString(community));
            target.setVersion(SnmpConstants.version2c);
            target.setAddress(new UdpAddress(ip + "/" + port));
            target.setRetries(2);
            target.setTimeout(5000);

            PDU pdu = new PDU();

            pdu.setType(PDU.INFORM);

            pdu.add(new VariableBinding(
                    new OID(oid),
                    new OctetString(value)));

            ResponseEvent<?> response =
                    snmp.send(pdu, target);

            snmp.close();

            if (response != null &&
                    response.getResponse() != null) {

                return "Inform Acknowledged";
            }

            return "No Acknowledgement";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public static String receiveTrap() {

        try {

            TransportMapping<UdpAddress> transport =
                    new DefaultUdpTransportMapping(
                            new UdpAddress("0.0.0.0/162"));

            Snmp snmp = new Snmp(transport);

            snmp.addCommandResponder(new CommandResponder() {

                @Override
                public void processPdu(CommandResponderEvent event) {

                    if (event == null ||
                            event.getPDU() == null) {
                        return;
                    }

                    String sourceIp =
                            event.getPeerAddress().toString();

                    for (VariableBinding vb :
                            event.getPDU().getVariableBindings()) {

                        TrapMessage trap =
                                new TrapMessage(
                                        sourceIp,
                                        vb.getOid().toString(),
                                        vb.getVariable().toString(),
                                        LocalDateTime.now().toString());

                        receivedTraps.add(trap);

                        System.out.println(trap);
                    }
                }
            });

            transport.listen();

            return "Trap Listener Started On Port 162";

        } catch (Exception e) {

            e.printStackTrace();
            return e.getMessage();
        }
    }
    public static List<TrapMessage> getReceivedTraps() {
        return receivedTraps;
    }
}
