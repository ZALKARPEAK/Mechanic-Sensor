package com.xemyru.sensorweather.repository;

import com.xemyru.sensorweather.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Sensor findByName(String sensorName);
    boolean existsByName(String sensorName);
}