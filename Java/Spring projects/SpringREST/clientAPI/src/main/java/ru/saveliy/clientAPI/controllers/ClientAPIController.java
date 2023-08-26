package ru.saveliy.clientAPI.controllers;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientAPI")
public class ClientAPIController {
    @PostMapping("/addRandomMeasurements")
    public void makePostRequests() {
        RestTemplate restTemplate = new RestTemplate();
        //Генерация имени сенсора
        int nameLength = 8;
        StringBuilder sensorName = new StringBuilder();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWHYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < nameLength; i++) {
            int index = (int) (Math.random() * (alphabet.length()));
            sensorName.append(alphabet.charAt(index));
        }
        //Создание запроса на добавление сенсора со случайным именем
        Map<String, String> sensorData = new HashMap<>();
        sensorData.put("name", sensorName.toString());
        String url = "http://localhost:8080/sensors/registration";
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(sensorData);
        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, String>>() {
        });
        HttpStatusCode code = responseEntity.getStatusCode();
        //Если запрос не выполнился успешно, заканчиваем выполнение метода
        if (code != HttpStatus.OK) return;
        int reqNumbers = 1000;
        for (int i = 0; i < reqNumbers; i++) {
            addMeasure(sensorName.toString());
        }
    }

    @GetMapping()
    public List<Map<String, Object>> getAllMeasurements(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements";
        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Map<String, Object>>>(){});
        return responseEntity.getBody();
    }

    //Создание запроса на добавление результатов для данного сканера
    public void addMeasure(String sensorName) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> measureData = new HashMap<>();
        measureData.put("value", ((Math.random() * 2001) - 1000)/10);
        measureData.put("raining", (int) (Math.random() * 2) == 0);
        Map<String, String> sensorData = new HashMap<>();
        sensorData.put("name", sensorName);
        measureData.put("sensor", sensorData);
        String url = "http://localhost:8080/measurements/add";
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(measureData);
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, String>>() {
        });
    }
}
