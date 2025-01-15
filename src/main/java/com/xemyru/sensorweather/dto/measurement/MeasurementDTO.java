package com.xemyru.sensorweather.dto.measurement;

import com.xemyru.sensorweather.dto.sensor.SensorDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementDTO {
    private Long id;
    private double value;
    private boolean raining;
    private SensorDTO sensor;
}