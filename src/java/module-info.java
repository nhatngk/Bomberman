module com.bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.bomberman to javafx.fxml;
    exports com.bomberman;
    exports com.bomberman.control;
    opens com.bomberman.control to javafx.fxml;
}