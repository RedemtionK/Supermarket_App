package sample;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CashierWindow{

    private ArrayList<Product> products = new ArrayList <Product>();
    private String name;
    private int totalPrice = 0;
    private int alertCounter = 0; //Alert if a product is out of stock

    public ArrayList <Product> getProducts() {
        return products;
    }

    public int getAlertCounter() {
        return alertCounter;
    }

    public void setAlertCounter(int alertCounter) {
        this.alertCounter = alertCounter;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    //Declare label controls for the different BorderPane areas


    public CashierWindow(String name) throws IOException {
        this.name = name;
        Stage primaryStage = new Stage();

        ComboBox comboBox = new ComboBox();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        gridPane.setHgap(5.5);
        gridPane.setVgap(5.5);


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

        HBox hbSelectProduct = new HBox(15);


        File f = new File("Stock.txt");
        try {
            Scanner read = new Scanner(f);
            read.nextLine();
            //loop through every line in the file and check against the user name & password
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

        int qnt;
        TextArea taQuantity = new TextArea();
        taQuantity.setMinSize(10,10);
        taQuantity.setPrefSize(50,10);
        Button bAdd = new Button("Add");
        bAdd.setMinSize(100,10);
        hbSelectProduct.getChildren().addAll(new Label("Product: "), comboBox, new Label("Quantity: "), taQuantity, bAdd);

        //BILL NUMBER and Price
        TextField tfBillNo = new TextField();
        tfBillNo.setEditable(false);
        File fBillNo = new File("BillNumber.txt");
        Scanner sBillNo = new Scanner(fBillNo);
        int iBillNo = Integer.parseInt(sBillNo.next()) + 1;
        tfBillNo.setText(String.valueOf(iBillNo));
        HBox hbBillNo = new HBox(15);
        TextArea taBillPrice = new TextArea("0");
        taBillPrice.setEditable(false);
        HBox hbTotal = new HBox(15);
        hbTotal.setPrefSize(300,20);
        hbTotal.setMaxSize(300,20);


        Label label1 = new Label("Total : ");
        label1.setMinWidth(90);
        hbTotal.getChildren().addAll(label1, taBillPrice);

        Label label = new Label("Bill NO : ");
        label.setMinWidth(80);
        hbBillNo.getChildren().addAll(label, tfBillNo);
        hbBillNo.setMaxSize(180,20);



        VBox vbBill = new VBox();


        bAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextField tfProduct = new TextField((String) comboBox.getValue());
                TextField tfQuantity = new TextField(taQuantity.getText());
                tfProduct.setEditable(false);
                tfQuantity.setEditable(false);

                TextArea taPrice = new TextArea();
                taPrice.setEditable(false);
                taPrice.setMinSize(10,10);
                taPrice.setPrefSize(50,10);
                File f = new File("Stock.txt");
                try {
                    Scanner read = new Scanner(f);
                    read.nextLine();

                    while (read.hasNextLine()) {
                        Scanner check = new Scanner(read.nextLine());
                        String tempName = check.next();
                        if(tempName.equals(comboBox.getValue())){
                            int tempPrice = Integer.parseInt(check.next());
                            taPrice.setText(String.valueOf(tempPrice));
                            int tempQnt = Integer.parseInt(taQuantity.getText());
                            int remainingQnt = Integer.parseInt(check.next());
                            if(tempQnt <= remainingQnt) {
                                Product p = new Product(tempName, tempPrice, tempQnt);
                                products.add(p);
                            }
                            taBillPrice.setText(String.valueOf(getTotalPrice() + (Integer.parseInt(taPrice.getText()))*(Integer.parseInt(String.valueOf(tfQuantity.getText())))));
                            setTotalPrice(Integer.parseInt(taBillPrice.getText()));
                            setAlertCounter(0);
                            if(tempQnt > remainingQnt) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Warning");
                                alert.setHeaderText("There is only " + remainingQnt + " left of this product");
                                alert.setResizable(false);
                                alert.setContentText("Please enter another quantity for the product.");
                                alert.showAndWait();
                                setAlertCounter(2);
                                setTotalPrice(getTotalPrice() - tempPrice*tempQnt);
                            }
                            if(tempQnt == remainingQnt) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Warning");
                                alert.setHeaderText("This product is now out of stock.");
                                alert.setResizable(false);
                                alert.showAndWait();
                                setAlertCounter(1);
                            }

                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                if(getAlertCounter()<2) {
                    HBox hbBill = new HBox(15);
                    hbBill.getChildren().addAll(tfProduct, tfQuantity, taPrice);
                    vbBill.getChildren().addAll(hbBill);
                }

            }
        });

        Button bSumbit = new Button("Submit");
        Button bNew = new Button("New");
        HBox hbSubNew = new HBox(15);
        hbSubNew.getChildren().addAll(bSumbit,bNew);
        vbBill.getChildren().add(hbSubNew);

        bNew.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    CashierWindow cashier = new CashierWindow(name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    primaryStage.close();

            }
        });

        bSumbit.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Product> list = new ArrayList <>();
                String pathName = "Stock.txt";
                String pathFile = "ProductsSold.txt";
                ArrayList<Product> list1;
                list1 = setList(pathName,list);
                File f = new File("BillsPrinted.txt");
                //Here true is to append the content to file
                File f1 = new File(pathFile);
                FileWriter fw1 = null;
                FileWriter fw = null;
                try {
                    fw = new FileWriter(f, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    fw1 = new FileWriter(f1, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //BufferedWriter writer give better performance
                BufferedWriter bw = new BufferedWriter(fw);
                BufferedWriter bw1 = new BufferedWriter(fw1);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();


                try {
                    bw.write(name +  " BillNO:" + tfBillNo.getText() + "  Total: " + taBillPrice.getText() + " Date: " + dateFormat.format(date) + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < products.size(); i++) {
                    try {
                    bw.write(products.get(i).getName() + " "
                            + products.get(i).getPrice() + " "
                            + products.get(i).getQuantity() + " "
                            + products.get(i).getPrice() * products.get(i).getQuantity()
                            + " " + dateFormat.format(date)
                            + "\n");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                    try {
                        bw1.write(products.get(i).getName() + " "
                                + products.get(i).getPrice() + " "
                                + products.get(i).getQuantity() + " "
                                + products.get(i).getPrice() * products.get(i).getQuantity()
                                + " " + dateFormat.format(date)
                                + "\n");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    //Closing BufferedWriter Stream

                    for(int j = 0; j < list1.size(); j++) {


                        String oldS = list1.get(j).getName() + " " + list1.get(j).getPrice() + " " + list1.get(j).getQuantity();
                        if(products.get(i).getName().equals(list1.get(j).getName())){
                            products.get(i).setQuantity(list1.get(j).getQuantity() - products.get(i).getQuantity());
                            String newS = products.get(i).getName() + " " + products.get(i).getPrice() + " " + products.get(i).getQuantity() + "\n";
                            try {
                                modifyFileByName(pathName,newS,list1.get(j).getName());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            list1.get(j).setQuantity(products.get(i).getQuantity());
                            break;
                        }
                     }
                }

                try {
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    bw1.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                Writer fwBillNo = null;
                try {
                    fwBillNo = new FileWriter("BillNumber.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    fwBillNo.write(String.valueOf(tfBillNo.getText()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    fwBillNo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Complete");
                alert.setHeaderText("The bill has been printed in BillsPrinted.");
                alert.setResizable(false);
                alert.showAndWait();
                try {
                    CashierWindow cashier = new CashierWindow(name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.close();


            }
            });


        gridPane.add(hbWelcome,0,0);
        gridPane.add(hbSelectProduct, 0,3);
        gridPane.add(hbBillNo,0,4);
        gridPane.add(hbTotal,0,5);
        gridPane.add(vbBill,0,6);




        Scene scene = new Scene(gridPane, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();


        //A simple method which changes the visibility of the
        //labels depending on the string passed


    }

    /*static void modifyFile(String filePath, String oldString, String newString)
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
    }*/
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



}