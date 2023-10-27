package pl.merito.projectwsb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;


public class ThreadForGenerating extends Thread{
    String fullName_convert, dateOfBirth_convert, passport_convert, country_convert, city_convert,
            idNumber_convert, currentDate_convert, tuitionDate_convert, faculty_convert, speciality_convert,
            paymentCurrency_convert, degree_convert, intake_convert, typeOfStudy_convert, language_convert,
            tuitionPay_convert, department_convert, currentYear_convert, tuition_convert, installments_convert,
            pathForSave, accommodation_convert, admPay_convert, admDate_convert;
    boolean interviewIsSelected;
    String dir = new File(".").getAbsoluteFile().getParentFile().getAbsolutePath() + System.getProperty("file.separator");


    public ThreadForGenerating(String fullName_convert, String dateOfBirth_convert, String passport_convert,
                               String country_convert, String city_convert, String idNumber_convert, String currentDate_convert,
                               String tuitionDate_convert, String faculty_convert, String speciality_convert,
                               String paymentCurrency_convert, String degree_convert, String intake_convert, String typeOfStudy_convert,
                               String language_convert, String tuitionPay_convert, String department_convert,
                               String currentYear_convert, String tuition_convert, String installments_convert, String pathForSave,
                               boolean interviewIsSelected, String accommodation_convert, String admPay_convert, String admDate_convert) {
        this.fullName_convert = fullName_convert;
        this.dateOfBirth_convert = dateOfBirth_convert;
        this.passport_convert = passport_convert;
        this.country_convert = country_convert;
        this.city_convert = city_convert;
        this.idNumber_convert = idNumber_convert;
        this.currentDate_convert = currentDate_convert;
        this.tuitionDate_convert = tuitionDate_convert;
        this.faculty_convert = faculty_convert;
        this.speciality_convert = speciality_convert;
        this.paymentCurrency_convert = paymentCurrency_convert;
        this.degree_convert = degree_convert;
        this.intake_convert = intake_convert;
        this.typeOfStudy_convert = typeOfStudy_convert;
        this.language_convert = language_convert;
        this.tuitionPay_convert = tuitionPay_convert;
        this.department_convert = department_convert;
        this.currentYear_convert = currentYear_convert;
        this.tuition_convert = tuition_convert;
        this.installments_convert = installments_convert;
        this.pathForSave = pathForSave;
        this.interviewIsSelected = interviewIsSelected;
        this.accommodation_convert = accommodation_convert;
        this.admPay_convert = admPay_convert;
        this.admDate_convert = admDate_convert;
    }
//Method to generate WORD documents (in process...)
    public void run() {
        switch (department_convert) {
            case "Toruń" -> {
                if(language_convert.equals("Polish")){
                    try {
                        docTorunPl(pathForSave);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        docTorunEng(pathForSave);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            case "Bydgoszcz" -> {
                if(language_convert.equals("Polish")){
                    try {
                        docBydgoszczPl(pathForSave);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        docBydgoszczEng(pathForSave);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            case "Łódź" -> {
                if(language_convert.equals("Polish")){
                    try {
                        docLodzPl(pathForSave);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        docLodzEng(pathForSave);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    //Generate documents for Bydgoszcz in Polish
    public void docBydgoszczPl(String pathForSave) throws IOException {
        //Generate "Potwerdzenie zakwaterowanie" in Bydgoszcz
        FileInputStream zakwaterowanieStream = new FileInputStream(dir + "src/main/resources/assets/zakwaterowanieBDG.docx");
        try(XWPFDocument zakwaterowanieInput = new XWPFDocument(zakwaterowanieStream)) {
            List<XWPFParagraph> zakwaterowanieParagraph = zakwaterowanieInput.getParagraphs();
            for(XWPFParagraph paragraph : zakwaterowanieParagraph) {
                for(XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$ASTART}", "15.09."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "30.09." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "16.03." + addYear(4));
                                default -> runText;
                            };
                        } else {
                            runText = runText.replace("{$ASTART}", "01.03."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "16.03." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "16.03." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                default -> runText;
                            };
                        }
                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream zakwaterowanieOutput = new FileOutputStream(pathForSave + "Potwerdzenie zakwaterowania " + fullName_convert + ".docx")){
                zakwaterowanieInput.write(zakwaterowanieOutput);
                zakwaterowanieOutput.close();
                zakwaterowanieInput.close();
            }
        }
        //Generate zaświadczenie o przyjęciu in Bydgoszcz
        FileInputStream przyjecieStream = new FileInputStream(dir + "src/main/resources/assets/przyjęcieBDG.docx");
        try(XWPFDocument przyjecieInput = new XWPFDocument(przyjecieStream)) {
            List<XWPFParagraph> przyjecieParagraph = przyjecieInput.getParagraphs();
            for(XWPFParagraph paragraph : przyjecieParagraph) {
                List<XWPFRun> przyjecieRun = paragraph.getRuns();
                for(XWPFRun run : przyjecieRun) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$CITY}", city_convert);
                        runText = runText.replace("{$COUNTRY}", country_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$DEGREE}", degree_convert);
                        runText = runText.replace("{$Yx1}", currentYear_convert + "/" + addYear(1));
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        runText = runText.replace("{$SPECIALITY}", speciality_convert);

                        if(typeOfStudy_convert.equals("Full time")){
                            runText = runText.replace("{$MODE}", "Stacjonarne");
                        } else {
                            runText = runText.replace("{$MODE}", "Niestacjonarne");
                        }

                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$INTAKE}", "01.10." + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "30.09." + currentYear_convert+ ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        } else {
                            runText = runText.replace("{$INTAKE}", "16.03." + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "15.03." + currentYear_convert + ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        }
                        if(degree_convert.equals("Bachelor")) {
                            runText = runText.replace("{$SEM_ENDING}", "semestrów");
                            runText = runText.replace("{$SEM_COUNT}", "6");
                        } else if(degree_convert.equals("Engineer")) {
                            runText = runText.replace("{$SEM_ENDING}", "semestrów");
                            runText = runText.replace("{$SEM_COUNT}", "7");
                        } else {
                            runText = runText.replace("{$SEM_ENDING}", "semestry");
                            runText = runText.replace("{$SEM_COUNT}", "4");
                        }
                        runText = runText.replace("{$TUITION}", tuition_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$INSTALLMENTS}", installments_convert);
                        runText = runText.replace("{$INST_COUNT}", instAmount(tuition_convert, installments_convert)+ " " + paymentCurrency_convert);
                        runText = runText.replace("{$PAID}",tuitionPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_T}",tuitionDate_convert);
                        runText = runText.replace("{$ADM}",admPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_A}",admDate_convert);

                        if (interviewIsSelected && runText.contains("{$KURS}")) {
                            runText = runText.replace("{$KURS}", "Ponadto informujemy, że Kandydat zapisał się na " +
                                    "bezpłatny kurs językowo-adaptacyjny dla studentów I roku kierunków polskojęzycznych," +
                                    " który odbędzie się w dniach 20.09–17.10."+currentYear_convert+
                                    " r. na Uniwersytecie WSB Merito w Toruniu, Wydziale Finansów i Zarządzania w Bydgoszczy.");
                            run.addBreak();
                        } else {
                            runText = runText.replace("{$KURS}", " ");

                        }

                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream przyjecieOutput = new FileOutputStream(pathForSave + "Zaświadczenie o przyjęciu " + fullName_convert + ".docx")){
                przyjecieInput.write(przyjecieOutput);
                przyjecieOutput.close();
                przyjecieInput.close();
            }
        }
    }
    //Generate document for Bydgoszcz in English
    public void docBydgoszczEng(String pathForSave) throws IOException {

        //Generating Invitation letter Bydgoszcz
        FileInputStream invitationStream = new FileInputStream(dir + "src/main/resources/assets/invitationBDG.docx");
        try(XWPFDocument invitationInput = new XWPFDocument(invitationStream)) {
            List<XWPFParagraph> invitationParagraph = invitationInput.getParagraphs();
            for(XWPFParagraph paragraph : invitationParagraph) {
                for(XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$DATESTART}", "1st of October "+currentYear_convert);
                        } else {
                            runText = runText.replace("{$DATESTART}", "16th of March "+currentYear_convert);
                        }
                        run.setText(runText, 0);
                    }
                }
            }

            try(FileOutputStream invitationOutput = new FileOutputStream(pathForSave + "Invitation " + fullName_convert + ".docx")){
                invitationInput.write(invitationOutput);
                invitationOutput.close();
                invitationInput.close();

            }
        }

        //Generating confirmation of Accommodation Bydgoszcz
        FileInputStream accoStream = new FileInputStream(dir + "src/main/resources/assets/conOfAccoBDG.docx");
        try(XWPFDocument accoInput = new XWPFDocument(accoStream)) {
            List<XWPFParagraph> accoParagraph = accoInput.getParagraphs();
            for(XWPFParagraph paragraph : accoParagraph) {
                for(XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$ASTART}", "15.09."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "30.09." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "16.03." + addYear(4));
                                default -> runText;
                            };
                        } else {
                            runText = runText.replace("{$ASTART}", "01.03."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "16.03." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "16.03." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                default -> runText;
                            };
                        }
                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream accoOutput = new FileOutputStream(pathForSave + "Confirmation of Accommodation " + fullName_convert + ".docx")){
                accoInput.write(accoOutput);
                accoOutput.close();
                accoInput.close();
            }
        }

        //Generating Admission Letter Bydgoszcz
        FileInputStream admissionStream = new FileInputStream(dir + "src/main/resources/assets/admissionBDG.docx");
        try(XWPFDocument admissionInput = new XWPFDocument(admissionStream)) {
            List<XWPFParagraph> admissionParagraph = admissionInput.getParagraphs();
            for(XWPFParagraph paragraph : admissionParagraph) {
                List<XWPFRun> admissionRun = paragraph.getRuns();
                for(XWPFRun run : admissionRun) {
                    String runText = run.getText(0);

                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$CITY}", city_convert);
                        runText = runText.replace("{$COUNTRY}", country_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$DEGREE}", degree_convert);
                        runText = runText.replace("{$Yx1}", currentYear_convert + "/" + addYear(1));
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        runText = runText.replace("{$SPECIALITY}", speciality_convert);
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$INTAKE}", "October " + currentYear_convert);
                            runText = runText.replace("{$DEADLINE}", "15th of September " + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "30th of September " + currentYear_convert + ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        } else {
                            runText = runText.replace("{$INTAKE}", "March " + currentYear_convert);
                            runText = runText.replace("{$DEADLINE}", "28th of February " + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "15th of March " + currentYear_convert + ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        }
                        if(degree_convert.equals("Bachelor")) {
                            runText = runText.replace("{$CYCLE}", "first");
                            runText = runText.replace("{$SEM_COUNT}", "6");
                        } else if(degree_convert.equals("Engineer")) {
                            runText = runText.replace("{$CYCLE}", "first");
                            runText = runText.replace("{$SEM_COUNT}", "7");
                        } else {
                            runText = runText.replace("{$CYCLE}", "second");
                            runText = runText.replace("{$SEM_COUNT}", "4");
                        }
                        runText = runText.replace("{$TUITION_CUR}", tuition_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$PAIDCUR}", tuitionPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_T}", tuitionDate_convert);
                        runText = runText.replace("{$ADM_CUR}", admPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_A}", admDate_convert);
                        if (paymentCurrency_convert.equals("EUR")) {
                            runText = runText.replace("{$APP_CUR}","20 " + paymentCurrency_convert);
                            runText = runText.replace("{$BANKDETAILS}","PL 55 1240 1936 1978 0010 0267 1667");
                        } else {
                            runText = runText.replace("{$APP_CUR}","85 " + paymentCurrency_convert);
                            runText = runText.replace("{$BANKDETAILS}","PL 67 1240 6478 1111 0000 4948 6670");
                        }
                        if (interviewIsSelected && runText.contains("{$INTERVIEW}")) {
                            runText = runText.replace("{$INTERVIEW}", "WSB Merito in Torun, Branch in Bydgoszcz" +
                                    " confirms that student has passed the internal online interview conducted by the representative" +
                                    " of the University.");
                            run.addBreak();
                        } else {
                            runText = runText.replace("{$INTERVIEW}", " ");

                        }

                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream admissionOutput = new FileOutputStream(pathForSave + "Admission Letter " + fullName_convert + ".docx")){
                admissionInput.write(admissionOutput);
                admissionOutput.close();
                admissionInput.close();
            }
        }
    }
    //Generate documents for Toruń in Polish
    public void docTorunPl(String pathForSave) throws IOException {
        //Generate "Potwerdzenie zakwaterowanie" in Toruń
        FileInputStream zakwaterowanieStream = new FileInputStream(dir + "src/main/resources/assets/zakwaterowanieTOR.docx");
        try(XWPFDocument zakwaterowanieInput = new XWPFDocument(zakwaterowanieStream)) {
            List<XWPFParagraph> zakwaterowanieParagraph = zakwaterowanieInput.getParagraphs();
            for(XWPFParagraph paragraph : zakwaterowanieParagraph) {
                for(XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        if(runText.contains("{$ACCOMMODATION}")) {
                            runText = switch(accommodation_convert) {
                                case "Akademikus" -> runText.replace("{$ACCOMMODATION}", "\"Akademikus Toruń\" Broniewskiego 35, 87-100 Toruń" );
                                case "Accepted" -> runText.replace("{$ACCOMMODATION}", "\"Accepted\" ul. Piekarów 33, 87-100 Toruń");
                                default -> runText;
                            };
                        }
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$ASTART}", "15.09."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "30.09." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "16.03." + addYear(4));
                                default -> runText;
                            };
                        } else {
                            runText = runText.replace("{$ASTART}", "01.03."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "16.03." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "16.03." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                default -> runText;
                            };
                        }
                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream zakwaterowanieOutput = new FileOutputStream(pathForSave + "Potwerdzenie zakwaterowania " + fullName_convert + ".docx")){
                zakwaterowanieInput.write(zakwaterowanieOutput);
                zakwaterowanieOutput.close();
                zakwaterowanieInput.close();
            }
        }
        //Generate zaświadczenie o przyjęciu in Torun
        FileInputStream przyjecieStream = new FileInputStream(dir + "src/main/resources/assets/przyjęcieTOR.docx");
        try(XWPFDocument przyjecieInput = new XWPFDocument(przyjecieStream)) {
            List<XWPFParagraph> przyjecieParagraph = przyjecieInput.getParagraphs();
            for(XWPFParagraph paragraph : przyjecieParagraph) {
                List<XWPFRun> przyjecieRun = paragraph.getRuns();
                for(XWPFRun run : przyjecieRun) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$CITY}", city_convert);
                        runText = runText.replace("{$COUNTRY}", country_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$DEGREE}", degree_convert);
                        runText = runText.replace("{$Yx1}", currentYear_convert + "/" + addYear(1));
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        runText = runText.replace("{$SPECIALITY}", speciality_convert);

                        if(typeOfStudy_convert.equals("Full time")){
                            runText = runText.replace("{$MODE}", "Stacjonarne");
                        } else {
                            runText = runText.replace("{$MODE}", "Niestacjonarne");
                        }

                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$INTAKE}", "01.10." + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "30.09." + currentYear_convert+ ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        } else {
                            runText = runText.replace("{$INTAKE}", "16.03." + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "15.03." + currentYear_convert + ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        }
                        if(degree_convert.equals("Bachelor")) {
                            runText = runText.replace("{$SEM_ENDING}", "semestrów");
                            runText = runText.replace("{$SEM_COUNT}", "6");
                        } else if(degree_convert.equals("Engineer")) {
                            runText = runText.replace("{$SEM_ENDING}", "semestrów");
                            runText = runText.replace("{$SEM_COUNT}", "7");
                        } else {
                            runText = runText.replace("{$SEM_ENDING}", "semestry");
                            runText = runText.replace("{$SEM_COUNT}", "4");
                        }
                        runText = runText.replace("{$TUITION}", tuition_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$INSTALLMENTS}", installments_convert);
                        runText = runText.replace("{$INST_COUNT}", instAmount(tuition_convert, installments_convert)+ " " + paymentCurrency_convert);
                        runText = runText.replace("{$PAID}",tuitionPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_T}",tuitionDate_convert);
                        runText = runText.replace("{$ADM}",admPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_A}",admDate_convert);

