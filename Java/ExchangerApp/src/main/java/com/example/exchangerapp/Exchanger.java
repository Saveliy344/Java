package com.example.exchangerapp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.Map;


class Exchanger {
    private final String API_KEY = "e6d6ec49a8154c1eaa36fb9909abb89b";
    //URL к сервису
    private final String API_URL = "https://openexchangerates.org/api/latest.json/?app_id=" + API_KEY;
    CloseableHttpClient httpClient;
    Exchanger(){
        //Инициализация клиента HTTP
        httpClient = HttpClientBuilder.create().build();
    }
    //Получить данные о курсах в виде JSON-строки
    private String getData(){
        //запрос к серверу API
        HttpGet request = new HttpGet(API_URL);
        try {
            //Ответ сервера
            HttpResponse response = httpClient.execute(request);
            //Получение сущности ответа
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Получить данные о курсах в виде словаря
    public Map<String, Object> getRates(){
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
        try {
            return (Map<String, Object>) objectMapper.readValue(getData(), typeReference).get("rates");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Закрыть клиента
    public void closeClient(){
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}