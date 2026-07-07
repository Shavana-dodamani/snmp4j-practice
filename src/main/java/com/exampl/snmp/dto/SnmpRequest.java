package com.exampl.snmp.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnmpRequest {

    private String version;

    private String ip;

    private int port;

    private String community;

    private String username;

    private String authPassword;

    private String privPassword;

    private String authProtocol;

    private String privProtocol;

    private String oid;

    private String value;

    private String dataType;

}
