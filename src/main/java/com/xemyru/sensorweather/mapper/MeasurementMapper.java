package com.xemyru.sensorweather.mapper;

import com.xemyru.sensorweather.dto.measurement.MeasurementDTO;
import com.xemyru.sensorweather.entity.Measurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeasurementMapper {

    @Mapping(source = "sensor", target = "sensor")
    MeasurementDTO toDto(Measurement measurement);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "raining", target = "raining")
    @Mapping(source = "sensor", target = "sensor")
    Measurement toEntity(MeasurementDTO measurementDTO);
}