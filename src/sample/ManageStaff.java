package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class ManageStaff {

    public ManageStaff(String name) throws FileNotFoundException {

        Stage primaryStage = new Stage();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        gridPane.setHgap(5.5);
        gridPane.setVgap(5.5);

        Button bBack = new Button("Back");


        bBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                {
                    AdminWindow adminWindow = new AdminWindow(name);
                    primaryStage.close();
                }
            }
        });


        Label lName = new Label("Name");
        Label lPassword = new Label("Password");
        Label lAccount = new Label("Access Level");
        Label lSalary = new Label("Salary");
        Label lBirthdate = new Label("Birthdate");
        Label lEmail = new Label("E-Mail");

        TextField tfName = new TextField();
        tfName.setEditable(false);
        TextField tfPassword = new TextField();
        TextField tfSalary = new TextField();
        TextField tfBirthdate = new TextField();
        TextField tfEmail = new TextField();

        ComboBox cbAccountLevel = new ComboBox();
        cbAccountLevel.getItems().addAll("Cashier" , "Economist", "Admin");

        Button bRemove = new Button("REMOVE");
        Button bModify = new Button("MODIFY");
        Button bRegister = new Button("Register");

        ComboBox comboBox = new ComboBox();

        String addNew = new String("Add New");
        comboBox.getItems().add(addNew);
        File fUsers = new File("Users.txt");

        Scanner reader = new Scanner(fUsers);
        reader.nextLine();
        while(reader.hasNext()){
            Scanner check = new Scanner(reader.nextLine());
                comboBox.getItems().add(check.next());

        }

        comboBox.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Scanner read = new Scanner(fUsers);
                    read.nextLine();
                    while (read.hasNextLine()){
                        Scanner check = new Scanner(read.nextLine());
                        String temp = check.next();
                        if(comboBox.getValue().equals("Add New")){
                            tfName.setText("");
                            tfPassword.setText("");
                            cbAccountLevel.valueProperty().set(null);
                            tfSalary.setText("");
                            tfBirthdate.setText("");
                            tfEmail.setText("");
                            tfName.setEditable(true);
                            break;

                        }


                        else if(temp.equals(comboBox.getValue())){
                                tfName.setText((String) comboBox.getValue());
                                tfPassword.setText(check.next());
                                cbAccountLevel.setValue(check.next());
                                tfSalary.setText(check.next());
                                tfBirthdate.setText(check.next());
                                tfEmail.setText(check.next());
                                tfName.setEditable(false);
                                break;

                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    }

            }
        });

        bModify.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!comboBox.getValue().equals("Add New")) {
                    String newString = tfName.getText() + " " + tfPassword.getText() + " "
                            + String.valueOf(cbAccountLevel.getValue()) + " " +tfSalary.getText() + " "
                            +tfBirthdate.getText() + " " +tfEmail.getText() + " " + "\n";

                    try {
                        modifyFileByName("Users.txt",newString,tfName.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        ManageStaff manageStaff = new ManageStaff(name);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    primaryStage.close();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Couldn't modify. Please select an user first.");
                    alert.setResizable(false);
                    alert.showAndWait();
                }
            }
        });

        bRegister.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(comboBox.getValue().equals("Add New")){
                    String newString = new String("");
                    newString += tfName.getText() + " " + tfPassword.getText() + " "
                            + String.valueOf(cbAccountLevel.getValue()) + " " + tfSalary.getText() + " "
                            + tfBirthdate.getText() + " " + tfEmail.getText() + "\n";
                    try(Writer fileWriter = new FileWriter("Users.txt", true)){
                        fileWriter.write(newString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        ManageStaff manageStaff = new ManageStaff(name);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    primaryStage.close();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("This user is already registered. Press MODIFY if you want to change data.");
                    alert.setResizable(false);
                    alert.showAndWait();

                }
            }
        });

        bRemove.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!comboBox.getValue().equals("Add New")){
                    String newString = new String();
                    newString = tfName.getText() + " " + tfPassword.getText() + " "
                            + String.valueOf(cbAccountLevel.getValue()) + " " + tfSalary.getText() + " "
                            + tfBirthdate.getText() + " " + tfEmail.getText() + "\n";
                    try {
                        removeLineFromFile("Users.txt",newString,tfName.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        ManageStaff manageStaff = new ManageStaff(name);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    primaryStage.close();
                }

            }
        });






        gridPane.add(bBack,0,0);
        gridPane.add(comboBox, 0,1);
        gridPane.add(lName,0,2);
        gridPane.add(lPassword,0,3);
        gridPane.add(lAccount,0,4);
        gridPane.add(lSalary,0,5);
        gridPane.add(lBirthdate,0,6);
        gridPane.add(lEmail,0,7);
        gridPane.add(tfName,1,2);
        gridPane.add(tfPassword,1,3);
        gridPane.add(cbAccountLevel,1,4);
        gridPane.add(tfSalary,1,5);
        gridPane.add(tfBirthdate,1,6);
        gridPane.add(tfEmail,1,7);
        gridPane.add(bRegister, 0,8);
        gridPane.add(bModify, 1,8);
        gridPane.add(bRemove, 2,8);


        Scene scene = new Scene(gridPane, 600, 500);


        primaryStage.setTitle("Supply");
        primaryStage.setScene(scene);
        primaryStage.show();

    }




  public void modifyFileByName(String filePath, String newString, String name) throws IOException {
        File f = new File(filePath);
        String newText = new String("");
        try {
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()){

                Scanner check = new Scanner(reader.nextLine());
                if(check.hasNextLine()) {
                    String temp = check.next();
                    if (!name.equals(temp)) {
                        newText += temp + " " + check.nextLine() + "\n";
                    } else {
                        newText += newString;
                    }
                }
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(newText);
        writer.close();



    }


   public void removeLineFromFile(String file, String lineToRemove, String name) throws IOException {

        File f = new File(file);
        String newText = new String("");


        try {
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                Scanner check = new Scanner(reader.nextLine());
                String temp = new String();
                String tempName = check.next();
                if(!tempName.equals(name)) {
                    temp = tempName + " ";
                    while (check.hasNext()) {
                        temp += check.next() + " ";
                    }

                        newText += temp + "\n";

                }

            }

            reader.close();

            } catch(FileNotFoundException e){
                e.printStackTrace();
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(newText);
            bufferedWriter.close();


        }
    }

