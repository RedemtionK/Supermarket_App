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
import java.util.*;

public class Cashier {
    private Map<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
    String name;
   int totNumOfBills = 0;
   ArrayList<String > products = new ArrayList <>();
   int totMoney = 0;
   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");




        Date startDate;

        Date endDate;

   Cashier(String economistName){

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
               EconomistWindow eco = new EconomistWindow(economistName);
               primaryStage.close();
            }
         }
      });

      ComboBox comboBox = new ComboBox();
      File f = new File("Users.txt");
      try {
         Scanner read = new Scanner(f);
         read.nextLine();
         while (read.hasNextLine()) {
            Scanner check = new Scanner(read.nextLine());
            while (check.hasNext()) {
               String tempName = check.next();
               check.next();
               if(check.next().equals("Cashier")) {
                  comboBox.getItems().addAll(tempName);
                  map.put(tempName, new ArrayList <>());
               }
            }
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      comboBox.setMaxWidth(Double.MAX_VALUE);
      comboBox.setMinWidth(0);
      comboBox.setPrefWidth(200);

      TextArea taListOfProducts = new TextArea();
      taListOfProducts.setMaxWidth(Double.MAX_VALUE);
      taListOfProducts.setMinWidth(0);
      taListOfProducts.setPrefWidth(200);
      taListOfProducts.setPrefHeight(20);
      taListOfProducts.setEditable(false);
      Label lListOfProds = new Label("List of products sold:");
      lListOfProds.setMaxWidth(Double.MAX_VALUE);
      lListOfProds.setMinWidth(0);
      lListOfProds.setPrefWidth(200);

       Label lDates = new Label("Enter dates (yyyy/MM/dd): ");
       lDates.setMaxWidth(Double.MAX_VALUE);
       lDates.setMinWidth(0);
       lDates.setPrefWidth(200);

      TextArea taStartDate = new TextArea("2018/01/30");
       taStartDate.setStyle("-fx-text-fill: GREY;");
       taStartDate.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
       taStartDate.setMinSize(0,0);
       taStartDate.setPrefSize(200,30);


      TextArea taEndDate = new TextArea("2018/01/30");
       taEndDate.setStyle("-fx-text-fill: GREY;");
       taEndDate.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
       taEndDate.setMinSize(0,0);
       taEndDate.setPrefSize(200,30);

      Label lChooseCash = new Label("Choose cashier:");
      lChooseCash.setMaxWidth(Double.MAX_VALUE);
      lChooseCash.setMinWidth(0);
      lChooseCash.setPrefWidth(200);

      Label lTotMoney = new Label("Total Money:");
      lTotMoney.setMaxWidth(Double.MAX_VALUE);
      lTotMoney.setMinWidth(0);
      lTotMoney.setPrefWidth(200);

      Label lTotNumBills = new Label("Number of bills:");
      lTotNumBills.setMaxWidth(Double.MAX_VALUE);
      lTotNumBills.setMinWidth(0);
      lTotNumBills.setPrefWidth(200);

      TextArea taMoneyMaid = new TextArea();
      taMoneyMaid.setEditable(false);
      taMoneyMaid.setStyle("-fx-text-fill: GREY;");
      taMoneyMaid.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
      taMoneyMaid.setMinSize(0,0);
      taMoneyMaid.setPrefSize(200,10);


      TextArea taTotNumBills = new TextArea();
      taTotNumBills.setEditable(false);
      taTotNumBills.setStyle("-fx-text-fill: GREY;");
      taTotNumBills.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
      taTotNumBills.setMinSize(0,0);
      taTotNumBills.setPrefSize(200,10);
      taTotNumBills.setEditable(false);




       Button bCheck = new Button("Check");

      bCheck.setOnAction(new EventHandler <ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              taMoneyMaid.setText("");
              taMoneyMaid.setText("");
              taListOfProducts.setText("");
              totMoney = 0;
              totNumOfBills = 0;
              int mon;
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
              Date date;
              File f = new File("BillsPrinted.txt");
              try {
                  Scanner read = new Scanner(f);
                  while (read.hasNextLine()) {
                      Scanner check = new Scanner(read.nextLine());
                      while (check.hasNext()) {
                         if(check.next().equals(comboBox.getValue())){
                             check.next();
                             check.next();
                             mon = Integer.parseInt(check.next());
                             check.next();
                             date = dateFormat.parse(check.next());

                             if ((date.getTime() - startDate.getTime() >= 0) && (endDate.getTime() - date.getTime() >= 0)) {
                                 totNumOfBills++;
                                 totMoney += mon;
                                 break;
                             }
                             }
                      }
                  }
                  taTotNumBills.setText(String.valueOf(totNumOfBills));
                  taMoneyMaid.setText(String.valueOf(totMoney));

                  File fBillsPrinted = new File("BillsPrinted.txt");
                  try {
                      Scanner reads = new Scanner(fBillsPrinted);
                      reads.nextLine();
                      String keyName = new String();
                      while (reads.hasNextLine()) {
                          Scanner check = new Scanner(reads.nextLine());
                          while (check.hasNext()) {
                              String tempName=check.next();
                              if(map.containsKey(tempName) ){
                                  check.next();
                                  check.next();
                                  check.next();
                                  check.next();
                                  date = dateFormat.parse(check.next());
                                  if((date.getTime() - startDate.getTime() >= 0) && (endDate.getTime() - date.getTime() >= 0)){
                                    keyName = tempName;
                                    break;
                                  }

                              }
                              if(!map.containsKey((tempName))){
                                  List<String> l = map.get(keyName);
                                  if(l == null) map.put(keyName, (ArrayList <String>) (l = new ArrayList <String>()));
                                  l.add(tempName);
                                  break;
                              }
                          }
                      }
                  } catch (FileNotFoundException e) {
                      e.printStackTrace();
                  }

                  String lpr = "";
                  for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                      String key = entry.getKey();
                      ArrayList<String> values = entry.getValue();
                      if(key.equals(comboBox.getValue())) {
                          ArrayList <String> temp = new ArrayList <>();
                          for (int i = 0; i < values.size(); i++) {
                                if(!temp.contains(values.get(i))){
                                    temp.add(values.get(i));
                                }
                          }
                            lpr += temp;
                      }
                  }
                taListOfProducts.setText(lpr);
              } catch (FileNotFoundException e) {
                  e.printStackTrace();
              } catch (ParseException e) {
                  e.printStackTrace();
              }
          }
      });



      gridPane.add(bBack,0,0);
      gridPane.add(lChooseCash,0,1);
      gridPane.add(comboBox,0,2);
      gridPane.add(lTotNumBills,1,1);
      gridPane.add(lTotMoney,2,1);
      gridPane.add(taMoneyMaid,2,2);
      gridPane.add(taTotNumBills,1,2);
      gridPane.add(taListOfProducts, 2,3);
      gridPane.add(lListOfProds,1,3);
      gridPane.add(bCheck,0,3);
      gridPane.add(lDates,0,4);
      gridPane.add(taStartDate,1,4);
      gridPane.add(taEndDate,2,4);
      Scene scene = new Scene(gridPane, 600, 500);


      primaryStage.setTitle("Cashiers");
      primaryStage.setScene(scene);
      primaryStage.show();


   }

}
