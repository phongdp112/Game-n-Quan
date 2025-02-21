package src.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class HomeController {
    @FXML
    private ImageView backgroundImageHome;

    @FXML
    private Button exitButton;

    @FXML
    private Button guideButton;

    @FXML
    private StackPane homePane;

    @FXML
    private Button newGameButton;

    private void startNewGame() { 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/view/GameView.fxml"));
            Parent gameView = loader.load();
            Stage stage = (Stage) newGameButton.getScene().getWindow();
            stage.setScene(new Scene(gameView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showGuide() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/view/GuideView.fxml"));
            Parent guideView = loader.load();
            Stage stage = (Stage) guideButton.getScene().getWindow();
            stage.setScene(new Scene(guideView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitGame() {
        // Thoát ứng dụng hoặc quay lại màn hình chính.
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You're about to quit");
        alert.setContentText("Do you want this?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void initialize() {
        if(newGameButton!=null) {
            // Event handler for "Game Mới"
            newGameButton.setOnAction(event -> startNewGame());
        }
        if(guideButton!=null) {
            // Event handler for "Hướng Dẫn"
            guideButton.setOnAction(event -> showGuide());
        }
        if(exitButton!=null) {
            // Event handler for "Thoát"
            exitButton.setOnAction(event -> exitGame());
        }
    }
}
