package com.example.tictactoe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private static final int FIELD_SIZE = 4;
    private Button[][] buttons;
    private Game game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        game = new Game(FIELD_SIZE);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        buttons = new Button[FIELD_SIZE][FIELD_SIZE];
        for (int row = 0; row < FIELD_SIZE; row++) {
            for (int col = 0; col < FIELD_SIZE; col++) {
                Button button = createButton(row, col);
                buttons[row][col] = button;
                gridPane.add(button, col, row);
            }
        }

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Крестики-нолики");
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();
    }

    private Button createButton(int row, int col) {
        Button button = new Button();
        button.setText(CellType.Nullity.getCode());
        button.setPrefSize(200, 200);
        button.setOnAction(e -> handleButtonClick(row, col));
        return button;
    }

    public void handleButtonClick(int row, int col) {
        if (game.isEnded) {
            showEndGameMessage(game.winner);
            return;
        }
        String step = game.makeStep(row, col);
        updateButton(row, col, step);
        game.checkEnd();
        if (game.isEnded) {
            showEndGameMessage(game.winner);
        }
    }

    public void updateButton(int row, int col, String code) {
        Button button = buttons[row][col];
        button.setStyle("-fx-background-color: " + code);
        button.setDisable(true);
    }

    public void showEndGameMessage(String winner) {
        switch (winner) {
            case "red":
                winner = "Красный";
                break;
            case "blue":
                winner = "Синий";
                break;
        }
        String message;
        if (winner == null) {
            message = "Ничья!";
        } else {
            message = winner + " победил! ";
        }
        showAlert("Игра окончена", message);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}