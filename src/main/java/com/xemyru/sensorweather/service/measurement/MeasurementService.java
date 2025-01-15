package com.xemyru.sensorweather.service.measurement;

import com.xemyru.sensorweather.dto.measurement.MeasurementDTO;

import java.util.List;

public interface MeasurementService {
    /**
     * Добавляет новое измерение в систему.
     *
     * @param measurementDto объект DTO с данными измерения.
     * @return добавленное измерение в виде DTO.
     */
    MeasurementDTO addMeasurement(MeasurementDTO measurementDto);

    /**
     * Возвращает список всех измерений.
     *
     * @return список объектов DTO с данными измерений.
     */
    List<MeasurementDTO> getAllMeasurements();

    /**
     * Возвращает количество дождливых дней.
     *
     * @return количество дней с дождем.
     */
    long getRainyDaysCount();
}