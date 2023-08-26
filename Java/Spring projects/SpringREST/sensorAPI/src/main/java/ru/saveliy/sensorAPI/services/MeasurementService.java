package ru.saveliy.sensorAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saveliy.sensorAPI.models.Measurement;
import ru.saveliy.sensorAPI.repositories.MeasurementRepository;

import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public void add(Measurement measurement){
        measurementRepository.save(measurement);
    }

    public List<Measurement> getAllMeasurements(){
        return measurementRepository.findAll();
    }

    public Integer getCountRainyMeasurements(){
        return measurementRepository.countAllByRaining(true);
    }
}
