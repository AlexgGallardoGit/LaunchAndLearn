module org.launchandlearn {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.launchandlearn to javafx.fxml;
    exports org.launchandlearn;
}