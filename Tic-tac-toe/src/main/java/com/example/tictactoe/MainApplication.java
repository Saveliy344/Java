package com.example.tictactoe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private static final int FIELD_SIZE = 3;
    private Button[][] buttons;
    private Game game;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
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
        Button restartButton = new Button("Restart");
        restartButton.setPrefSize(200, 100);
        restartButton.setStyle("-fx-background-color: green;" +
                "-fx-font-size: 25");
        restartButton.setOnAction(e -> {
            restartApplication(restartButton);
        });
        gridPane.add(restartButton, (int) FIELD_SIZE/2, FIELD_SIZE);
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-tac-toe");
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();
    }

    private Button createButton(int row, int col) {
        Button button = new Button();
        button.setStyle("-fx-focus-traversable: false");
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
        game.checkEnd(row, col);
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
        String message;
        if (winner == null) {
            message = "Draw!";
        } else {
            message = winner + " wins! ";
        }
        showAlert("Game over", message);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void restartApplication(Button restartButton) {
        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) restartButton.getScene().getWindow();
                stage.close();
                MainApplication mainApplication = new MainApplication();
                mainApplication.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}