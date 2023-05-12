package com.teamlimonta.majorproject.datamodel;

import com.teamlimonta.majorproject.Projects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ProjectData {
    private static final ProjectData instance = new ProjectData();
    private static final String filename = "C:\\Users\\Roman\\Desktop\\MajorProject\\userFiles\\TempListOfProjects.txt";
    private ObservableList<Projects> projectsList;
    private final DateTimeFormatter formatter;



    public static ProjectData getInstance() {
        return instance;
    }

    private ProjectData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<Projects> getProjectList() {
        return projectsList;
    }

    public void addProject(Projects project) {
        projectsList.add(project);
    }

    public void deleteProject(Projects project){
        projectsList.remove(project);
    }

    public void loadProjectList() throws IOException {
        projectsList = FXCollections.observableArrayList();
        Path path = Paths.get(filename);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                String name = itemPieces[0];
                String details = itemPieces[1];
                String protagonist = itemPieces[2];
                String questionTool = itemPieces[3];
                String heartOfDarkness = itemPieces[4];
                String dateCreated = itemPieces[5];

                LocalDate date = LocalDate.parse(dateCreated, formatter);
                Projects project = new Projects(name, details, protagonist,questionTool,heartOfDarkness, date);
                projectsList.add(project);
            }

        }

    }

    public void saveProjectList() throws IOException {
        Path path = Paths.get(filename);

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Projects project : projectsList) {
                bw.write(String.format("%s\t%s\t%s\t%s\t%s\t%s",
                        project.getName(),
                        project.getDetails(),
                        project.getProtagonist(),
                        project.getQuestionTool(),
                        project.getHeartOfDarkness(),
                        project.getDateCreated().format(formatter)));
                bw.newLine();
            }

        }
    }


}