                        if (interviewIsSelected && runText.contains("{$KURS}")) {
                            runText = runText.replace("{$KURS}", "Ponadto informujemy, że Kandydat zapisał się na " +
                                    "bezpłatny kurs językowo-adaptacyjny dla studentów I roku kierunków polskojęzycznych," +
                                    " który odbędzie się w dniach 20.09–17.10."+currentYear_convert+" r. na Uniwersytecie WSB Merito w Toruniu.");
                            run.addBreak();
                        } else {
                            runText = runText.replace("{$KURS}", " ");

                        }

                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream przyjecieOutput = new FileOutputStream(pathForSave + "Zaświadczenie o przyjęciu " + fullName_convert + ".docx")){
                przyjecieInput.write(przyjecieOutput);
                przyjecieOutput.close();
                przyjecieInput.close();
            }
        }
    }
    //Generate document for Toruń in English
    public void docTorunEng(String pathForSave) throws IOException {
        //Generating Invitation letter Toruń
        FileInputStream invitationStream = new FileInputStream(dir + "src/main/resources/assets/invitationTOR.docx");
        try(XWPFDocument invitationInput = new XWPFDocument(invitationStream)) {
            List<XWPFParagraph> invitationParagraph = invitationInput.getParagraphs();
            for(XWPFParagraph paragraph : invitationParagraph) {
                for(XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$DATESTART}", "1st of October "+currentYear_convert);
                        } else {
                            runText = runText.replace("{$DATESTART}", "16th of March "+currentYear_convert);
                        }
                        run.setText(runText, 0);
                    }
                }
            }

            try(FileOutputStream invitationOutput = new FileOutputStream(pathForSave + "Invitation " + fullName_convert + ".docx")){
                invitationInput.write(invitationOutput);
                invitationOutput.close();
                invitationInput.close();
            }
        }

        //Generating confirmation of Accommodation Toruń
        FileInputStream accoStream = new FileInputStream(dir + "src/main/resources/assets/conOfAccoTOR.docx");
        try(XWPFDocument accoInput = new XWPFDocument(accoStream)) {
            List<XWPFParagraph> accoParagraph = accoInput.getParagraphs();
            for(XWPFParagraph paragraph : accoParagraph) {
                for(XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        if(runText.contains("{$ACCOMMODATION}")) {
                            runText = switch(accommodation_convert) {
                                case "Akademikus" -> runText.replace("{$ACCOMMODATION}", "\"Akademikus Toruń\" Broniewskiego 35, 87-100 Toruń" );
                                case "Accepted" -> runText.replace("{$ACCOMMODATION}", "\"Accepted\" ul. Piekarów 33, 87-100 Toruń");
                                default -> runText;
                            };
                        }
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$ASTART}", "15.09."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "30.09." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "16.03." + addYear(4));
                                default -> runText;
                            };
                        } else {
                            runText = runText.replace("{$ASTART}", "01.03."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "16.03." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "16.03." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                default -> runText;
                            };
                        }
                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream accoOutput = new FileOutputStream(pathForSave + "Confirmation of Accommodation " + fullName_convert + ".docx")){
                accoInput.write(accoOutput);
                accoOutput.close();
                accoInput.close();
            }
        }

        //Generating Admission Letter Toruń
        FileInputStream admissionStream = new FileInputStream(dir + "src/main/resources/assets/admissionTOR.docx");
        try(XWPFDocument admissionInput = new XWPFDocument(admissionStream)) {
            List<XWPFParagraph> admissionParagraph = admissionInput.getParagraphs();
            for(XWPFParagraph paragraph : admissionParagraph) {
                List<XWPFRun> admissionRun = paragraph.getRuns();
                for(XWPFRun run : admissionRun) {
                    String runText = run.getText(0);

                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$CITY}", city_convert);
                        runText = runText.replace("{$COUNTRY}", country_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$DEGREE}", degree_convert);
                        runText = runText.replace("{$Yx1}", currentYear_convert + "/" + addYear(1));
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        runText = runText.replace("{$SPECIALITY}", speciality_convert);
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$INTAKE}", "October " + currentYear_convert);
                            runText = runText.replace("{$DEADLINE}", "15th of September " + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "30th of September " + currentYear_convert + ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        } else {
                            runText = runText.replace("{$INTAKE}", "March " + currentYear_convert);
                            runText = runText.replace("{$DEADLINE}", "28th of February " + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "15th of March " + currentYear_convert + ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        }
                        if(degree_convert.equals("Bachelor")) {
                            runText = runText.replace("{$CYCLE}", "first");
                            runText = runText.replace("{$SEM_COUNT}", "6");
                        } else if(degree_convert.equals("Engineer")) {
                            runText = runText.replace("{$CYCLE}", "first");
                            runText = runText.replace("{$SEM_COUNT}", "7");
                        } else {
                            runText = runText.replace("{$CYCLE}", "second");
                            runText = runText.replace("{$SEM_COUNT}", "4");
                        }
                        runText = runText.replace("{$TUITION_CUR}", tuition_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$PAIDCUR}", tuitionPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_T}", tuitionDate_convert);
                        runText = runText.replace("{$ADM_CUR}", admPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_A}", admDate_convert);
                        if (paymentCurrency_convert.equals("EUR")) {
                            runText = runText.replace("{$APP_CUR}","20 " + paymentCurrency_convert);
                            runText = runText.replace("{$BANKDETAILS}","PL 55 1240 1936 1978 0010 0267 1667");
                        } else {
                            runText = runText.replace("{$APP_CUR}","85 " + paymentCurrency_convert);
                            runText = runText.replace("{$BANKDETAILS}","PL 67 1240 6478 1111 0000 4948 6670");
                        }
                        if (interviewIsSelected && runText.contains("{$INTERVIEW}")) {
                            runText = runText.replace("{$INTERVIEW}", "WSB Merito in Torun," +
                                    " confirms that student has passed the internal online interview conducted by the representative" +
                                    " of the University.");
                            run.addBreak();
                        } else {
                            runText = runText.replace("{$INTERVIEW}", " ");

                        }

                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream admissionOutput = new FileOutputStream(pathForSave + "Admission Letter " + fullName_convert + ".docx")){
                admissionInput.write(admissionOutput);
                admissionOutput.close();
                admissionInput.close();
            }
        }
    }
    //Generate documents for Lodz in Polish
    public void docLodzPl(String pathForSave) throws IOException {
        //Generate "Potwerdzenie zakwaterowanie" in Lodz
        FileInputStream zakwaterowanieStream = new FileInputStream(dir + "src/main/resources/assets/zakwaterowanieLOD.docx");
        try(XWPFDocument zakwaterowanieInput = new XWPFDocument(zakwaterowanieStream)) {
            List<XWPFParagraph> zakwaterowanieParagraph = zakwaterowanieInput.getParagraphs();
            for(XWPFParagraph paragraph : zakwaterowanieParagraph) {
                for(XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$ASTART}", "15.09."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "30.09." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "16.03." + addYear(4));
                                default -> runText;
                            };
                        } else {
                            runText = runText.replace("{$ASTART}", "01.03."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "16.03." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "16.03." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                default -> runText;
                            };
                        }
                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream zakwaterowanieOutput = new FileOutputStream(pathForSave + "Potwerdzenie zakwaterowania " + fullName_convert + ".docx")){
                zakwaterowanieInput.write(zakwaterowanieOutput);
                zakwaterowanieOutput.close();
                zakwaterowanieInput.close();
            }
        }
        //Generate zaświadczenie o przyjęciu in Lodz
        FileInputStream przyjecieStream = new FileInputStream(dir + "src/main/resources/assets/przyjęcieLOD.docx");
        try(XWPFDocument przyjecieInput = new XWPFDocument(przyjecieStream)) {
            List<XWPFParagraph> przyjecieParagraph = przyjecieInput.getParagraphs();
            for(XWPFParagraph paragraph : przyjecieParagraph) {
                List<XWPFRun> przyjecieRun = paragraph.getRuns();
                for(XWPFRun run : przyjecieRun) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$CITY}", city_convert);
                        runText = runText.replace("{$COUNTRY}", country_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$DEGREE}", degree_convert);
                        runText = runText.replace("{$Yx1}", currentYear_convert + "/" + addYear(1));
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        runText = runText.replace("{$SPECIALITY}", speciality_convert);

                        if(typeOfStudy_convert.equals("Full time")){
                            runText = runText.replace("{$MODE}", "Stacjonarne");
                        } else {
                            runText = runText.replace("{$MODE}", "Niestacjonarne");
                        }

                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$INTAKE}", "01.10." + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "30.09." + currentYear_convert+ ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        } else {
                            runText = runText.replace("{$INTAKE}", "16.03." + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "15.03." + currentYear_convert + ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        }
                        if(degree_convert.equals("Bachelor")) {
                            runText = runText.replace("{$SEM_ENDING}", "semestrów");
                            runText = runText.replace("{$SEM_COUNT}", "6");
                        } else if(degree_convert.equals("Engineer")) {
                            runText = runText.replace("{$SEM_ENDING}", "semestrów");
                            runText = runText.replace("{$SEM_COUNT}", "7");
                        } else {
                            runText = runText.replace("{$SEM_ENDING}", "semestry");
                            runText = runText.replace("{$SEM_COUNT}", "4");
                        }
                        runText = runText.replace("{$TUITION}", tuition_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$INSTALLMENTS}", installments_convert);
                        runText = runText.replace("{$INST_COUNT}", instAmount(tuition_convert, installments_convert)+ " " + paymentCurrency_convert);
                        runText = runText.replace("{$PAID}",tuitionPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_T}",tuitionDate_convert);
                        runText = runText.replace("{$ADM}",admPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_A}",admDate_convert);

                        if (interviewIsSelected && runText.contains("{$KURS}")) {
                            runText = runText.replace("{$KURS}", "Ponadto informujemy, że Kandydat zapisał się na " +
                                    "bezpłatny kurs językowo-adaptacyjny dla studentów I roku kierunków polskojęzycznych," +
                                    " który odbędzie się w dniach 20.09–17.10."+currentYear_convert+
                                    " r. na Uniwersytecie WSB Merito w Toruniu, Wydziale Finansów i Zarządzania w Łodzi.");
                            run.addBreak();
                        } else {
                            runText = runText.replace("{$KURS}", " ");

                        }

                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream przyjecieOutput = new FileOutputStream(pathForSave + "Zaświadczenie o przyjęciu " + fullName_convert + ".docx")){
                przyjecieInput.write(przyjecieOutput);
                przyjecieOutput.close();
                przyjecieInput.close();
            }
        }
    }

    //Generate document for Łódź in English
    public void docLodzEng(String pathForSave) throws IOException {

        //Generating Invitation letter Łódź
        FileInputStream invitationStream = new FileInputStream(dir + "src/main/resources/assets/invitationLOD.docx");
        try(XWPFDocument invitationInput = new XWPFDocument(invitationStream)) {
            List<XWPFParagraph> invitationParagraph = invitationInput.getParagraphs();
            for(XWPFParagraph paragraph : invitationParagraph) {
                for(XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$DATESTART}", "1st of October "+currentYear_convert);
                        } else {
                            runText = runText.replace("{$DATESTART}", "16th of March "+currentYear_convert);
                        }
                        run.setText(runText, 0);
                    }
                }
            }

            try(FileOutputStream invitationOutput = new FileOutputStream(pathForSave + "Invitation " + fullName_convert + ".docx")){
                invitationInput.write(invitationOutput);
                invitationOutput.close();
                invitationInput.close();
            }
        }

        //Generating confirmation of Accommodation Łódź
        FileInputStream accoStream = new FileInputStream(dir + "src/main/resources/assets/conOfAccoLOD.docx");
        try(XWPFDocument accoInput = new XWPFDocument(accoStream)) {
            List<XWPFParagraph> accoParagraph = accoInput.getParagraphs();
            for(XWPFParagraph paragraph : accoParagraph) {
                for(XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$ASTART}", "15.09."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "30.09." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "16.03." + addYear(4));
                                default -> runText;
                            };
                        } else {
                            runText = runText.replace("{$ASTART}", "01.03."+currentYear_convert);
                            runText = switch (degree_convert) {
                                case "Bachelor" -> runText.replace("{$AEND}", "16.03." + addYear(3));
                                case "Master" -> runText.replace("{$AEND}", "16.03." + addYear(2));
                                case "Engineer" -> runText.replace("{$AEND}", "30.09." + addYear(3));
                                default -> runText;
                            };
                        }
                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream accoOutput = new FileOutputStream(pathForSave + "Confirmation of Accommodation " + fullName_convert + ".docx")){
                accoInput.write(accoOutput);
                accoOutput.close();
                accoInput.close();
            }
        }

        //Generating Admission Letter Łódź
        FileInputStream admissionStream = new FileInputStream(dir + "src/main/resources/assets/admissionLOD.docx");
        try(XWPFDocument admissionInput = new XWPFDocument(admissionStream)) {
            List<XWPFParagraph> admissionParagraph = admissionInput.getParagraphs();
            for(XWPFParagraph paragraph : admissionParagraph) {
                List<XWPFRun> admissionRun = paragraph.getRuns();
                for(XWPFRun run : admissionRun) {
                    String runText = run.getText(0);

                    if(runText != null) {
                        runText = runText.replace("{$ID}", idNumber_convert);
                        runText = runText.replace("{$Y}", currentYear_convert);
                        runText = runText.replace("{$DATE}", currentDate_convert);
                        runText = runText.replace("{$NAME}", fullName_convert);
                        runText = runText.replace("{$BIRTH}", dateOfBirth_convert);
                        runText = runText.replace("{$CITY}", city_convert);
                        runText = runText.replace("{$COUNTRY}", country_convert);
                        runText = runText.replace("{$PASSPORT}", passport_convert);
                        runText = runText.replace("{$DEGREE}", degree_convert);
                        runText = runText.replace("{$Yx1}", currentYear_convert + "/" + addYear(1));
                        runText = runText.replace("{$FACULTY}", faculty_convert);
                        runText = runText.replace("{$SPECIALITY}", speciality_convert);
                        if(intake_convert.equals("October")) {
                            runText = runText.replace("{$INTAKE}", "October " + currentYear_convert);
                            runText = runText.replace("{$DEADLINE}", "15th of September " + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "30th of September " + currentYear_convert + ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        } else {
                            runText = runText.replace("{$INTAKE}", "March " + currentYear_convert);
                            runText = runText.replace("{$DEADLINE}", "28th of February " + currentYear_convert);
                            if(runText.contains("{$ARRIVE_DEADLINE}")){
                                runText = runText.replace("{$ARRIVE_DEADLINE}", "15th of March " + currentYear_convert + ".");
                                if(interviewIsSelected){
                                    run.addBreak();}}
                        }
                        if(degree_convert.equals("Bachelor")) {
                            runText = runText.replace("{$CYCLE}", "first");
                            runText = runText.replace("{$SEM_COUNT}", "6");
                        } else if(degree_convert.equals("Engineer")) {
                            runText = runText.replace("{$CYCLE}", "first");
                            runText = runText.replace("{$SEM_COUNT}", "7");
                        } else {
                            runText = runText.replace("{$CYCLE}", "second");
                            runText = runText.replace("{$SEM_COUNT}", "4");
                        }
                        runText = runText.replace("{$TUITION_CUR}", tuition_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$PAIDCUR}", tuitionPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_T}", tuitionDate_convert);
                        runText = runText.replace("{$ADM_CUR}", admPay_convert + " " + paymentCurrency_convert);
                        runText = runText.replace("{$DATE_PAID_A}", admDate_convert);
                        if (paymentCurrency_convert.equals("EUR")) {
                            runText = runText.replace("{$APP_CUR}","20 " + paymentCurrency_convert);
                            runText = runText.replace("{$BANKDETAILS}","PL 55 1240 1936 1978 0010 0267 1667");
                        } else {
                            runText = runText.replace("{$APP_CUR}","85 " + paymentCurrency_convert);
                            runText = runText.replace("{$BANKDETAILS}","PL 67 1240 6478 1111 0000 4948 6670");
                        }
                        if (interviewIsSelected && runText.contains("{$INTERVIEW}")) {
                            runText = runText.replace("{$INTERVIEW}", "WSB Merito in Torun, Branch in Lodz" +
                                    " confirms that student has passed the internal online interview conducted by the representative" +
                                    " of the University.");
                            run.addBreak();
                        } else {
                            runText = runText.replace("{$INTERVIEW}", " ");

                        }

                        run.setText(runText, 0);
                    }
                }
            }
            try(FileOutputStream admissionOutput = new FileOutputStream(pathForSave + "Admission Letter " + fullName_convert + ".docx")){
                admissionInput.write(admissionOutput);
                admissionOutput.close();
                admissionInput.close();
            }
        }
    }


    //add years to variable currentYear_convert
    public String addYear(int year) {
        int currentYearToInt;
        try {
            currentYearToInt = Integer.parseInt(currentYear_convert);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        currentYearToInt +=year;
        return Integer.toString(currentYearToInt);
    }

    //find price for one installment
    public String instAmount(String tuition, String installments) {
        double b;
        double a;
        try {
            a = Double.parseDouble(tuition);
            b = Double.parseDouble(installments);
        } catch (Exception e) {
            return null;
        }
        int convert = (int)Math.floor(a / b);
        return String.valueOf(convert);
    }

}
