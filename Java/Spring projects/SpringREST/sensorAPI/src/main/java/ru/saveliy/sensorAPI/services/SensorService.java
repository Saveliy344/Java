package ru.saveliy.sensorAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saveliy.sensorAPI.models.Sensor;
import ru.saveliy.sensorAPI.repositories.SensorRepository;

import java.util.List;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll(){
        return sensorRepository.findAll();
    }
    public void register(Sensor sensor) {
        sensorRepository.save(sensor);
    }
    public List<Sensor> findAllByName(String name){
        return sensorRepository.findAllByName(name);
    }
    public Sensor findSensorByName(String name){
        return sensorRepository.findSensorByName(name);
    }
}
