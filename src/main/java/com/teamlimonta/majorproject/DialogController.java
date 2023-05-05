package com.teamlimonta.majorproject;

import com.teamlimonta.majorproject.datamodel.ProjectData;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController {

    @FXML
    public TextArea questionToolTextArea;
    @FXML
    public TextArea heartDarknessToolTextArea;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea detailTextArea;
    @FXML
    private TextField protagonistTextField;

    Projects processResults() {
        String projectName = nameTextField.getText().trim();
        String details = detailTextArea.getText().trim();
        String protagonist = protagonistTextField.getText().trim();
        String questionTool = questionToolTextArea.getText().trim();
        String heartOfDarkness = heartDarknessToolTextArea.getText().trim();

        Projects newProject = new Projects(projectName, details, protagonist, questionTool, heartOfDarkness);
        ProjectData.getInstance().addProject(newProject);

        System.out.println("Test");
        return newProject;
    }
}
