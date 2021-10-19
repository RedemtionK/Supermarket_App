package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Finance {
    private Date startDate =new Date();
    private Date endDate = new Date();
    private Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private int incomeSum = 0;
    private int outcomeSum = 0;
    private int salaryOutcome = 0;
    public Finance(String name){




            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.TOP_CENTER);
            gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
            gridPane.setHgap(5.5);
            gridPane.setVgap(5.5);

            Stage primaryStage = new Stage();

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

            ComboBox comboBox = new ComboBox();
            comboBox.getItems().addAll( "Daily", "Monthly", "Yearly");

            comboBox.setMaxWidth(Double.MAX_VALUE);
            comboBox.setMinWidth(0);
            comboBox.setPrefWidth(200);


            TextArea taStartDate = new TextArea("2018/01/29");
            taStartDate.setStyle("-fx-text-fill: GREY;");
            taStartDate.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            taStartDate.setMinSize(0,0);
            taStartDate.setPrefSize(200,30);

            TextArea taEndDate = new TextArea(dateFormat.format(date));
            taEndDate.setStyle("-fx-text-fill: GREY;");
            taEndDate.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            taEndDate.setMinSize(0,0);
            taEndDate.setPrefSize(200,30);

            Label lIncome = new Label("Income : ");
            lIncome.setMaxWidth(Double.MAX_VALUE);
            lIncome.setMinWidth(0);
            lIncome.setPrefWidth(200);

            Label lOutcome = new Label("Outcome : ");
            lOutcome.setMaxWidth(Double.MAX_VALUE);
            lOutcome.setMinWidth(0);
            lOutcome.setPrefWidth(200);

            TextField tfIncome = new TextField();
            tfIncome.setStyle("-fx-text-fill: GREY;");
            tfIncome.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            tfIncome.setMinSize(0,0);
            tfIncome.setPrefSize(70,200);
            tfIncome.setEditable(false);

            TextField tfOutcome = new TextField();
            tfOutcome.setStyle("-fx-text-fill: GREY;");
            tfOutcome.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            tfOutcome.setMinSize(0,0);
            tfOutcome.setPrefSize(70,200);
            tfOutcome.setEditable(false);

            comboBox.setOnAction(new EventHandler <ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -1);
                    Date date2 = cal.getTime();
                    cal.add(Calendar.DATE, -30);
                    Date date3 = cal.getTime();
                    cal.add(Calendar.DATE, - 365);
                    Date date4 = cal.getTime();
                    if(comboBox.getValue().equals("Yearly")){
                        taStartDate.setText(dateFormat.format(date));
                        taEndDate.setText(dateFormat.format(date4));
                    }
                    if(comboBox.getValue().equals("Daily")){
                        taEndDate.setText(dateFormat.format(date));

                        taStartDate.setText(dateFormat.format(date2));
                    }
                    if(comboBox.getValue().equals("Monthly")){
                        taEndDate.setText(dateFormat.format(date));
                        taStartDate.setText(dateFormat.format(date3));
                    }
                }
            });

            Button bCheck = new Button("Check");

            bCheck.setOnAction(new EventHandler <ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    incomeSum = 0;
                    outcomeSum = 0;
                    salaryOutcome = 0;
                    try {
                        startDate = dateFormat.parse(taStartDate.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        endDate = dateFormat.parse(taEndDate.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    File f = new File("ProductsSold.txt");
                    File f1 = new File("ProductsBought.txt");
                    try {
                        Scanner read = new Scanner(f);
                        Scanner read1 = new Scanner(f1);

                        while (read.hasNextLine()) {
                            Scanner check = new Scanner(read.nextLine());
                            if(check.hasNext()) {
                                String a = check.next();
                                String b = check.next();
                                String c = check.next();
                                String temp = check.next();

                                date = dateFormat.parse(check.next());
                                if (date.getTime() - startDate.getTime() >= 0 &&
                                        endDate.getTime() - date.getTime() >= 0) {
                                        incomeSum += Integer.parseInt(temp);
                                }
                            }
                        }
                        while (read1.hasNextLine()) {
                            Scanner check = new Scanner(read1.nextLine());
                            if(check.hasNext()) {
                                String temp = check.next();
                                String pu = check.next();
                                String qnt = check.next();
                                date = dateFormat.parse(check.next());
                                if (date.getTime() - startDate.getTime() >= 0 &&
                                        endDate.getTime() - date.getTime() >= 0) {
                                    outcomeSum += Integer.parseInt(pu) * Integer.parseInt(qnt);

                                }
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    File fUsers = new File("Users.txt");

                    try {
                        Scanner reader = new Scanner(fUsers);
                        reader.nextLine();
                        while (reader.hasNextLine()){
                            Scanner check = new Scanner(reader.nextLine());
                            if(check.hasNext())
                                check.next();
                            if(check.hasNext())
                            check.next();
                            if(check.hasNext())
                                check.next();
                            String temp;
                            if(check.hasNext()) {
                                temp = check.next();
                               salaryOutcome += Integer.parseInt(temp);
                            }

                            }
                        if(comboBox.getValue().equals("Yearly"))
                            salaryOutcome *= 12;


                        if(comboBox.getValue().equals("Daily"))
                            salaryOutcome /= 30;

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    outcomeSum += salaryOutcome;
                    tfIncome.setText(String.valueOf(incomeSum));
                    tfOutcome.setText(String.valueOf(outcomeSum));
                }

            });








            gridPane.add(bBack,0,0);
            gridPane.add(comboBox,0,2);
            gridPane.add(bCheck,1,2);
            gridPane.add(taStartDate,1,1);
            gridPane.add(taEndDate,2,1);
            gridPane.add(lIncome,0,3);
            gridPane.add(lOutcome,1,3);
            gridPane.add(tfIncome,0,4);
            gridPane.add(tfOutcome,1,4);
            Scene scene = new Scene(gridPane, 600, 500);

            primaryStage.setTitle("Statistics");
            primaryStage.setScene(scene);
            primaryStage.show();


        }
}
