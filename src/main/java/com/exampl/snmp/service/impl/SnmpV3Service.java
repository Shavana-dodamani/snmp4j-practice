package com.exampl.snmp.service.impl;

import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.dto.SnmpV3Request;

public interface SnmpV3Service {

    SnmpResponse get(SnmpV3Request request);

    SnmpResponse getNext(SnmpV3Request request);

    SnmpResponse getBulk(SnmpV3Request request);

    SnmpResponse walk(SnmpV3Request request);

    SnmpResponse set(SnmpV3Request request);

    SnmpResponse trap(SnmpV3Request request);

    SnmpResponse inform(SnmpV3Request request);

    SnmpResponse discoverEngineId(SnmpV3Request request);
}