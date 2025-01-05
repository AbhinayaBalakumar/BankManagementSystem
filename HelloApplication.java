package com.example.bankmanagementsystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HelloApplication extends Application {
    ArrayList<String> accounts = new ArrayList<>();
    Bank bank = new Bank();
    Customer a = null;

    @Override
    public void start(Stage primaryStage) {
        //Put everything in file and put it into an arraylist at the start of the launch
        try {
            getInput();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        //First Screen
        final Label label = new Label("Welcome");
        Label label2 = new Label("Do you want to login or sign up?");
        Button login = new Button("Login");
        Button signup = new Button("Sign up");
        Button next = new Button("Next");
        //The rest of the buttons are for the main menu
        Button logoff = new Button("Go");
        Button checkA = new Button("Go");
        Button addB = new Button("Go");
        Button addC = new Button("Go");
        Button addS = new Button("Go");
        Button makeP = new Button("Go");
        Button transF = new Button("Go");
        Button makeB = new Button("Go");
        //Back mean go back into main menu
        Button back = new Button("Back");
        //The rest are for the buttons on the main menu, so if a certain button is pressed then a certain screen will show
        login.setOnAction(e -> primaryStage.setScene(logIn(next)));
        signup.setOnAction(e -> primaryStage.setScene(signUp(next)));
        next.setOnAction(e -> primaryStage.setScene(mainMenu(logoff, checkA, addB, addC, addS, makeP, transF, makeB)));
        logoff.setOnAction(e -> {
            getOutput();
            primaryStage.close();
        });
        checkA.setOnAction(e -> {
            Label l = new Label(a.everything());
            VBox v = new VBox();
            v.getChildren().addAll(l, back);
            Scene scene2 = new Scene(v, 200, 200);
            primaryStage.setScene(scene2);
        });
        addB.setOnAction(e -> primaryStage.setScene(addBalance(back)));
        addC.setOnAction(e -> primaryStage.setScene(addChecking(back)));
        addS.setOnAction(e -> primaryStage.setScene(addSavings(back)));
        makeP.setOnAction(e -> primaryStage.setScene(makePayments(back)));
        transF.setOnAction(e -> primaryStage.setScene(tranferFunds(back)));
        makeB.setOnAction(e -> primaryStage.setScene(makeBudget(back)));
        back.setOnAction(e -> primaryStage.setScene(mainMenu(logoff, checkA, addB, addC, addS, makeP, transF, makeB)));
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(label, label2, login, signup);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //if true they can go to the main menu
    boolean authorize = false;

    //verify if card # and password are correct to proceed.
    public void verify(TextField card2, TextField pass2) {
        boolean theSame = false;
        String card = card2.getText();
        String pass = pass2.getText();
        for (int i = 0; i < accounts.size(); i++) {
            //for every card and password
            String fileUserName = accounts.get(i);
            fileUserName = fileUserName.substring(1, fileUserName.length() - 1);
            String[] list = fileUserName.split(", ");
            //check to see if they are the same
            if (list[0].equals(card) && list[1].equals(pass)) {
                theSame = true;
                double b = Double.parseDouble(list[2]);
                double c = Double.parseDouble(list[3]);
                double s = Double.parseDouble(list[4]);
                double budget = Double.parseDouble(list[5]);
                a = new Customer(card, pass, b, c, s, budget);
                //Finish loop
                i = accounts.size();
            }
        }
        //else try again
        authorize = theSame;


    }

    //Log In Scene
    public Scene logIn(Button next) {
        Label label = new Label("Log In");
        Label card = new Label("Card Number: ");
        Label pass = new Label("Password:");
        TextField c = new TextField();
        TextField p = new TextField();
        Button confirm = new Button("Confirm");
        //Boxs are used to format the screen
        VBox vbox = new VBox();
        VBox vbox2 = new VBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        VBox vbox3 = new VBox();
        Scene scene = new Scene(vbox3, 300, 300);
        vbox.getChildren().addAll(card, pass);
        vbox2.getChildren().addAll(c, p);
        hbox2.getChildren().addAll(vbox, vbox2);
        hbox3.getChildren().addAll(confirm, next);
        vbox3.getChildren().addAll(label, hbox2, hbox3);
        //Back must be disabled because you would need to confirm information before going back
        next.setDisable(true);

        //If the confirm button is pressed then it will check if they can go to next screen else you will need to try again
        confirm.setOnAction(e -> {
            verify(c, p);
            if (authorize) {
                next.setDisable(false);
                confirm.setDisable(true);
            }
            //Confirm must be disabled because we don't want to break the code

        });
        return scene;
    }

    //Sign Up Scene
    public Scene signUp(Button next) {
        Label l = new Label("Sign Up");
        Label label = new Label();
        Label card = new Label();
        TextField pass = new TextField();
        TextField con = new TextField();
        TextField bal = new TextField();
        TextField check = new TextField();
        TextField sav = new TextField();
        card.setText("Your card number is: " + bank.getCard());
        Label password = new Label("Password:");
        Label confirm = new Label("Confirm: ");
        Label balance = new Label("Balance: ");
        Label checking = new Label("Checking:");
        Label savings = new Label("Saving:  ");
        //Need boxes to format the screen
        HBox vbox1 = new HBox();
        HBox vbox2 = new HBox();
        HBox vbox3 = new HBox();
        HBox vbox4 = new HBox();
        HBox vbox5 = new HBox();
        Button confirmB = new Button("Confirm");
        vbox1.getChildren().addAll(password, pass);
        vbox2.getChildren().addAll(confirm, con);
        vbox3.getChildren().addAll(balance, bal);
        vbox4.getChildren().addAll(checking, check);
        vbox5.getChildren().addAll(savings, sav);
        //Must be disabled so info can be counted before going forward
        next.setDisable(true);
        confirmB.setOnAction(e1 -> {
            //If the password == confirmed password then the following info will be counted
            if (pass.getText().equals(con.getText())) {
                double b = Double.parseDouble(bal.getText());
                int c = Integer.parseInt(check.getText());
                int s = Integer.parseInt(sav.getText());
                a = new Customer(bank.getCard(), pass.getText(), b, c, s, 0);
                accounts.add(a.toString());
                next.setDisable(false);
                confirmB.setDisable(true);
            } else {
                label.setText("Please try again.");
            }

        });
        HBox hbox1 = new HBox();
        VBox fin = new VBox();
        hbox1.getChildren().addAll(confirmB, next);
        fin.getChildren().addAll(l, label, card, vbox1, vbox2, vbox3, vbox4, vbox5, hbox1);

        return new Scene(fin, 500, 500);


    }

    //Main menu Scene
    public Scene mainMenu(Button button, Button button2, Button button3, Button button4, Button button5, Button button6, Button button7, Button button8) {
        //Have all related scenes and buttons show on the main menu
        Label l = new Label("Log Off:                 ");
        Label ac = new Label("Check Accounts:   ");
        Label b = new Label("Add to Balance:    ");
        Label c = new Label("Add to Checking:  ");
        Label s = new Label("Add to Savings:     ");
        Label p = new Label("Make Payments:    ");
        Label t = new Label("Transfer Funds:      ");
        Label mb = new Label("Make a Budget:     ");
        Label label = new Label();
        Label title = new Label("Main Menu");
        VBox vbox1 = new VBox();
        HBox hbox1 = new HBox(l, button);
        HBox hbox2 = new HBox(ac, button2);
        HBox hbox3 = new HBox(b, button3);
        HBox hbox4 = new HBox(c, button4);
        HBox hbox5 = new HBox(s, button5);
        HBox hbox6 = new HBox(p, button6);
        HBox hbox7 = new HBox(t, button7);
        HBox hbox8 = new HBox(mb, button8);
        VBox vbox2 = new VBox(hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8);
        vbox1.getChildren().addAll(title, vbox2, label);
        return new Scene(vbox1, 500, 500);
    }

    //Add to Balance Scene
    public Scene addBalance(Button back) {
        Label label = new Label("Add to Balance");
        Label label2 = new Label();
        TextField text = new TextField();
        Button confirm = new Button("Confirm");
        //Confirm before going back
        back.setDisable(true);
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        vbox.getChildren().add(label);
        confirm.setOnAction(e -> {
            //Get new balance and input it into the customer class
            a.addBalance(Integer.parseInt(text.getText()));
            label2.setText("Your new balance is: " + a.checkBalance());
            vbox.getChildren().add(label2);
            //Confirm disable to not get create more errors
            back.setDisable(false);
            confirm.setDisable(true);
        });
        hbox.getChildren().addAll(confirm, back);
        vbox.getChildren().addAll(text, hbox);
        return new Scene(vbox, 300, 300);
    }

    //Add to Checking Scene
    public Scene addChecking(Button back) {
        Label label = new Label("Add to Checking");
        Label label2 = new Label();
        TextField text = new TextField();
        Button confirm = new Button("Confirm");
        //Confirm before going back
        back.setDisable(true);
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        vbox.getChildren().add(label);
        confirm.setOnAction(e -> {
            //Get new balance and input it into the customer class
            a.addChecking(Integer.parseInt(text.getText()));
            label2.setText("Your new balance is: " + a.checkChecking());
            vbox.getChildren().add(label2);
            //Confirm disable to not get create more errors
            back.setDisable(false);
            confirm.setDisable(true);
        });
        hbox.getChildren().addAll(confirm, back);
        vbox.getChildren().addAll(text, hbox);
        return new Scene(vbox, 300, 300);
    }

    //Add to Savings
    public Scene addSavings(Button back) {
        Label label = new Label("Add to Savings");
        Label label2 = new Label();
        TextField text = new TextField();
        Button confirm = new Button("Confirm");
        //Confirm before going back
        back.setDisable(true);
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        vbox.getChildren().add(label);
        confirm.setOnAction(e -> {
            //Confirm disable to not get create more errors
            a.addSavings(Integer.parseInt(text.getText()));
            label2.setText("Your new balance is: " + a.checkSavings());
            vbox.getChildren().add(label2);
            //Confirm disable to not get create more errors
            back.setDisable(false);
            confirm.setDisable(true);
        });
        hbox.getChildren().addAll(confirm, back);
        vbox.getChildren().addAll(text, hbox);
        return new Scene(vbox, 300, 300);
    }

    //Make payments
    public Scene makePayments(Button back) {
        Label label = new Label("Make payments");
        Label label2 = new Label();
        TextField text = new TextField();
        CheckBox ck1 = new CheckBox("Savings");
        CheckBox ck2 = new CheckBox("Checking");
        Button confirm = new Button("Confirm");
        Button end = new Button("End");
        back.setDisable(true);
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        vbox.getChildren().addAll(label, label2);
        //Make multiple payments using different accounts and confirm it
        confirm.setOnAction(e -> {
            // if savings is selected
            if (ck1.isSelected() && !ck2.isSelected()) {
                double account = a.checkSavings();
                //Checks if checking has enough money and that checking has more than 0
                if (account > 0 && Integer.parseInt(text.getText()) <= account) {
                    a.subSavings(a.makePayments(account, Integer.parseInt(text.getText())));
                    label2.setText("Confirmed");
                }
                //Else say that they can't pay that much
                else {
                    label2.setText("You can't pay that much!");
                }
            }
            //if checking is selected
            else if (ck2.isSelected() && !ck1.isSelected()) {
                double account = a.checkChecking();
                //Checks if checking has enough money and that checking has more than 0
                if (account > 0 && Integer.parseInt(text.getText()) <= account) {
                    a.subChecking(a.makePayments(account, Integer.parseInt(text.getText())));
                    label2.setText("Confirmed");
                }
                //Else say that they can't pay that much
                else {
                    label2.setText("You can't pay that much!");
                }
            }
            //else say select one box
            else {
                label2.setText("Please select only one box");
            }
            //clears boxes
            ck1.setDisable(false);
            ck2.setDisable(false);
            ck1.setSelected(false);
            ck2.setSelected(false);
        });
        //Disable confirm button to end payments
        end.setOnAction(e -> {
            confirm.setDisable(true);
            end.setDisable(true);
            back.setDisable(false);
        });
        hbox.getChildren().addAll(confirm, end, back);
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(ck1, ck2);
        vbox.getChildren().addAll(text, hbox2, hbox);
        return new Scene(vbox, 300, 300);
    }

    //Transfer Funds Scene
    public Scene tranferFunds(Button back) {
        Label label = new Label("Transfer Funds");
        Label label2 = new Label();
        TextField text = new TextField();
        Button confirm = new Button("Confirm");
        back.setDisable(true);
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        vbox.getChildren().add(label);
        confirm.setOnAction(e -> {
            //Only transfers up to $1000, else it won't transfer and the balance account must have the money required
            if (Integer.parseInt(text.getText()) < 1001 && Integer.parseInt(text.getText()) <= a.checkBalance()) {
                a.subBalance(a.transferFunds(a.checkBalance(), Integer.parseInt(text.getText())));
                label2.setText("Your new balance is: " + a.checkBalance());
                //Disable confirm to not break code
                back.setDisable(false);
                confirm.setDisable(true);
            } else {
                label2.setText("You can only transfer amounts of up to a $1000 and when your balance has enough money");

            }
        });
        hbox.getChildren().addAll(confirm, back);
        vbox.getChildren().addAll(text, hbox,label2);
        return new Scene(vbox, 300, 300);
    }

    //Make a budget Scene
    public Scene makeBudget(Button back) {
        Label label = new Label("Make a Budget");
        Label label2 = new Label("Please enter monthly income and expense without current payments");
        Label label3 = new Label();
        Label i = new Label("Monthly Income: ");
        Label e = new Label("Monthly Expense:");
        TextField in = new TextField();
        TextField ex = new TextField();
        Button confirm = new Button("Confirm");
        //Boxes are needed to format the screen
        VBox vbox = new VBox();
        VBox l = new VBox();
        HBox hbox = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox(confirm, back);
        VBox fin = new VBox(l, vbox, hbox3);
        Scene scene = new Scene(fin, 500, 500);
        hbox.getChildren().addAll(i, in);
        l.getChildren().addAll(label, label2, label3);
        hbox2.getChildren().addAll(e, ex);
        vbox.getChildren().addAll(hbox, hbox2);
        //Disable so user won't accidentally press it
        back.setDisable(true);
        confirm.setOnAction(e1 -> {
            //Get the budget
            double budget = a.makeBudget(Double.parseDouble(in.getText()), Double.parseDouble(ex.getText()));
            //Below gives advice
            if (budget < 0) {
                label3.setText("Spend more wisely, you will lose: " + Math.abs(budget));
            } else {
                label3.setText("Your budget is: " + budget);
            }
            //Disable confirm so we don't accidentally break the code
            back.setDisable(false);
            confirm.setDisable(true);
        });
        return scene;
    }

    //Get file info and put it into the arraylist
    public void getInput() throws FileNotFoundException {
        Scanner fileInput = new Scanner(new File("C:\\Users\\abhin\\IdeaProjects\\bankManagementSystem\\src\\main\\java\\loginFile.txt"));
        while (fileInput.hasNextLine()) {
            accounts.add(fileInput.nextLine());
        }
        fileInput.close();
    }

    //Gets accounts arraylist and outputs it into the file
    public void getOutput() {
        try {
            for (int i = 0; i < accounts.size(); i++) {
                String account = accounts.get(i);
                account = account.substring(1, account.length() - 1);
                String[] l = account.split(", ");
                if (a.getUser().equals(l[0])) {
                    accounts.add(a.toString());
                    accounts.remove(i);
                    i = accounts.size();
                }
            }
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\abhin\\IdeaProjects\\bankManagementSystem\\src\\main\\java\\loginFile.txt")));
            for (String each : accounts) {
                pw.println(each);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("This is broken!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

