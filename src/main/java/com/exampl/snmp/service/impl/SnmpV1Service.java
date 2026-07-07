package com.exampl.snmp.service.impl;

import com.exampl.snmp.dto.SnmpRequest;
import com.exampl.snmp.dto.SnmpResponse;

public interface SnmpV1Service {

    SnmpResponse get(SnmpRequest request);


    SnmpResponse getNext(SnmpRequest request);


    SnmpResponse set(SnmpRequest request);


    SnmpResponse walk(SnmpRequest request);


    SnmpResponse trap(SnmpRequest request);

}