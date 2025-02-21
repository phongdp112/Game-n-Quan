package src.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/view/HomeView.fxml"));

        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Trò chơi Ô Ăn Quan");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) { 
        launch(args);
    }
}
