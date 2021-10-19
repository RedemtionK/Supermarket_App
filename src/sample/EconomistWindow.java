package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EconomistWindow{

    private String name;
    private ArrayList<String> outOfStock = new ArrayList <>();

    public String getName() {
        return name;
    }

    public EconomistWindow(String name) {
        this.name = name;
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        gridPane.setHgap(5.5);
        gridPane.setVgap(5.5);

        Stage primaryStage = new Stage();

        File f = new File("Stock.txt");
        try {
            Scanner read = new Scanner(f);
            read.nextLine();

            while (read.hasNextLine()) {
                Scanner check = new Scanner(read.nextLine());
                while (check.hasNext()) {
                  String tempName = check.next();
                  check.next();
                  if(Integer.parseInt(check.next()) < 5){
                      outOfStock.add(tempName);
                  }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(outOfStock.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("The following products are either out of stock or few of them are left.");
            alert.setResizable(false);
            String contentText = new String("");
            for(int i = 0; i < outOfStock.size(); i++) {
                contentText += outOfStock.get(i) + " ";
            }
            alert.setContentText(contentText);
            alert.showAndWait();
        }

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

        Button bSupply = new Button("SUPPLY");
        bSupply.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bSupply.setMinSize(0,0);
        bSupply.setPrefSize(250,250);
        bSupply.setAlignment(Pos.CENTER);

        Button bCashiers = new Button("CASHIERS");
        bCashiers.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bCashiers.setMinSize(0,0);
        bCashiers.setPrefSize(250,250);
        bCashiers.setAlignment(Pos.CENTER);

        HBox hbSuppCash = new HBox(15);
        hbSuppCash.getChildren().addAll(bSupply,bCashiers);
        bCashiers.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bCashiers.setMinSize(0,0);
        bCashiers.setAlignment(Pos.CENTER);

        Button bStats = new Button("STATISTICS");
        bStats.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bStats.setMinSize(0,0);
        bStats.setPrefSize(250,250);
        bStats.setAlignment(Pos.CENTER);

        Button bSuppliers = new Button("SUPPLIERS");
        bSuppliers.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bSuppliers.setMinSize(0,0);
        bSuppliers.setPrefSize(250,250);
        bSuppliers.setAlignment(Pos.TOP_CENTER);

        bSupply.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Supply supply = new Supply(getName());
                primaryStage.close();
            }
        });

        bCashiers.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Cashier cashier = new Cashier(getName());
                primaryStage.close();
            }
        });

        bStats.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Statistics stats = new Statistics(getName());
                primaryStage.close();
            }
        });

        bSuppliers.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SuppliersWindow suppliers = new SuppliersWindow(getName());
                primaryStage.close();
            }
        });





        HBox hbStatsSupps = new HBox(15);
        hbStatsSupps.getChildren().addAll(bStats,bSuppliers);
        bSuppliers.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bSuppliers.setAlignment(Pos.CENTER);

        gridPane.add(hbWelcome,0,0);
        gridPane.add(hbSuppCash,0,1);
        gridPane.add(hbStatsSupps,0,2);


        Scene scene = new Scene(gridPane, 600, 500);


        primaryStage.setTitle("EconomistWindow");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}