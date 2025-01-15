package com.xemyru.sensorweather.repository;

import com.xemyru.sensorweather.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    long countByRainingTrue();
    Stream<Measurement> findAllBy();
}