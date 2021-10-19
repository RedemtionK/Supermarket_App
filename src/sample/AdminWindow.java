package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AdminWindow{



    public AdminWindow(String name) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        gridPane.setHgap(5.5);
        gridPane.setVgap(5.5);

        Stage primaryStage = new Stage();


        Button bLogOut = new Button("Log Out");

        HBox hbWelcome = new HBox(15);
        hbWelcome.setStyle("-fx-font-weight: bold;");
        hbWelcome.getChildren().addAll(new Label("Welcome " + name),bLogOut);
        hbWelcome.setMinWidth(400);

        bLogOut.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                {
                    LogIn logIn = new LogIn();
                    System.out.println();
                    primaryStage.close();
                }
            }
        });

        Button bCashier = new Button("CASHIER");
        bCashier.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bCashier.setMinSize(0,0);
        bCashier.setPrefSize(250,250);
        bCashier.setAlignment(Pos.CENTER);

        Button bEconomist = new Button("ECONOMIST");
        bEconomist.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bEconomist.setMinSize(0,0);
        bEconomist.setPrefSize(250,250);
        bEconomist.setAlignment(Pos.CENTER);

        HBox hbCashEco = new HBox(15);
        hbCashEco.getChildren().addAll(bCashier,bEconomist);
        bEconomist.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bEconomist.setMinSize(0,0);
        bEconomist.setAlignment(Pos.CENTER);

        Button bStaff = new Button("MANAGE STAFF");
        bStaff.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bStaff.setMinSize(0,0);
        bStaff.setPrefSize(250,250);
        bStaff.setAlignment(Pos.CENTER);

        Button bFinance = new Button("FINANCE");
        bFinance.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bFinance.setMinSize(0,0);
        bFinance.setPrefSize(250,250);
        bFinance.setAlignment(Pos.TOP_CENTER);

        bCashier.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    CashierWindow cashierWindow = new CashierWindow(name);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        bEconomist.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EconomistWindow economist = new EconomistWindow(name);

            }
        });

        bStaff.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ManageStaff staff = new ManageStaff(name);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                primaryStage.close();
            }
        });

        bFinance.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                  Finance finance = new Finance(name);
                primaryStage.close();
            }
        });





        HBox hbStatsSupps = new HBox(15);
        hbStatsSupps.getChildren().addAll(bStaff,bFinance);
        bFinance.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bFinance.setAlignment(Pos.CENTER);

        gridPane.add(hbWelcome,0,0);
        gridPane.add(hbCashEco,0,1);
        gridPane.add(hbStatsSupps,0,2);


        Scene scene = new Scene(gridPane, 600, 500);


        primaryStage.setTitle("Admin Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}