package com.exampl.snmp.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnmpV3Request {

    private String ip;
    private int port;

    private String username;

    private String authProtocol;
    private String authPassword;

    private String privProtocol;
    private String privPassword;

    private int securityLevel;

    private String oid;

    private String value;
    private Integer nonRepeaters;
    private Integer maxRepetitions;
}