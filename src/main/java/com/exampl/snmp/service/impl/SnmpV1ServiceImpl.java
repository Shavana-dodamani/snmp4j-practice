package com.exampl.snmp.service.impl;

import com.exampl.snmp.dto.SnmpRequest;
import com.exampl.snmp.dto.SnmpResponse;

import com.exampl.snmp.util.SnmpV1Util;
import org.springframework.stereotype.Service;


@Service
public class SnmpV1ServiceImpl implements SnmpV1Service {


    @Override
    public SnmpResponse get(SnmpRequest request) {


        String result =
                SnmpV1Util.snmpGet(
                        request.getIp(),
                        request.getPort(),
                        request.getCommunity(),
                        request.getOid()
                );


        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V1 GET successful")
                .build();

    }


    @Override
    public SnmpResponse getNext(SnmpRequest request) {


        String result =
                SnmpV1Util.snmpGetNext(
                        request.getIp(),
                        request.getPort(),
                        request.getCommunity(),
                        request.getOid()
                );


        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V1 GETNEXT successful")
                .build();

    }


    @Override
    public SnmpResponse set(SnmpRequest request) {


        String result =
                SnmpV1Util.snmpSet(
                        request.getIp(),
                        request.getPort(),
                        request.getCommunity(),
                        request.getOid(),
                        request.getValue()
                );


        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V1 SET successful")
                .build();

    }
    @Override
    public SnmpResponse trap(SnmpRequest request) {


        String result =
                SnmpV1Util.snmpTrap(
                        request.getIp(),
                        request.getPort(),
                        request.getCommunity(),
                        request.getOid(),
                        request.getValue()
                );


        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V1 TRAP sent successfully")
                .build();
    }
    @Override
    public SnmpResponse walk(SnmpRequest request) {


        String result =
                SnmpV1Util.snmpWalk(
                        request.getIp(),
                        request.getPort(),
                        request.getCommunity(),
                        request.getOid()
                );


        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP V1 WALK successful")
                .build();
    }
}