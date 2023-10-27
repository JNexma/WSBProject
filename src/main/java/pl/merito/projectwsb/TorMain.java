package pl.merito.projectwsb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class TorMain extends Application {

    Image wsbIcon = new Image(getClass().getResourceAsStream("/assets/WSBFrameLogo.png"));

    @Override
    public void start(Stage stage) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("TorFrame.fxml"));
            Scene scene = new Scene(root, 600, 461);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setTitle("WSB Merito Torun (prod. by SD)");
            stage.setResizable(false);
            stage.getIcons().add(wsbIcon);
            stage.setScene(scene);
            stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}