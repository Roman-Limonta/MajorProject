package com.teamlimonta.majorproject;

import com.teamlimonta.majorproject.datamodel.ProjectData;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainController {

    public ListView<Projects> projectsView = new ListView<>();
    public TextArea projectDetailsTextArea;
    public Label bottomLineLabel;
    public BorderPane mainBorderPane;
    private Stack<String> nameList = new Stack<>();

    private ContextMenu listContextMenu;
    private final File file = new File("C:\\Users\\Roman\\Desktop\\MajorProject\\userFiles\\ListofNames.txt");


    public void initialize() {

        nameList = loadStackList(file);

        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> {
            Projects project = projectsView.getSelectionModel().getSelectedItem();
            deleteProject(project);
        });
        listContextMenu.getItems().addAll(deleteMenuItem);

        projectsView.getSelectionModel().selectedItemProperty().addListener((observableValue, projects, t1) -> {
            if (t1 != null) {
                getFullDetails();
            }
        });
        projectsView.setItems(ProjectData.getInstance().getProjectList());

        projectsView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        projectsView.getSelectionModel().selectFirst();

        projectsView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Projects> call(ListView<Projects> projectsListView) {
                ListCell<Projects> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Projects project, boolean b) {
                        super.updateItem(project, b);
                        if (b) {
                            setText(null);
                        } else {
                            setText(project.getName());
                            if (project.getDateCreated().equals(LocalDate.now())) {
                                setTextFill(Color.GREEN);
                            }
                        }
                    }
                };

                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }
                );

                return cell;
            }
        });

    }

    public void showNewProjectDialog(ActionEvent event) {
        System.out.println("new Project Button");
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Concept");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newProjectDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        Button button = new Button();

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            Projects newProject = controller.processResults();
            //projectsView.getItems().setAll(ProjectData.getInstance().getProjectsItem());
            projectsView.getSelectionModel().select(newProject);
        }
    }

    public void deleteProject(Projects project) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Project?");
        alert.setHeaderText("Delete Project: " + project.getName());
        alert.setContentText("Are you sure you want to delete? Press OK to confirm, or cancel to back out");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            ProjectData.getInstance().deleteProject(project);
        }
    }

    public static void saveStackList(Stack<String> stack, File file) {
        String tmp = stack.toString();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (pw != null) {
            pw.write(tmp);
            pw.close();
        }
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
            tempStack.add(scanner.nextLine().replaceAll("[^a-zA-Z0-9 ,]", ""));
        }

        return tempStack;
    }

    public void getFullDetails() {
        Projects project = projectsView.getSelectionModel().getSelectedItem();

        String sb = "The Main Concept: \n" + project.getDetails() + "\n" +
                "Name of Protagonist: \n" + project.getProtagonist() + "\n" +
                "Question Tool: \n" + project.getQuestionTool() + "\n" +
                "Heart of Darkness Scene: \n" + project.getHeartOfDarkness();
        projectDetailsTextArea.setText(sb);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        bottomLineLabel.setText(df.format(project.getDateCreated()));
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
        Projects project = projectsView.getSelectionModel().getSelectedItem();
        if (project != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteProject(project);
            }
        }
    }

    public void addCharacterName(ActionEvent event) {
        TextInputDialog popUP = new TextInputDialog();
        popUP.setTitle("Add Name");
        popUP.setHeaderText("Enter Name:");
        popUP.setContentText("Name:");

        Optional<String> result = popUP.showAndWait();

        if (result.isPresent()) {
            if (popUP.getResult().isEmpty()) {
                popUP.close();
                System.out.println("No name entered");
            } else {
                System.out.println(result.get());
                nameList.add(result.get().trim());
                saveStackList(nameList, file);
            }
        } else {
            popUP.close();
        }
        System.out.println(nameList);

    }


    public void viewCharacterNameList(ActionEvent event) throws IOException {

        Stack<String> temp;
        temp = (Stack<String>) nameList.clone();
        ArrayList<String> nameListArray = new ArrayList<>();


        while (!temp.isEmpty()) {
            int i = 0;
            nameListArray.add(temp.pop());

        }


//
//        sorting(nameListArray, 0, nameListArray.size()-1);
//        System.out.println(nameListArray);
//
////        System.out.println(Arrays.toString(nameListArray));
////
////        sorting(nameListArray,0,nameListArray.length-1);
////        System.out.println(Arrays.toString(nameListArray));
//
//
////        String[] trimmedArray = new String[names.length];
////        for (int i = 0; i < names.length; i++)
////            trimmedArray[i] = names[i].trim();

//        // list that holds strings of a file
//        List<String> listOfStrings = new ArrayList<String>();
//
//        // load data from file
//        BufferedReader bf = new BufferedReader(new FileReader(file));
//
//        // read entire line as string
//        String line = bf.readLine();
//
//        // checking for end of file
//        while (line != null) {
//            listOfStrings.add(line);
//            line = bf.readLine();
//        }
//
//        // closing bufferreader object
//        bf.close();
//
//        // storing the data in arraylist to array
//        String[] array = listOfStrings.toArray(new String[0]);
//
//        // printing each line of file
//        // which is stored in array
//        for (String str : array) {
//            System.out.println(str);
//    }
//        sorting(array,0, array.length-1);
//        System.out.println(Arrays.toString(array));
    }

//    public static void qsort(ArrayList<String> mylist, int left, int right) {
//        //base case
//
//        if (left >= right) {
//        } else {
//            String pivot = mylist.get(left);
//            int i = left + 1;
//            String tmp;
//            //partition array
//            for (int j = left + 1; j <= right; j++) {
//                if (pivot.charAt(0) > mylist.get(j).charAt(0)) {
//                    tmp = mylist.get(j);
//                    mylist.set(j, mylist.get(i));
//                    mylist.set(i, tmp);
//
//                    i++;
//                }
//            }
//            //put pivot in right position
//            mylist.set(left, mylist.get(i - 1));
//            mylist.set(i - 1, pivot);
//
//            //call qsort on right and left sides of pivot
//            qsort(mylist, left, i - 2);
//            qsort(mylist, i, right);
//        }
//
//    }

    public void backToLoginWindow(ActionEvent event) throws IOException {
        String fxml = "login-window.fxml";
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
