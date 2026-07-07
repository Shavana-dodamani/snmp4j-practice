package com.exampl.snmp.controller;

import com.exampl.snmp.dto.SnmpRequest;
import com.exampl.snmp.dto.SnmpResponse;
import com.exampl.snmp.dto.TrapMessage;
import com.exampl.snmp.service.impl.SnmpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SnmpController.class)
class SnmpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SnmpService snmpService;

    private SnmpRequest request;
    private SnmpResponse response;

    @BeforeEach
    void setUp() {

        request = new SnmpRequest();
        request.setVersion("V2C");
        request.setIp("127.0.0.1");
        request.setPort(161);
        request.setCommunity("public");
        request.setOid("1.3.6.1.2.1.1.5.0");
        request.setValue("VCTI_DEVICE");

        response = SnmpResponse.builder()
                .status("SUCCESS")
                .oid(request.getOid())
                .value("TEST")
                .message("SUCCESS")
                .build();
    }

    @Test
    void testGet() throws Exception {

        Mockito.when(snmpService.get(any())).thenReturn(response);

        mockMvc.perform(post("/snmp/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testSet() throws Exception {

        Mockito.when(snmpService.set(any())).thenReturn(response);

        mockMvc.perform(post("/snmp/set")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetNext() throws Exception {

        Mockito.when(snmpService.getNext(any())).thenReturn(response);

        mockMvc.perform(post("/snmp/getnext")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testWalk() throws Exception {

        Mockito.when(snmpService.walk(any())).thenReturn(response);

        mockMvc.perform(post("/snmp/walk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBulk() throws Exception {

        Mockito.when(snmpService.getBulk(any())).thenReturn(response);

        mockMvc.perform(post("/snmp/getbulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testTrap() throws Exception {

        Mockito.when(snmpService.trap(any())).thenReturn(response);

        mockMvc.perform(post("/snmp/trap")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testInform() throws Exception {

        Mockito.when(snmpService.inform(any())).thenReturn(response);

        mockMvc.perform(post("/snmp/inform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testReceiveTrap() throws Exception {

        Mockito.when(snmpService.receiveTrap()).thenReturn(response);

        mockMvc.perform(get("/snmp/receive"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetReceivedTraps() throws Exception {

        Mockito.when(snmpService.getReceivedTraps())
                .thenReturn(Collections.singletonList(new TrapMessage()));

        mockMvc.perform(get("/snmp/traps"))
                .andExpect(status().isOk());
    }
}