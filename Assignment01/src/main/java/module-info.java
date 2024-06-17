module com.aone.assignment01 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.aone.assignment01 to javafx.fxml;
    exports com.aone.assignment01;
}