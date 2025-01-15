package com.xemyru.sensorweather.service.sensor;

import com.xemyru.sensorweather.dto.sensor.SensorDTO;
import org.springframework.stereotype.Service;

@Service
public interface SensorService {
    /**
     * Регистрирует новый сенсор в системе.
     *
     * @param sensorDto объект DTO с информацией о сенсоре.
     * @return зарегистрированный сенсор в виде DTO.
     */
    SensorDTO registerSensor(SensorDTO sensorDto);
}