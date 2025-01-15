package com.xemyru.sensorweather.service.sensor;

import com.xemyru.sensorweather.config.jwt.JwtService;
import com.xemyru.sensorweather.dto.sensor.SensorDTO;
import com.xemyru.sensorweather.entity.Sensor;
import com.xemyru.sensorweather.mapper.SensorMapper;
import com.xemyru.sensorweather.repository.SensorRepository;
import com.xemyru.sensorweather.validator.sensor.SensorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final JwtService jwtService;
    private final SensorMapper sensorMapper;
    private final SensorValidator sensorValidator;

    @Override
    public SensorDTO registerSensor(SensorDTO sensorDto) {
        sensorValidator.validateSensorName(sensorDto.getName());

        sensorDto.setJwtToken(jwtService.generateToken(sensorDto.getName()));

        Sensor newSensor = sensorRepository.save(sensorMapper.sensorDTOtoSensor(sensorDto));

        return sensorMapper.sensorToSensorDTO(newSensor);
    }
}