package com.exampl.snmp.service.impl;

import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.dto.SnmpV3Request;
import com.exampl.snmp.util.SnmpV3Util;
import org.springframework.stereotype.Service;

@Service
public class SnmpV3ServiceImpl implements SnmpV3Service {

    @Override
    public SnmpResponse get(SnmpV3Request request) {

        String result = SnmpV3Util.snmpGet(request);

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V3 GET successful")
                .build();
    }

    @Override
    public SnmpResponse getNext(SnmpV3Request request) {

        String result = SnmpV3Util.snmpGetNext(request);

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V3 GETNEXT successful")
                .build();
    }

    @Override
    public SnmpResponse getBulk(SnmpV3Request request) {

        String result = SnmpV3Util.snmpGetBulk(request);

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V3 GETBULK successful")
                .build();
    }

    @Override
    public SnmpResponse walk(SnmpV3Request request) {

        String result = SnmpV3Util.snmpWalk(request);

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V3 WALK successful")
                .build();
    }

    @Override
    public SnmpResponse set(SnmpV3Request request) {

        String result = SnmpV3Util.snmpSet(request);

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V3 SET successful")
                .build();
    }

    @Override
    public SnmpResponse trap(SnmpV3Request request) {

        String result = SnmpV3Util.snmpTrap(request);

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V3 TRAP sent successfully")
                .build();
    }

    @Override
    public SnmpResponse inform(SnmpV3Request request) {

        String result = SnmpV3Util.snmpInform(request);

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V3 INFORM sent successfully")
                .build();
    }

    @Override
    public SnmpResponse discoverEngineId(SnmpV3Request request) {

        String engineId =
                SnmpV3Util.discoverEngineId(
                        request.getIp(),
                        request.getPort());

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(null)
                .value(engineId)
                .message("SNMP V3 Engine ID discovered successfully")
                .build();
    }
}

