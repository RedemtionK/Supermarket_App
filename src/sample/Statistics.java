package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Statistics {

    private Date startDate =new Date();
    private Date endDate = new Date();
    private Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private ArrayList<String> list = new ArrayList <>();
    private ArrayList<String> list1 = new ArrayList <>();

    public Statistics(String name){
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
                    EconomistWindow eco = new EconomistWindow(name);
                    primaryStage.close();
                }
            }
        });

        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll( "Total", "Daily", "Monthly");
        comboBox.getSelectionModel().selectFirst();

        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.setMinWidth(0);
        comboBox.setPrefWidth(200);
        Label lDates = new Label("Enter dates (yyyy/MM/dd): ");
        lDates.setMaxWidth(Double.MAX_VALUE);
        lDates.setMinWidth(0);
        lDates.setPrefWidth(200);

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

        Label lSoldProducts = new Label("Sold Products : ");
        lSoldProducts.setMaxWidth(Double.MAX_VALUE);
        lSoldProducts.setMinWidth(0);
        lSoldProducts.setPrefWidth(200);

        Label lBoughtProducts = new Label("Bought Products : ");
        lBoughtProducts.setMaxWidth(Double.MAX_VALUE);
        lBoughtProducts.setMinWidth(0);
        lBoughtProducts.setPrefWidth(200);

        TextArea taSoldProducts = new TextArea();
        taSoldProducts.setStyle("-fx-text-fill: GREY;");
        taSoldProducts.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        taSoldProducts.setMinSize(0,0);
        taSoldProducts.setPrefSize(70,200);
        taSoldProducts.setEditable(false);

        TextArea taBoughtProducts = new TextArea();
        taBoughtProducts.setStyle("-fx-text-fill: GREY;");
        taBoughtProducts.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        taBoughtProducts.setMinSize(0,0);
        taBoughtProducts.setPrefSize(70,200);
        taBoughtProducts.setEditable(false);

        comboBox.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(comboBox.getValue().equals("Total")){
                    taStartDate.setText("2018/01/29");
                    taEndDate.setText(dateFormat.format(date));
                }
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                Date date2 = cal.getTime();
                cal.add(Calendar.DATE, -30);
                Date date3 = cal.getTime();
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
                            String temp = check.next();
                            String a = check.next();
                            String b = check.next();
                            String c = check.next();

                            date = dateFormat.parse(check.next());
                            if (date.getTime() - startDate.getTime() >= 0 &&
                                    endDate.getTime() - date.getTime() >= 0) {
                                list.add(temp);
                            }
                        }
                    }
                    while (read1.hasNextLine()) {
                        Scanner check = new Scanner(read1.nextLine());
                        if(check.hasNext()) {
                            String temp = check.next();
                            check.next();
                            check.next();
                            date = dateFormat.parse(check.next());
                            if (date.getTime() - startDate.getTime() >= 0 &&
                                    endDate.getTime() - date.getTime() >= 0) {
                                list1.add(temp);
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String s = "";
                String s1 = "";
                for(int i = 0; i < list.size(); i++){
                    s+=list.get(i) + "\n";
                }
                taSoldProducts.setText(s);
                for(int i = 0; i < list1.size(); i++){
                    s1+=list1.get(i) + "\n";
                }
                taBoughtProducts.setText(s1);

                }
        });

        gridPane.add(bBack,0,0);
        gridPane.add(comboBox,0,2);
        gridPane.add(bCheck,1,2);
        gridPane.add(lDates,0,1);
        gridPane.add(taStartDate,1,1);
        gridPane.add(taEndDate,2,1);
        gridPane.add(lSoldProducts,0,3);
        gridPane.add(lBoughtProducts,1,3);
        gridPane.add(taSoldProducts,0,4);
        gridPane.add(taBoughtProducts,1,4);
        Scene scene = new Scene(gridPane, 600, 500);

        primaryStage.setTitle("Statistics");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
