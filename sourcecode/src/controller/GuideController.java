package src.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GuideController {

    @FXML
    private ImageView backgroundImageGuide;

    @FXML
    private Button guideCloseButton;

    @FXML
    private StackPane guidePane;

    @FXML
    private void closeGuide() {
        guidePane.setVisible(false);
        guidePane.setManaged(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/view/HomeView.fxml"));
            Parent homeView = loader.load();
            Stage stage = (Stage) guideCloseButton.getScene().getWindow();
            stage.setScene(new Scene(homeView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        if(guideCloseButton!=null) {
            // Event handler for "Đóng"
            guideCloseButton.setOnAction(event -> closeGuide());
        }
    }

}
