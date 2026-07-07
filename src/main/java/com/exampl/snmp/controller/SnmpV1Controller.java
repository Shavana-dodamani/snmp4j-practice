package com.exampl.snmp.controller;


import com.exampl.snmp.dto.SnmpRequest;
import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.service.impl.SnmpV1Service;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/snmp/v1")
public class SnmpV1Controller {


    private final SnmpV1Service service;


    public SnmpV1Controller(
            SnmpV1Service service){

        this.service=service;
    }



    @PostMapping("/get")
    public SnmpResponse get(
            @RequestBody SnmpRequest request){

        return service.get(request);
    }



    @PostMapping("/getnext")
    public SnmpResponse getNext(
            @RequestBody SnmpRequest request){

        return service.getNext(request);
    }



    @PostMapping("/set")
    public SnmpResponse set(
            @RequestBody SnmpRequest request){

        return service.set(request);
    }
    @PostMapping("/walk")
    public SnmpResponse walk(
            @RequestBody SnmpRequest request){

        return service.walk(request);
    }

    @PostMapping("/trap")
    public SnmpResponse trap(
            @RequestBody SnmpRequest request){

        return service.trap(request);
    }
}