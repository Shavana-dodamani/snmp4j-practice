package com.exampl.snmp.controller;

import com.exampl.snmp.dto.SnmpRequest;
import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.dto.TrapMessage;
import com.exampl.snmp.service.impl.SnmpService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/snmp")
public class SnmpController {


    private final SnmpService snmpService;


    public SnmpController(SnmpService snmpService){
        this.snmpService = snmpService;
    }


    @PostMapping("/get")
    public SnmpResponse get(
            @RequestBody SnmpRequest request){

        return snmpService.get(request);
    }
    @PostMapping("/set")
    public SnmpResponse set(
            @RequestBody SnmpRequest request){

        return snmpService.set(request);
    }
    @PostMapping("/getnext")
    public SnmpResponse getNext(@RequestBody SnmpRequest request) {
        return snmpService.getNext(request);
    }
    @PostMapping("/walk")
    public SnmpResponse walk(@RequestBody SnmpRequest request) {
        return snmpService.walk(request);
    }
    @PostMapping("/getbulk")
    public SnmpResponse getBulk(@RequestBody SnmpRequest request) {
        return snmpService.getBulk(request);
    }
    @PostMapping("/trap")
    public SnmpResponse trap(@RequestBody SnmpRequest request) {
        return snmpService.trap(request);
    }

    @PostMapping("/inform")
    public SnmpResponse inform(@RequestBody SnmpRequest request) {
        return snmpService.inform(request);
    }

    @GetMapping("/traps")
    public List<TrapMessage> getReceivedTraps() {
        return snmpService.getReceivedTraps();
    }
    @GetMapping("/receive")
    public SnmpResponse receiveTrap() {
        return snmpService.receiveTrap();
    }

}