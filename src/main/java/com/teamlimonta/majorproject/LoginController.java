package com.teamlimonta.majorproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginController {
    public Button loginButton;
    public Button registerButton;
    public TextField userNameTextField;
    public PasswordField passwordTextField;
    public Label resultMessage;
    public Button enterButton;
    public Button cancelButton;
    public TextField loginNameRegTextField;
    public TextField passwordRegTextField;
    public Map<String, String> userLogin = HashMapFromTextFile();
    public static File loginInfofile = new File("C:\\Users\\Roman\\Desktop\\MajorProject\\userFiles\\UserNameAndPassword.txt");
    public Button adminButton;
    public Label regLabel;


    public void onLoginClicked(ActionEvent event) throws Exception {
        System.out.println("LoginClicked");
        if (!userLogin.containsKey(userNameTextField.getText())) {
            resultMessage.setText("Login Name Not Found!");
        }

        //This is to check if the username and password are registered!
        if (userLogin.containsKey(userNameTextField.getText())) {
            if (userLogin.get(userNameTextField.getText()).equals(passwordTextField.getText())) {
                resultMessage.setText("Successful Login");

                stageSwitcher("main-window.fxml", event);

            } else {
                resultMessage.setText("Failed Login");
            }
        }
    }

    public void onRegisterClicked(ActionEvent event) throws IOException {
        System.out.println("Register Clicked");
        stageSwitcher("register-window.fxml", event);
    }

    public void onAdminClicked(ActionEvent event) throws IOException {
        System.out.println("Admin Clicked");
        if (!userLogin.containsKey(userNameTextField.getText())) {
            resultMessage.setText("Login Name Not Found!");
        }

        //This is to check if the username and password are registered!
        if (userLogin.containsKey(userNameTextField.getText()) && userNameTextField.getText().matches("admin")) {
            if (userLogin.get(userNameTextField.getText()).equals(passwordTextField.getText()) && passwordTextField.getText().matches("admin")) {
                resultMessage.setText("Successful Login");

                stageSwitcher("admin-window.fxml", event);
            } else {
                resultMessage.setText("Failed Login");
            }
        }


    }

    public void onEnterButtonClicked(ActionEvent event) throws IOException {

        if (userLogin.containsKey(loginNameRegTextField.getText())) {
            System.out.println(loginNameRegTextField.getText() + " already exist!");
            regLabel.setText(loginNameRegTextField.getText() + " already exist.");
        } else {
            addUserAndPassword(loginNameRegTextField.getText(), passwordRegTextField.getText(), userLogin);

            try (BufferedWriter bf = new BufferedWriter(new FileWriter(loginInfofile))) {

                for (Map.Entry<String, String> entry : userLogin.entrySet()) {
                    bf.write(entry.getKey() + ":" + entry.getValue());
                    bf.newLine();
                }
                bf.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stageSwitcher("login-window.fxml", event);
        }
    }

    public void onCancelButtonClicked(ActionEvent event) throws IOException {
        stageSwitcher("login-window.fxml", event);
    }

    public void stageSwitcher(String fxml, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static Map<String, String> HashMapFromTextFile() {


        Map<String, String> map = new HashMap<>();


        try (BufferedReader br = new BufferedReader(new FileReader(loginInfofile))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(":");
                String name = parts[0].trim();
                String number = parts[1].trim();

                if (!name.equals("") && !number.equals(""))
                    map.put(name, number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;


    }

    public static void addUserAndPassword(String user, String password, Map<String, String> map) {
        map.put(user, password);
        System.out.println("input added to map");
        System.out.println("New user has been Registered!");
        //Account temp = new Account(user, password);
    }

}