package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.model.Banco;
import co.edu.uniquindio.poo.proyectofinal.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;

public class LoginController {

    @FXML private TextField fieldUser;
    @FXML private PasswordField fieldPass;
    @FXML private Label labelError;

    @FXML
    private void handleLogin(ActionEvent event) {
        String user = fieldUser.getText();
        String pass = fieldPass.getText();

        // 1. Autenticar usando el Banco (Singleton)
        Cliente clienteLogueado = Banco.getInstancia().autenticar(user, pass);

        if (clienteLogueado != null) {
            ingresarAlSistema(event, clienteLogueado);
        } else {
            labelError.setText("Usuario o contraseña incorrectos.");
        }
    }

    private void ingresarAlSistema(ActionEvent event, Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyectofinal/monedero.fxml"));
            Parent root = loader.load();
            MonederoControlador controller = loader.getController();

            controller.initData(cliente);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            labelError.setText("Error al cargar la vista.");
        }
    }
}