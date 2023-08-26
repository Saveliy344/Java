package ru.saveliy.sensorAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saveliy.sensorAPI.models.Measurement;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    public Integer countAllByRaining(Boolean raining);
}
