package com.xemyru.sensorweather.service.measurement;

import com.xemyru.sensorweather.config.jwt.JwtService;
import com.xemyru.sensorweather.dto.measurement.MeasurementDTO;
import com.xemyru.sensorweather.entity.Measurement;
import com.xemyru.sensorweather.exception.InvalidJwtTokenException;
import com.xemyru.sensorweather.mapper.MeasurementMapper;
import com.xemyru.sensorweather.repository.MeasurementRepository;
import com.xemyru.sensorweather.validator.sensor.SensorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;
    private final SensorValidator sensorValidator;
    private final JwtService jwtService;

    @Override
    public MeasurementDTO addMeasurement(MeasurementDTO measurementDto) {
        String sensorName = measurementDto.getSensor().getName();

        sensorValidator.validateSensorName(sensorName);

        String jwtToken = measurementDto.getSensor().getJwtToken();
        if (!jwtService.isTokenValid(jwtToken, sensorName)) {
            throw new InvalidJwtTokenException("Invalid token for sensor: " + sensorName);
        }

        Measurement measurement = measurementRepository.save(measurementMapper.toEntity(measurementDto));

        return measurementMapper.toDto(measurement);
    }

    @Override
    @Transactional
    public List<MeasurementDTO> getAllMeasurements() {
        try (Stream<Measurement> measurementStream = measurementRepository.findAllBy()) {
            return measurementStream
                    .map(measurementMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getRainyDaysCount() {
        return measurementRepository.countByRainingTrue();
    }
}