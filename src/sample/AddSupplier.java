package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class AddSupplier {

    public AddSupplier(String name){
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
                    SuppliersWindow suppliersWindow = new SuppliersWindow(name);
                    primaryStage.close();
                }
            }
        });

        TextField tfName= new TextField();
        TextArea taProucts = new TextArea();

        Button bAdd = new Button("Add");

        HBox hbName = new HBox();
        hbName.getChildren().addAll(new Label("Supplier name: "), tfName);

        HBox hbProducts = new HBox();
        hbProducts.getChildren().addAll(new Label("Products: "), taProucts);



        String filePath = "Suppliers.txt";

        bAdd.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String supplier = tfName.getText() + " " + taProucts.getText() + "\n";

                try (Writer fileWriter = new FileWriter(filePath,true)){
                    fileWriter.write(supplier);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                SuppliersWindow suppliersWindow = new SuppliersWindow(name);
                primaryStage.close();

            }
        });

        gridPane.add(bBack,0,0);
        gridPane.add(hbName,0,1);
        gridPane.add(hbProducts,0,2);
        gridPane.add(bAdd,0,3);

        Scene scene = new Scene(gridPane, 600, 500);


        primaryStage.setTitle("Cashiers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
