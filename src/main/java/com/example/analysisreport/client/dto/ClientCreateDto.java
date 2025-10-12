package com.example.analysisreport.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class ClientCreateDto {

    @NotBlank(message = "Client name is required when adding a new client.")
    private String name;

    @Size(max = 200, message = "Client address cannot exceed 200 characters")
    private String address;
}
