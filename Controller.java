package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Controller implements Initializable {
    @FXML private BorderPane mainPane;
    @FXML private Label statusLabel;
    @FXML private ProgressBar progressBar;
    @FXML private Button uploadButton;

    //We create another thread to handle file upload.
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    //Choosing file Method
    @FXML
    public void ButtonAction(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File chosenFile = chooser.showOpenDialog(null);

        if (chosenFile != null) {
           Task<Void> sendFileTask = new SendFileTask(chosenFile);
           statusLabel.textProperty().bind(sendFileTask.messageProperty());
           progressBar.progressProperty().bind(sendFileTask.progressProperty());
           executor.submit(sendFileTask);
       }
       else {
           System.out.println("An error occurred while choosing file. Try again.");
           return;
       }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
