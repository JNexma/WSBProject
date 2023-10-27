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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LodController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amountOfPayment_input;

    @FXML
    private MenuItem bydgoszczSelected;

    @FXML
    private TextField city_input;

    @FXML
    private TextField country_input;

    @FXML
    private TextField currentDate_input;

    @FXML
    private TextField dateOfBirth_input;

    @FXML
    private TextField dateOfPayment_input;

    @FXML
    private ChoiceBox<?> degree_input;

    @FXML
    private Button docGenerationBt;

    @FXML
    private TextField faculty_input;

    @FXML
    Image imageWsbLod = new Image(getClass().getResourceAsStream("/assets/logoWsbLOD.png"));

    @FXML
    private ImageView frameLogo;

    @FXML
    private TextField fullName_input;

    @FXML
    private TextField idNumber_input;

    @FXML
    private TextField installments_input;

    @FXML
    private ChoiceBox<?> intake_input;

    @FXML
    private CheckBox interview_no_input;

    @FXML
    private CheckBox interview_yes_input;

    @FXML
    private ChoiceBox<?> language_input;

    @FXML
    private MenuItem lodzSelected;

    @FXML
    private TextField passport_input;

    @FXML
    private ChoiceBox<?> paymentCurrency_input;

    @FXML
    private TextField speciality_input;

    @FXML
    private MenuItem torunSelected;

    @FXML
    private TextField tuition_input;

    @FXML
    private ChoiceBox<?> typeOfStudy_input;

    @FXML
    Button buttonSwitch;

    String department_input;

    @FXML
    void citySwitcher(ActionEvent event) throws IOException {
        if(event.getSource() == torunSelected) {
            buttonSwitch.setDisable(false);
            department_input = "Toruń";
            buttonSwitch.fire();
            buttonSwitch.setDisable(true);
        } else if(event.getSource() == bydgoszczSelected) {
            buttonSwitch.setDisable(false);
            department_input = "Bydgoszcz";
            buttonSwitch.fire();
            buttonSwitch.setDisable(true);
        }
    }
    //Switch country
    @FXML
    void citySwitcherBtn(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        if(department_input.equals("Toruń")) {
            root = FXMLLoader.load(getClass().getResource("TorFrame.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("WSB Merito Toruń");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (department_input.equals("Bydgoszcz")){
            root = FXMLLoader.load(getClass().getResource("BydFrame.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("WSB Merito Bydgoszcz");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void documentsGeneration(ActionEvent event) {

    }

    @FXML
    void nextTextField(KeyEvent event) {

    }

    @FXML
    void rotateImage(MouseEvent event) {

    }

    @FXML
    void setInterviewNo(ActionEvent event) {

    }

    @FXML
    void setInterviewYes(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert amountOfPayment_input != null : "fx:id=\"amountOfPayment_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert bydgoszczSelected != null : "fx:id=\"bydgoszczSelected\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert city_input != null : "fx:id=\"city_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert country_input != null : "fx:id=\"country_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert currentDate_input != null : "fx:id=\"currentDate_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert dateOfBirth_input != null : "fx:id=\"dateOfBirth_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert dateOfPayment_input != null : "fx:id=\"dateOfPayment_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert degree_input != null : "fx:id=\"degree_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert docGenerationBt != null : "fx:id=\"docGenerationBt\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert faculty_input != null : "fx:id=\"faculty_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert frameLogo != null : "fx:id=\"frameLogo\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert fullName_input != null : "fx:id=\"fullName_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert idNumber_input != null : "fx:id=\"idNumber_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert installments_input != null : "fx:id=\"installments_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert intake_input != null : "fx:id=\"intake_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert interview_no_input != null : "fx:id=\"interview_no_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert interview_yes_input != null : "fx:id=\"interview_yes_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert language_input != null : "fx:id=\"language_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert lodzSelected != null : "fx:id=\"lodzSelected\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert passport_input != null : "fx:id=\"passport_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert paymentCurrency_input != null : "fx:id=\"paymentCurrency_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert speciality_input != null : "fx:id=\"speciality_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert torunSelected != null : "fx:id=\"torunSelected\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert tuition_input != null : "fx:id=\"tuition_input\" was not injected: check your FXML file 'LodFrame.fxml'.";
        assert typeOfStudy_input != null : "fx:id=\"typeOfStudy_input\" was not injected: check your FXML file 'LodFrame.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        frameLogo.setImage(imageWsbLod);
    }
}
