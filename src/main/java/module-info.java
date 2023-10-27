module pl.merito.projectwsb {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.desktop;


    opens pl.merito.projectwsb to javafx.fxml;
    exports pl.merito.projectwsb;
}