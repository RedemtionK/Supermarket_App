package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SuppliersWindow {
    private ArrayList<Suppliers> suppliers = new ArrayList <>();

    public SuppliersWindow(String name){
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

        String filePath = "Suppliers.txt";
        File f = new File(filePath);

        Scanner read = null;
        try {
            read = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (read.hasNextLine()){
            String temp;
            ArrayList<String> list = new ArrayList <>();
            Scanner check = new Scanner(read.nextLine());
            temp = check.next();
            while (check.hasNext()){
                list.add(check.next());
            }
            Suppliers s = new Suppliers(temp,list);
            suppliers.add(s);
        }

        for(int i = 0; i < suppliers.size(); i++){
            comboBox.getItems().add(suppliers.get(i).getName());
        }

        Button bRemove = new Button("Remove");
        Button bAdd = new Button("Add");

        TextArea taProducts = new TextArea();
        taProducts.setPrefSize(200,400);
        taProducts.setEditable(false);

        comboBox.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String s = "";
                for(int i = 0; i < suppliers.size(); i++) {
                    if (comboBox.getValue().equals(suppliers.get(i).getName())){
                        for(int j = 0; j < suppliers.get(i).getProducts().size(); j++) {
                            s += suppliers.get(i).getProducts().get(j) + "    " +"\n";
                        }
                        }
                }
                taProducts.setText(s);
            }
        });


        bAdd.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddSupplier addSupplier = new AddSupplier(name);
                primaryStage.close();
            }
        });

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(comboBox,bAdd);

        gridPane.add(bBack,0,0);
        gridPane.add(vBox, 0,1);
        gridPane.add(taProducts,1,1);

        Scene scene = new Scene(gridPane, 600, 500);


        primaryStage.setTitle("Cashiers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
