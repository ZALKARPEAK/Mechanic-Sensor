package com.xemyru.sensorweather.service;

import com.xemyru.sensorweather.config.jwt.JwtService;
import com.xemyru.sensorweather.dto.measurement.MeasurementDTO;
import com.xemyru.sensorweather.dto.sensor.SensorDTO;
import com.xemyru.sensorweather.entity.Measurement;
import com.xemyru.sensorweather.exception.InvalidJwtTokenException;
import com.xemyru.sensorweather.mapper.MeasurementMapper;
import com.xemyru.sensorweather.repository.MeasurementRepository;
import com.xemyru.sensorweather.service.measurement.MeasurementServiceImpl;
import com.xemyru.sensorweather.validator.sensor.SensorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceImplTest {

    @InjectMocks
    private MeasurementServiceImpl measurementService;

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private MeasurementMapper measurementMapper;

    @Mock
    private SensorValidator sensorValidator;

    @Mock
    private JwtService jwtService;

    private MeasurementDTO measurementDto;
    private Measurement measurement;

    @BeforeEach
    void init() {
        measurementDto = MeasurementDTO.builder()
                .value(25.0)
                .raining(true)
                .sensor(SensorDTO.builder()
                        .name("TestSensor")
                        .jwtToken("validToken")
                        .build())
                .build();

        measurement = Measurement.builder()
                .id(1)
                .value(25.0)
                .raining(true)
                .id(1)
                .build();
    }

    @Test
    @DisplayName("Add measurement - success")
    void whenAddMeasurementThenReturnMeasurementDto() {
        when(jwtService.isTokenValid("validToken", "TestSensor")).thenReturn(true);
        when(measurementMapper.toEntity(measurementDto)).thenReturn(measurement);
        when(measurementRepository.save(measurement)).thenReturn(measurement);
        when(measurementMapper.toDto(measurement)).thenReturn(measurementDto);

        MeasurementDTO result = measurementService.addMeasurement(measurementDto);

        assertNotNull(result);
        verify(sensorValidator).validateSensorName("TestSensor");
        verify(jwtService).isTokenValid("validToken", "TestSensor");
        verify(measurementRepository).save(measurement);
    }

    @Test
    @DisplayName("Add measurement - invalid token")
    void whenAddMeasurementWithInvalidTokenThenThrowException() {
        when(jwtService.isTokenValid("invalidToken", "TestSensor")).thenReturn(false);

        measurementDto.getSensor().setJwtToken("invalidToken");

        assertThrows(InvalidJwtTokenException.class, () -> measurementService.addMeasurement(measurementDto));
        verify(sensorValidator).validateSensorName("TestSensor");
        verify(jwtService).isTokenValid("invalidToken", "TestSensor");
        verifyNoInteractions(measurementRepository);
    }

    @Test
    @DisplayName("Get all measurements - success")
    void whenGetAllMeasurementsThenReturnList() {
        when(measurementRepository.findAllBy()).thenReturn(Stream.of(measurement));
        when(measurementMapper.toDto(measurement)).thenReturn(measurementDto);

        List<MeasurementDTO> result = measurementService.getAllMeasurements();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(measurementRepository).findAllBy();
    }

    @Test
    @DisplayName("Get rainy days count - success")
    void whenGetRainyDaysCountThenReturnCount() {
        when(measurementRepository.countByRainingTrue()).thenReturn(5L);

        long result = measurementService.getRainyDaysCount();

        assertEquals(5L, result);
        verify(measurementRepository).countByRainingTrue();
    }
}
