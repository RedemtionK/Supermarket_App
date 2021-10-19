package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Supply {

    private String name;

    Supply(String name) {
        this.name = name;

        Stage primaryStage = new Stage();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        gridPane.setHgap(5.5);
        gridPane.setVgap(5.5);

        Button bBack = new Button("Back");


        bBack.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                {
                    EconomistWindow eco = new EconomistWindow(name);
                    primaryStage.close();
                }
            }
        });



        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("New Product");
        File f = new File("Stock.txt");
        try {
            Scanner read = new Scanner(f);
            read.nextLine();
            while (read.hasNextLine()) {
                Scanner check = new Scanner(read.nextLine());
                while (check.hasNext()) {
                    comboBox.getItems().addAll(check.next());
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.setMinWidth(0);
        comboBox.setPrefWidth(200);

        TextArea taProductName = new TextArea();
        taProductName.setEditable(false);
        taProductName.setStyle("-fx-text-fill: GREY;");
        taProductName.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        taProductName.setMinSize(0,0);
        taProductName.setPrefSize(200,10);
        TextArea taProductPrice = new TextArea();
        taProductPrice.setEditable(false);
        taProductPrice.setStyle("-fx-text-fill: GREY;");
        taProductPrice.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        taProductPrice.setMinSize(0,0);
        taProductPrice.setPrefSize(200,10);
        TextArea taPricePerUnit = new TextArea();
        taPricePerUnit.setStyle("-fx-text-fill: GREY;");
        taPricePerUnit.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        taPricePerUnit.setMinSize(0,0);
        taPricePerUnit.setPrefSize(200,30);
        TextArea taQuantity = new TextArea();
        taQuantity.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        taQuantity.setMinSize(0,0);
        taQuantity.setPrefSize(200,10);

        HBox hBox1 = new HBox(15);
        Label lChooseProd = new Label("Choose product:");
        lChooseProd.setMaxWidth(Double.MAX_VALUE);
        lChooseProd.setMinWidth(0);
        lChooseProd.setPrefWidth(200);
        Label lProdName = new Label("Product name: ");
        lProdName.setMaxWidth(Double.MAX_VALUE);
        lProdName.setMinWidth(0);
        lProdName.setPrefWidth(200);
        Label lPriceUnit = new Label("Price/Unit (sell): ");
        lPriceUnit.setMaxWidth(Double.MAX_VALUE);
        lPriceUnit.setMinWidth(0);
        lPriceUnit.setPrefWidth(200);
        Label lPriceUnitb = new Label("Price/Unit (buy): ");
        lPriceUnitb.setMaxWidth(Double.MAX_VALUE);
        lPriceUnitb.setMinWidth(0);
        lPriceUnitb.setPrefWidth(200);
        Label lQuantity = new Label("Quantity: ");
        lQuantity.setMaxWidth(Double.MAX_VALUE);
        lQuantity.setMinWidth(0);
        lQuantity.setPrefWidth(200);


        comboBox.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(comboBox.getValue().equals("New Product")){
                    taProductName.setEditable(true);
                    taProductPrice.setEditable(true);
                    taProductName.setText("");
                    taProductPrice.setText("");
                }
                if(!comboBox.getValue().equals("New Product")){
                    taProductName.setEditable(false);
                    taProductPrice.setEditable(false);
                    taProductName.setText((String) comboBox.getValue());
                    File f = new File("Stock.txt");
                    try {
                        Scanner read = new Scanner(f);
                        read.nextLine();
                        while (read.hasNextLine()) {
                            Scanner check = new Scanner(read.nextLine());
                            while (check.hasNext()) {
                                if(comboBox.getValue().equals(check.next())){
                                    taProductPrice.setText(check.next());
                                }
                                break;

                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        Button bAdd = new Button("Add");

        bAdd.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Product> list = new ArrayList <>();
                String pathName = "Stock.txt";
                ArrayList<Product> list1;
                list1 = setList(pathName,list);

                File fs = new File("Stock.txt");

                FileWriter fw = null;
                try {
                    fw = new FileWriter(fs, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                int success = 0;
                //BufferedWriter writer give better performance
                BufferedWriter bw = new BufferedWriter(fw);
                    for(int i = 0; i < list1.size(); i++) {
                        String oldS = list1.get(i).getName() + " " + list1.get(i).getPrice() + " " + list1.get(i).getQuantity();
                        if(list1.get(i).getName().equals(comboBox.getValue())){
                                    list1.get(i).setQuantity(list1.get(i).getQuantity() + Integer.parseInt(taQuantity.getText()));
                                    String newS = list1.get(i).getName() + " " +  list1.get(i).getPrice() + " " + list1.get(i).getQuantity() + "\n";
                            try {
                                modifyFileByName(pathName,newS,list1.get(i).getName());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            success = 1;
                                    break;
                        }
                    }
                    if(success == 0){
                        try {
                            bw.write(taProductName.getText() + " " + taProductPrice.getText() + " " +  taQuantity.getText() + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                try {
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                String filePath = "ProductsBought.txt";
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                String productBought = taProductName.getText() + " " + taPricePerUnit.getText()
                                        + " " + taQuantity.getText() + " " + dateFormat.format(date) + "\n";

                try (Writer fileWriter = new FileWriter(filePath,true)){
                    fileWriter.write(productBought);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Complete");
                alert.setHeaderText("The products have been added successfully.");
                alert.setResizable(false);
                alert.showAndWait();
                Supply supply = new Supply(name);
                primaryStage.close();


            }
        });

        gridPane.add(bBack,0,0);
        gridPane.add(lChooseProd,0,1);
        gridPane.add(lProdName,1,1);
        gridPane.add(lPriceUnit,2,1);
        gridPane.add(lQuantity,3,1);
        gridPane.add(comboBox,0,2);
        gridPane.add(taProductName,1,2);
        gridPane.add(taProductPrice,2,2);
        gridPane.add(taQuantity,3,2);
        gridPane.add(bAdd,3,3);
        gridPane.add(lPriceUnitb,0,4);
        gridPane.add(taPricePerUnit,1,4);

        Scene scene = new Scene(gridPane, 600, 500);


        primaryStage.setTitle("Supply");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    static void modifyFile(String filePath, String oldString, String newString)
    {
        File fileToBeModified = new File(filePath);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            //Reading all the lines of input text file into oldContent

            String line = reader.readLine();

            while (line != null)
            {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }

            //Replacing oldString with newString in the oldContent

            //  String newContent = oldContent.replaceAll(oldString, newString);
            String newtext = oldContent.replaceAll(oldString, newString);

            //Rewriting the input text file with newContent

            writer = new FileWriter(fileToBeModified);
            writer.write(newtext);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources

                reader.close();

                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public ArrayList <Product> setList(String pathname, ArrayList<Product> list) {

        File f = new File(pathname);
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        s.nextLine();

        while (s.hasNextLine()){
            Scanner read = new Scanner(s.nextLine());
            String a = read.next();
            int b =Integer.parseInt(read.next());
            int c = Integer.parseInt(read.next());
            Product p = new Product(a,b,c);
            list.add(p);
        }
        s.close();

        return list;
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
}
