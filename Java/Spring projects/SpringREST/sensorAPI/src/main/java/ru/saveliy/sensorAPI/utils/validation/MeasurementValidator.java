package ru.saveliy.sensorAPI.utils.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.saveliy.sensorAPI.dto.MeasurementDTO;
import ru.saveliy.sensorAPI.services.MeasurementService;
import ru.saveliy.sensorAPI.services.SensorService;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(MeasurementService measurementService, SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        if (measurementDTO.getSensor() == null){
            return;
        }
        if (!measurementDTO.getSensor().containsKey("name")){
            String message = "Sensor must be in format: \"sensor\": {\"name\": \"name\"}";
            errors.rejectValue("sensor", "", message);
            return;
        }
        if (sensorService.findAllByName(measurementDTO.getSensor().get("name")).size() == 0) {
            String message = "Sensor " + measurementDTO.getSensor().get("name") + " was not found!";
            errors.rejectValue("sensor", "", message);
        }
    }
}
