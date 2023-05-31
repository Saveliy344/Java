package com.example.exchangerapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;


class ExchangerHandler{
    public Map<String, Object> rates;
    ExchangerHandler() {
        Exchanger exchanger = new Exchanger();
        rates =  exchanger.getRates();
        exchanger.closeClient();
    }
    String getResult(String cur1, String cur2, double value){
        double result = getValue(rates.get(cur1))/getValue(rates.get(cur2)) * value;
        return Double.toString(result);
    }
    private static Double getValue(Object value){
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        }
        return null;
    }
}
public class ExchangerApplication extends Application {
    ExchangerHandler exchangerHandler = new ExchangerHandler();
    @Override
    public void start(Stage stage) throws IOException {
        //Создаём клиента exchanger, получаем данные и сразу же закрываем его
        stage.setTitle("Exchanger");
        // Создание полей для ввода и выпадающих списков
        TextField value = new TextField();
        // Изначально в поле для ввода создан ноль
        value.setText("0");
        //Установка формата ввода только неотрицательных чисел
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d+|0\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        });
        value.setTextFormatter(textFormatter);
        //Из какой валюты сделать перевод
        ComboBox<String> originalCur = new ComboBox<>();
        //В какую валюту сделать перевод
        ComboBox<String> convertedCur = new ComboBox<>();
        // Добавление элементов в выпадающие списки
        originalCur.getItems().addAll(exchangerHandler.rates.keySet());
        convertedCur.getItems().addAll(exchangerHandler.rates.keySet());
        originalCur.setValue("USD");
        convertedCur.setValue("USD");
        // Создание кнопки
        Button button = new Button("Конвертировать");
        Text result = new Text();

        // Обработка события нажатия на кнопку
        button.setOnAction(e -> {
            String valueText = value.getText();
            String originalCurValue = originalCur.getValue();
            String convertedCurValue = convertedCur.getValue();
            String res = "Error!";
            if (valueText.length() > 0 &&
                    originalCurValue != null &&
                    convertedCurValue != null)
            res = exchangerHandler.getResult(originalCurValue, convertedCurValue, Float.parseFloat(valueText));
            result.setText(res);

        });

        // Создание контейнера VBox и добавление элементов в него
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(value, convertedCur, originalCur, button, result);

        // Создание сцены и установка корневого контейнера
        Scene scene = new Scene(root, 300, 200);

        // Установка сцены на главную сцену приложения
        stage.setScene(scene);

        // Отображение главной сцены приложения
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}