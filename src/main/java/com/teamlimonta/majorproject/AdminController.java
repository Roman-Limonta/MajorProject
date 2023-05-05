package com.teamlimonta.majorproject;

import com.teamlimonta.majorproject.datamodel.ProjectData;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class AdminController {
    public ListView<String> projectsView = new ListView<>();
    public File file = new File("userFiles/UserNameAndPassword.txt");
    public Label resultLabel;
    Stack<String> userNameAndPassword = loadStackList(file);
    public static File loginInfofile = new File("C:\\Users\\Roman\\Desktop\\MajorProject\\userFiles\\UserNameAndPassword.txt");
    public Map<String, String> userLogin = HashMapFromTextFile();
    public static BST<String> bst = new BST<>();
    public String[] arrayOfUsers = new String[bst.getSize()];



    public String[] temp;
    public int length;




    public void initialize() {

        for (Map.Entry<String, String> entry : userLogin.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
            bst.insert(entry.getKey());
        }

        List<String> itemsSchool = new ArrayList<>();

        try {
            FileInputStream fstream_school = new FileInputStream("C:\\Users\\Roman\\Desktop\\MajorProject\\userFiles\\UserNameAndPassword.txt");
            DataInputStream data_input = new DataInputStream(fstream_school);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
            String str_line;

            while ((str_line = buffer.readLine()) != null)
            {
                str_line = str_line.trim();
                if ((str_line.length()!=0))
                {
                    itemsSchool.add(str_line);
                }
            }


        }catch (IOException ex) {
            ex.printStackTrace();
        }

        arrayOfUsers = itemsSchool.toArray(new String[0]);
        sort(arrayOfUsers);
        itemsSchool = Arrays.stream(arrayOfUsers).toList();
        projectsView.getItems().addAll(itemsSchool);

    }

    public void backToLoginWindow(ActionEvent event) throws IOException {
        String fxml = "login-window.fxml";
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchUserName(ActionEvent event) {
        System.out.println(userNameAndPassword.toString());
        System.out.println("*********************");
        System.out.println(bst.getSize());

        TextInputDialog popUP = new TextInputDialog();
        popUP.setTitle("Search User Name");
        popUP.setHeaderText("Enter Name:");
        popUP.setContentText("Name:");

        Optional<String> result = popUP.showAndWait();

        if (result.isPresent()) {
            if (popUP.getResult().isEmpty()) {
                popUP.close();
                System.out.println("No name entered");
            } else {
                System.out.println(result.get());
                if (bst.search(result.get())) {
                    System.out.println(result.get() + " is NOT available");
                    resultLabel.setText(result.get() + " is NOT available");
                } else {
                    System.out.println(result.get() + " is available");
                    resultLabel.setText(result.get() + " is available");
                }
            }
        } else {
            popUP.close();
        }

    }

    public void deleteAccount(ActionEvent event) {

        projectsView.getSelectionModel().selectFirst();
        String userSelected = projectsView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete User?");
        alert.setHeaderText("Delete User: " + userSelected);
        alert.setContentText("Are you sure you want to delete?\n Press OK to confirm, or cancel to back out");
        Optional<ButtonType> result = alert.showAndWait();

        userSelected = userSelected.substring(userSelected.indexOf(":")+1);

        System.out.println(userSelected);

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            userLogin.remove(userSelected);
            initialize();
        }
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
    }

    public static Stack<String> loadStackList(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Stack<String> tempStack = new Stack<>();

        while (true) {
            assert scanner != null;
            if (!scanner.hasNextLine()) break;
            tempStack.add(scanner.nextLine());
        }

        return tempStack;
    }

    public static Map<String, String> HashMapFromTextFile() {
        Map<String, String> map = new HashMap<String, String>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(loginInfofile));

            String line = null;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(":");
                String name = parts[0].trim();
                String number = parts[1].trim();

                if (!name.equals("") && !number.equals(""))
                    map.put(name, number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ignored) {
                }
                ;
            }
        }

        return map;
    }

    public void sort(String[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        temp = array;
        length = array.length;
        quickSort(0, length - 1);
    }

    public void quickSort(int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;
        String pivot = temp[lowerIndex + (higherIndex - lowerIndex) / 2];

        while (i <= j) {
            while (temp[i].compareToIgnoreCase(pivot) < 0) {
                i++;
            }

            while (temp[j].compareToIgnoreCase(pivot) > 0) {
                j--;
            }

            if (i <= j) {
                swapNames(i, j);
                i++;
                j--;
            }
        }
        //call quickSort recursively
        if (lowerIndex < j) {
            quickSort(lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(i, higherIndex);
        }
    }

    public void swapNames(int i, int j) {
        String swap = temp[i];
        temp[i] = temp[j];
        temp[j] = swap;
    }
}

