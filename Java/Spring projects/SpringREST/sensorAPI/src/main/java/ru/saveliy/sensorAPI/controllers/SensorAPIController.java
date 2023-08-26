package ru.saveliy.sensorAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.saveliy.sensorAPI.dto.SensorDTO;
import ru.saveliy.sensorAPI.models.Sensor;
import ru.saveliy.sensorAPI.services.SensorService;
import ru.saveliy.sensorAPI.utils.messages.ExceptionRequestMessage;
import ru.saveliy.sensorAPI.utils.exceptions.SensorRegistrationError;
import ru.saveliy.sensorAPI.utils.messages.SuccessRequestMessage;
import ru.saveliy.sensorAPI.utils.validation.SensorValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorAPIController {
    private final ObjectMapper objectMapper;
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorAPIController(ObjectMapper objectMapper, SensorService sensorService, SensorValidator sensorValidator) {
        this.objectMapper = objectMapper;
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }


    @PostMapping("/registration")
    public ResponseEntity<SuccessRequestMessage> register(@RequestBody @Valid SensorDTO sensorDTO, BindingResult result){
        sensorValidator.validate(sensorDTO, result);
        if (result.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError error: result.getAllErrors()){
                stringBuilder.append(error.getDefaultMessage());
                stringBuilder.append("\n");
            }
            throw new SensorRegistrationError(stringBuilder.toString());
        }
        Sensor sensor = sensorDTOtoSensor(sensorDTO);
        sensorService.register(sensor);
        SuccessRequestMessage message = new SuccessRequestMessage(sensor.getName() +  " was successfully registered!");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping()
    public List<String> getAllSensorsName(){
        List<Sensor> sensors = sensorService.findAll();
        List<String> data = new ArrayList<>();
        for (Sensor sensor: sensors){
            data.add(sensor.getName());
        }
        return data;
    }

    //Конвертирует SensorDTO в Sensor
    Sensor sensorDTOtoSensor(SensorDTO sensorDTO){
        return objectMapper.convertValue(sensorDTO, Sensor.class);
    }

    //Конвертирует Sensor в SensorDTO
    public SensorDTO sensorToSensorDTO(Sensor sensor){
        return new SensorDTO(sensor.getName());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionRequestMessage> handleSensorRegistrationError(SensorRegistrationError e){
        ExceptionRequestMessage message = new ExceptionRequestMessage(System.currentTimeMillis(), e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
