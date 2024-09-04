package pl.merito.projectwsb;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class LodController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tuitionPay_input, city_input, country_input, dateOfBirth_input, tuitionDate_input,
            faculty_input, fullName_input, idNumber_input, passport_input,
            speciality_input, tuition_input, admPay_input, admDate_input;

    @FXML
    private ChoiceBox<String> degree_input, intake_input, language_input, paymentCurrency_input,
            typeOfStudy_input, installments_input;
    private final String[] degree_list = {"Bachelor", "Engineer", "Master"};
    private final String[] intake_list = {"October", "March"};
    private final String[] language_list = {"English", "Polish"};
    private final String[] paymentCurrency_list = {"EUR", "PLN"};
    private final String[] typeOfStudy_list = {"Full time", "Part time"};
    private final String[] installments_list = {"1", "2", "10", "12"};

    @FXML
    private Button docGenerationBt, buttonSwitch;

    @FXML
    Image imageWsbTor = new Image(getClass().getResourceAsStream("/assets/logoWsbLOD.png"));

    @FXML
    private ImageView frameLogo;

    @FXML
    private CheckBox interview_no_input, interview_yes_input;

    @FXML
    private MenuItem toMainBtn, clearFields;

    boolean genInterviewText = false;
    LocalDate localDate = LocalDate.now();
    DateTimeFormatter formatted = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String currentDate = localDate.format(formatted);


    @FXML
    Label labelInterview;

    //Method for Button "docGenerationBt" to generate WORD Documents
    @FXML
    void documentsGeneration(ActionEvent event) {
        String fullName_get = fullName_input.getText();
        String dateOfBirth_get = dateOfBirth_input.getText();
        String passport_get = passport_input.getText();
        String country_get = country_input.getText();
        String city_get = city_input.getText();
        String idNumber_get = idNumber_input.getText();
        String currentDate_get = currentDate;
        String tuition_get = tuition_input.getText();
        String tuitionPay_get = tuitionPay_input.getText();
        String tuitionDate_get = tuitionDate_input.getText();
        String faculty_get = faculty_input.getText();
        String speciality_get = speciality_input.getText();
        String admPay_get = admPay_input.getText();
        String admDate_get = admDate_input.getText();
        String paymentCurrency_get = paymentCurrency_input.getValue();
        String degree_get = degree_input.getValue();
        String intake_get = intake_input.getValue();
        String typeOfStudy_get = typeOfStudy_input.getValue();
        String language_get = language_input.getValue();
        String installments_get = installments_input.getValue();

        if (!checkFields()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Required fields are missing");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
        } else if(!checkInt(tuition_get) || !checkInt(tuitionPay_get) || !checkInt(admPay_get)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect value");
            alert.setHeaderText("Unreadable variable");
            alert.setContentText("Please check if specific fields contains integer");
            alert.showAndWait();
        } else {
            String pathForSave = getPath();
            if(pathForSave == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing Path");
                alert.setHeaderText("Required path is missing");
                alert.setContentText("Please choose a path for your files.");
                alert.showAndWait();
            } else {
                //extract a year from currentDate_get
                String currentYear = String.valueOf(localDate.getYear());
                int currentMonth = localDate.getMonthValue();
                //create instants of "ThreadForGenerating" class
                ThreadForGenerating generate = new ThreadForGenerating(fullName_get, dateOfBirth_get, passport_get,
                        country_get, city_get, idNumber_get, currentDate_get, tuitionDate_get, faculty_get, speciality_get,
                        paymentCurrency_get, degree_get, intake_get, typeOfStudy_get, language_get,
                        tuitionPay_get, "Łódź", currentYear, tuition_get, installments_get,
                        pathForSave, genInterviewText, "Accommodation", admPay_get, admDate_get, currentMonth);
                generate.start();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information about success");
                alert.setHeaderText("Documents were generated");
                alert.setContentText("You can find your documents in file which you have chosen for save.");
                alert.showAndWait();
            }
        }
    }

    //Choose country for switch
    @FXML
    void citySwitcher(ActionEvent event) throws IOException {
        buttonSwitch.setDisable(false);
        buttonSwitch.fire();
        buttonSwitch.setDisable(true);
    }
    //Switch city
    @FXML
    void citySwitcherBtn(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        root = FXMLLoader.load(getClass().getResource("HelloFrame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("WSB Merito Universities (prod. by SD)");
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styleForMain.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    //go to then ext field after clicking "Enter"
    @FXML
    void nextTextField(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            TextField source = (TextField)event.getSource();
            switch (source.getId()) {
                case "fullName_input" -> dateOfBirth_input.requestFocus();
                case "dateOfBirth_input" -> passport_input.requestFocus();
                case "passport_input" -> country_input.requestFocus();
                case "country_input" -> city_input.requestFocus();
                case "city_input" -> idNumber_input.requestFocus();
                case "idNumber_input" -> tuition_input.requestFocus();
                case "tuition_input" -> tuitionPay_input.requestFocus();
                case "tuitionPay_input" -> tuitionDate_input.requestFocus();
                case "tuitionDate_input" -> admPay_input.requestFocus();
                case "admPay_input" -> admDate_input.requestFocus();
                case "admDate_input" -> faculty_input.requestFocus();
                case "faculty_input" -> speciality_input.requestFocus();
                case "speciality_input" -> fullName_input.requestFocus();
            }
        }
    }
    //rotate logo for fun
    @FXML
    void rotateImage(MouseEvent event) {
        Scale scale = new Scale(-1, 1, frameLogo.getBoundsInLocal().getWidth()/2, 0);
        frameLogo.getTransforms().addAll(scale);
    }
    //control "interview choicebox
    @FXML
    void setInterviewNo(ActionEvent event) {
        if(interview_no_input.isSelected()){
            interview_yes_input.setSelected(false);
            genInterviewText=false;
        }
    }
    //control "interview choicebox
    @FXML
    void setInterviewYes(ActionEvent event) {
        if(interview_yes_input.isSelected()){
            interview_no_input.setSelected(false);
            genInterviewText = true;
        }
    }

    @FXML
    void initialize() {

    }
    //Initialize Choice Boxes and image for Frame
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List <ChoiceBox> choiceBoxes = new ArrayList<>(Arrays.asList(degree_input, intake_input, language_input, paymentCurrency_input, typeOfStudy_input,installments_input));
        List <String[]> choiceBoxesList = new ArrayList<>(Arrays.asList(degree_list, intake_list, language_list, paymentCurrency_list, typeOfStudy_list, installments_list));
        for(int i = 0; i<choiceBoxes.size(); i++){
            choiceBoxes.get(i).getItems().addAll(choiceBoxesList.get(i));
            choiceBoxes.get(i).setValue("Select");
        }
        frameLogo.setImage(imageWsbTor);
        language_input.setOnAction(this::showLabel);
    }

    public void showLabel(ActionEvent event){
        if(language_input.getValue().equals("English")){
            labelInterview.setText("Interview");
            interview_yes_input.setDisable(false);
            interview_yes_input.setOpacity(1.0);
            interview_no_input.setDisable(false);
            interview_no_input.setOpacity(1.0);
        } else if(language_input.getValue().equals("Polish")){
            labelInterview.setText("Course");
            interview_yes_input.setDisable(false);
            interview_yes_input.setOpacity(1.0);
            interview_no_input.setDisable(false);
            interview_no_input.setOpacity(1.0);
        } else if(language_input.getValue().equals("Select")) {
            labelInterview.setText("Choose language...");
            interview_yes_input.setDisable(true);
            interview_yes_input.setOpacity(0);
            interview_no_input.setDisable(true);
            interview_no_input.setOpacity(0);
        }
    }
    //Check if all fields are filled
    public boolean checkFields() {
        String fullName_get = fullName_input.getText();
        String dateOfBirth_get = dateOfBirth_input.getText();
        String passport_get = passport_input.getText();
        String country_get = country_input.getText();
        String city_get = city_input.getText();
        String idNumber_get = idNumber_input.getText();
        String tuitionDate_get = tuitionDate_input.getText();
        String faculty_get = faculty_input.getText();
        String speciality_get = speciality_input.getText();
        String tuitionPay_get = tuitionPay_input.getText();
        String tuition_get = tuition_input.getText();
        String admPay_get = admPay_input.getText();
        String admDate_get = admDate_input.getText();
        String[] textFields = {fullName_get,dateOfBirth_get,passport_get,country_get,city_get,idNumber_get,
                tuitionDate_get,faculty_get,speciality_get,tuitionPay_get,tuition_get,
                admPay_get, admDate_get};
        String[] choiceBoxes = {degree_input.getValue(), intake_input.getValue(), language_input.getValue(),
                paymentCurrency_input.getValue(), typeOfStudy_input.getValue(), installments_input.getValue()};
        for(String check : textFields) {
            if(check.isEmpty()) {
                return false;
            }
        }
        for(String check : choiceBoxes){
            if(check.equals("Select")){
                return false;
            }
        }
        return true;
    }
    //clear all fields and choiceBoxes
    @FXML
    void clearFields(ActionEvent event){
        tuitionPay_input.setText("");
        city_input.setText("");
        country_input.setText("");
        dateOfBirth_input.setText("");
        tuitionDate_input.setText("");
        faculty_input.setText("");
        fullName_input.setText("");
        idNumber_input.setText("");
        passport_input.setText("");
        speciality_input.setText("");
        tuition_input.setText("");
        admPay_input.setText("");
        admDate_input.setText("");
        degree_input.setValue("Select");
        intake_input.setValue("Select");
        language_input.setValue("Select");
        paymentCurrency_input.setValue("Select");
        typeOfStudy_input.setValue("Select");
        installments_input.setValue("Select");

    }
    //Method to get path for save documents at disc
    @FXML
    public String getPath() {
        DirectoryChooser chooseDirection = new DirectoryChooser();
        File chooseFile = chooseDirection.showDialog(new Stage());
        String pathForDoc = "C:\\Users\\sadov\\Desktop\\";

        if(chooseFile == null) {
            return null;
        } else {
            pathForDoc = chooseFile.getAbsolutePath() + "\\";
        }
        return pathForDoc;
    }
    boolean checkInt(String fee) {
        try {
            int year = Integer.parseInt(fee);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
