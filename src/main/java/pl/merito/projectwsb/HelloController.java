package pl.merito.projectwsb;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;


public class HelloController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    Image animLogoIm = new Image(getClass().getResourceAsStream("/assets/animLogo.gif"));

    @FXML
    Image mapIm = new Image(getClass().getResourceAsStream("/assets/mapa.png"));

    @FXML
    private ImageView animLogo, map, lamp;

    @FXML
    private ChoiceBox<String> selFrame;
    private final String[] selFrameCountry = {"Toruń", "Bydgoszcz", "Łódź"};

    @FXML
    private AnchorPane moveMapPane, moveBluePane;


    //Choose city
    @FXML
    void openFrame(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        if(selFrame.getValue().equals(selFrameCountry[0])){
            root = FXMLLoader.load(getClass().getResource("TorFrame.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("WSB Merito Toruń (prod. by Denis Sadovskiy)");
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } else if(selFrame.getValue().equals(selFrameCountry[1])){
            root = FXMLLoader.load(getClass().getResource("BydFrame.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("WSB Merito Bydgoszcz (prod. by Denis Sadovskiy)");
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } else if(selFrame.getValue().equals(selFrameCountry[2])){
            root = FXMLLoader.load(getClass().getResource("LodFrame.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("WSB Merito Bydgoszcz (prod. by Denis Sadovskiy)");
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect value");
            alert.setHeaderText("Unreachable variable");
            alert.setContentText("Please choose city to open it's frame");
            alert.showAndWait();
        }
    }

    @FXML
    void openSite(ActionEvent event) throws Exception {
        Desktop salesForce = Desktop.getDesktop();
        salesForce.browse(new URI("https://tebakademia.lightning.force.com/lightning/o/Enrollment__c/list?filterName=00B7T000001I4WGUA0"));
    }

    @FXML
    void openWSB(MouseEvent event) throws Exception {
        Desktop wsbWebSite = Desktop.getDesktop();
        wsbWebSite.browse(new URI("https://www.merito.pl/"));
    }

    @FXML
    void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selFrame.getItems().addAll(selFrameCountry);
        selFrame.setValue("Choose city");
        map.setImage(mapIm);
        animLogo.setImage(animLogoIm);

        TranslateTransition transitionMap = new TranslateTransition(Duration.millis(2000));
        transitionMap.setNode(moveMapPane);
        transitionMap.setByX(-5);
        transitionMap.setByY(-10);
        transitionMap.setAutoReverse(true);
        transitionMap.setCycleCount(TranslateTransition.INDEFINITE);
        transitionMap.setInterpolator(Interpolator.EASE_BOTH);
        transitionMap.play();

    }
}
