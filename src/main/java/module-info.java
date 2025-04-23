module org.launchandlearn {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens org.launchandlearn to javafx.fxml;
    exports org.launchandlearn;
}