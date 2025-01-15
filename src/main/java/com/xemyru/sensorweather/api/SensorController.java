package com.xemyru.sensorweather.api;

import com.xemyru.sensorweather.dto.sensor.SensorDTO;
import com.xemyru.sensorweather.service.sensor.SensorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sensor")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping("/register")
    public ResponseEntity<SensorDTO> registerSensor(@RequestBody @Valid SensorDTO sensorDto) {
        SensorDTO registeredSensor = sensorService.registerSensor(sensorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredSensor);
    }
}