package pl.merito.projectwsb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    Image wsbIcon = new Image(getClass().getResourceAsStream("/assets/WSBFrameLogo.png"));

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HelloFrame.fxml"));
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("styleForMain.css").toExternalForm());
        stage.setTitle("WSB Merito Universities (prod. by Denis Sadovskiy)");
        stage.setResizable(false);
        stage.getIcons().add(wsbIcon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }



}
