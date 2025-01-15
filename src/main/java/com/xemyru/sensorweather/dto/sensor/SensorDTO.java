package com.xemyru.sensorweather.dto.sensor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorDTO {
    private Long id;

    @NotBlank(message = "Field name can't empty or null")
    private String name;

    private String jwtToken;
}