module com.teamlimonta.majorproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.teamlimonta.majorproject to javafx.fxml;
    exports com.teamlimonta.majorproject;
}