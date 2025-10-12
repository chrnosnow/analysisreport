package com.example.analysisreport.client.dto;

import lombok.Data;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class ClientUpdateDto {

    private String name;

    private String address;
}
