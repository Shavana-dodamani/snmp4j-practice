package com.exampl.snmp.controller;

import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.dto.SnmpV3Request;
import com.exampl.snmp.service.impl.SnmpV3Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/snmp/v3")
public class SnmpV3Controller {

    private final SnmpV3Service service;

    public SnmpV3Controller(SnmpV3Service service) {
        this.service = service;
    }

    @PostMapping("/get")
    public SnmpResponse get(
            @RequestBody SnmpV3Request request) {
        return service.get(request);
    }

    @PostMapping("/getnext")
    public SnmpResponse getNext(
            @RequestBody SnmpV3Request request) {
        return service.getNext(request);
    }

    @PostMapping("/getbulk")
    public SnmpResponse getBulk(
            @RequestBody SnmpV3Request request) {
        return service.getBulk(request);
    }

    @PostMapping("/walk")
    public SnmpResponse walk(
            @RequestBody SnmpV3Request request) {
        return service.walk(request);
    }

    @PostMapping("/set")
    public SnmpResponse set(
            @RequestBody SnmpV3Request request) {
        return service.set(request);
    }

    @PostMapping("/trap")
    public SnmpResponse trap(
            @RequestBody SnmpV3Request request) {
        return service.trap(request);
    }

    @PostMapping("/inform")
    public SnmpResponse inform(
            @RequestBody SnmpV3Request request) {
        return service.inform(request);
    }

    @PostMapping("/discover-engine-id")
    public SnmpResponse discoverEngineId(
            @RequestBody SnmpV3Request request) {
        return service.discoverEngineId(request);
    }


}