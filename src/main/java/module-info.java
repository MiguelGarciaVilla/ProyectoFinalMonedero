module co.edu.uniquindio.poo.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jakarta.mail;
    requires javafx.graphics;

    exports co.edu.uniquindio.poo.proyectofinal.app;
    opens co.edu.uniquindio.poo.proyectofinal.viewController to javafx.fxml;

}