package com.xemyru.sensorweather.validator.sensor;

import com.xemyru.sensorweather.exception.DataValidationException;
import com.xemyru.sensorweather.dto.sensor.SensorDTO;
import com.xemyru.sensorweather.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SensorValidator {

    private final SensorRepository sensorRepository;

    public void validateSensorName(String sensorName) {
        if (sensorRepository.existsByName(sensorName)) {
            throw new DataValidationException("Sensor already exists with name " + sensorName);
        }
    }
}