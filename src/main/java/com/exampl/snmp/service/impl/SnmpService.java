package com.exampl.snmp.service.impl;

import com.exampl.snmp.dto.SnmpRequest;
import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.dto.TrapMessage;

import java.util.List;

public interface SnmpService {
    SnmpResponse get(SnmpRequest request);
    SnmpResponse set(SnmpRequest request);
    SnmpResponse getNext(SnmpRequest request);
    SnmpResponse walk(SnmpRequest request);
    SnmpResponse getBulk(SnmpRequest request);
    SnmpResponse trap(SnmpRequest request);

    SnmpResponse inform(SnmpRequest request);

    SnmpResponse receiveTrap();

    List<TrapMessage> getReceivedTraps();


}
