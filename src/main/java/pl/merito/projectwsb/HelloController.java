package pl.merito.projectwsb;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class HelloController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> selFrame;
    private final String[] selFrameCountry = {"Toruń", "Bydgoszcz", "Łódź"};

    //Choose city
    @FXML
    void openFrame(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        if(selFrame.getValue().equals(selFrameCountry[0])){
            root = FXMLLoader.load(getClass().getResource("TorFrame.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("WSB Merito Toruń");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if(selFrame.getValue().equals(selFrameCountry[1])){
            root = FXMLLoader.load(getClass().getResource("BydFrame.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("WSB Merito Bydgoszcz");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if(selFrame.getValue().equals(selFrameCountry[2])){
            root = FXMLLoader.load(getClass().getResource("LodFrame.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("WSB Merito Bydgoszcz");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selFrame.getItems().addAll(selFrameCountry);
        selFrame.setValue(selFrameCountry[0]);
    }
}
