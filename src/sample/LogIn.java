package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.Scanner;

public class LogIn {

    public LogIn() {
        start();
    }

        public void start() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("SuperMarket Software");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final RadioButton rbCashier = new RadioButton();
        final RadioButton rbEconomist = new RadioButton();
        final RadioButton rbAdmin = new RadioButton();
        rbCashier.setUserData("Cashier");
        rbEconomist.setUserData("Economist");
        rbAdmin.setUserData("Admin");
        final ToggleGroup tg = new ToggleGroup();
        rbCashier.setToggleGroup(tg);
        rbEconomist.setToggleGroup(tg);
        rbAdmin.setToggleGroup(tg);
        HBox hbAccountType = new HBox();
        hbAccountType.getChildren().addAll(new Label("Login as: "), new Label("Cashier"),
                rbCashier, new Label("Economist"), rbEconomist,
                new Label("Administrator"), rbAdmin);
        grid.add(hbAccountType, 0, 3);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean grantAccess = false;


                String username = userTextField.getText();
                String password = pwBox.getText();

                File f = new File("Users.txt");
                try {
                    Scanner read = new Scanner(f);

                    //loop through every line in the file and check against the user name & password
                    while (read.hasNextLine()) {
                        Scanner check = new Scanner(read.nextLine());
                        while (check.hasNext()) {
                            if (check.next().equals(username)) { // if the same user name
                                if (check.next().equals(password)) { // check password
                                    String temp = check.next();
                                    if (temp.equals(tg.getSelectedToggle().getUserData()) || temp.equals("Admin"))
                                        grantAccess = true; // if also same, change boolean to true
                                    break; // and break the while-loop
                                }
                            }
                        }

                    }
                    if (grantAccess) {
                        if (tg.getSelectedToggle().getUserData() == "Cashier") {
                            CashierWindow cashier = new CashierWindow(userTextField.getText());
                            System.out.println();
                            primaryStage.close();
                        }

                        if (tg.getSelectedToggle().getUserData() == "Economist") {
                            EconomistWindow eco = new EconomistWindow(userTextField.getText());
                            primaryStage.close();
                        }

                        if (tg.getSelectedToggle().getUserData() == "Admin") {
                            AdminWindow adm = new AdminWindow(userTextField.getText());
                            primaryStage.close();
                        }

                    } else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("User not found");

                    }

                } catch (java.io.IOException fe) {

                    fe.printStackTrace();
                }

            }

        });

        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}