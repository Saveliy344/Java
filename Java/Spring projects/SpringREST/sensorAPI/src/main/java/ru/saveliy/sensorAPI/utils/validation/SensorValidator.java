package ru.saveliy.sensorAPI.utils.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.saveliy.sensorAPI.dto.SensorDTO;
import ru.saveliy.sensorAPI.services.SensorService;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;
        if (sensorService.findAllByName(sensorDTO.getName()).size() != 0) {
            String message = "Name " + sensorDTO.getName() + " is already used!";
            errors.rejectValue("name", "", message);
        }
    }
}
