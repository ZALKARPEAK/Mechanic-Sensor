package com.xemyru.sensorweather.mapper;

import com.xemyru.sensorweather.dto.sensor.SensorDTO;
import com.xemyru.sensorweather.entity.Sensor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SensorMapper {
    SensorDTO sensorToSensorDTO(Sensor sensor);
    Sensor sensorDTOtoSensor(SensorDTO sensorDTO);
}