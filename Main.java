package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //From this *.fxml file we get application window "settings"
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //Main scene of the application.
        Scene scene = new Scene(root);
        stage.setTitle("File Uploader");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
