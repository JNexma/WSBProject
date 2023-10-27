package pl.merito.projectwsb;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/*
public class sampleController implements Initializable {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amountOfPayment_input;

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
    private ChoiceBox<String> degree_input;
    private final String[] degree_list = {"Bachelor", "Engineer", "Master"};

    @FXML
    private Button docGenerationBt;

    @FXML
    private TextField faculty_input;

    @FXML
    private TextField fullName_input;

    @FXML
    private TextField idNumber_input;

    @FXML
    private ChoiceBox<String> intake_input;
    private final String[] intake_list = {"October", "March"};

    @FXML
    private ChoiceBox<String> language_input;
    private final String[] language_list = {"English", "Polish"};

    @FXML
    private TextField passport_input;

    @FXML
    private ChoiceBox<String> paymentCurrency_input;
    private final String[] paymentCurrency_list = {"EUR", "PLN"};

    @FXML
    private TextField speciality_input;

    @FXML
    private ChoiceBox<String> typeOfStudy_input;
    private final String[] typeOfStudy_list = {"Full time", "Part time"};

    @FXML
    private MenuItem bydgoszczSelected;

    @FXML
    private MenuItem lodzSelected;

    @FXML
    private MenuItem torunSelected;

    private String department_input = "Toruń";

    @FXML
    private TextField tuition_input;

    @FXML
    private TextField installments_input;

    @FXML CheckBox interview_yes_input, interview_no_input;
    boolean genInterviewText = false;

    @FXML
    ChoiceBox <String> accommodation_input;
    public final String[] accommodation_list = {"Akademikus", "Accepted"};

    @FXML
    Label accommodation_field;
    @FXML
    Image imageWsbTor = new Image(getClass().getResourceAsStream("/assets/logoWsbTOR.png"));
    @FXML
    Image imageWsbBdg = new Image(getClass().getResourceAsStream("/assets/logoWsbBDG.png"));
    @FXML
    Image imageWsbLod = new Image(getClass().getResourceAsStream("/assets/logoWsbLOD.png"));
    @FXML
    ImageView frameLogo;

    //Method to change a city in MenuBar
    @FXML
    void citySwitcher(ActionEvent event) {
        if(bydgoszczSelected == event.getSource()) {
            bydgoszczSelected.setText("Bydgoszcz (selected)");
            lodzSelected.setText("Łódź");
            torunSelected.setText("Toruń");
            department_input = "Bydgoszcz";
            accommodation_input.setOpacity(0.0);
            accommodation_input.setDisable(true);
            accommodation_field.setOpacity(0.0);
            frameLogo.setImage(imageWsbBdg);
        } else if(lodzSelected == event.getSource()) {
            lodzSelected.setText("Łódź (selected)");
            bydgoszczSelected.setText("Bydgoszcz");
            torunSelected.setText("Toruń");
            department_input = "Łódź";
            accommodation_input.setOpacity(0.0);
            accommodation_input.setDisable(true);
            accommodation_field.setOpacity(0.0);
            frameLogo.setImage(imageWsbLod);
        } else if(torunSelected == event.getSource()) {
            torunSelected.setText("Toruń (selected)");
            bydgoszczSelected.setText("Bydgoszcz");
            lodzSelected.setText("Łódź");
            department_input = "Toruń";
            accommodation_input.setOpacity(1.0);
            accommodation_input.setDisable(false);
            accommodation_field.setOpacity(1.0);
            frameLogo.setImage(imageWsbTor);
        }
    }

    //Method for Button "docGenerationBt" to generate WORD Documents
    @FXML
    void documentsGeneration(ActionEvent event) {
        String fullName_get = fullName_input.getText();
        String dateOfBirth_get = dateOfBirth_input.getText();
        String passport_get = passport_input.getText();
        String country_get = country_input.getText();
        String city_get = city_input.getText();
        String idNumber_get = idNumber_input.getText();
        String currentDate_get = currentDate_input.getText();
        String dateOfPayment_get = dateOfPayment_input.getText();
        String faculty_get = faculty_input.getText();
        String speciality_get = speciality_input.getText();
        String paymentCurrency_get = paymentCurrency_input.getValue();
        String degree_get = degree_input.getValue();
        String intake_get = intake_input.getValue();
        String typeOfStudy_get = typeOfStudy_input.getValue();
        String language_get = language_input.getValue();
        String amountOfPayment_get = amountOfPayment_input.getText();
        String tuition_get = tuition_input.getText();
        String installments_get = installments_input.getText();
        String accommodation_get = accommodation_input.getValue();

        if (!checkFields()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Required fields are missing");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
        } else if(!checkCurrentData(currentDate_input)){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Wrong field");
            alert.setHeaderText("Cannot compile field");
            alert.setContentText("Field \"Current Date\" must contains the following type: dd.mm.yyyy");
            alert.showAndWait();
        } else {
            String pathForSave = getPath();
            if(pathForSave == null){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Missing Path");
                alert.setHeaderText("Required path is missing");
                alert.setContentText("Please choose a path for your files.");
                alert.showAndWait();
            } else {
                //extract a year from currentDate_get
                char [] findYear = currentDate_get.toCharArray();
                String currentYear = Character.toString(findYear[findYear.length-4]) + Character.toString(findYear[findYear.length-3]) +
                        Character.toString(findYear[findYear.length-2]) + Character.toString(findYear[findYear.length-1]);
                ThreadForGenerating generate = new ThreadForGenerating(fullName_get, dateOfBirth_get, passport_get,
                        country_get, city_get, idNumber_get, currentDate_get, dateOfPayment_get, faculty_get, speciality_get,
                        paymentCurrency_get, degree_get, intake_get, typeOfStudy_get, language_get,
                        amountOfPayment_get, department_input, currentYear, tuition_get, installments_get,
                        pathForSave, genInterviewText, accommodation_get);
                generate.start();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information about success");
                alert.setHeaderText("Documents were generated");
                alert.setContentText("You can find your documents in file which you have chosen for save.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void initialize() {
    }

    //Initialize Choice Boxes and image for Frame
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        degree_input.getItems().addAll(degree_list);
        degree_input.setValue(degree_list[0]);
        intake_input.getItems().addAll(intake_list);
        intake_input.setValue(intake_list[0]);
        language_input.getItems().addAll(language_list);
        language_input.setValue(language_list[0]);
        paymentCurrency_input.getItems().addAll(paymentCurrency_list);
        paymentCurrency_input.setValue(paymentCurrency_list[0]);
        typeOfStudy_input.getItems().addAll(typeOfStudy_list);
        typeOfStudy_input.setValue(typeOfStudy_list[0]);
        accommodation_input.getItems().addAll(accommodation_list);
        accommodation_input.setValue(accommodation_list[0]);
        frameLogo.setImage(imageWsbTor);

    }

    //Check if all fields are filled
    public boolean checkFields() {
        String fullName_get = fullName_input.getText();
        String dateOfBirth_get = dateOfBirth_input.getText();
        String passport_get = passport_input.getText();
        String country_get = country_input.getText();
        String city_get = city_input.getText();
        String idNumber_get = idNumber_input.getText();
        String currentDate_get = currentDate_input.getText();
        String dateOfPayment_get = dateOfPayment_input.getText();
        String faculty_get = faculty_input.getText();
        String speciality_get = speciality_input.getText();
        String amountOfPayment_get = amountOfPayment_input.getText();
        String tuition_get = tuition_input.getText();
        String installments_get = installments_input.getText();
        String[] fieldsMassive = {fullName_get,dateOfBirth_get,passport_get,country_get,city_get,idNumber_get,
                currentDate_get,dateOfPayment_get,faculty_get,speciality_get,amountOfPayment_get,tuition_get,installments_get};
        boolean isFilled = true;
        for(String check : fieldsMassive) {
            if(check.isEmpty()) {
                isFilled = false;
                break;
            }
        }

        return isFilled;
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
    //two below methods to control interview choiceBox
    @FXML
    public void setInterviewYes(){
        if(interview_yes_input.isSelected()){
            interview_no_input.setSelected(false);
            genInterviewText = true;
        }
    }
    @FXML
    public void setInterviewNo(){
        if(interview_no_input.isSelected()){
            interview_yes_input.setSelected(false);
            genInterviewText=false;
        }
    }
    //turn imageView for fun
    @FXML
    void rotateImage(MouseEvent event) {
        Scale scale = new Scale(-1, 1, frameLogo.getBoundsInLocal().getWidth()/2, 0);

        frameLogo.getTransforms().addAll(scale);
    }
    //Method which check if current data contains more than 4 symbols and hey are int
    public boolean checkCurrentData(TextField field){
        String currentDate_check = field.getText();
        char [] findYear = currentDate_check.toCharArray();
        if(findYear.length<5){
            return false;
        }
        String currentYear = Character.toString(findYear[findYear.length-4]) + Character.toString(findYear[findYear.length-3]) +
                Character.toString(findYear[findYear.length-2]) + Character.toString(findYear[findYear.length-1]);
        try{
            int year = Integer.parseInt(currentYear);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
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
                case "idNumber_input" -> currentDate_input.requestFocus();
                case "currentDate_input" -> tuition_input.requestFocus();
                case "tuition_input" -> installments_input.requestFocus();
                case "installments_input" -> amountOfPayment_input.requestFocus();
                case "amountOfPayment_input" -> dateOfPayment_input.requestFocus();
                case "dateOfPayment_input" -> faculty_input.requestFocus();
                case "faculty_input" -> speciality_input.requestFocus();
                case "speciality_input" -> fullName_input.requestFocus();
            }
        }
    }

}
*/