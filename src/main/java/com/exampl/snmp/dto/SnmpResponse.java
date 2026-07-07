package com.exampl.snmp.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnmpResponse {

    private String status;

    private String oid;

    private String value;

    private String message;

}
