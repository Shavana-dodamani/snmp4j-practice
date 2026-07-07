package com.exampl.snmp.service.impl;

import com.exampl.snmp.dto.SnmpRequest;
import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.dto.TrapMessage;
import com.exampl.snmp.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnmpServiceImpl implements SnmpService {
private  ValidationUtil validationUtil;

    @Override
    public SnmpResponse get(SnmpRequest request) {

        String result = validationUtil.snmpGet(
                request.getIp(),
                request.getPort(),
                request.getCommunity(),
                request.getOid()
        );


        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP GET successful")
                .build();
    }

    public SnmpResponse set(SnmpRequest request) {

        String result = validationUtil.snmpSet(
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
                .message("SNMP SET successful")
                .build();
    }

    @Override
    public SnmpResponse getNext(SnmpRequest request) {

        String result = ValidationUtil.snmpGetNext(
                request.getIp(),
                request.getPort(),
                request.getCommunity(),
                request.getOid());

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP GETNEXT successful")
                .build();
    }

    @Override
    public SnmpResponse walk(SnmpRequest request) {

        String result = ValidationUtil.snmpWalk(
                request.getIp(),
                request.getPort(),
                request.getCommunity(),
                request.getOid());

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP WALK successful")
                .build();
    }
    @Override
    public SnmpResponse getBulk(SnmpRequest request) {

        String result = ValidationUtil.snmpGetBulk(
                request.getIp(),
                request.getPort(),
                request.getCommunity(),
                request.getOid());

        return SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value(result)
                .message("SNMP GETBULK successful")
                .build();
    }
    @Override
    public SnmpResponse trap(SnmpRequest request) {

        String result = ValidationUtil.snmpTrap(
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
                .message("SNMP TRAP sent successfully")
                .build();
    }

    @Override
    public SnmpResponse inform(SnmpRequest request) {

        String result = ValidationUtil.snmpInform(
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
                .message("SNMP INFORM sent successfully")
                .build();
    }

    @Override
    public SnmpResponse receiveTrap() {

        String result = ValidationUtil.receiveTrap();

        return SnmpResponse.builder()
                .status("SUCCESS")
                .message(result)
                .build();
    }

    @Override
    public List<TrapMessage> getReceivedTraps() {
        return ValidationUtil.getReceivedTraps();
    }

}