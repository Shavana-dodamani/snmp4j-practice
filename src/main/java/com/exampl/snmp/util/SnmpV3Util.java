package com.exampl.snmp.util;

import com.exampl.snmp.dto.SnmpV3Request;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpV3Util {

    private static Snmp createSnmp(SnmpV3Request request)
            throws Exception {

        TransportMapping<UdpAddress> transport =
                new DefaultUdpTransportMapping();

        Snmp snmp = new Snmp(transport);

        USM usm = new USM(
                SecurityProtocols.getInstance(),
                new OctetString(
                        MPv3.createLocalEngineID()),
                0
        );

        SecurityModels.getInstance().addSecurityModel(usm);

        OID authProtocol =
                "SHA".equalsIgnoreCase(
                        request.getAuthProtocol())
                        ? AuthSHA.ID
                        : AuthMD5.ID;

        OID privProtocol =
                "AES".equalsIgnoreCase(
                        request.getPrivProtocol())
                        ? PrivAES128.ID
                        : PrivDES.ID;

        UsmUser user =
                new UsmUser(
                        new OctetString(
                                request.getUsername()),
                        authProtocol,
                        new OctetString(
                                request.getAuthPassword()),
                        privProtocol,
                        new OctetString(
                                request.getPrivPassword())
                );

        snmp.getUSM()
                .addUser(
                        new OctetString(
                                request.getUsername()),
                        user
                );

        transport.listen();

        return snmp;
    }

    private static UserTarget createTarget(
            SnmpV3Request request) {

        UserTarget target =
                new UserTarget();

        target.setAddress(
                new UdpAddress(
                        request.getIp()
                                + "/"
                                + request.getPort()));

        target.setVersion(
                SnmpConstants.version3);

        target.setSecurityName(
                new OctetString(
                        request.getUsername()));

        switch (
                request.getSecurityLevel()) {

            case 1:
                target.setSecurityLevel(
                        SecurityLevel.NOAUTH_NOPRIV);
                break;

            case 2:
                target.setSecurityLevel(
                        SecurityLevel.AUTH_NOPRIV);
                break;

            case 3:
                target.setSecurityLevel(
                        SecurityLevel.AUTH_PRIV);
                break;

            default:
                target.setSecurityLevel(
                        SecurityLevel.AUTH_PRIV);
        }

        target.setRetries(2);
        target.setTimeout(5000);

        return target;
    }

    public static String snmpGet(
            SnmpV3Request request) {

        try {

            Snmp snmp = createSnmp(request);

            UserTarget target = createTarget(request);

            System.out.println("Username = " + request.getUsername());
            System.out.println("SecurityLevel = " + request.getSecurityLevel());
            System.out.println("AuthProtocol = " + request.getAuthProtocol());
            System.out.println("PrivProtocol = " + request.getPrivProtocol());

            ScopedPDU pdu = new ScopedPDU();

            pdu.add(
                    new VariableBinding(
                            new OID(request.getOid())
                    )
            );

            pdu.setType(PDU.GET);

            ResponseEvent<?> event =
                    snmp.send(pdu, target);

            System.out.println("Event = " + event);

            if (event != null) {
                System.out.println("Response = " + event.getResponse());
                System.out.println("Error = " + event.getError());
            }

            if (event == null) {
                snmp.close();
                return "Timeout";
            }

            if (event.getResponse() == null) {
                snmp.close();
                return "Null Response";
            }

            String result =
                    event.getResponse()
                            .getVariableBindings()
                            .get(0)
                            .toString();

            snmp.close();

            return result;

        } catch (Exception e) {

            e.printStackTrace();
            return e.getMessage();
        }
    }
    public static String snmpGetNext(
            SnmpV3Request request) {

        try {

            Snmp snmp =
                    createSnmp(request);

            UserTarget target =
                    createTarget(request);

            ScopedPDU pdu =
                    new ScopedPDU();

            pdu.add(
                    new VariableBinding(
                            new OID(
                                    request.getOid()
                            )
                    )
            );

            pdu.setType(PDU.GETNEXT);

            ResponseEvent<?> event =
                    snmp.send(
                            pdu,
                            target);

            snmp.close();

            if(event != null &&
                    event.getResponse()!=null){

                return event.getResponse()
                        .getVariableBindings()
                        .get(0)
                        .toString();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "No Response";
    }
    public static String snmpWalk(
            SnmpV3Request request){

        StringBuilder result =
                new StringBuilder();

        try {

            Snmp snmp =
                    createSnmp(request);

            UserTarget target =
                    createTarget(request);

            OID rootOid =
                    new OID(
                            request.getOid());

            OID currentOid =
                    rootOid;

            while(true){

                ScopedPDU pdu =
                        new ScopedPDU();

                pdu.add(
                        new VariableBinding(
                                currentOid));

                pdu.setType(
                        PDU.GETNEXT);

                ResponseEvent<?> event =
                        snmp.send(
                                pdu,
                                target);

                if(event == null
                        || event.getResponse()==null){

                    break;
                }

                VariableBinding vb =
                        event.getResponse()
                                .getVariableBindings()
                                .get(0);

                if(!vb.getOid()
                        .startsWith(rootOid)){

                    break;
                }

                result.append(vb)
                        .append("\n");

                currentOid =
                        vb.getOid();
            }

            snmp.close();

        } catch(Exception e){

            e.printStackTrace();
        }

        return result.toString();
    }
    public static String snmpSet(
            SnmpV3Request request) {

        try {

            Snmp snmp = createSnmp(request);

            UserTarget target = createTarget(request);

            ScopedPDU pdu = new ScopedPDU();

            pdu.add(
                    new VariableBinding(
                            new OID(request.getOid()),
                            new OctetString(request.getValue())
                    )
            );

            pdu.setType(PDU.SET);

            ResponseEvent<?> event =
                    snmp.send(pdu, target);

            snmp.close();

            if (event != null &&
                    event.getResponse() != null) {

                return event.getResponse()
                        .getVariableBindings()
                        .get(0)
                        .toString();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "SET Failed";
    }

    public static String snmpGetBulk(
            SnmpV3Request request) {

        StringBuilder result = new StringBuilder();

        try {

            Snmp snmp = createSnmp(request);

            UserTarget target = createTarget(request);

            ScopedPDU pdu = new ScopedPDU();

            pdu.add(
                    new VariableBinding(
                            new OID(request.getOid())
                    )
            );

            pdu.setType(PDU.GETBULK);

            pdu.setNonRepeaters(
                    request.getNonRepeaters());

            pdu.setMaxRepetitions(
                    request.getMaxRepetitions());

            ResponseEvent<?> event =
                    snmp.send(pdu, target);

            if (event != null &&
                    event.getResponse() != null) {

                for (VariableBinding vb :
                        event.getResponse().getVariableBindings()) {

                    result.append(vb)
                            .append("\n");
                }
            }

            snmp.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result.toString();
    }
    public static String snmpTrap(
            SnmpV3Request request) {

        try {

            Snmp snmp = createSnmp(request);

            UserTarget target = createTarget(request);

            ScopedPDU pdu = new ScopedPDU();

            pdu.setType(PDU.TRAP);

            pdu.add(
                    new VariableBinding(
                            new OID(request.getOid()),
                            new OctetString(
                                    request.getValue())
                    )
            );

            snmp.send(pdu, target);

            snmp.close();

            return "SNMP V3 Trap Sent Successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return e.getMessage();
        }
    }
    public static String snmpInform(
            SnmpV3Request request) {

        try {

            Snmp snmp = createSnmp(request);

            UserTarget target = createTarget(request);

            ScopedPDU pdu = new ScopedPDU();

            pdu.setType(PDU.INFORM);

            pdu.add(
                    new VariableBinding(
                            new OID(request.getOid()),
                            new OctetString(
                                    request.getValue())
                    )
            );

            ResponseEvent<?> event =
                    snmp.send(pdu, target);

            snmp.close();

            if (event != null &&
                    event.getResponse() != null) {

                return "INFORM Acknowledged";
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "INFORM Failed";
    }
    public static String discoverEngineId(
            String ip,
            int port) {

        try {

            TransportMapping<UdpAddress> transport =
                    new DefaultUdpTransportMapping();

            Snmp snmp =
                    new Snmp(transport);

            USM usm =
                    new USM(
                            SecurityProtocols.getInstance(),
                            new OctetString(
                                    MPv3.createLocalEngineID()),
                            0);

            SecurityModels.getInstance()
                    .addSecurityModel(usm);

            transport.listen();

            UserTarget<UdpAddress> target =
                    new UserTarget<>();

            target.setAddress(
                    new UdpAddress(
                            ip + "/" + port));

            target.setVersion(
                    SnmpConstants.version3);

            target.setSecurityLevel(
                    SecurityLevel.NOAUTH_NOPRIV);

            target.setSecurityName(
                    new OctetString(""));

            ScopedPDU pdu =
                    new ScopedPDU();

            pdu.setType(PDU.GET);

            pdu.add(
                    new VariableBinding(
                            SnmpConstants.sysDescr));

            ResponseEvent<?> event =
                    snmp.send(
                            pdu,
                            target);

            if (event != null &&
                    event.getResponse() != null) {

                OctetString engineId =
                        snmp.getUSM()
                                .getLocalEngineID();

                snmp.close();

                return engineId.toHexString();
            }

            snmp.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "Engine ID Discovery Failed";
    }
}