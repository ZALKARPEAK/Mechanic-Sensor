package com.xemyru.sensorweather.api;

import com.xemyru.sensorweather.dto.measurement.MeasurementDTO;
import com.xemyru.sensorweather.service.measurement.MeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/measurement")
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @Operation(
            summary = "Add a new measurement",
            description = "Adds a new measurement to the system, including sensor details."
    )
    @PostMapping("/measurement")
    public ResponseEntity<MeasurementDTO> addMeasurement(
            @RequestBody @Valid MeasurementDTO measurementDto) {
        MeasurementDTO addedMeasurement = measurementService.addMeasurement(measurementDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMeasurement);
    }

    @Operation(
            summary = "Get all measurements",
            description = "Retrieves all measurements"
    )
    @GetMapping
    public ResponseEntity<List<MeasurementDTO>> getAllMeasurements() {
        List<MeasurementDTO> measurements = measurementService.getAllMeasurements();
        return ResponseEntity.ok(measurements);
    }

    @Operation(
            summary = "Get count of rainy days",
            description = "Returns the count of days where it rained based on the measurements."
    )
    @GetMapping("/rainy-days")
    public ResponseEntity<Long> getRainyDaysCount() {
        long rainyDaysCount = measurementService.getRainyDaysCount();
        return ResponseEntity.ok(rainyDaysCount);
    }
}