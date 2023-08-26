package ru.saveliy.sensorAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.saveliy.sensorAPI.dto.MeasurementDTO;
import ru.saveliy.sensorAPI.models.Measurement;
import ru.saveliy.sensorAPI.models.Sensor;
import ru.saveliy.sensorAPI.services.MeasurementService;
import ru.saveliy.sensorAPI.services.SensorService;
import ru.saveliy.sensorAPI.utils.exceptions.MeasurementAddError;
import ru.saveliy.sensorAPI.utils.exceptions.SensorDoesNotExistError;
import ru.saveliy.sensorAPI.utils.messages.ExceptionRequestMessage;
import ru.saveliy.sensorAPI.utils.messages.SuccessRequestMessage;
import ru.saveliy.sensorAPI.utils.validation.MeasurementValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/measurements")
public class MeasurementAPIController {
    private final MeasurementValidator measurementValidator;
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ObjectMapper objectMapper;

    @Autowired
    public MeasurementAPIController(MeasurementValidator measurementValidator, MeasurementService measurementService, SensorService sensorService, ObjectMapper objectMapper) {
        this.measurementValidator = measurementValidator;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<SuccessRequestMessage> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult result){
        measurementValidator.validate(measurementDTO, result);
        if (result.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError error: result.getAllErrors()){
                stringBuilder.append(error.getDefaultMessage());
                stringBuilder.append("\n");
            }
            throw new MeasurementAddError(stringBuilder.toString());
        }
        Measurement measurement = measurementDTOtoMeasurement(measurementDTO);
        measurementService.add(measurement);
        SuccessRequestMessage message = new SuccessRequestMessage("Measurement was added successfully!");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements(){
        List<Measurement> measurements = measurementService.getAllMeasurements();
        List<MeasurementDTO> data = new ArrayList<>();
        for (Measurement measurement: measurements){
            data.add(measurementToMeasurementDTO(measurement));
        }
        return data;
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Integer> getCountRainyMeasurements(){
        return new ResponseEntity<>(measurementService.getCountRainyMeasurements(), HttpStatus.OK);
    }


    //Переводит объект MeasurementDTO в объект Measurement (для получении данных)
    public Measurement measurementDTOtoMeasurement(MeasurementDTO measurementDTO){
        Sensor sensor = sensorService.findSensorByName(measurementDTO.getSensor().get("name"));
        if (sensor == null) throw new SensorDoesNotExistError();
        //Нужно поставить null для корректной конвертации, sensor был сохранён ранее в этом методе
        measurementDTO.setSensor(null);
        Measurement measurement =  objectMapper.convertValue(measurementDTO, Measurement.class);
        measurement.setSensor(sensor);
        return measurement;
    }

    //Переводит объект Measurement в объект MeasurementDTO (для отправки данных)
    public MeasurementDTO measurementToMeasurementDTO(Measurement measurement){
        Map<String, String> sensor = new HashMap<>();
        sensor.put("name", measurement.getSensor().getName());
        //Нужно поставить null для корректной ковертации, sensor был сохранён ранее в этом методе
        measurement.setSensor(null);
        MeasurementDTO measurementDTO = objectMapper.convertValue(measurement, MeasurementDTO.class);
        measurementDTO.setSensor(sensor);
        return measurementDTO;
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionRequestMessage> handleSensorRegistrationError(MeasurementAddError e){
        ExceptionRequestMessage message = new ExceptionRequestMessage(System.currentTimeMillis(), e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionRequestMessage> handleSensorDoesNotExistError(SensorDoesNotExistError e){
        ExceptionRequestMessage message = new ExceptionRequestMessage(System.currentTimeMillis(), e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
