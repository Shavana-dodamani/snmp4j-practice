package com.exampl.snmp.controller;


import com.exampl.snmp.dto.SnmpRequest;
import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.service.impl.SnmpV1Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;



import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(SnmpV1Controller.class)
class SnmpV1ControllerTest {



    @Autowired
    private MockMvc mockMvc;



    @MockitoBean
    private SnmpV1Service service;



    @Autowired
    private ObjectMapper objectMapper;



    private SnmpRequest request;



    @BeforeEach
    void setup(){


        request = new SnmpRequest();

        request.setIp("127.0.0.1");
        request.setPort(161);
        request.setCommunity("public");
        request.setOid("1.3.6.1.2.1.1.5.0");
        request.setValue("TestDevice");

    }



    // =========================
    // TEST GET API
    // =========================

    @Test
    void testGet() throws Exception {



        SnmpResponse response =
                SnmpResponse.builder()
                        .status("SUCCESS")
                        .oid(request.getOid())
                        .value("simulator")
                        .message("SNMP V1 GET successful")
                        .build();



        Mockito.when(
                        service.get(Mockito.any(SnmpRequest.class))
                )
                .thenReturn(response);



        mockMvc.perform(
                        post("/snmp/v1/get")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status")
                        .value("SUCCESS"))
                .andExpect(jsonPath("$.message")
                        .value("SNMP V1 GET successful"));

    }



    // =========================
    // TEST GETNEXT API
    // =========================


    @Test
    void testGetNext() throws Exception {



        SnmpResponse response =
                SnmpResponse.builder()
                        .status("SUCCESS")
                        .oid(request.getOid())
                        .value("next oid value")
                        .message("SNMP V1 GETNEXT successful")
                        .build();



        Mockito.when(
                        service.getNext(Mockito.any())
                )
                .thenReturn(response);



        mockMvc.perform(
                        post("/snmp/v1/getnext")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("SNMP V1 GETNEXT successful"));

    }



    // =========================
    // TEST SET API
    // =========================


    @Test
    void testSet() throws Exception {


        SnmpResponse response =
                SnmpResponse.builder()
                        .status("SUCCESS")
                        .oid(request.getOid())
                        .value("Updated")
                        .message("SNMP V1 SET successful")
                        .build();



        Mockito.when(
                        service.set(Mockito.any())
                )
                .thenReturn(response);



        mockMvc.perform(
                        post("/snmp/v1/set")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("SNMP V1 SET successful"));

    }



    // =========================
    // TEST WALK API
    // =========================


    @Test
    void testWalk() throws Exception {


        SnmpResponse response =
                SnmpResponse.builder()
                        .status("SUCCESS")
                        .oid(request.getOid())
                        .value("OID tree values")
                        .message("SNMP V1 WALK successful")
                        .build();



        Mockito.when(
                        service.walk(Mockito.any())
                )
                .thenReturn(response);



        mockMvc.perform(
                        post("/snmp/v1/walk")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("SNMP V1 WALK successful"));

    }



    // =========================
    // TEST TRAP API
    // =========================


    @Test
    void testTrap() throws Exception {


        SnmpResponse response =
                SnmpResponse.builder()
                        .status("SUCCESS")
                        .oid(request.getOid())
                        .value("Trap Sent")
                        .message("SNMP V1 TRAP sent successfully")
                        .build();



        Mockito.when(
                        service.trap(Mockito.any())
                )
                .thenReturn(response);



        mockMvc.perform(
                        post("/snmp/v1/trap")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("SNMP V1 TRAP sent successfully"));

    }

}