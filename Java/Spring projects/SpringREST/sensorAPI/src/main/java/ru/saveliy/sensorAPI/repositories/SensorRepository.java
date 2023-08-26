package ru.saveliy.sensorAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saveliy.sensorAPI.models.Sensor;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    public List<Sensor> findAllByName(String name);
    public Sensor findSensorByName(String name);
}
