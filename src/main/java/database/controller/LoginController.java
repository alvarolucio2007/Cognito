package database.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Button botaoLogin;

    @FXML private void login() {
        // TODO: implementar autenticação
    }

    @FXML private void irParaCadastro() throws Exception {
        Parent root = FXMLLoader.load(
            getClass().getResource("/fxml/Cadastro.fxml")
        );
        botaoLogin.getScene().setRoot(root);
    }
}